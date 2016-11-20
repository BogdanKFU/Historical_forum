package ru.kpfu.itis.group501.popov.models;

import java.sql.Time;

public class CustomCookie extends Model {
    private int id;
    private String cookie = "";
    private Time assign_time = null;
    private static String table_name = "cookies";

    public CustomCookie(int id, String cookie, Time assign_time) {
        this.id = id;
        this.cookie = cookie;
        this.assign_time = assign_time;
    }
}
