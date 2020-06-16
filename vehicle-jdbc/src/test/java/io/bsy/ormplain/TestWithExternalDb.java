package io.bsy.ormplain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestWithExternalDb {

    VehicleDao vehicleDao = new VehicleHibernateDao();

    @BeforeEach
    @AfterEach
    public void wipe() {
        vehicleDao.deleteAll();
    }

    @Test
    public void testCreateNew() {
        Vehicle vehicle = new Vehicle("HH666H777", "Black", 2, 2);
        vehicleDao.store(vehicle);

        Optional<Vehicle> vehicleFound = vehicleDao.findById(vehicle.getId());
        assertAll("insertWithPlainHiber",
                () -> assertTrue(vehicleFound.isPresent()),
                () -> assertEquals(vehicle, vehicleFound.get()),
                () -> assertEquals(vehicle.getColor(), vehicleFound.get().getColor()),
                () -> assertEquals(vehicle.getVehicleNo(), vehicleFound.get().getVehicleNo()),
                () -> assertEquals(vehicle.getSeat(), vehicleFound.get().getSeat()),
                () -> assertEquals(vehicle.getWheel(), vehicleFound.get().getWheel())
        );
    }
}