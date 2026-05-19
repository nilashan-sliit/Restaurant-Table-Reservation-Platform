package com.example.Tablemanagment.model;

public class VIPTable extends Table {

    public VIPTable() {
    }

    public VIPTable(Long id,
                    int capacity,
                    String location,
                    String reservationDate) {

        super(id,
                "VIP",
                capacity,
                location,
                reservationDate);
    }

    @Override
    public String display() {

        return "ID: " + getId() +
                ", Type: " + getTableType() +
                ", Capacity: " + getCapacity() +
                ", Location: " + getLocation() +
                ", Reservation Date: " + getReservationDate();
    }
}