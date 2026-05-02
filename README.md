CPU Scheduling Simulator

Overview

This is a Java-based CPU Scheduling Simulator

The system simulates two important CPU scheduling algorithms:

- Preemptive Shortest Job First (SJF)
- Preemptive Priority Scheduling

The main goal of this project is to compare the performance of different scheduling algorithms based on process execution.


Features

- Process creation with:
  - Process ID
  - Arrival Time
  - Burst Time
  - Priority

- Preemptive SJF Scheduling

- Preemptive Priority Scheduling

- Automatic calculation of:
  - Completion Time (CT)
  - Turnaround Time (TAT)
  - Waiting Time (WT)
  - Response Time (RT)

- Performance comparison between algorithms

- Average metrics calculation for better evaluation


How the Project Works

Step 1: Process Input

The user enters:

- Number of processes
- Arrival Time for each process
- Burst Time for each process
- Priority value for each process

Each process is stored as an object using the `Process` class.


Step 2: Scheduling Execution

The system runs:

Preemptive SJF

The CPU selects the process with the shortest remaining burst time.

If a shorter process arrives, the current process is preempted.


Preemptive Priority Scheduling

The CPU selects the process with the highest priority.

(Lower priority number means higher priority)

If a higher-priority process arrives, the current process is preempted.


Step 3: Metrics Calculation

After execution, the system calculates:

- Completion Time
- Waiting Time
- Turnaround Time
- Response Time

It also calculates the average values for all processes.


Step 4: Algorithm Comparison

The system compares:

- Average Waiting Time
- Average Turnaround Time
- Average Response Time

Then it shows which scheduling algorithm performs better.
