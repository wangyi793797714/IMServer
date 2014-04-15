package vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Content implements Serializable {

	private int id;

	/** 发送者名字 */
	private String sendName;

	/** 接受人姓名 */
	private String receiveName;

	/** 发送日期 */
	private Date date;

	/** 发送内容 */
	private String msg;

	/** true:发送出去；false：接受 */
	private boolean isSendMsg;

	/** 0：群发；!=0:点对点:目标channelId */
	private int receiveId;

	/** 发送消息的人员编号 */
	private int sendId;

	/** 接收消息的对象编号 */
	private List<Integer> targetIds;

	/** 如果是群聊，那么指定当前的聊天内容是属于哪一聊天组的 */
	private long grouppTag;

	/** 用来标示当前消息的归属，主要用于进入聊天界面显示最近的10条信息 */
	private String belongTo;

	/** 标示当前信息是否已经查阅 */
	private String isRead = "false";

	/**标示当前消息是否在本地已经存在*/
	private String isLocalMsg = "false";

	/**消息类型：0：文本，1：图片，2：语音*/
	private int msgType;
	
	/**如果消息是图片或者语音那么对应在本地的Url*/
	private String msgLocalUrl;
	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSendMsg() {
		return isSendMsg;
	}

	public void setSendMsg(boolean isSendMsg) {
		this.isSendMsg = isSendMsg;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public int getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(int receiveId) {
		this.receiveId = receiveId;
	}

	public int getSendId() {
		return sendId;
	}

	public void setSendId(int sendId) {
		this.sendId = sendId;
	}

	public List<Integer> getTargetIds() {
		return targetIds;
	}

	public void setTargetIds(List<Integer> targetIds) {
		this.targetIds = targetIds;
	}

	public long getGrouppTag() {
		return grouppTag;
	}

	public void setGrouppTag(long grouppTag) {
		this.grouppTag = grouppTag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getIsLocalMsg() {
		return isLocalMsg;
	}

	public void setIsLocalMsg(String isLocalMsg) {
		this.isLocalMsg = isLocalMsg;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getMsgLocalUrl() {
		return msgLocalUrl;
	}

	public void setMsgLocalUrl(String msgLocalUrl) {
		this.msgLocalUrl = msgLocalUrl;
	}
	
}
