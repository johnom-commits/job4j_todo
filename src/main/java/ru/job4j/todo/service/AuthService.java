package ru.job4j.todo.service;

import ru.job4j.todo.domain.User;
import ru.job4j.todo.persistence.HbmStore;

public class AuthService {
    public void newUserService(String login, String email, String password) {
        User user = User.of(login, email, password);
        HbmStore.instanceOf().create(user);
    }
}
