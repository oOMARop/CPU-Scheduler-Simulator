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
        double avgWT = calculateAverageWaitingTime(processes);
        double avgTAT = calculateAverageTurnaroundTime(processes);
        double avgRT = calculateAverageResponseTime(processes);

        System.out.println("\n===== Performance Metrics =====");
        System.out.println("Average Waiting Time: " + avgWT);
        System.out.println("Average Turnaround Time: " + avgTAT);
        System.out.println("Average Response Time: " + avgRT);
    }

    public static void compareAlgorithms(
        double sjfWT, double sjfTAT, double sjfRT,
        double prWT, double prTAT, double prRT) {

    System.out.println("\n===== Comparison =====");

    
    if (sjfWT < prWT)
        System.out.println("SJF better in Waiting Time");
    else if (sjfWT > prWT)
        System.out.println("Priority better in Waiting Time");
    else
        System.out.println("Both are equal in Waiting Time");

   
    if (sjfTAT < prTAT)
        System.out.println("SJF better in Turnaround Time");
    else if (sjfTAT > prTAT)
        System.out.println("Priority better in Turnaround Time");
    else
        System.out.println("Both are equal in Turnaround Time");

    
    if (sjfRT < prRT)
        System.out.println("SJF better in Response Time");
    else if (sjfRT > prRT)
        System.out.println("Priority better in Response Time");
    else
        System.out.println("Both are equal in Response Time");

    System.out.println("\n===== Conclusion =====");
    System.out.println("SJF is better for minimizing waiting time.");
    System.out.println("Priority Scheduling is better for handling important processes first.");
}
}
