package vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AddFriendRequest implements Serializable {

    private String myselfName;

    private int FriendNum;

    private int myselfNum;

    public String getMyselfName() {
        return myselfName;
    }

    public void setMyselfName(String myselfName) {
        this.myselfName = myselfName;
    }

    public int getFriendNum() {
        return FriendNum;
    }

    public void setFriendNum(int friendNum) {
        FriendNum = friendNum;
    }

    public int getMyselfNum() {
        return myselfNum;
    }

    public void setMyselfNum(int myselfNum) {
        this.myselfNum = myselfNum;
    }
    
}
