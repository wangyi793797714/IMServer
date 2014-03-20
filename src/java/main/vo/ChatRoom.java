package vo;

import java.io.Serializable;
import java.util.List;

import server.util.Util;

@SuppressWarnings("serial")
public class ChatRoom implements Serializable {

    private int id;

    /** 聊天室编号 */
    private long grouppTag;

    /** 聊天室的名字 */
    private String groupName;

    /** 邀请参加聊天室的好友列表 */
    private List<RoomChild> childDatas;

    /** 创建聊天室人的编号 */
    private int sendId;

    public ChatRoom() {
    }

    public ChatRoom(long grouppTag, String groupName, List<RoomChild> childDatas, int sendId) {
        this.grouppTag = grouppTag;
        this.groupName = groupName;
        this.childDatas = childDatas;
        this.sendId = sendId;
    }

    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<RoomChild> getChildDatas() {
        return childDatas;
    }

    public void setChildDatas(List<RoomChild> childDatas) {
        this.childDatas = childDatas;
    }

    public void addToChilds(RoomChild u) {
        childDatas.add(u);
    }

    public void addToChilds(List<RoomChild> u) {
        childDatas.addAll(u);
    }

    public void removeChild(Myself u) {
        childDatas.remove(u);
    }

    public void removeChild(int childPosition) {
        childDatas.remove(childPosition);
    }

    public int childsSize() {
        if (Util.isEmpty(childDatas)) {
            return 0;
        }
        return childDatas.size();
    }

    public RoomChild getChild(int position) {
        return childDatas.get(position);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getGrouppTag() {
        return grouppTag;
    }

    public void setGrouppTag(long grouppTag) {
        this.grouppTag = grouppTag;
    }
}
