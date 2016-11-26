package ru.kpfu.itis.group501.popov.repository;

import ru.kpfu.itis.group501.popov.models.Model;
import ru.kpfu.itis.group501.popov.repository.custom.CustomStatement;

import java.util.List;
import java.util.Map;

public interface Repository {
    void add(Model model);
    void delete(Class model, int id);
    void update(Model model);
    List get(Class model);
    List getBy(Class model, String field_name, Object value);
    Map<String, List<Object>> do_sql(CustomStatement statement);
}
