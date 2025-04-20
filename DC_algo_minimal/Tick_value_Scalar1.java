import java.util.*;
class LamportClock {
    private int clock;
    private int tickValue;
    public LamportClock(int tickValue) {
        this.clock = 0;
        this.tickValue = tickValue;
    }
    public void tick() {
        clock += tickValue;
    }
    public int sendEvent() {
        clock += tickValue;
        return clock;
    }
    public void receiveEvent(int receivedTime) {
        if (receivedTime > clock) {
            clock = receivedTime + 1;
        } else {
            clock += tickValue;
        }
    }
    public int getTime() {
        return clock;
    }
}
public class Tick_value_Scalar1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();
        Map<Integer, LamportClock> processes = new HashMap<>();
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter tick value for Process " + (i + 1) + ": ");
            int tickValue = scanner.nextInt();
            processes.put(i, new LamportClock(tickValue));
        }
        while (true) {
            System.out.print("\nEnter process number to perform event (-1 to exit): ");
            int processNum = scanner.nextInt();
            if (processNum == -1) break;
            System.out.println("Choose event type for Process " + processNum + ":");
            System.out.println("1. Internal Event");
            System.out.println("2. Send Message");
            System.out.println("3. Receive Message");
            System.out.print("Enter choice (1-3): ");
            int choice = scanner.nextInt();
            int processIndex = processNum - 1;
            LamportClock current = processes.get(processIndex);
            if (choice == 1) {
                current.tick();
            } else if (choice == 2) {
                System.out.print("Enter receiver process number: ");
                int receiverIndex = scanner.nextInt() - 1;
                int sentTime = current.sendEvent();
                processes.get(receiverIndex).receiveEvent(sentTime);
            } else if (choice == 3) {
                System.out.print("Enter sender process number: ");
                int senderIndex = scanner.nextInt() - 1;
                int receivedTime = processes.get(senderIndex).getTime();
                current.receiveEvent(receivedTime);
            }
        }
        System.out.println("\nFinal Lamport Clock values:");
        for (int i = 0; i < numProcesses; i++) {
            System.out.println("Process P" + (i + 1) + ": " + processes.get(i).getTime());
        }
        scanner.close();
    }
}