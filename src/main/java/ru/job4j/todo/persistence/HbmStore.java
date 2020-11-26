package ru.job4j.todo.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.todo.domain.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void create(final Item item) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public List<Item> getList(final boolean allTasks) {
        List result = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            final var query = new StringBuilder("from Item ");
            if (!allTasks) {
                query.append("where done = false ");
            }
            query.append("order by id desc ");
            result = session.createQuery(query.toString()).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    public Optional<Item> findById(final int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Item result = session.get(Item.class, id);
            session.getTransaction().commit();
            return Optional.of(result);
        } catch (HibernateException e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    public void update(final int id, final Item item) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("update Item set done = :newDone where id = :id");
            query.setParameter("newDone", item.isDone());
            query.setParameter("id", id);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public void close() throws Exception {
        sf.close();
    }
}
