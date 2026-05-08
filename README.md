# CPU Scheduling Simulator

## Project Description

This project is a CPU Scheduling Simulator developed using JavaFX.

The simulator compares two CPU scheduling algorithms:

* Shortest Job First (SJF)
* Priority Scheduling

The system allows users to:

* Enter process information
* Select scheduling algorithm
* Calculate scheduling metrics
* Compare algorithms
* Display Gantt Chart visualization

---

# Features

* GUI Interface using JavaFX
* Dynamic process table
* Editable input fields
* SJF Scheduling
* Priority Scheduling
* Performance Metrics:

  * Waiting Time (WT)
  * Turnaround Time (TAT)
  * Response Time (RT)
* Algorithm Comparison
* Gantt Chart Visualization
* Input Validation
* Ready-made Scenario Buttons

---

# Technologies Used

* Java
* JavaFX
* Maven
* OOP Concepts


---

# How To Run

1. Open the project in NetBeans or IntelliJ.
2. Run MainGUI.java
3. Enter number of processes.
4. Choose scheduling algorithm.
5. Fill process data.
6. Click Run Scheduling.

---

# Scheduling Metrics

The simulator calculates:

* Completion Time (CT)
* Waiting Time (WT)
* Turnaround Time (TAT)
* Response Time (RT)

Average metrics are also displayed.

---

# Scenario 1 — SJF Better Performance

## Purpose

Demonstrates how SJF minimizes waiting time and turnaround time.

## Input

| Process | Arrival | Burst | Priority |
| ------- | ------- | ----- | -------- |
| P1      | 0       | 7     | 2        |
| P2      | 2       | 4     | 1        |
| P3      | 4       | 1     | 3        |

## Expected Result

* SJF achieves lower waiting time.
* Faster execution for short processes.



<img width="1127" height="860" alt="Screenshot_2" src="https://github.com/user-attachments/assets/35bf0578-797b-4bc9-b112-4df530543e1c" />


<img width="1128" height="853" alt="Screenshot_3" src="https://github.com/user-attachments/assets/8fa333c1-e60d-42df-bca4-b1274156adc1" />



---

# Scenario 2 — Priority Scheduling

## Purpose

Demonstrates how Priority Scheduling handles important processes first.

## Input

| Process | Arrival | Burst | Priority |
| ------- | ------- | ----- | -------- |
| P1      | 0       | 8     | 3        |
| P2      | 1       | 2     | 1        |
| P3      | 2       | 1     | 2        |
| P4      | 3       | 3     | 1        |

## Expected Result

* High priority processes execute earlier.
* Better responsiveness for critical tasks.

<img width="1128" height="855" alt="Screenshot_4" src="https://github.com/user-attachments/assets/c632d88e-f8e5-409d-92c0-17b608d20188" />


<img width="1126" height="855" alt="Screenshot_5" src="https://github.com/user-attachments/assets/84c626f3-f28b-4d6f-9340-0ba52aaaeda2" />


---

# Scenario 3 — Equal/Fair Scheduling

## Purpose

Demonstrates a balanced case between algorithms.

## Input

| Process | Arrival | Burst | Priority |
| ------- | ------- | ----- | -------- |
| P1      | 0       | 5     | 1        |
| P2      | 0       | 5     | 1        |

## Expected Result

* Similar results between both algorithms.

(Add Screenshot Here)

---

# Future Improvements

* Add Round Robin Scheduling
* Add FCFS Scheduling
* Better Gantt Chart Timeline
* Export Results to File
* Dark Mode UI

---


# Conclusion

This project demonstrates how CPU scheduling algorithms affect system performance and process execution behavior.

SJF is efficient for minimizing waiting time, while Priority Scheduling is useful for handling critical processes first.
