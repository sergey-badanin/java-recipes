package io.bsy.springjdbctemplate;

import io.bsy.pure.Vehicle;
import io.bsy.pure.VehicleDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Junit assertions
 * https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {VehicleConfiguration.class})
class testWithExternalDb {

    @Autowired
    @Qualifier("simpleTemplate")
    VehicleDao vehicleDao;

    @Autowired
    @Qualifier("namedParamsTemplate")
    VehicleDao vehicleDaoNamedParams;

    @BeforeEach
    @AfterEach
    public void wipe() {
        vehicleDao.deleteAll();
    }

    @Test
    public void testInsert() {
        Vehicle vehicle01 = new Vehicle("HH666H777", "Black", 2, 2);
        Vehicle vehicle02 = new Vehicle("HH555H444", "Red", 3, 2);
        Vehicle vehicle03 = new Vehicle("KK222H556", "White", 4, 4);

        vehicleDao.insert(vehicle01);
        vehicleDao.insert(vehicle02);
        vehicleDao.insert(vehicle03);

        List<Vehicle> vehicles = vehicleDao.findAll();

        assertAll("insert",
                () -> assertNotNull(vehicles),
                () -> assertEquals(Set.of(vehicle01, vehicle02, vehicle03), new HashSet<>(vehicles))
        );
    }

    @Test
    public void testInsertNamedParams() {
        Vehicle vehicle01 = new Vehicle("HH666H777", "Black", 2, 2);
        Vehicle vehicle02 = new Vehicle("HH555H444", "Red", 3, 2);
        Vehicle vehicle03 = new Vehicle("KK222H556", "White", 4, 4);

        vehicleDaoNamedParams.insert(vehicle01);
        vehicleDaoNamedParams.insert(vehicle02);
        vehicleDaoNamedParams.insert(vehicle03);

        List<Vehicle> vehicles = vehicleDao.findAll();

        assertAll("insertNamedParams",
                () -> assertNotNull(vehicles),
                () -> assertEquals(Set.of(vehicle01, vehicle02, vehicle03), new HashSet<>(vehicles))
        );
    }

    @Test
    public void testUpdateNamedParams() {
        Vehicle vehicle = new Vehicle("HH666H777", "Black", 2, 2);

        vehicleDaoNamedParams.insert(vehicle);
        vehicle.setColor("Red");
        vehicleDaoNamedParams.update(vehicle);

        var vehicleResult = vehicleDao.findByVehicleNo(vehicle.getVehicleNo());

        assertAll("updateNamedParams",
                () -> assertTrue(vehicleResult.isPresent()),
                () -> assertEquals(vehicle, vehicleResult.get())
        );
    }

    @Test
    public void testInsertBatchNamedParams() {
        Vehicle vehicle01 = new Vehicle("HH666H777", "Black", 2, 2);
        Vehicle vehicle02 = new Vehicle("HH555H444", "Red", 3, 2);
        Vehicle vehicle03 = new Vehicle("KK222H556", "White", 4, 4);

        Set<Vehicle> vehiclesBefore = Set.of(vehicle01, vehicle02, vehicle03);
        vehicleDaoNamedParams.insert(vehiclesBefore);

        List<Vehicle> vehiclesAfter = vehicleDao.findAll();

        assertAll("insertNamedParams",
                () -> assertNotNull(vehiclesAfter),
                () -> assertEquals(vehiclesBefore, new HashSet<>(vehiclesAfter))
        );
    }

    @Test
    public void testSpringDataAccessExceptionThrown() {
        Vehicle vehicle01 = new Vehicle("HH666H777", "Black", 2, 2);
        vehicleDaoNamedParams.insert(vehicle01);

        Vehicle vehicle02 = new Vehicle("HH666H777", "Red", 3, 2);

        assertThrows(DataAccessException.class, () -> vehicleDao.insert(vehicle02));
    }
}