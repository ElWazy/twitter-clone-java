package org.elwazy.twitter_clone.models;

public class HashtagPublication {

    private int id;
    private int publication_id_fk;
    private int hashtag_id_fk;

    public HashtagPublication() {
    }

    public HashtagPublication(int id, int publication_id_fk, int hashtag_id_fk) {
        this.id = id;
        this.publication_id_fk = publication_id_fk;
        this.hashtag_id_fk = hashtag_id_fk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublication_id_fk() {
        return publication_id_fk;
    }

    public void setPublication_id_fk(int publication_id_fk) {
        this.publication_id_fk = publication_id_fk;
    }

    public int getHashtag_id_fk() {
        return hashtag_id_fk;
    }

    public void setHashtag_id_fk(int hashtag_id_fk) {
        this.hashtag_id_fk = hashtag_id_fk;
    }

    @Override
    public String toString() {
        return "HashtagPublication{" +
                "id=" + id +
                ", publication_id_fk=" + publication_id_fk +
                ", hashtag_id_fk=" + hashtag_id_fk +
                '}';
    }
}
