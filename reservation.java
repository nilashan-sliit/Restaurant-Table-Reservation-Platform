package restuarant;

public class Reservation {
    private String resID;
    private String userID;
    private String tableID;
    private String date;
    private String status;

    // Constructor, Getters, and Setters (Encapsulation)
    public Reservation(String resID, String userID, String tableID, String date, String status) {
        this.resID = resID;
        this.userID = userID;
        this.tableID = tableID;
        this.date = date;
        this.status = status;
    }

    // Calculate fee based on User type (Polymorphism)
    public double calculateCancellationFee(User user, double billAmount) {
        if (user instanceof VIPUser) {
            return 0.0; // VIPs get free cancellation
        } else {
            return billAmount * 0.10; // Regular users pay 10%
        }
    }

    // add Getters and Setters
}
