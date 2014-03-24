package vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AddFriendRequest implements Serializable {

    /**申请人的名字*/
    private String myselfName;

    /**被申请者的编号*/
    private int friendNum;

    /**申请人的编号*/
    private int myselfNum;

    public String getMyselfName() {
        return myselfName;
    }

    public void setMyselfName(String myselfName) {
        this.myselfName = myselfName;
    }

    public int getFriendNum() {
        return friendNum;
    }

    public void setFriendNum(int friendNum) {
        this.friendNum = friendNum;
    }

    public int getMyselfNum() {
        return myselfNum;
    }

    public void setMyselfNum(int myselfNum) {
        this.myselfNum = myselfNum;
    }
    
}
