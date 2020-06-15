package io.bsy.springjdbctemplate;

import io.bsy.pure.Vehicle;
import io.bsy.pure.VehicleDao;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JdbcTemplateNamedParamsVehicleDao extends NamedParameterJdbcDaoSupport implements VehicleDao {
    private static final String INSERT_SQL = "INSERT INTO vehicle (color, wheel, seat, vehicle_no) " +
            "VALUES (:color, :wheel, :seat, :vehicleNo)";
    private static final String UPDATE_SQL = "UPDATE vehicle SET color=:color, wheel=:wheel, seat=:seat " +
            "WHERE vehicle_no=:vehicleNo";

    @Override
    public void insert(Vehicle vehicle) {
        getNamedParameterJdbcTemplate().update(INSERT_SQL, toParameterMap(vehicle));
    }

    /*
     * Illustrate SqlParameterSource
     */
    @Override
    public void update(Vehicle vehicle) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(vehicle);

        getNamedParameterJdbcTemplate().update(UPDATE_SQL, parameterSource);
    }

    @Override
    public void insert(Collection<Vehicle> vehicles) {
        SqlParameterSource[] sources = vehicles.stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);
        getNamedParameterJdbcTemplate().batchUpdate(INSERT_SQL, sources);
    }

    private Map<String, Object> toParameterMap(Vehicle vehicle) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("vehicleNo", vehicle.getVehicleNo());
        parameters.put("color", vehicle.getColor());
        parameters.put("wheel", vehicle.getWheel());
        parameters.put("seat", vehicle.getSeat());
        return parameters;
    }
}
