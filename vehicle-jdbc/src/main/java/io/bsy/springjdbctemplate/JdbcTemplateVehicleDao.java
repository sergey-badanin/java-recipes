package io.bsy.springjdbctemplate;

import io.bsy.pure.Vehicle;
import io.bsy.pure.VehicleDao;
import io.bsy.utils.Holder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/*
 * Spring documentation on JdbcTemplates
 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/package-summary.html#package.description
 */
public class JdbcTemplateVehicleDao extends JdbcDaoSupport implements VehicleDao {

    public JdbcTemplateVehicleDao() {
    }

    /*
     * Illustration of PreparedStatementCreator interface
     */
    @Override
    public void insert(Vehicle vehicle) {
        getJdbcTemplate().update((conn) -> {
            PreparedStatement ps = conn.prepareStatement(INSERT_SQL);
            prepareStatement(ps, vehicle);
            return ps;
        });
    }

    /*
     * Illustration of PreparedStatementSetter interface
     */
    @Override
    public void insert(Collection<Vehicle> vehicles) {
        getJdbcTemplate().batchUpdate(INSERT_SQL, vehicles, vehicles.size(), JdbcTemplateVehicleDao::prepareStatement);
    }

    /*
     * Illustration of direct parameter values interface
     */
    @Override
    public void update(Vehicle vehicle) {
        getJdbcTemplate().update(UPDATE_SQL, vehicle.getColor(), vehicle.getWheel(), vehicle.getSeat(), vehicle.getVehicleNo());
    }

    @Override
    public void deleteAll() {
        getJdbcTemplate().execute(DELETE_ALL);
    }

    /*
     * Illustration of BeanPropertyRowMapper<T> - spring default RowMapper<T> implementation
     */
    @Override
    public Optional<Vehicle> findByVehicleNo(String vehicleNo) {
        return Optional.ofNullable(getJdbcTemplate()
                .queryForObject(SELECT_ONE_SQL, BeanPropertyRowMapper.newInstance(Vehicle.class), vehicleNo));
    }


    /*
     * Illustration of RowCallbackHandler interface
     */
    @Override
    public Optional<Vehicle> findOneByColor(String color) {
        final Holder<Optional<Vehicle>> holder = new Holder<>(Optional.empty());
        getJdbcTemplate().query(SELECT_ONE_SQL,
                rs -> {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setVehicleNo(rs.getString("vehicle_no"));
                    vehicle.setColor(rs.getString("color"));
                    vehicle.setWheel(rs.getInt("wheel"));
                    vehicle.setSeat(rs.getInt("seat"));
                    holder.value = Optional.of(vehicle);
                }, color);

        return holder.value;
    }

    /*
     * Illustration of custom RowMapper<T> interface
     */
    @Override
    public List<Vehicle> findAll() {
        return getJdbcTemplate().query(SELECT_ALL_SQL, BeanPropertyRowMapper.newInstance(Vehicle.class));
    }

    /*
     * Illustration of mapping to single value
     */
    public Optional<String> getColor(String vehicleNo) {
        return Optional.ofNullable(getJdbcTemplate().queryForObject(SELECT_COLOR_SQL, String.class, vehicleNo));
    }

    /*
     * Illustration of mapping to single value
     */
    public Optional<Integer> countAll() {
        return Optional.ofNullable(getJdbcTemplate().queryForObject(COUNT_ALL_SQL, Integer.class));
    }

    private static void prepareStatement(PreparedStatement ps, Vehicle vehicle) throws SQLException {
        ps.setString(1, vehicle.getColor());
        ps.setInt(2, vehicle.getWheel());
        ps.setInt(3, vehicle.getSeat());
        ps.setString(4, vehicle.getVehicleNo());
    }
}
