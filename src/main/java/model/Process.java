package model;

public class Process {

    public int pid;
    public int arrivalTime;
    public int burstTime;
    public int remainingTime;
    public int priority;

    public int completionTime;
    public int turnaroundTime;
    public int waitingTime;
    public int responseTime;

    public boolean isCompleted;
    public boolean started;

    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;

        this.completionTime = 0;
        this.turnaroundTime = 0;
        this.waitingTime = 0;
        this.responseTime = -1;

        this.isCompleted = false;
        this.started = false;
    }

    public void calculateTimes() {
        turnaroundTime = completionTime - arrivalTime;
        waitingTime = turnaroundTime - burstTime;
    }

    public void display() {
        System.out.println(
            "P" + pid +
            " | AT: " + arrivalTime +
            " | BT: " + burstTime +
            " | Priority: " + priority +
            " | CT: " + completionTime +
            " | TAT: " + turnaroundTime +
            " | WT: " + waitingTime +
            " | RT: " + responseTime
        );
    }
}
