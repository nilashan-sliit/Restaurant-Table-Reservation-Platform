package com.example.Tablemanagment.model;
public class Table {

    private Long id;
    private String tableType;
    private int capacity;
    private String location;
    private String reservationDate;

    public Table() {
    }

    public Table(Long id,
                 String tableType,
                 int capacity,
                 String location,
                 String reservationDate) {

        this.id = id;
        this.tableType = tableType;
        this.capacity = capacity;
        this.location = location;
        this.reservationDate = reservationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }
    public String display() {

        return "ID: " + id +
                ", Type: " + tableType +
                ", Capacity: " + capacity +
                ", Location: " + location +
                ", Reservation Date: " + reservationDate;
    }
}