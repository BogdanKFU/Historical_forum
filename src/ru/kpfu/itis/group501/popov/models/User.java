package ru.kpfu.itis.group501.popov.models;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class User extends Model {
    // username - unique
    private String username = "";
    private String last_name = null;
    private String password = "";
    // id - primary key
    private int id = 0;
    // здесь должно быть либо manytomany, либо нужно создать новую сущность - "Принадлежность к роли"
    private String email = "";
    private boolean blocked = false;
    private String first_name = null;
    private String interest = null;
    private String photo_name = null;
    private Date birth_date = null;
    private static String table_name = "users_profiles";
    private static Map<String, String> foreign_key = new HashMap<>();
    private Map<String, Model> relations = new HashMap<>();

    // Общий конструктор
    public User(String username, String last_name, String password, int id, String email, boolean blocked, String first_name, String interest, String photo_name, Date birth_date) {
        this.username = username;
        this.last_name = last_name;
        this.password = password;
        this.id = id;
        this.email = email;
        this.blocked = blocked;
        this.first_name = first_name;
        this.interest = interest;
        this.photo_name = photo_name;
        this.birth_date = birth_date;
    }

    // Конструктор для регистрации
    public User(String username, String password, String email, java.util.Date birth_date, String first_name, String last_name) {
        if (check_args(username, password, email, birth_date)) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.birth_date = new Date(birth_date.getTime());
            this.first_name = first_name;
            this.last_name = last_name;
        }
        else {
            throw new NullPointerException("You sent null value to non-nullable variable");
        }
    }

    public User() {
    }

    private boolean check_args(String username, String password, String email, java.util.Date birth_date) {
        return (username != null & password != null & email != null & birth_date != null);
    }
    public static Map<String, String> getForeign_key() {
        return foreign_key;
    }
}