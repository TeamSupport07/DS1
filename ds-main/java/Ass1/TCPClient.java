
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.*;

public class TCPClient {

    private static final Logger logger = Logger.getLogger(TCPClient.class.getName());

    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;
        try (Socket socket = new Socket(host, port); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); Scanner scanner = new Scanner(System.in)) {
            logger.info("Connected to server at " + host + ":" + port);
            new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println("[Server]: " + line);
                        if (line.contains("bye")) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    logger.log(Level.WARNING, "Error reading from server", e);
                }
            }).start();
            while (true) {
                String userInput = scanner.nextLine();
                out.println(userInput);
                if ("bye".equalsIgnoreCase(userInput)) {
                    break;
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Client error", e);
        }
    }
}
