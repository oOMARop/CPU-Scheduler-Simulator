package scheduler;

import java.util.List;
import model.Process;

public class PriorityScheduling {

    public static void execute(List<Process> processes) {

        int time = 0;
        int completed = 0;
        int n = processes.size();

        System.out.println("\n===== Preemptive Priority Scheduling =====");

        while (completed < n) {

            Process highest = null;

            for (Process proc : processes) {

                if (proc.arrivalTime <= time
                        && proc.remainingTime > 0) {

                    
                    if (highest == null
                            || proc.priority < highest.priority
                            || (proc.priority == highest.priority
                            && proc.arrivalTime < highest.arrivalTime)) {

                        highest = proc;
                    }
                }
            }

            if (highest != null) {

                 
                if (!highest.started) {
                    highest.responseTime = time - highest.arrivalTime;
                    highest.started = true;
                }

               
                highest.remainingTime--;
                time++;

                if (highest.remainingTime == 0) {
                    highest.completionTime = time;
                    highest.calculateTimes();
                    highest.isCompleted = true;
                    completed++;
                }

            } else {
                
                time++;
            }
        }

        
        for (Process p : processes) {
            p.display();
        }
    }
}
