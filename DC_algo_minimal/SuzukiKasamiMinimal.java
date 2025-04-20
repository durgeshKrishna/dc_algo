import java.util.*;

public class SuzukiKasamiMinimal{
    static int numProcesses;
    static int tokenHolder;
    static int[] RN;
    static int[] LN;
    static List<Integer> tokenQueue = new ArrayList<>();
    static Map<Integer, Integer> executionTicks = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        numProcesses = scanner.nextInt();
        RN = new int[numProcesses];
        LN = new int[numProcesses];

        System.out.print("Enter initial token holder: ");
        tokenHolder = scanner.nextInt();

        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Execution ticks for Process " + i + ": ");
            executionTicks.put(i, scanner.nextInt());
        }

        scanner.nextLine(); // consume newline
        while (true) {
            System.out.print("Processes requesting CS (-1 to exit): ");
            String input = scanner.nextLine();
            if (input.equals("-1")) break;

            String[] ids = input.trim().split("\\s+");
            List<Integer> requests = new ArrayList<>();
            for (String id : ids) {
                requests.add(Integer.parseInt(id));
            }
            handleRequests(requests);
        }

        scanner.close();
    }

    public static void handleRequests(List<Integer> processes) {
        for (int pid : processes) {
            RN[pid]++;
            if (RN[pid] == LN[pid] + 1 && !tokenQueue.contains(pid)) {
                tokenQueue.add(pid);
            }
        }

        while (!tokenQueue.isEmpty()) {
            int next = tokenQueue.get(0);
            if (tokenHolder == next) {
                executeCS(next);
            } else if (RN[next] == LN[next] + 1) {
                passToken(next);
            } else {
                tokenQueue.remove(0);
            }
        }
    }

    public static void passToken(int toProcess) {
        tokenHolder = toProcess;
        tokenQueue.remove(Integer.valueOf(toProcess));
        executeCS(toProcess);
    }

    public static void executeCS(int pid) {
        int ticks = executionTicks.get(pid);
        for (int i = 1; i <= ticks; i++) {
            System.out.println("Process " + pid + " Tick " + i);
        }
        LN[pid] = RN[pid];
        checkWaitingProcesses();
    }

    public static void checkWaitingProcesses() {
        while (!tokenQueue.isEmpty()) {
            int next = tokenQueue.get(0);
            if (RN[next] == LN[next] + 1) {
                passToken(next);
                return;
            } else {
                tokenQueue.remove(0);
            }
        }
    }
}
