
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {

    private static final int PORT = 5000;
    private static final Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) {
        System.out.println("Server started on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket);
                clients.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {

        private Socket socket;
        private PrintWriter out;
        private String name;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println("Enter your name:");
                name = in.readLine();
                broadcast(name + " has joined the chat.");
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("bye")) {
                        break;
                    }
                    broadcast(name + ": " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
                clients.remove(this);
                broadcast(name + " has left the chat.");
            }
        }

        void broadcast(String message) {
            for (ClientHandler client : clients) {
                if (client.out != null) {
                    client.out.println(message);
                }
            }
        }
    }
}
