package ru.kpfu.itis.group501.popov.models;

import java.sql.Time;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class EventArticle extends Model {
    private Date begin_date = null;
    private String content = null;
    // creater - foreign key
    private int creater = 0;
    private Date end_date = null;
    private int id = 0;
    private String name = null;
    private Date write_date = null;
    private Time write_time = null;
    private static String table_name = "events_articles";
    // В foreign_key хранится пара ключ-значение. Ключ - название модели, значение - название поля ключа, по которому соединять
    private static Map<String, String> foreign_key = new HashMap<>();
    private Map<String, Model> relations = new HashMap<>();

    static {
        foreign_key.put("creater", "User");
    }

    public EventArticle(Date begin_date, String content, int creater, Date end_date, int id, String name, Date write_date, Time write_time) {
        this.begin_date = begin_date;
        this.content = content;
        this.creater = creater;
        this.end_date = end_date;
        this.id = id;
        this.name = name;
        this.write_date = write_date;
        this.write_time = write_time;
    }

    public EventArticle(Date begin_date, String content, int creater, Date end_date, String name, Date write_date, Time write_time) {
        this.begin_date = begin_date;
        this.content = content;
        this.creater = creater;
        this.end_date = end_date;
        this.name = name;
        this.write_date = write_date;
        this.write_time = write_time;
    }

    public EventArticle() {
    }

    public static Map<String, String> getForeign_key() {
        return foreign_key;
    }
}
