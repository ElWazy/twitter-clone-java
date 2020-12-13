package org.elwazy.twitter_clone.models;

public class User {

    private int id;
    private String username;
    private String passwd;
    private int user_type_id_fk;
    private boolean active;

    public User() {
    }

    public User(int id, String username, String passwd, int user_type_id_fk, boolean active) {
        this.id = id;
        this.username = username;
        this.passwd = passwd;
        this.user_type_id_fk = user_type_id_fk;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getUser_type_id_fk() {
        return user_type_id_fk;
    }

    public void setUser_type_id_fk(int user_type_id_fk) {
        this.user_type_id_fk = user_type_id_fk;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", passwd='" + passwd + '\'' +
                ", user_type_id_fk=" + user_type_id_fk +
                ", active=" + active +
                '}';
    }
}
