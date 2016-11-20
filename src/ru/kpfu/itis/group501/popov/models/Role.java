package ru.kpfu.itis.group501.popov.models;

import java.util.HashMap;
import java.util.Map;

public class Role extends Model {
    private int id = 0;
    private String name = null;
    private static String table_name = "role";
    private Map<String, Model> relations = new HashMap<>();
    // здесь должно быть либо manytomany, либо нужно создать новую сущность - "Принадлежность к роли"

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role() {
    }
}
