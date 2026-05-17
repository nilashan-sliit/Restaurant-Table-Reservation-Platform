package com.example.Tablemanagment.model;

public class StandardTable extends Table {

    public StandardTable() {
    }

    public StandardTable(Long id,
                         int capacity,
                         String location,
                         String reservationDate) {

        super(id,
                "NORMAL",
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