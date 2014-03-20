package vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RoomChild implements Serializable {

    private int id;

    /**用户名*/
    private String name;

    /**用户编号*/
    private int channelId;

    /**所在组的编号*/
    private long GroupTag;
    
    public RoomChild() {
    }

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

    public long getGroupTag() {
        return GroupTag;
    }

    public void setGroupTag(long groupTag) {
        GroupTag = groupTag;
    }

}