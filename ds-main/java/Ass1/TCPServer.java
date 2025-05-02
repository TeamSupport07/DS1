
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.logging.*;

public class TCPServer {

    private static final Logger logger = Logger.getLogger(TCPServer.class.getName());
    private static final int PORT = 5000;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Server error", e);
        }
    }

    static class ClientHandler implements Runnable {

        private final Socket socket;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                out.println("Enter username:");
                String username = in.readLine();
                logger.info(username + " connected from " + socket.getInetAddress());
                out.println("Welcome, " + username + "! Type 'bye' to exit.");
                String msg;
                while ((msg = in.readLine()) != null) {
                    logger.info(username + ": " + msg);
                    out.println("Server Echo: " + msg);
                    if ("bye".equalsIgnoreCase(msg)) {
                        break;
                    }
                }
                logger.info(username + " disconnected.");
                socket.close();
            } catch (IOException e) {
                logger.log(Level.WARNING, "Client handler error", e);
            }
        }
    }
}
