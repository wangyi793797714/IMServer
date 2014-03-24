package vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AddFriendResponse implements Serializable {

    /**是否接受了添加好友的请求*/
    private boolean isAccept;
    
    /**申请者的名字*/
    private String responseName;
    
    /**如果接受了，添加好友的请求，被请求的人将自己的信息返回给申请者*/
    private Myself respFriend;

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean isAccept) {
        this.isAccept = isAccept;
    }

    public Myself getRespFriend() {
        return respFriend;
    }

    public void setRespFriend(Myself respFriend) {
        this.respFriend = respFriend;
    }

    public String getResponseName() {
        return responseName;
    }

    public void setResponseName(String responseName) {
        this.responseName = responseName;
    }
}
