package vo;

import java.io.Serializable;

/**
 * 
 *  @desc:仅仅作为用户上线下线的标志,客户端只保存在线的好友
 *  @author WY 
 *  创建时间 2014年3月19日 上午11:19:26
 */
@SuppressWarnings("serial")
public class Friends implements Serializable {

    private int id;

    /**好友名字*/
    private String name;
    
    /**好友编号*/
    private int channelId;

    /**是否在线*/
    private boolean isOnline;
    
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

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }
}