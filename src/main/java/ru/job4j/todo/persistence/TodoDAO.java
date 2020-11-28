package ru.job4j.todo.persistence;

import ru.job4j.todo.domain.Item;
import ru.job4j.todo.domain.User;

import java.util.List;
import java.util.Optional;

public interface TodoDAO {
    <T> void create(T item);

    List<Item> getList(boolean allTasks, User user);

    Optional<Item> findById(int id);

    void update(int id, Item item);

    Optional<User> findByEmail(String email);
}
