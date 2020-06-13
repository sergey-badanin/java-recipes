package io.bsy.springjdbctemplate;

import io.bsy.pure.Vehicle;
import io.bsy.pure.VehicleDao;
import io.bsy.utils.Holder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/*
 * Spring documentation on JdbcTemplates
 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/package-summary.html#package.description
 */
public class JdbcTemplateVehicleDao implements VehicleDao {

    private final DataSource dataSource;

    public JdbcTemplateVehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /*
     * Illustration of PreparedStatementCreator interface
     */
    @Override
    public void insert(Vehicle vehicle) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update((conn) -> {
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
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.batchUpdate(INSERT_SQL, vehicles, vehicles.size(), JdbcTemplateVehicleDao::prepareStatement);
    }

    /*
     * Illustration of direct parameter values interface
     */
    @Override
    public void update(Vehicle vehicle) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.update(UPDATE_SQL, vehicle.getColor(), vehicle.getWheel(), vehicle.getSeat(), vehicle.getVehicleNo());
    }

    @Override
    public void delete(Vehicle vehicle) {

    }

    @Override
    public void deleteAll() {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        template.execute(DELETE_ALL);
    }

    /*
     * Illustration of BeanPropertyRowMapper<T> - spring default RowMapper<T> implementation
     */
    @Override
    public Vehicle findByVehicleNo(String vehicleNo) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        return template.queryForObject(SELECT_ONE_SQL, BeanPropertyRowMapper.newInstance(Vehicle.class), vehicleNo);
    }


    /*
     * Illustration of RowCallbackHandler interface
     */
    @Override
    public Optional<Vehicle> findOneByColor(String color) {
        JdbcTemplate template = new JdbcTemplate(dataSource);

        final Holder<Optional<Vehicle>> holder = new Holder<>(Optional.empty());
        template.query(SELECT_ONE_SQL,
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
        JdbcTemplate template = new JdbcTemplate(dataSource);
        return template.query(SELECT_ALL_SQL, BeanPropertyRowMapper.newInstance(Vehicle.class));
    }

    /*
     * Illustration of mapping to single value
     */
    public Optional<String> getColor(String vehicleNo) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        return Optional.ofNullable(template.queryForObject(SELECT_COLOR_SQL, String.class, vehicleNo));
    }

    /*
     * Illustration of mapping to single value
     */
    public Optional<Integer> countAll() {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        return Optional.ofNullable(template.queryForObject(COUNT_ALL_SQL, Integer.class));
    }

    private static void prepareStatement(PreparedStatement ps, Vehicle vehicle) throws SQLException {
        ps.setString(1, vehicle.getColor());
        ps.setInt(2, vehicle.getWheel());
        ps.setInt(3, vehicle.getSeat());
        ps.setString(4, vehicle.getVehicleNo());
    }
}
