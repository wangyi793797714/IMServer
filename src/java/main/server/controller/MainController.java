package server.controller;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import server.dao.TestMapper;
import server.netty4.ChatServerHandler;
import server.util.Util;
import vo.AddFriendRequest;
import vo.AddFriendResponse;
import vo.Content;
import vo.Friend;
import vo.FriendBody;
import vo.Friends;
import vo.LoginSucsess;
import vo.Myself;
import vo.RoomChild;

@Controller
@RequestMapping("/api")
public class MainController {

    @Autowired
    TestMapper mapper;

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Myself> register(@RequestParam("u") String name,
            @RequestParam("p") String pass) {
        try {
            Myself regisUser = new Myself();
            regisUser.setChannelId(Util.RandomUserNum());
            regisUser.setName(name);
            regisUser.setPass(pass);
            mapper.register(regisUser);
            return new ResponseEntity<Myself>(regisUser, HttpStatus.OK);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<LoginSucsess> login(@RequestParam("n") int num, @RequestParam("p") String pass) {
        try {
            int result = mapper.login(num, pass);
            if (result == 1) {
                LoginSucsess success= new LoginSucsess();
                Myself self = mapper.queryUserIsExist(num, pass);
                success.setMyself(self);
                List<Content> offlineMsgs= mapper.queryOfflineMsgs(num);
                success.setOfflineMsgs(offlineMsgs);
                mapper.deleteOfflineMsgs(num);
                return new ResponseEntity<LoginSucsess>(success, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/addFriend", method = RequestMethod.POST)
    public ResponseEntity<?> addFriend(@RequestParam("fn") int friendNum,
            @RequestParam("un") int userNum) {
        try {
            Friend f = new Friend();
            f.setFriendNum(friendNum);
            f.setUserNum(userNum);
            f.setGroupId(0);
            mapper.addFriend(f);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/addFriendRequest", method = RequestMethod.GET)
    public ResponseEntity<?> addFriendRequest(@RequestParam("fn") int friendNum,
            @RequestParam("name") String myselfName, @RequestParam("mn") int myselfNum) {
        try {
            AddFriendRequest req = new AddFriendRequest();
            req.setMyselfName(myselfName);
            req.setFriendNum(friendNum);
            req.setMyselfNum(myselfNum);
            Channel channel = ChatServerHandler.onlineMap.get(friendNum);
            if (channel == null) {
                return new ResponseEntity<>("查找的好友不存在", HttpStatus.OK);
            } else {
                channel.writeAndFlush(req);
                return new ResponseEntity<>("请等待好友响应", HttpStatus.OK);
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/addFriendResponse", method = RequestMethod.GET)
    public void addFriendResponse(@RequestParam("rn") int respNum,
            @RequestParam("name") String respName, @RequestParam("tn") int targetNum) {
        // 不同意加友请求
        if (respNum == -1) {
            AddFriendResponse resp = new AddFriendResponse();
            resp.setAccept(false);
            Myself self = new Myself();
            self.setName(respName);
            resp.setRespFriend(self);
            ChatServerHandler.onlineMap.get(targetNum).writeAndFlush(resp);
        } else {
            // 发送给请求方：被请求的人的信息
            AddFriendResponse resp1 = new AddFriendResponse();
            resp1.setAccept(true);
            resp1.setRespFriend(mapper.queryUser(respNum));
            resp1.setResponseName(respName);
            ChatServerHandler.onlineMap.get(targetNum).writeAndFlush(resp1);

            // 发送给被请求方：发送请求方的信息给被请求的人
            AddFriendResponse resp2 = new AddFriendResponse();
            resp2.setAccept(true);
            resp2.setRespFriend(mapper.queryUser(targetNum));
            ChatServerHandler.onlineMap.get(respNum).writeAndFlush(resp2);

            Friend friend = new Friend();
            friend.setFriendNum(respNum);
            friend.setGroupId(0);
            friend.setUserNum(targetNum);
            mapper.addFriend(friend);

            Friend friend2 = new Friend();
            friend2.setFriendNum(targetNum);
            friend2.setGroupId(0);
            friend2.setUserNum(respNum);
            mapper.addFriend(friend2);
        }
    }

    /**
     * 
     * @desc:返回好友列表，并且标示出哪些是在线的
     * @author WY 创建时间 2014年3月20日 上午11:04:35
     * @param num
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/friend", method = RequestMethod.GET)
    public ResponseEntity<List<Myself>> getOnlineFriends(@RequestParam("n") int num) {
        try {
            // 1.获取所有的好友
            List<Friend> friends = mapper.fetchFriends(num);
            // 2.好友列表和在线列表取交集，交集是在线的好友,设置为在线
            List<Myself> onlineFriends = new ArrayList<Myself>();
            if (!Util.isEmpty(friends)) {
                for (Friend friend : friends) {
                    Friends friendOnline = ChatServerHandler.onlineUser.get(friend.getFriendNum());
                    Myself self = null;
                    if (friendOnline != null) {
                        self = new Myself();
                        self.setChannelId(friendOnline.getChannelId());
                        self.setName(friendOnline.getName());
                        self.setOnline(true);
                    } else {
                        self = mapper.queryUser(friend.getFriendNum());
                        self.setOnline(false);
                    }
                    onlineFriends.add(self);
                }
            }
            return new ResponseEntity<List<Myself>>(onlineFriends, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/addf2room", method = RequestMethod.POST)
    public ResponseEntity<?> addFriend2room(@RequestBody FriendBody fb) {
        try {
            for (RoomChild roomChild : fb.getRoom().getChildDatas()) {
                Channel channel = ChatServerHandler.onlineMap.get(roomChild.getChannelId());
                // 如果为空，说明已经下线
                if (channel != null) {
                    channel.writeAndFlush(fb);
                }
            }
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
