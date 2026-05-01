package scheduler;

import java.util.List;
import model.Process;

public class SJF {

    public static void execute(List<Process> processes) {

        int time = 0;
        int completed = 0;
        int n = processes.size();

        System.out.println("\n===== Preemptive SJF Scheduling =====");

        while (completed < n) {

            Process shortest = null;

            for (Process proc : processes) {

                if (proc.arrivalTime <= time
                        && proc.remainingTime > 0) {

                    if (shortest == null
                            || proc.remainingTime < shortest.remainingTime
                            || (proc.remainingTime == shortest.remainingTime
                            && proc.arrivalTime < shortest.arrivalTime)) {

                        shortest = proc;
                    }
                }
            }

            if (shortest != null) {

                // أول مرة يشتغل فيها
                if (!shortest.started) {
                    shortest.responseTime = time - shortest.arrivalTime;
                    shortest.started = true;
                }

                // تنفيذ وحدة زمن واحدة (Preemptive)
                shortest.remainingTime--;
                time++;

                if (shortest.remainingTime == 0) {
                    shortest.completionTime = time;
                    shortest.calculateTimes();
                    shortest.isCompleted = true;
                    completed++;
                }

            } else {
                // CPU Idle
                time++;
            }
        }

        // عرض النتائج
        for (Process p : processes) {
            p.display();
        }
    }
}