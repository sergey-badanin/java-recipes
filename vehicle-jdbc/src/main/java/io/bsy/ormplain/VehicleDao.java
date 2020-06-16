package io.bsy.ormplain;

import java.util.List;
import java.util.Optional;

public interface VehicleDao {

    Vehicle store(Vehicle vehicle);

    void delete(Long id);

    void deleteAll();

    Optional<Vehicle> findById(Long id);

    List<Vehicle> findAll();
}
