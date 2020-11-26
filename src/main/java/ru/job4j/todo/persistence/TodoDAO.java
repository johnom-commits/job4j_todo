package ru.job4j.todo.persistence;

import ru.job4j.todo.domain.Item;

import java.util.List;
import java.util.Optional;

public interface TodoDAO {
    void create(Item item);

    List<Item> getList(boolean allTasks);

    Optional<Item> findById(int id);

    void update(int id, Item item);
}
