package ru.kpfu.itis.group501.popov.models;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class Topic extends Model {
    private String content = null;
    // foreign key
    private int creater = 0;
    private int id = 0;
    private String name = null;
    private Date publish_date = null;
    private Time publish_time = null;
    // foreign key
    private int section = 0;
    private static String table_name = "topics";
    private static Map<String, String> foreign_key = new HashMap<>();
    private Map<String, Model> relations = new HashMap<>();

    static {
        foreign_key.put("creater", "User");
    }

    public Topic(String content, int creater, int id, String name, Date publish_date, Time publish_time, int section) {
        this.content = content;
        this.creater = creater;
        this.id = id;
        this.name = name;
        this.publish_date = publish_date;
        this.publish_time = publish_time;
        this.section = section;
    }

    public Topic(String content, int creater, String name, Date publish_date, Time publish_time, int section) {
        this.content = content;
        this.creater = creater;
        this.name = name;
        this.publish_date = publish_date;
        this.publish_time = publish_time;
        this.section = section;
    }

    public Topic() {
    }

    public static Map<String, String> getForeign_key() {
        return foreign_key;
    }
}