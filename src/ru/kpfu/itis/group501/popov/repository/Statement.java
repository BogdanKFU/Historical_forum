package ru.kpfu.itis.group501.popov.repository;

public interface Statement {
    Statement and(String field_name, Object value);
    Statement and();
    Statement by(String field_name, Object value);
    Statement ge(String field_name, Object value);
    Statement le(String field_name, Object value);
    Statement like(String field_name, Object value);
    Statement or(String field_name, Object value);
    Statement orderBy(String field_name);
    Statement select(Class model);
    Statement selectBy(Class model, String field_name, Object value);
    Statement join(Class model);
    Statement joinBy(Class model, String field_name, Object value);
}
