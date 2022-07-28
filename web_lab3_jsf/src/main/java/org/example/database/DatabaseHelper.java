package org.example.database;

import org.example.model.PointEntry;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DatabaseHelper {
    private SessionFactory factory;

    public DatabaseHelper() {
        factory = SessionFactoryUtil.getSessionFactory();
    }

    public void save(PointEntry entry) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entry);
        transaction.commit();
        session.close();
    }

    public List<PointEntry> getAll() {
        Session session = factory.openSession();
        return session.createQuery("SELECT p FROM " + PointEntry.class.getSimpleName() + " p",
                PointEntry.class).getResultList();
    }
}
