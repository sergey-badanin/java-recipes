package io.bsy.pure;

public class Main {

    public static void main(String... args) {
        var ds = DataSourceFactory.getDataSource();
        var dao = new JdbcVehicleDao(ds);

        dao.insert(new Vehicle("HH666H777", "Black", 4, 4));
        dao.insert(new Vehicle("EE444E555", "White", 2, 10));

        var vehicles = dao.findAll();
        for (var vehicle : vehicles) {
            System.out.println(vehicle.getVehicleNo());
        }
    }
}
