package ru.kpfu.itis.group501.popov.models;

import java.sql.Time;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class TopicComment extends Model{
    private String content = null;
    private int id = 0;
    private int rating = 0;
    // foreign key
    private int sender = 0;
    private Date sending_date = null;
    private Time sending_time = null;
    // foreign key
    private int topic_id = 0;
    private static String table_name = "topic_comments";
    private static Map<String, String> foreign_key = new HashMap<>();
    private Map<String, Model> relations = new HashMap<>();

    static {
        foreign_key.put("sender", "User");
    }

    public TopicComment(String content, int id, int rating, int sender, Date sending_date, Time sending_time, int topic_id) {
        this.content = content;
        this.id = id;
        this.rating = rating;
        this.sender = sender;
        this.sending_date = sending_date;
        this.sending_time = sending_time;
        this.topic_id = topic_id;
    }

    public TopicComment(String content, int sender, Date sending_date, Time sending_time, int topic_id) {
        this.content = content;
        this.sender = sender;
        this.sending_date = sending_date;
        this.sending_time = sending_time;
        this.topic_id = topic_id;
    }

    public TopicComment() {
    }

    public static Map<String, String> getForeign_key() {
        return foreign_key;
    }
}