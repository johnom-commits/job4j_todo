package ru.job4j.todo.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.todo.domain.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class HbmStore implements TodoDAO, AutoCloseable {
    private static final Logger LOG = LogManager.getLogger(HbmStore.class.getName());
    private final SessionFactory sf;

    private HbmStore() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        sf = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    private static final class Lazy {
        private static final HbmStore INSTANCE = new HbmStore();
    }

    public static HbmStore instanceOf() {
        return Lazy.INSTANCE;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            LOG.error(e.getMessage(), e);
            throw e;
        } finally {
            session.close();
        }
    }

    public void create(final Item item) {
        tx(session -> session.save(item));
    }

    public List<Item> getList(final boolean allTasks) {
        return tx(session -> {
            final var query = new StringBuilder("from Item ");
            if (!allTasks) {
                query.append("where done = false ");
            }
            query.append("order by id desc ");
            return session.createQuery(query.toString()).list();
        });
    }

    public Optional<Item> findById(final int id) {
        return tx(session -> Optional.of(session.get(Item.class, id)));
    }

    public void update(final int id, final Item item) {
        tx(session -> {
            Query query = session.createQuery("update Item set done = :newDone where id = :id");
            query.setParameter("newDone", item.isDone());
            query.setParameter("id", id);
            return query.executeUpdate();
        });
    }

    @Override
    public void close() throws Exception {
        sf.close();
    }
}
