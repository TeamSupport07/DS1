
import java.rmi.Naming;
import java.util.Scanner;

public class RMIClient {

    public static void main(String[] args) {
        try {
            MessageService stub = (MessageService) Naming.lookup("rmi://localhost/MessageService");
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter your process name (e.g., Process 1): ");
            String sender = sc.nextLine();
            System.out.print("Enter message to send: ");
            String msg = sc.nextLine();
            stub.sendMessage(sender, msg);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
