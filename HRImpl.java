import java.rmi.RemoteException;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class HRImpl extends java.rmi.server.UnicastRemoteObject implements HRInterface {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  Vector<Integer> rooms = new Vector<Integer>(5);
  ConcurrentHashMap<String, Guest> reservationsList = new ConcurrentHashMap<String, Guest>();
  
  /* Part B: Lists for every type of room */
  ConcurrentHashMap<String, ConcurrentHashMap<RoomAvailabilityListener, Integer>> availabilityLists = new ConcurrentHashMap<String, ConcurrentHashMap<RoomAvailabilityListener, Integer>>();
  
  public HRImpl() throws java.rmi.RemoteException {
    super();

    /* Initiatilizes the starting availability */
    rooms.add(30);
    rooms.add(45);
    rooms.add(25);
    rooms.add(10);
    rooms.add(5);

    /* Initializes empty lists for every  type of room */
    availabilityLists.put("A", new ConcurrentHashMap<RoomAvailabilityListener, Integer>());
    availabilityLists.put("B", new ConcurrentHashMap<RoomAvailabilityListener, Integer>());
    availabilityLists.put("C", new ConcurrentHashMap<RoomAvailabilityListener, Integer>());
    availabilityLists.put("D", new ConcurrentHashMap<RoomAvailabilityListener, Integer>());
    availabilityLists.put("E", new ConcurrentHashMap<RoomAvailabilityListener, Integer>());


  }

  /* Part B: Notify Guests for room availability */
  private synchronized void notifyAvailabilityListeners(String type, int number){
    
        for (RoomAvailabilityListener lListener : availabilityLists.get(type).keySet())
        {
            try
            {
                lListener.AvailabilityChanged(type, number);
                availabilityLists.get(type).remove(lListener);
            }
            catch (RemoteException aInE)
            {
                // If the listener is not responding and the call to the listener's method fails, 
                // remove listener from the list
            	System.out.println("removing");
            	availabilityLists.get(type).remove(lListener);
                
            }
          }
         
        
  }


  public Vector list(String hostname) {
    return rooms;
  }

  public float book(String hostname, char type, int number, String name) {
    /* Checks if rooms are available */

    switch (type) {
      case 'A':
        /* If 0 rooms of this type are left */
        if (rooms.get(0) == 0) {
          return -50;
        } else if (number > rooms.get(0)) {
          /* less rooms available */
          return -1 * rooms.get(0);
        } else {
          /* updates availability */
          rooms.set(0, rooms.get(0) - number);
        }
        break;
      case 'B':
        if (rooms.get(1) == 0) {
          return -50;
        } else if (number > rooms.get(1)) {
          /* less rooms available */
          return -1 * rooms.get(1);
        } else {
          /* updates availability */
          rooms.set(1, rooms.get(1) - number);
        }
        break;
      case 'C':
        if (rooms.get(2) == 0) {
          return -50;
        } else if (number > rooms.get(2)) {
          /* less rooms available */
          return -1 * rooms.get(2);
        } else {
          /* updates availability */
          rooms.set(2, rooms.get(2) - number);
        }
        break;
      case 'D':
        if (rooms.get(3) == 0) {
          return -50;
        } else if (number > rooms.get(3)) {
          /* less rooms available */
          return -1 * rooms.get(3);
        } else {
          /* updates availability */
          rooms.set(3, rooms.get(3) - number);
        }
        break;
      case 'E':
        if (rooms.get(4) == 0) {
          return -50;
        } else if (number > rooms.get(4)) {
          /* less rooms available */
          return -1 * rooms.get(4);
        } else {
          /* updates availability */
          rooms.set(4, rooms.get(4) - number);
        }
        break;
      default:

    }

    /*
     * Searches if guest is new, if he yes, create a new entry on reservation's list
     */
    if (!reservationsList.containsKey(name)) {
      reservationsList.put(name, new Guest(name));
    }

    /* makes the new reservations for the guest */
    reservationsList.get(name).setReservation(name, type, number);
    reservationsList.get(name).printGuestInfo();

    return reservationsList.get(name).totalCost;
  }

  public Guest[] guests(String hostname) {
    int size = reservationsList.size();
    /* Allocating guest list */
    Guest[] guest_list = new Guest[size];

    /* Copying ConcurrentHashMap values into Vector */
    Set<String> keys = reservationsList.keySet();
    int i = 0;
    for (String key : keys) {
      guest_list[i] = reservationsList.get(key);
      i += 1;
    }

    return guest_list;
  }

  public synchronized Guest cancel(String hostname, char type, int number, String name) {
    /*
     * Searches if guest is new, if he yes, create a new entry on reservation's list
     */
    if (reservationsList.containsKey(name)) {
      Guest cancel_res = new Guest(reservationsList.get(name));
      String i = Character.toString(type);
      /* Check if this guest has reservation for this type */
      if (cancel_res.room.get(i) > 0) {
        /* Gets number of reserved room to be canceled */
        int temp = cancel_res.room.get(i) - (cancel_res.room.get(i) - number);
        /* Renews reservation list of guest */
        reservationsList.get(name).setReservation(name, type, -1 * number);
        switch (type) {
          case 'A':
            /* adds canceled rooms in the available rooms */
            rooms.set(0, rooms.get(0) + temp);
            System.out.println("Server Notification: " + rooms.get(0) + " Rooms of type A are now available!");
            notifyAvailabilityListeners(i, number);
            break;
          case 'B':
            /* adds canceled rooms in the available rooms */
            rooms.set(1, rooms.get(1) + temp);
            System.out.println("Server Notification: " + rooms.get(1) + " Rooms of type B are now available!");
            notifyAvailabilityListeners(i, number);
            break;
          case 'C':
            /* adds canceled rooms in the available rooms */
            rooms.set(2, rooms.get(2) + temp);
            System.out.println("Server Notification: " + rooms.get(2) + " Rooms of type C are now available!");
            notifyAvailabilityListeners(i, number);
            break;
          case 'D':
            /* adds canceled rooms in the available rooms */
            rooms.set(3, rooms.get(3) + temp);
            System.out.println("Server Notification: " + rooms.get(3) + " Rooms of type D are now available!");
            notifyAvailabilityListeners(i, number);
            break;
          case 'E':
            /* adds canceled rooms in the available rooms */
            rooms.set(4, rooms.get(4) + temp);
            System.out.println("Server Notification: " + rooms.get(4) + " Rooms of type E are now available!");
            notifyAvailabilityListeners(i, number);
            break;
          default:
        }

      } else {
        /* This guest doesnt have a reservation for this type of rooms */
        return null;
      }
      cancel_res = reservationsList.get(name);
      return cancel_res;
    } else {
      /* Reservation for this name isn't found */
      return null;
    }
  }

  @Override
  public synchronized void addAvailabilityListener(RoomAvailabilityListener addClient, String type, Integer number) throws RemoteException {
    
    if(!availabilityLists.get(type).containsKey(addClient)){
      availabilityLists.get(type).put(addClient, number);
    }
    System.out.println("Listener added!");
  }

  @Override
  public synchronized void removeAvailabilityListener(RoomAvailabilityListener removeClient, String type) throws RemoteException {
    
    if(availabilityLists.get(type).containsKey(removeClient)){
      availabilityLists.get(type).remove(removeClient);
    }
    System.out.println("Listener removed!");
  }

  // @Override
  // public int getRoomAvailability(Character type) throws RemoteException {
    
  //   int rooms_avail = 0;
  //   switch (type) {
  //     case 'A':
  //     rooms_avail = rooms.get(0);
  //       break;
  //     case 'B':
  //     rooms_avail = rooms.get(1);
  //       break;
  //     case 'C':
  //     rooms_avail = rooms.get(2);
  //       break;
  //     case 'D':
  //     rooms_avail = rooms.get(3);
  //       break;
  //     case 'E':
  //     rooms_avail = rooms.get(4);
  //       break;
  //     default:

  //   }
  //   return rooms_avail;
  // }
}