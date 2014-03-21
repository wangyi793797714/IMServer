package vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FriendBody implements Serializable {
    private ChatRoom room;

    public ChatRoom getRoom() {
        return room;
    }

    public void setRoom(ChatRoom room) {
        this.room = room;
    }
}
