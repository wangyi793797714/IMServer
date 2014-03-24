package server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import vo.Content;
import vo.Friend;
import vo.Myself;

public interface TestMapper {
    public Myself getUserId(Myself me);

    public void register(Myself me);

    public int login(@Param("num") int num, @Param("pass") String pass);

    public Myself queryUserIsExist(@Param("num") int num, @Param("pass") String pass);

    public Myself queryUser(@Param("num") int num);

    public void addFriend(Friend friend);

    public List<Friend> fetchFriends(@Param("num") int num);

    public void saveOfflineContent(Content msg);
    
    public void saveHistoryContent(Content msg);
    
    public List<Content> queryOfflineMsgs(@Param("num") int num);

    public void deleteOfflineMsgs(@Param("num") int num);
}
