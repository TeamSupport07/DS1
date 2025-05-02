
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            MessageServiceImpl obj = new MessageServiceImpl();
            Naming.rebind("MessageService", obj);
            System.out.println("MPI-style RMI Server is ready.");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
