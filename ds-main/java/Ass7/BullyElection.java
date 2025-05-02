// import java.util.Scanner;
// public class BullyElection {
//     public static void main(String[] args) {
//         Scanner sc = new Scanner(System.in);
//         System.out.print("Enter number of processes: ");
//         int n = sc.nextInt();
//         int[] processes = new int[n];
//         boolean[] alive = new boolean[n];
//         System.out.println("Enter process IDs:");
//         for (int i = 0; i < n; i++) {
//             processes[i] = sc.nextInt();
//             alive[i] = true;
//         }
//         System.out.print("Enter the process ID that detects coordinator failure: ");
//         int initiator = sc.nextInt();
//         System.out.println("\nElection initiated by process " + initiator);
//         int newCoordinator = initiator;
//         for (int i = 0; i < n; i++) {
//             if (processes[i] > initiator && alive[i]) {
//                 System.out.println("Process " + initiator + " sends election to " + processes[i]);
//                 System.out.println("Process " + processes[i] + " replies OK");
//                 newCoordinator = Math.max(newCoordinator, processes[i]);
//             }
//         }
//         System.out.println("\nProcess " + newCoordinator + " becomes the new coordinator.");
//         sc.close();
//     }
// }

import java.util.*;

public class BullyElection {

    static int n;
    static int[] processes;
    static boolean[] isAlive;
    static int coordinator = -1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        n = sc.nextInt();
        processes = new int[n];
        isAlive = new boolean[n];

        for (int i = 0; i < n; i++) {
            processes[i] = i + 1;
            isAlive[i] = true;
        }

        coordinator = n;
        System.out.println("Initial coordinator is: " + coordinator);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Kill a process");
            System.out.println("2. Recover a process");
            System.out.println("3. Display coordinator");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("Enter process ID to kill: ");
                    int killId = sc.nextInt();
                    killProcess(killId);
                    break;
                case 2:
                    System.out.print("Enter process ID to recover: ");
                    int recoverId = sc.nextInt();
                    recoverProcess(recoverId);
                    break;
                case 3:
                    System.out.println("Current coordinator is: " + coordinator);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void killProcess(int id) {
        if (id < 1 || id > n || !isAlive[id - 1]) {
            System.out.println("Invalid or already dead process.");
            return;
        }

        isAlive[id - 1] = false;
        System.out.println("Process " + id + " killed.");

        if (id == coordinator) {
            System.out.println("Coordinator down. Starting election...");
            startElection();
        }
    }

    static void recoverProcess(int id) {
        if (id < 1 || id > n || isAlive[id - 1]) {
            System.out.println("Invalid or already alive process.");
            return;
        }

        isAlive[id - 1] = true;
        System.out.println("Process " + id + " recovered.");

        startElectionFrom(id);
    }

    static void startElection() {
        for (int i = n - 1; i >= 0; i--) {
            if (isAlive[i]) {
                coordinator = processes[i];
                System.out.println("Process " + coordinator + " becomes new coordinator.");
                break;
            }
        }
    }

    static void startElectionFrom(int id) {
        boolean foundHigher = false;
        for (int i = id; i < n; i++) {
            if (isAlive[i]) {
                foundHigher = true;
                break;
            }
        }

        if (foundHigher) {
            startElection();
        } else {
            coordinator = id;
            System.out.println("Recovered Process " + id + " becomes coordinator (no higher process alive).");
        }
    }
}
