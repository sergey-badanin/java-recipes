package io.bsy.springjdbctemplate;

import io.bsy.pure.Vehicle;
import io.bsy.pure.VehicleDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
    VehicleDao vehicleDao;

    @BeforeEach
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
                () -> assertEquals(new HashSet<>(vehicles), Set.of(vehicle01, vehicle02, vehicle03))
        );
    }
}