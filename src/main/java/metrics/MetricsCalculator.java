package metrics;

import java.util.List;
import model.Process;

public class MetricsCalculator {

    public static double calculateAverageWaitingTime(List<Process> processes) {
        double total = 0;
        for (Process p : processes) {
            total += p.waitingTime;
        }
        return total / processes.size();
    }

    public static double calculateAverageTurnaroundTime(List<Process> processes) {
        double total = 0;
        for (Process p : processes) {
            total += p.turnaroundTime;
        }
        return total / processes.size();
    }

    public static double calculateAverageResponseTime(List<Process> processes) {
        double total = 0;
        for (Process p : processes) {
            total += p.responseTime;
        }
        return total / processes.size();
    }
}