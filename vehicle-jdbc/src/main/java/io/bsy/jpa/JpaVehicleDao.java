package io.bsy.jpa;

import io.bsy.ormplain.Vehicle;
import io.bsy.ormplain.VehicleDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class JpaVehicleDao implements VehicleDao {

    private final EntityManagerFactory entityManagerFactory;

    public JpaVehicleDao() {
        entityManagerFactory = Persistence.createEntityManagerFactory("vehicle");
    }

    @Override
    public Vehicle store(Vehicle vehicle) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = manager.getTransaction();
        try {
            tx.begin();
            var storedVehicle = manager.merge(vehicle);
            tx.commit();
            return storedVehicle;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            manager.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = manager.getTransaction();
        try {
            tx.begin();
            Vehicle vehicle = manager.find(Vehicle.class, id);
            manager.remove(vehicle);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            manager.close();
        }
    }

    @Override
    public void deleteAll() {
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = manager.getTransaction();
        try {
            tx.begin();
            manager.createQuery("DELETE FROM Vehicle").executeUpdate();
            tx.commit();
        } catch (Throwable e) {
            tx.rollback();
            throw e;
        } finally {
            manager.close();
        }
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        try {
            return Optional.ofNullable(manager.find(Vehicle.class, id));
        } finally {
            manager.close();
        }
    }

    @Override
    public List<Vehicle> findAll() {
        EntityManager manager = entityManagerFactory.createEntityManager();
        try {
            Query query = manager.createQuery("FROM Vehicle", Vehicle.class);
            return query.getResultList();
        } finally {
            manager.close();
        }
    }
}
