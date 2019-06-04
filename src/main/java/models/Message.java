package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity(name = "Message")
public class Message implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    private String message;

    @ManyToOne
    private User user;

    private long unixTimeStamp;

    public Message() {}

    public Message(String message, User user, long unixTimeStamp) {
        this.message = message;
        this.user = user;
        this.unixTimeStamp = unixTimeStamp;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public long getUnixTimeStamp() {
        return unixTimeStamp;
    }
}
