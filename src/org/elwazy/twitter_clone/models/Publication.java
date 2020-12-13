package org.elwazy.twitter_clone.models;

import java.sql.Date;

public class Publication {

    private int id;
    private int user_id_fk;
    private String text;
    private Date date;

    public Publication() {
    }

    public Publication(int id, int user_id_fk, String text, Date date) {
        this.id = id;
        this.user_id_fk = user_id_fk;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id_fk() {
        return user_id_fk;
    }

    public void setUser_id_fk(int user_id_fk) {
        this.user_id_fk = user_id_fk;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "user_id_fk=" + user_id_fk +
                ", text='" + text + '\'' +
                ", datetime=" + date +
                '}';
    }
}
