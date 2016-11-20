package ru.kpfu.itis.group501.popov.models;

import java.sql.Time;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class EventComment extends Model {
    private String content = null;
    private int events_article = 0;
    private int id = 0;
    private int rating = 0;
    private Date send_date = null;
    private Time send_time = null;
    // foreign key
    private int sender = 0;
    private static String table_name = "events_comments";
    private static Map<String, String> foreign_key = new HashMap<>();
    private Map<String, Model> relations = new HashMap<>();

    static {
        foreign_key.put("sender", "User");
    }

    public EventComment(String content, int events_article, int id, int rating, Date send_date, Time send_time, int sender) {
        this.content = content;
        this.events_article = events_article;
        this.id = id;
        this.rating = rating;
        this.send_date = send_date;
        this.send_time = send_time;
        this.sender = sender;
    }

    public EventComment(String content, int events_article, int rating, Date send_date, Time send_time, int sender) {
        this.content = content;
        this.events_article = events_article;
        this.rating = rating;
        this.send_date = send_date;
        this.send_time = send_time;
        this.sender = sender;
    }

    public EventComment() {
    }

    public static Map<String, String> getForeign_key() {
        return foreign_key;
    }
}
