package io.bsy.pure;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface VehicleDao {
    String INSERT_SQL = "INSERT INTO vehicle (color, wheel, seat, vehicle_no) VALUES (?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE vehicle SET color=?,wheel=?,seat=? WHERE vehicle_no=?";
    String SELECT_ALL_SQL = "SELECT * FROM vehicle";
    String SELECT_ONE_SQL = "SELECT * FROM vehicle WHERE vehicle_no=?";
    String SELECT_ONE_BY_COLOR = "SELECT * FROM vehicle WHERE color=? LIMIT 1";
    String SELECT_BY_WHEELS_SQL = "SELECT * FROM vehicle WHERE vehicle_no=?";
    String DELETE_SQL = "DELETE FROM vehicle WHERE vehicle_no=?";
    String COUNT_ALL_SQL = "SELECT COUNT(*) FROM VEHICLE";
    String SELECT_COLOR_SQL = "SELECT COLOR FROM VEHICLE WHERE VEHICLE_NO=?";
    String DELETE_ALL = "DELETE FROM vehicle";

    default void insert(Vehicle vehicle) {
        //do  nothing
    }

    default void insert(Collection<Vehicle> vehicles) {
        //do nothing
    }

    default void update(Vehicle vehicle) {
        //do nothing
    }

    default void delete(Vehicle vehicle) {
        //do nothing
    }

    default void deleteAll() {
        //do nothing
    }

    default Optional<Vehicle> findByVehicleNo(String vehicleNo) {
        return Optional.empty();
    }

    default Optional<Vehicle> findOneByColor(String color) {
        return Optional.empty();
    }

    default List<Vehicle> findByWheels(int wheels) {
        return Collections.emptyList();
    }


    default Optional<String> getColor(String vehicleNo) {
        return Optional.empty();
    }

    default Optional<Integer> countAll() {
        return Optional.empty();
    }

    default List<Vehicle> findAll() {
        return Collections.emptyList();
    }
}
