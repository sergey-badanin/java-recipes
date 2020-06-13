package io.bsy.pure;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JdbcVehicleDao implements VehicleDao {

    public JdbcVehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private final DataSource dataSource;

    @Override
    public void insert(Vehicle vehicle) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            prepareStatement(ps, vehicle);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Collection<Vehicle> vehicles) {
        vehicles.forEach(this::insert);
    }

    @Override
    public void update(Vehicle vehicle) {

    }

    @Override
    public void delete(Vehicle vehicle) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Vehicle findByVehicleNo(String vehicleNo) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ONE_SQL)) {
            ps.setString(1, vehicleNo);

            Vehicle vehicle = null;
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    vehicle = toVehicle(rs);
                }
            }
            return vehicle;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Vehicle> findAll() {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            List<Vehicle> vehicles = new ArrayList<>();
            while (rs.next()) {
                vehicles.add(toVehicle(rs));
            }
            return vehicles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void prepareStatement(PreparedStatement ps, Vehicle vehicle) throws SQLException {
        ps.setString(1, vehicle.getColor());
        ps.setInt(2, vehicle.getWheel());
        ps.setInt(3, vehicle.getSeat());
        ps.setString(4, vehicle.getVehicleNo());
    }

    private static Vehicle toVehicle(ResultSet rs) throws SQLException {
        return new Vehicle(rs.getString("VEHICLE_NO"),
                rs.getString("COLOR"),
                rs.getInt("WHEEL"),
                rs.getInt("SEAT"));
    }

}
