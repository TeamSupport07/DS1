
// import java.util.*;
// public class RingElection {
//     public static void main(String[] args) {
//         Scanner sc = new Scanner(System.in);
//         System.out.print("Enter number of processes in the ring: ");
//         int n = sc.nextInt();
//         int[] processes = new int[n];
//         System.out.println("Enter process IDs:");
//         for (int i = 0; i < n; i++) {
//             processes[i] = sc.nextInt();
//         }
//         System.out.print("Enter the process ID that initiates the election: ");
//         int initiator = sc.nextInt();
//         List<Integer> election = new ArrayList<>();
//         int index = -1;
//         for (int i = 0; i < n; i++) {
//             if (processes[i] == initiator) {
//                 index = i;
//                 break;
//             }
//         }
//         System.out.println("\nElection message passing in ring:");
//         for (int i = 0; i < n; i++) {
//             int idx = (index + i) % n;
//             election.add(processes[idx]);
//             System.out.println("Process " + processes[idx] + " passes message.");
//         }
//         int coordinator = Collections.max(election);
//         System.out.println("\nProcess " + coordinator + " is elected as new coordinator.");
//         sc.close();
//     }
// }
import java.util.*;

public class RingElection {

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
                    System.out.println("Invalid choice.");
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
            startElectionFrom((id % n) + 1);
        }
    }

    static void recoverProcess(int id) {
        if (id < 1 || id > n || isAlive[id - 1]) {
            System.out.println("Invalid or already alive process.");
            return;
        }

        isAlive[id - 1] = true;
        System.out.println("Process " + id + " recovered.");

        System.out.println("Process " + id + " starts an election.");
        startElectionFrom(id);
    }

    static void startElectionFrom(int startId) {
        int[] electionList = new int[n];
        int count = 0;
        int current = startId;

        do {
            if (isAlive[current - 1]) {
                electionList[count++] = current;
            }
            current = (current % n) + 1;
        } while (current != startId);

        int newCoordinator = -1;
        for (int i = 0; i < count; i++) {
            if (electionList[i] > newCoordinator) {
                newCoordinator = electionList[i];
            }
        }

        coordinator = newCoordinator;
        System.out.println("New coordinator elected: " + coordinator);
    }
}
