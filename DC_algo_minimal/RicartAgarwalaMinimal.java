import java.util.*;

public class RicartAgarwalaMinimal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the total number of sites: ");
        int totalSites = scanner.nextInt();
        System.out.print("Enter the number of requesting sites: ");
        int requestingSites = scanner.nextInt();

        int[] timestamps = new int[totalSites];
        int[] requestSites = new int[requestingSites];
        int[] requestTimestamps = new int[requestingSites];
        int[] orderedSites = new int[requestingSites];
        ArrayList<Integer>[] deferredReplies = new ArrayList[totalSites];

        for (int i = 0; i < totalSites; i++) {
            deferredReplies[i] = new ArrayList<>();
        }

        for (int i = 0; i < totalSites; i++) {
            System.out.print("Enter timestamp for Site " + (i + 1) + ": ");
            timestamps[i] = scanner.nextInt();
        }

        for (int i = 0; i < requestingSites; i++) {
            System.out.print("Enter site number that wants to enter CS: ");
            int siteNum = scanner.nextInt();
            requestSites[i] = siteNum;
            requestTimestamps[i] = timestamps[siteNum - 1];
            orderedSites[i] = siteNum;
        }

        for (int i = 0; i < requestingSites; i++) {
            int reqSite = requestSites[i];
            for (int j = 0; j < totalSites; j++) {
                int currentSite = j + 1;
                if (currentSite == reqSite) continue;
                if (timestamps[j] <= timestamps[reqSite - 1]) {
                    deferredReplies[j].add(reqSite);
                }
            }
        }

        for (int i = 0; i < requestingSites - 1; i++) {
            for (int j = 0; j < requestingSites - i - 1; j++) {
                if (requestTimestamps[j] > requestTimestamps[j + 1]) {
                    int tmpTime = requestTimestamps[j];
                    requestTimestamps[j] = requestTimestamps[j + 1];
                    requestTimestamps[j + 1] = tmpTime;

                    int tmpSite = orderedSites[j];
                    orderedSites[j] = orderedSites[j + 1];
                    orderedSites[j + 1] = tmpSite;
                }
            }
        }

        System.out.println("\n--- Critical Section Access ---");
        for (int i = 0; i < requestingSites; i++) {
            int site = orderedSites[i];
            System.out.println("Site " + site + " entered CS");
            for (int deferred : deferredReplies[site - 1]) {
                System.out.println("Site " + site + " replies to deferred Site " + deferred);
            }
            deferredReplies[site - 1].clear();
        }

        scanner.close();
    }
}
