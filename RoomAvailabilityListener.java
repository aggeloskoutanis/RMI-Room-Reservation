import java.rmi.Remote;
import java.rmi.RemoteException;

// This interface's method is implemented by the client application used by the server 
// application to notify the client when the availability is changed.

public interface RoomAvailabilityListener extends Remote
{
    void AvailabilityChanged(String roomName, int number) throws RemoteException;
}