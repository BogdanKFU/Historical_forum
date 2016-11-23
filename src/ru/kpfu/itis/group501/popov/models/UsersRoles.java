package ru.kpfu.itis.group501.popov.models;

import java.util.HashMap;
import java.util.Map;

public class UsersRoles extends Model {
    private static String table_name = "users_roles";
    private static Map<String, String> foreign_key = new HashMap<>();
    private Map<String, Model> relations = new HashMap<>();
    private int id_user = 0;
    private int id_role = 0;

    static {
        foreign_key.put("id_user", "User");
        foreign_key.put("id_role", "Role");
    }

    public static Map<String, String> getForeign_key() {
        return foreign_key;
    }
}
