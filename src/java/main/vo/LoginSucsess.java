package vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 *  @desc:登陆成功返回的对象：用户信息，以及用户的离线消息
 *  @author WY 
 *  创建时间 2014年3月24日 下午1:51:31
 */
@SuppressWarnings("serial")
public class LoginSucsess implements Serializable{
    
    private Myself myself;
    
    private List<Content> offlineMsgs;

    public Myself getMyself() {
        return myself;
    }

    public void setMyself(Myself myself) {
        this.myself = myself;
    }

    public List<Content> getOfflineMsgs() {
        return offlineMsgs;
    }

    public void setOfflineMsgs(List<Content> offlineMsgs) {
        this.offlineMsgs = offlineMsgs;
    }
}
