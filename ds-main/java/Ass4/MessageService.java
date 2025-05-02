
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageService extends Remote {

    void sendMessage(String sender, String message) throws RemoteException;
}
