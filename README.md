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
2. Run MainGUI.java or javafx run
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

<img width="1201" height="884" alt="s 1" src="https://github.com/user-attachments/assets/b5461f79-a6f1-4389-8edf-500f0f2bff45" />


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

<img width="1201" height="881" alt="s 2" src="https://github.com/user-attachments/assets/dd890516-d16b-4ac5-84ab-b81bb4bef055" />


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

<img width="1201" height="883" alt="s 3" src="https://github.com/user-attachments/assets/9f54a608-b800-4712-a6d6-57e11a6529a0" />


---

# Scenario 4 — Invalid Input

<img width="1201" height="881" alt="s 4" src="https://github.com/user-attachments/assets/2ece9ec1-64dd-48b2-a88e-44d660d8e249" />


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
