package scheduler;

import java.util.List;
import model.Process;

public class PriorityScheduling {

    public static void execute(List<Process> processes) {

        int currentTime = 0;
        int completed = 0;
        int n = processes.size();

        System.out.println("\n===== Preemptive Priority Scheduling =====");

        while (completed < n) {

            Process highest = null;
            int bestPriority = Integer.MAX_VALUE;

            for (Process p : processes) {

                if (p.arrivalTime <= currentTime
                        && !p.isCompleted
                        && p.priority < bestPriority) {

                    bestPriority = p.priority;
                    highest = p;
                }
            }

            if (highest == null) {
                currentTime++;
                continue;
            }

            // Response time
            if (highest.responseTime == -1) {
                highest.responseTime = currentTime - highest.arrivalTime;
            }

            // Execute 1 unit (Preemptive)
            highest.burstTime--;
            currentTime++;

            if (highest.burstTime == 0) {
                highest.completionTime = currentTime;
                highest.calculateTimes();
                highest.isCompleted = true;
                completed++;
            }
        }

        for (Process p : processes) {
            p.display();
        }
    }
}