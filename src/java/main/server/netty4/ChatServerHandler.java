package server.netty4;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import server.dao.TestMapper;
import server.util.Util;
import vo.ChatRoom;
import vo.Content;
import vo.Friend;
import vo.Friends;
import vo.HeartbeatVo;
import vo.RoomChild;

public class ChatServerHandler extends SimpleChannelInboundHandler<Content> {

	public static ChannelGroup channels = new DefaultChannelGroup(
			GlobalEventExecutor.INSTANCE);

	/** 用来保存所有的在线用户 */
	public static ConcurrentHashMap<Integer, Friends> onlineUser = new ConcurrentHashMap<Integer, Friends>();

	/** 用来保存所有的在线链接 */
	public static ConcurrentHashMap<Integer, Channel> onlineConnectes = new ConcurrentHashMap<Integer, Channel>();

	/**chanel和用户chanelid的对应关系:key:channel().hashCode(),value:userChanelid*/
	private ConcurrentHashMap<Integer, Integer> ids = new ConcurrentHashMap<Integer, Integer>();
	
	private TestMapper mapper;

	public ChatServerHandler(TestMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
		channels.add(ctx.channel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		channels.remove(ctx.channel());
		ctx.close();
	}

	/** 心跳监测 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state().equals(IdleState.READER_IDLE)) {
				// 读超时时间:服务器一定时间内未接受到客户端消息
				// 客户端掉线处理
				System.out.println("READER_IDLE 读超时");
				ctx.disconnect();
			} else if (event.state().equals(IdleState.WRITER_IDLE)) {
				// 写超时时间:服务器一定时间内向客户端发送消息
				System.out.println("WRITER_IDLE 写超时");
			} else if (event.state().equals(IdleState.ALL_IDLE)) {
				// 全体超时时间:同时没有读写的时间
				System.out.println("ALL_IDLE 总超时");

			}
		}

		super.userEventTriggered(ctx, evt);
	}

	@Override
	public boolean acceptInboundMessage(Object msg) throws Exception {
		return super.acceptInboundMessage(msg);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (msg instanceof Friends) {
			Friends user = (Friends) msg;
			// 用户上线,将个人信息广播给其在线的好友
			if (user.getName() != null) {
				onlineUser.put(user.getChannelId(), user);
				List<Friend> friends = mapper.fetchFriends(user.getChannelId());
				if (!Util.isEmpty(friends)) {
					for (Friend friend : friends) {
						Friends friendOnline = ChatServerHandler.onlineUser
								.get(friend.getFriendNum());
						if (friendOnline != null) {
							onlineConnectes.get(friendOnline.getChannelId())
									.writeAndFlush(user);
						}
					}
				}
				onlineConnectes.put(user.getChannelId(), ctx.channel());
				ids.put(ctx.channel().hashCode(), user.getChannelId());
			}
			// 用户下线,将个人信息广播给其他人
			else {
				onlineConnectes.remove(user.getChannelId());
				for (Entry<Integer, Channel> MapString : onlineConnectes.entrySet()) {
					Channel value = MapString.getValue();
					value.writeAndFlush(user);
				}
				onlineUser.remove(user.getChannelId());
			}
		} else if (msg instanceof Content) {
			Content content = (Content) msg;
			// 群发
			if (content.getGrouppTag() != 0) {
				List<Integer> ids = content.getTargetIds();
				for (int i = 0; i < ids.size(); i++) {
					// 指定的组员在线
					if (onlineConnectes.get(ids.get(i)) != null) {
						onlineConnectes.get(ids.get(i)).writeAndFlush(content);
					} else {
						content.setReceiveId(ids.get(i));
						mapper.saveOfflineContent(content);
						content.setReceiveId(0);
					}
				}
			}
			// 私聊
			else {
				// 判断接受者是否在线
				Channel c = onlineConnectes.get(content.getReceiveId());
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
						onlineConnectes.get(child.getChannelId()).writeAndFlush(msg);
					}
				}
			}
		} else if (msg instanceof HeartbeatVo) {
			HeartbeatVo vo = (HeartbeatVo) msg;
			System.out.println("收到了心跳信息:" + vo.getMsg());
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Content msg)
			throws Exception {
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		channels.remove(ctx.channel());
		ctx.close();
		disConnect(ctx.channel());
		super.channelInactive(ctx);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
	}
	
	/**
	 * 
	 * @Des: 掉线处理，通知其好友，该人已经下线
	 * @param    
	 * @return void
	 */
	private void disConnect(Channel channel){
		if(ids.get(channel.hashCode())!=null){
			int userChannelId=ids.get(channel.hashCode());
			onlineConnectes.remove(onlineConnectes);
			Friends user = new Friends();
			//客户端根据返回的用户名判断是上线（！=null）还是下线（==null）
			user.setName(null);
			user.setChannelId(userChannelId);
			for (Entry<Integer, Channel> MapString : onlineConnectes.entrySet()) {
				Channel value = MapString.getValue();
				value.writeAndFlush(user);
			}
			onlineUser.remove(userChannelId);
		}
	}

}
