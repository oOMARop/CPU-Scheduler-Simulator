package scheduler;

import java.util.List;
import model.Process;

public class SJF {

    public static void execute(List<Process> processes) {

        int currentTime = 0;
        int completed = 0;
        int n = processes.size();

        System.out.println("\n===== Preemptive SJF Scheduling =====");

        while (completed < n) {

            Process shortest = null;
            int minRemainingTime = Integer.MAX_VALUE;

            for (Process p : processes) {

                if (p.arrivalTime <= currentTime
                        && !p.isCompleted
                        && p.burstTime < minRemainingTime) {

                    minRemainingTime = p.burstTime;
                    shortest = p;
                }
            }

            if (shortest == null) {
                currentTime++;
                continue;
            }

            // First response time
            if (shortest.responseTime == -1) {
                shortest.responseTime = currentTime - shortest.arrivalTime;
            }

            // Execute 1 unit (Preemptive behavior)
            shortest.burstTime--;
            currentTime++;

            if (shortest.burstTime == 0) {
                shortest.completionTime = currentTime;
                shortest.calculateTimes();
                shortest.isCompleted = true;
                completed++;
            }
        }

        for (Process p : processes) {
            p.display();
        }
    }
}