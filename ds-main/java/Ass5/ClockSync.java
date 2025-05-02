package berkeley;

import java.util.*;

public class ClockSync {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of slave machines: ");
        int n = sc.nextInt();
        int[] slaveClocks = new int[n];
        System.out.println("Enter the time (in ms) for each slave machine:");
        for (int i = 0; i < n; i++) {
            System.out.print("Slave " + (i + 1) + ": ");
            slaveClocks[i] = sc.nextInt();
        }
        System.out.print("Enter the master's clock time (in ms): ");
        int masterClock = sc.nextInt();
        int sum = masterClock;
        for (int t : slaveClocks) {
            sum += t;
        }
        int avgTime = sum / (n + 1);
        System.out.println("\nAverage time calculated by master: " + avgTime + " ms");
        int masterDiff = avgTime - masterClock;
        System.out.println("Master needs to adjust by: " + masterDiff + " ms");
        for (int i = 0; i < n; i++) {
            int diff = avgTime - slaveClocks[i];
            System.out.println("Slave " + (i + 1) + " needs to adjust by: " + diff + " ms");
        }
        sc.close();
    }
}
