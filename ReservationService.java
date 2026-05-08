package restuarant;

import java.io.*;
import java.util.*;

public class ReservationService {
    private final String filePath = "data/reservations.txt";

    // Create: Save new reservation
    public boolean saveReservation(Reservation res) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath, true))) {
            out.println(res.getResID() + "|" + res.getUserID() + "|" + res.getTableID() + "|" + res.getDate() + "|" + res.getStatus());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Abstraction: Check if a table is free
    public boolean isAvailable(String tableID, String date) {
        return true;
    }

    // Read: Get reservations for a specific user
    public List<Reservation> getMyReservations(String userID) {
        List<Reservation> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data[1].equals(userID)) {
                    list.add(new Reservation(data[0], data[1], data[2], data[3], data[4]));
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }
}

