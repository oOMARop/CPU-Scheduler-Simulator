package model;

public class Process {

    public int id;
    public int arrivalTime;
    public int burstTime;
    public int remainingTime;
    public int priority;

    public int completionTime;
    public int waitingTime;
    public int turnaroundTime;
    public int responseTime;

    public boolean started = false;

    public Process(int id, int at, int bt, int p) {

        this.id = id;
        this.arrivalTime = at;
        this.burstTime = bt;
        this.remainingTime = bt;
        this.priority = p;

        this.completionTime = 0;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.responseTime = 0;
    }
}