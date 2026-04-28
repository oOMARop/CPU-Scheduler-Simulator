import java.util.ArrayList;
import java.util.List;

import model.Process;
import metrics.MetricsCalculator;
import scheduler.SJF;
import scheduler.PriorityScheduling;

public class Main {

    public static void main(String[] args) {

        List<Process> processes1 = new ArrayList<>();
        processes1.add(new Process(1, 0, 7, 2));
        processes1.add(new Process(2, 2, 4, 1));
        processes1.add(new Process(3, 4, 1, 3));
        processes1.add(new Process(4, 5, 4, 2));

        // Clone idea (simple reset approach)
        List<Process> processes2 = new ArrayList<>();
        for (Process p : processes1) {
            processes2.add(new Process(p.pid, p.arrivalTime, p.burstTime, p.priority));
        }

        // SJF
        SJF.execute(processes1);
        MetricsCalculator.displayAverages(processes1);

        // Priority
        PriorityScheduling.execute(processes2);
        MetricsCalculator.displayAverages(processes2);
    }
}