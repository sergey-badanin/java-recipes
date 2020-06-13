package io.bsy.pure;

import java.util.Objects;

public class Vehicle {
    private String vehicleNo;
    private String color;
    private int wheel;
    private int seat;

    public Vehicle() {
    }

    public Vehicle(String vehicleNo, String color, int wheel, int seat) {
        this.vehicleNo = vehicleNo;
        this.color = color;
        this.wheel = wheel;
        this.seat = seat;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWheel() {
        return wheel;
    }

    public void setWheel(int wheel) {
        this.wheel = wheel;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return wheel == vehicle.wheel &&
                seat == vehicle.seat &&
                Objects.equals(vehicleNo, vehicle.vehicleNo) &&
                Objects.equals(color, vehicle.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleNo, color, wheel, seat);
    }
}
