package server.netty4;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import server.dao.TestMapper;
import server.util.Util;
import vo.ChatRoom;
import vo.Content;
import vo.Friend;
import vo.Friends;
import vo.RoomChild;

public class ChatServerHandler extends SimpleChannelInboundHandler<Content> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /** 用来保存所有的在线用户 */
    public static Map<Integer, Friends> onlineUser = new HashMap<Integer, Friends>();

    /** 用来保存所有的在线链接 */
    public static Map<Integer, Channel> onlineMap = new HashMap<Integer, Channel>();

    private TestMapper mapper;

    public ChatServerHandler(TestMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        channels.remove(ctx.channel());
        ctx.close();
    }

    @Override
    public boolean acceptInboundMessage(Object msg) throws Exception {
        return super.acceptInboundMessage(msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Friends) {
            Friends user = (Friends) msg;
            // 用户上线,将个人信息广播给其在线的好友
            if (user.getName() != null) {
                onlineUser.put(user.getChannelId(), user);
                List<Friend> friends = mapper.fetchFriends(user.getChannelId());
                if (!Util.isEmpty(friends)) {
                    for (Friend friend : friends) {
                        Friends friendOnline = ChatServerHandler.onlineUser.get(friend
                                .getFriendNum());
                        if (friendOnline != null) {
                            onlineMap.get(friendOnline.getChannelId()).writeAndFlush(user);
                        }
                    }
                }
                onlineMap.put(user.getChannelId(), ctx.channel());
            }
            // 用户下线,将个人信息广播给其他人
            else {
                onlineMap.remove(user.getChannelId());
                for (Entry<Integer, Channel> MapString : onlineMap.entrySet()) {
                    Channel value = MapString.getValue();
                    value.writeAndFlush(user);
                }
                onlineUser.remove(user.getChannelId());
            }
        } else if (msg instanceof Content) {
            Content content = (Content) msg;
            // 群发
            if (content.getReceiveId() == 0) {
                List<Integer> ids = content.getTargetIds();
                for (int i = 0; i < ids.size(); i++) {
                    if (onlineMap.get(ids.get(i)) != null) {
                        onlineMap.get(ids.get(i)).writeAndFlush(msg);
                    }
                }
            }
            // 私聊
            else {
                // 判断接受者是否在线
                Channel c = onlineMap.get(content.getReceiveId());
                if (c == null) {
                    mapper.saveOfflineContent(content);
                } else {
                    c.writeAndFlush(content);
                    mapper.saveHistoryContent(content);
                }
            }
        }
        // 创建聊天室请求
        else if (msg instanceof ChatRoom) {
            List<RoomChild> childs = ((ChatRoom) msg).getChildDatas();
            if (childs != null && childs.size() > 0) {
                for (RoomChild child : childs) {
                    if (child.getChannelId() != ((ChatRoom) msg).getSendId()) {
                        onlineMap.get(child.getChannelId()).writeAndFlush(msg);
                    }
                }
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Content msg) throws Exception {
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        channels.remove(ctx.channel());
        super.channelInactive(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }

}
