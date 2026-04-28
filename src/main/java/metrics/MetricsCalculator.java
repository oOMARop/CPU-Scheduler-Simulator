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

    public static void displayAverages(List<Process> processes) {
        System.out.println("\n===== Performance Metrics =====");

        System.out.println("Average Waiting Time: "
                + calculateAverageWaitingTime(processes));

        System.out.println("Average Turnaround Time: "
                + calculateAverageTurnaroundTime(processes));

        System.out.println("Average Response Time: "
                + calculateAverageResponseTime(processes));
    }
}