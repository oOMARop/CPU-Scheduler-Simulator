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

        // Clone for second algorithm
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

        // Run SJF
        SJF.execute(processes1);
        MetricsCalculator.displayAverages(processes1);

        // Run Priority Scheduling
        PriorityScheduling.execute(processes2);
        MetricsCalculator.displayAverages(processes2);

        input.close();
    }
}