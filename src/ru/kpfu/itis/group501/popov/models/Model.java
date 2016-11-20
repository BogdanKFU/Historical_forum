package ru.kpfu.itis.group501.popov.models;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

// Для работы SELECT следует реализовать class servlets.Repository<T>, в котором при помощи рефлексии будет реализованы SELECT запросы

// models.Model является аналогией ModelDAO. Если использовать servlets.Repository, то следует все методы add(), update(), delete(), get(), ... реализовать в нем.
// Параметризовать servlets.Repository, реализовать все методы при помощи рефлексии Java.
// servlets.Repository<models.User>.add(models.User user);
public class Model {
    private static Map<String, String> foreign_key = new HashMap<>();
    // Берет нужный параметр из models.Model и возращает его
    public Object get(String param) {
        try {
            Class c = this.getClass();
            Field field = c.getDeclaredField(param);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return field.get(this);
        }
        catch (NoSuchFieldException | IllegalAccessException ex) {
            return null;
        }
    }

    // Присваивает указанному параметру param значение object. Возвращает true, если удалось присвоить, и false, если нет.
    public boolean set(String param, Object object) {
        try {
            Class c = this.getClass();
            Field field = c.getDeclaredField(param);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            field.set(this, object);
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }
}
