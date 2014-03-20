package vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OnlineFriends implements Serializable {

    private int id;

    /**好友名字*/
    private String name;
    
    /**好友编号*/
    private int channelId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", channelId=" + channelId + "]";
    }
}