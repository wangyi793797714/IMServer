package vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AddFriendResponse implements Serializable {

    private boolean isAccept;
    
    /**被请求的人*/
    private String responseName;
    
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
