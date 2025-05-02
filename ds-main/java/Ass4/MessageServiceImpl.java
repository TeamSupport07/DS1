
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MessageServiceImpl extends UnicastRemoteObject implements MessageService {

    public MessageServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void sendMessage(String sender, String message) throws RemoteException {
        System.out.println("Message received from " + sender + ": " + message);
    }
}
