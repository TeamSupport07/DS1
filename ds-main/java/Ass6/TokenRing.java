package tokenring;

import java.util.Scanner;

public class TokenRing {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes in the ring: ");
        int n = sc.nextInt();
        boolean[] hasToken = new boolean[n];
        System.out.print("Enter the process number (0 to " + (n - 1) + ") that initially holds the token: ");
        int tokenHolder = sc.nextInt();
        hasToken[tokenHolder] = true;
        System.out.print("Enter the number of times to circulate the token: ");
        int rounds = sc.nextInt();
        System.out.println("\nStarting Token Ring Simulation...\n");
        for (int r = 1; r <= rounds; r++) {
            System.out.println("Round " + r + ":");
            for (int i = 0; i < n; i++) {
                if (hasToken[i]) {
                    System.out.println("Process " + i + " has the token and is entering the critical section.");
                    hasToken[i] = false;
                    hasToken[(i + 1) % n] = true;
                    break;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        sc.close();
        System.out.println("\nToken Ring Simulation Ended.");
    }
}
