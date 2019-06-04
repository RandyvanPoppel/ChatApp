package models;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries(value = {
        @NamedQuery(name = "user.getByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
})
@Entity(name = "User")
public class User implements Serializable {
    @Id
    private long id;

    private String username;

    public User() {
    }

    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
