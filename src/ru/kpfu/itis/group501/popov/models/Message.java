package ru.kpfu.itis.group501.popov.models;

import java.sql.Time;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class Message extends Model {
    private String content = null;
    private int id = 0;
    // foreign key
    private int recipient = 0;
    // foreign key
    private int sender = 0;
    private String topic = null;
    private Date write_date = null;
    private Time write_time = null;
    private static String table_name = "messages";
    private static Map<String, String> foreign_key = new HashMap<>();
    private Map<String, Model> relations = new HashMap<>();

    static {
        foreign_key.put("sender", "User");
        foreign_key.put("recipient", "User");
    }

    public Message(String content, int id, int recipient, int sender, String topic, Date write_date, Time write_time) {
        this.content = content;
        this.id = id;
        this.recipient = recipient;
        this.sender = sender;
        this.topic = topic;
        this.write_date = write_date;
        this.write_time = write_time;
    }

    public Message(String content, int recipient, int sender, String topic, Date write_date, Time write_time) {
        this.content = content;
        this.recipient = recipient;
        this.sender = sender;
        this.topic = topic;
        this.write_date = write_date;
        this.write_time = write_time;
    }

    public Message() {
    }

    public static Map<String, String> getForeign_key() {
        return foreign_key;
    }
}
