import java.rmi.RemoteException;
import java.util.Vector;
public interface HRInterface extends java.rmi.Remote {

    /* Part A */
    public Vector list(String hostname) throws java.rmi.RemoteException;
    public float book(String hostname, char type, int number, String name) throws java.rmi.RemoteException;
    public Guest[] guests(String hostname) throws java.rmi.RemoteException;
    public Guest cancel(String hostname, char type, int number, String name) throws java.rmi.RemoteException;


    /* Part B: functions to add listeners */

    /* Method that adds a Guest in the listener's list */
     void addAvailabilityListener(RoomAvailabilityListener addClient, String type, Integer number) throws RemoteException;

     /* Method that removes a Guest from the listener's list */
     void removeAvailabilityListener(RoomAvailabilityListener removeClient, String type) throws RemoteException;

    //  /* Method that gets the room's availability */
    //  int getRoomAvailability(Character type) throws RemoteException;
}