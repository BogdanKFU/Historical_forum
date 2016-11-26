package ru.kpfu.itis.group501.popov.singletons;

import ru.kpfu.itis.group501.popov.repository.custom.CustomRepository;
import ru.kpfu.itis.group501.popov.repository.Repository;

public class RepositorySingleton {
    private static Repository rep = null;
    public static Repository getRepository() {
        if (rep == null) {
            rep = new CustomRepository();
        }
        return rep;
    }
}
