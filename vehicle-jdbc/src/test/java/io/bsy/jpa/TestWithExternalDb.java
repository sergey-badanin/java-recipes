package io.bsy.jpa;

import io.bsy.ormplain.Vehicle;
import io.bsy.ormplain.VehicleDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


//TODO: Test find All
class TestWithExternalDb {

    VehicleDao vehicleDao = new JpaVehicleDao();

    @BeforeEach
    @AfterEach
    public void wipe() {
        vehicleDao.deleteAll();
    }

    @Test
    public void testCreateNew() {
        var vehicleStored = vehicleDao.store(new Vehicle("HH666H777", "Black", 2, 2));

        Optional<Vehicle> vehicleFound = vehicleDao.findById(vehicleStored.getId());
        assertAll("insertWithPlainHiber",
                () -> assertTrue(vehicleFound.isPresent()),
                () -> assertEquals(vehicleStored, vehicleFound.get()),
                () -> assertEquals(vehicleStored.getColor(), vehicleFound.get().getColor()),
                () -> assertEquals(vehicleStored.getVehicleNo(), vehicleFound.get().getVehicleNo()),
                () -> assertEquals(vehicleStored.getSeat(), vehicleFound.get().getSeat()),
                () -> assertEquals(vehicleStored.getWheel(), vehicleFound.get().getWheel())
        );
    }
}