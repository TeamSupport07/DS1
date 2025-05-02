package distributedchat;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); Scanner scanner = new Scanner(System.in)) {
            System.out.println(in.readLine()); // "Enter your name:"
            String name = scanner.nextLine();
            out.println(name);
            Thread reader = new Thread(() -> {
                String msg;
                try {
                    while ((msg = in.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            });
            reader.start();
            while (true) {
                String msg = scanner.nextLine();
                out.println(msg);
                if (msg.equalsIgnoreCase("bye")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Could not connect to server.");
        }
    }
}
