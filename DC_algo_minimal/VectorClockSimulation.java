import java.util.*;
class VectorClock {
    private int[] clock;
    public VectorClock(int numProcesses) {
        this.clock = new int[numProcesses];
        Arrays.fill(this.clock, 0);
    }
    public void tick(int processIndex) {
        clock[processIndex]++;
    }
    public void sendEvent(int processIndex) {
        clock[processIndex]++;
    }
    public void receiveEvent(int processIndex, int[] receivedClock) {
        for (int i = 0; i < clock.length; i++) {
            clock[i] = Math.max(clock[i], receivedClock[i]);
        }
        clock[processIndex]++;
    }
    public int[] getTime() {
        return clock.clone();
    }
}
public class VectorClockSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();
        Map<Integer, VectorClock> processes = new HashMap<>();
        for (int i = 0; i < numProcesses; i++) {
            processes.put(i, new VectorClock(numProcesses));
        }
        while (true) {
            System.out.print("\nEnter process number to perform an event (-1 to exit): ");
            int processNum = scanner.nextInt();
            if (processNum == -1) {
                System.out.println("Exiting...");
                break;
            }
            int processIndex = processNum - 1;
            System.out.println("Choose event type for Process " + processNum + ":");
            System.out.println("1. Internal Event");
            System.out.println("2. Send Message");
            System.out.println("3. Receive Message");
            System.out.print("Enter choice (1-3): ");
            int choice = scanner.nextInt();
            VectorClock current = processes.get(processIndex);
            if (choice == 1) {
                current.tick(processIndex);
                System.out.println("Process " + processNum + " performed an internal event. New Clock: " + Arrays.toString(current.getTime()));
            } else if (choice == 2) {
                System.out.print("Enter receiver process number: ");
                int receiver = scanner.nextInt();
                int receiverIndex = receiver - 1;
                current.sendEvent(processIndex);
                int[] senderClock = current.getTime();
                processes.get(receiverIndex).receiveEvent(receiverIndex, senderClock);
                System.out.println("Process " + processNum + " sent a message to Process " + receiver);
            } else if (choice == 3) {
                System.out.print("Enter sender process number: ");
                int sender = scanner.nextInt();
                int senderIndex = sender - 1;
                int[] receivedClock = processes.get(senderIndex).getTime();
                current.receiveEvent(processIndex, receivedClock);
                System.out.println("Process " + processNum + " received a message from Process " + sender + ". Updated Clock: " + Arrays.toString(current.getTime()));
            }
        }
        System.out.println("\nFinal Vector Clocks for All Processes:");
        System.out.println("-----------------------------------");
        System.out.println("Process | Vector Clock");
        System.out.println("-----------------------------------");
        for (int i = 0; i < numProcesses; i++) {
            System.out.println("  P" + (i + 1) + "     |     " + Arrays.toString(processes.get(i).getTime()));
        }
        scanner.close();
    }
}
