import java.io.Serializable;
import java.util.Hashtable;

public class Guest implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String clientName;
    float totalCost;
    Hashtable<String, Integer> room = new Hashtable<String, Integer>();

    /* Default Constructor */
    public Guest() {
        clientName = null;
        totalCost = 0;
        room.put("A", 0);
        room.put("B", 0);
        room.put("C", 0);
        room.put("D", 0);
        room.put("E", 0);

    }

    /* a Constructor */
    public Guest(String n) {
        clientName = n;
        totalCost = 0;
        room.put("A", 0);
        room.put("B", 0);
        room.put("C", 0);
        room.put("D", 0);
        room.put("E", 0);

    }

    /* Copy Constructor */
    public Guest(Guest g) {
        clientName = g.clientName;
        totalCost = g.totalCost;
        room = new Hashtable<String, Integer>(g.room);
    }

    /* Register a reservetion */
    public void setReservation(String cn, char type, int number) {
        clientName = cn;

        String key = Character.toString(type);
        if (room.containsKey(key)) {
            room.put(key, room.get(key) + number);
        }
        totalCost = room.get("A") * 50 + room.get("B") * 70 + room.get("C") * 80 + room.get("D") * 120
                + room.get("E") * 150;

    }

    public void printGuestInfo() {
        System.out.println("The client " + clientName + " has a booking with the following rooms:");
        if (room.get("A") > 0) {
            System.out.println(room.get("A") + " A rooms");
        }
        if (room.get("B") > 0) {
            System.out.println(room.get("B") + " B rooms");
        }
        if (room.get("C") > 0) {
            System.out.println(room.get("C") + " C rooms");
        }
        if (room.get("D") > 0) {
            System.out.println(room.get("D") + " D rooms");
        }
        if (room.get("E") > 0) {
            System.out.println(room.get("E") + " E rooms");
        }
        System.out.println("The total cost of the reservation is: " + totalCost + " Euro.");
    }

}