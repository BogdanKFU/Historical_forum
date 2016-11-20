package ru.kpfu.itis.group501.popov.models;

import java.sql.Time;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class PersonComment extends Model {
    private String content = null;
    private int id = 0;
    private int persons_article = 0;
    private int rating = 0;
    private Date send_date = null;
    private Time send_time = null;
    // foreign key
    private int sender = 0;
    private static String table_name = "persons_comments";
    private static Map<String, String> foreign_key = new HashMap<>();
    private Map<String, Model> relations = new HashMap<>();

    static {
        foreign_key.put("sender", "User");
    }

    public PersonComment(String content, int id, int persons_article, int rating, Date send_date, Time send_time, int sender) {
        this.content = content;
        this.id = id;
        this.persons_article = persons_article;
        this.rating = rating;
        this.send_date = send_date;
        this.send_time = send_time;
        this.sender = sender;
    }

    public PersonComment() {
    }

    public PersonComment(String content, Integer persons_article, int i, Date send_date, Time send_time, int id) {
        this.content = content;
        this.persons_article = persons_article;
        this.rating = i;
        this.send_date = send_date;
        this.send_time = send_time;
        this.sender = id;
    }

    public static Map<String, String> getForeign_key() {
        return foreign_key;
    }
}
