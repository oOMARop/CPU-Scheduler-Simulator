import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Process;
import metrics.MetricsCalculator;
import scheduler.SJF;
import scheduler.PriorityScheduling;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        List<Process> processes1 = new ArrayList<>();

        System.out.print("Enter number of processes: ");
        int n = input.nextInt();

        for (int i = 0; i < n; i++) {

            System.out.println("\nProcess " + (i + 1));

            System.out.print("Arrival Time: ");
            int at = input.nextInt();

            System.out.print("Burst Time: ");
            int bt = input.nextInt();

            System.out.print("Priority: ");
            int p = input.nextInt();

            processes1.add(new Process(i + 1, at, bt, p));
        }

        
        List<Process> processes2 = new ArrayList<>();

        for (Process p : processes1) {
            processes2.add(
                new Process(
                    p.pid,
                    p.arrivalTime,
                    p.burstTime,
                    p.priority
                )
            );
        }

        
        SJF.execute(processes1);

        double sjfWT = MetricsCalculator.calculateAverageWaitingTime(processes1);
        double sjfTAT = MetricsCalculator.calculateAverageTurnaroundTime(processes1);
        double sjfRT = MetricsCalculator.calculateAverageResponseTime(processes1);

        MetricsCalculator.displayAverages(processes1);

        
        PriorityScheduling.execute(processes2);

        double prWT = MetricsCalculator.calculateAverageWaitingTime(processes2);
        double prTAT = MetricsCalculator.calculateAverageTurnaroundTime(processes2);
        double prRT = MetricsCalculator.calculateAverageResponseTime(processes2);

        MetricsCalculator.displayAverages(processes2);

        
        MetricsCalculator.compareAlgorithms(
                sjfWT, sjfTAT, sjfRT,
                prWT, prTAT, prRT
        );

        input.close();
    }
}
