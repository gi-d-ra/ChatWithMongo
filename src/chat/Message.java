package chat;

import java.io.Serializable;

public class Message implements Serializable {
    final private MessageType type;
    final private String data;

    public Message(MessageType messageType) {
        this.type = messageType;
        data = null;
    }

    public Message(MessageType messageType, String data) {
        this.type = messageType;
        this.data = data;
    }

    public MessageType getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}
