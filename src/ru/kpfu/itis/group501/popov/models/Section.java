package ru.kpfu.itis.group501.popov.models;

import java.util.HashMap;
import java.util.Map;

public class Section extends Model {
    // foreign key
    private int creater = 0;
    private int id = 0;
    private String name = null;
    private static String table_name = "section";
    private static Map<String, String> foreign_key = new HashMap<>();
    private Map<String, Model> relations = new HashMap<>();

    static {
        foreign_key.put("creater", "User");
    }

    public Section(int creater, int id, String name) {
        this.creater = creater;
        this.id = id;
        this.name = name;
    }

    public Section(int creater, String name) {
        this.creater = creater;
        this.name = name;
    }

    public Section() {
    }

    public static Map<String, String> getForeign_key() {
        return foreign_key;
    }
}
