import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException; 
import java.rmi.NotBoundException; 
import java.util.Vector;
import java.util.Scanner;

public class HRClient extends UnicastRemoteObject implements RoomAvailabilityListener{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    static HRInterface c;
    static String host;
    static String type;
    static String name;
    static int room_s;
    public HRClient() throws RemoteException {
        super();
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            c = (HRInterface) Naming.lookup("rmi://localhost/HRService");
           

            if (args.length == 0)
            {
                System.out.println("Please run the program with one of the following ways:\njava HRClient list <hostname>\njava HRClient book <hostname> <type> <number> <name> \njava HRClient guests <hostname>\njava HRClient cancel <hostname> <type> <number> <name> \n");
            }
            if(args.length==2 && args[0].equals("list"))
            {
                host = args[1];
                Vector<Integer> list = c.list("list");
                System.out.println("The available rooms are:\n"+ list.get(0) +" rooms of type A costing 50 euro per night. \n"+ list.get(1) +" rooms of type B costing 70 euro per night. \n"+ list.get(2) +" rooms of type C costing 80 euro per night. \n"+ list.get(3) +" rooms of type D costing 120 euro per night. \n"+ list.get(4) +" rooms of type E costing 150 euro per night. \n");
            }
            if(args.length == 5 && args[0].equals("book")){
                room_s= Integer.parseInt(args[3]);
                host = args[1];
                type = args[2];
                name = args[4];
                float totalcost=0;
                Boolean answ = false;
                /*  Calls book function */ 
                totalcost = c.book(args[1], args[2].charAt(0), Integer.parseInt(args[3]),  args[4]);
                while(!answ){
                    if(totalcost > 0){
                        System.out.println("Booking was succesful!The total cost of this booking is: " + totalcost);
                        answ= true;
                    }
                    if(totalcost < 0){
                        String a = null;
                        int nextInt=0;
                        
                        Scanner keyboard = new Scanner(System.in);
                        if(totalcost == -50){
                            System.out.println("Currently this type of rooms are not available.");
                            answ=true;
                            /* Part B */
                            System.out.println("Unfortunately, there are not any type " + args[2] + " rooms available.\nDo you want to register in the waiting list?\n Enter 1 for YES, 0 for NO.");
                            
                            /* Gets answer from guest */
                            if (keyboard.hasNextLine()){
                                a = keyboard.nextLine();
                                nextInt = Integer.parseInt(a);
                            }
                        }
                        else if(totalcost != -50){
                            int roomsleft = Math.round(-1*totalcost);
                            System.out.println("Unfortunately only " + roomsleft + " of this type of rooms are available. Would you like to book them?Press 1 for YES or 0 for NO.");
                            if (keyboard.hasNextLine()){
                                a = keyboard.nextLine();
                                nextInt = Integer.parseInt(a);
                            }
                            
                            /* Calls again the booking function with the new number */
                            if(nextInt == 1){
                                totalcost = c.book(args[1], args[2].charAt(0), roomsleft,  args[4]);
                                answ = true;
                                System.out.println("Booking was succesful!The total cost of this booking is: " + totalcost);
                                /* Part B */
                                System.out.println("Do you want to be added in the waiting list for the remaining rooms?\n Enter 1 for YES, 0 for NO.");
                                /* Gets answer from guest */
                                if (keyboard.hasNextLine()){
                                    a = keyboard.nextLine();
                                    nextInt = Integer.parseInt(a);
                                } 
                            }
                            else{
                                System.out.println("Sorry for the incovenience.\n");
                            }
                            //keyboard.close();
                        }
            
                        /* add client to the waiting list */
                        HRClient client = new HRClient();
                        HRClient.room_s = Integer.parseInt(args[3]);
                        c.addAvailabilityListener(client, args[2], Integer.parseInt(args[3]));
                        System.out.println("Listener registered");
                        Thread.sleep(30000);
                        c.removeAvailabilityListener(client, args[2]);
                        System.out.println("Listener unregistered");

                    }
                }
            }
            if(args.length == 2 && args[0].equals("guests")){
                /* Prints the guest list */
                host = args[1];
                Guest[] list = c.guests(args[1]);
                for(int i=0;i<list.length;i++){
                   list[i].printGuestInfo();
                }
                
            }
            if(args.length == 5 && args[0].equals("cancel")){
                Guest canceled = c.cancel(args[1], args[2].charAt(0), Integer.parseInt(args[3]),  args[4]);
                if(canceled != null){
                    canceled.printGuestInfo();
                }
                else{
                    System.out.println("You dont seem to have a reservation of this type.");
                }
            }
        } 
        catch (MalformedURLException murle) { 
            System.out.println(); 
            System.out.println(
              "MalformedURLException"); 
            System.out.println(murle); 
        } 
        catch (RemoteException re) { 
            System.out.println(); 
            System.out.println(
                        "RemoteException"); 
            System.out.println(re); 
        } 
        catch (NotBoundException nbe) { 
            System.out.println(); 
            System.out.println(
                       "NotBoundException"); 
            System.out.println(nbe); 
        } 
    }
    
    /* Informs the client about the updated room availability */
    public void AvailabilityChanged(String roomName, int number) throws RemoteException
    {
        String  a=null; 
        int nextInt =0;
        float cost=0;
        System.out.println(number + " rooms of type " + roomName + " are now available!");
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Do you want to book the rooms?1 YES or 0 NO");
        
        // br.close();
        try{
            /* Gets answer from guest */
            nextInt = Integer.parseInt(br.readLine());
            // System.out.println(nextInt);
            if(nextInt == 1){
                cost = c.book(host, roomName.charAt(0), room_s, name);
                System.out.println("Booking was succesful!The total cost of this booking is: " + cost);
            }
            
            
        }catch(final IOException e)
        {
            e.printStackTrace();
        }
        
    }
}
