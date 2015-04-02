package vo;

import java.io.Serializable;

/**
 * 
 * @Des: 心跳监测包
 * @author Rhino
 * @version V1.0
 * @created 2015年4月2日 下午3:22:43
 */
public class HeartbeatVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
