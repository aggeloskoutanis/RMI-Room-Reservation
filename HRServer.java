import java.rmi.Naming;


public class HRServer{
    public HRServer() {
        try {
          HRInterface c = new HRImpl();
          Naming.rebind("rmi://localhost/HRService", c);
        } catch (Exception e) {
          System.out.println("Trouble: " + e);
        }
      }
   
      public static void main(String args[]) {
        new HRServer();
        
      }
}