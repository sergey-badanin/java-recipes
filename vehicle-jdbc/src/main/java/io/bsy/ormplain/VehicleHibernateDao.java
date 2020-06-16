package io.bsy.ormplain;

import io.bsy.utils.PropertiesProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class VehicleHibernateDao implements VehicleDao {

    private final SessionFactory sessionFactory;

    public VehicleHibernateDao() {
        Properties props = PropertiesProvider.getDbProperties();
        Configuration config = new Configuration()
                .setProperties(PropertiesProvider.getHiberProperties())
                .addAnnotatedClass(Vehicle.class);

        sessionFactory = config.buildSessionFactory();
    }

    @Override
    public Vehicle store(Vehicle vehicle) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.getTransaction();
        try {
            tx.begin();
            session.saveOrUpdate(vehicle);
            tx.commit();
            return vehicle;
        } catch (Throwable e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.getTransaction();
        try {
            tx.begin();
            var vehicle = session.get(Vehicle.class, id);
            session.delete(vehicle);
            tx.commit();
        } catch (Throwable e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteAll() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.getTransaction();
        try {
            tx.begin();
            session.createQuery("DELETE FROM Vehicle").executeUpdate();
            tx.commit();
        } catch (Throwable e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            return Optional.ofNullable(session.get(Vehicle.class, id));
        } finally {
            session.close();
        }
    }

    @Override
    public List<Vehicle> findAll() {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM Vehicle", Vehicle.class).list();
        } finally {
            session.close();
        }
    }
}
