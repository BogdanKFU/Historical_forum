package ru.kpfu.itis.group501.popov.models;

import java.sql.Time;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class PersonArticle extends Model {
    private Date birth_date = null;
    private String content = null;
    // foreign key
    private int creater = 0;
    private Date dead_date = null;
    private int id = 0;
    private String name = null;
    private Date write_date = null;
    private Time write_time = null;
    private static String table_name = "persons_articles";
    private static Map<String, String> foreign_key = new HashMap<>();
    private Map<String, Model> relations = new HashMap<>();

    static {
        foreign_key.put("creater", "User");
    }

    public PersonArticle(Date birth_date, String content, int creater, Date dead_date, int id, String name, Date write_date, Time write_time) {
        this.birth_date = birth_date;
        this.content = content;
        this.creater = creater;
        this.dead_date = dead_date;
        this.id = id;
        this.name = name;
        this.write_date = write_date;
        this.write_time = write_time;
    }

    public PersonArticle() {
    }

    public PersonArticle(Date birth_date, String content, int creater, Date dead_date, String name, Date write_date, Time write_time) {
        this.birth_date = birth_date;
        this.content = content;
        this.creater = creater;
        this.dead_date = dead_date;
        this.name = name;
        this.write_date = write_date;
        this.write_time = write_time;
    }

    public static Map<String, String> getForeign_key() {
        return foreign_key;
    }
}
