package gui;

import model.Process;
import metrics.MetricsCalculator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.ArrayList;

public class MainGUI extends Application {

    TableView<String[]> inputTable = new TableView<>();
    TableView<String[]> sjfTable = new TableView<>();
    TableView<String[]> priorityTable = new TableView<>();

    HBox sjfGantt = new HBox(3);
    HBox priorityGantt = new HBox(3);

    Label metricsLabel = new Label();
    Label comparisonLabel = new Label();

    ArrayList<String> sjfSteps = new ArrayList<>();
    ArrayList<String> prioritySteps = new ArrayList<>();

    @Override
    public void start(Stage stage) {

        Label label = new Label("Enter number of processes:");
        TextField numberField = new TextField();

        
        numberField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                numberField.setText(newVal.replaceAll("[^\\d]", ""));
            }
        });

        
        Button createBtn = new Button("Create Table");
        Button runBtn = new Button("Run Scheduling");

        Button scenario1 = new Button("Scenario 1");
        Button scenario2 = new Button("Scenario 2");
        Button scenario3 = new Button("Scenario 3");

        
        scenario1.setOnAction(e -> {
            numberField.setText("3");
            createBtn.fire();
            inputTable.getItems().set(0, new String[]{"P1", "0", "7", "2"});
            inputTable.getItems().set(1, new String[]{"P2", "2", "4", "1"});
            inputTable.getItems().set(2, new String[]{"P3", "4", "1", "3"});
        });

        scenario2.setOnAction(e -> {
            numberField.setText("4");
            createBtn.fire();
            inputTable.getItems().set(0, new String[]{"P1", "0", "8", "3"});
            inputTable.getItems().set(1, new String[]{"P2", "1", "2", "1"});
            inputTable.getItems().set(2, new String[]{"P3", "2", "1", "2"});
            inputTable.getItems().set(3, new String[]{"P4", "3", "3", "1"});
        });

        scenario3.setOnAction(e -> {
            numberField.setText("2");
            createBtn.fire();
            inputTable.getItems().set(0, new String[]{"P1", "0", "5", "1"});
            inputTable.getItems().set(1, new String[]{"P2", "0", "5", "1"});
        });

        
        createBtn.setOnAction(e -> {
            try {
                if (numberField.getText().isEmpty()) {
                    showError("Enter number!");
                    return;
                }
                int n = Integer.parseInt(numberField.getText());
                if (n <= 0) {
                    showError("Number must be greater than 0");
                    return;
                }
                inputTable.getColumns().clear();
                inputTable.getItems().clear();
                String[] titles = {"Process", "Arrival", "Burst", "Priority"};
                for (int i = 0; i < titles.length; i++) {
                    final int idx = i;
                    TableColumn<String[], String> col = new TableColumn<>(titles[i]);
                    col.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[idx]));
                    col.setCellFactory(tc -> new EditingCell(idx));
                    inputTable.getColumns().add(col);
                }
                for (int i = 0; i < n; i++) {
                    inputTable.getItems().add(new String[]{"P" + (i + 1), "", "", ""});
                }
                inputTable.setEditable(true);
            } catch (Exception ex) {
                showError("Invalid number!");
            }
        });

        runBtn.setOnAction(e -> {
            try {
                sjfSteps.clear();
                prioritySteps.clear();
                int n = inputTable.getItems().size();
                ArrayList<Process> sjfList = new ArrayList<>();
                ArrayList<Process> priorityList = new ArrayList<>();

                for (int i = 0; i < n; i++) {
                    String[] row = inputTable.getItems().get(i);
                    if (row[1].isEmpty() || row[2].isEmpty() || row[3].isEmpty()) {
                        showError("Fill all fields!");
                        return;
                    }
                    int at = Integer.parseInt(row[1]);
                    int bt = Integer.parseInt(row[2]);
                    int pr = Integer.parseInt(row[3]);

                    if (at < 0 || bt <= 0 || pr < 0) {
                        showError("Invalid input values!");
                        return;
                    }
                    sjfList.add(new Process(i + 1, at, bt, pr));
                    priorityList.add(new Process(i + 1, at, bt, pr));
                }

                runSJFNonPreemptive(sjfList);
                runPriorityNonPreemptive(priorityList);
                setupResultTable(sjfTable, sjfList);
                setupResultTable(priorityTable, priorityList);

                double sjfWT = MetricsCalculator.calculateAverageWaitingTime(sjfList);
                double sjfTAT = MetricsCalculator.calculateAverageTurnaroundTime(sjfList);
                double sjfRT = MetricsCalculator.calculateAverageResponseTime(sjfList);
                double prWT = MetricsCalculator.calculateAverageWaitingTime(priorityList);
                double prTAT = MetricsCalculator.calculateAverageTurnaroundTime(priorityList);
                double prRT = MetricsCalculator.calculateAverageResponseTime(priorityList);

                metricsLabel.setText("===== SJF RESULTS =====\nAvg WT: " + sjfWT + "\nAvg TAT: " + sjfTAT + "\nAvg RT: " + sjfRT +
                                     "\n\n===== PRIORITY RESULTS =====\nAvg WT: " + prWT + "\nAvg TAT: " + prTAT + "\nAvg RT: " + prRT);

                String betterWT = (sjfWT < prWT) ? "SJF" : (prWT < sjfWT ? "Priority" : "Equal");
                String betterTAT = (sjfTAT < prTAT) ? "SJF" : (prTAT < sjfTAT ? "Priority" : "Equal");
                String betterRT = (sjfRT < prRT) ? "SJF" : (prRT < sjfRT ? "Priority" : "Equal");

                comparisonLabel.setText("===== COMPARISON =====\nBetter WT: " + betterWT + "\nBetter TAT: " + betterTAT + "\nBetter RT: " + betterRT);

                drawGantt(sjfSteps, sjfGantt);
                drawGantt(prioritySteps, priorityGantt);
            } catch (Exception ex) {
                showError("Invalid Input!");
            }
        });

        

        
        HBox scenarioBox = new HBox(10);
        scenarioBox.getChildren().addAll(scenario1, scenario2, scenario3);

        
        VBox leftPane = new VBox(15);
        leftPane.setPadding(new Insets(20));
        leftPane.setMinWidth(350);
        leftPane.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ddd; -fx-border-width: 0 1 0 0;");

        leftPane.getChildren().addAll(
            new Label("⚙️ Configuration"),
            label, numberField,
            new Label("Quick Scenarios:"), scenarioBox,
            createBtn,
            new Label("Input Data Table:"), inputTable,
            runBtn
        );

        
        VBox rightPane = new VBox(20);
        rightPane.setPadding(new Insets(20));
        rightPane.setAlignment(Pos.TOP_LEFT);

        
        VBox sjfSection = new VBox(5, new Label("📊 SJF Non-Preemptive"), sjfTable, new Label("SJF Gantt Chart"), sjfGantt);
        
        VBox prioritySection = new VBox(5, new Label("📊 Priority Non-Preemptive"), priorityTable, new Label("Priority Gantt Chart"), priorityGantt);

        
        HBox statsBox = new HBox(20, metricsLabel, comparisonLabel);
        statsBox.setStyle("-fx-background-color: #fff; -fx-padding: 15; -fx-border-color: #eee; -fx-border-radius: 5;");

        rightPane.getChildren().addAll(
            sjfSection,
            prioritySection,
            new Label("📈 Metrics Analysis"),
            statsBox
        );

        
        HBox mainLayout = new HBox(leftPane, rightPane);
        HBox.setHgrow(rightPane, Priority.ALWAYS);

        
        inputTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        sjfTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        priorityTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        inputTable.setPrefHeight(300);
        sjfTable.setPrefHeight(180);
        priorityTable.setPrefHeight(180);

        ScrollPane scroll = new ScrollPane(mainLayout);
        scroll.setFitToWidth(true);

        Scene scene = new Scene(scroll, 1200, 850);
        stage.setScene(scene);
        stage.setTitle("CPU Scheduling Simulator - Improved Layout");
        stage.show();
    }

    

    void setupResultTable(TableView<String[]> table, ArrayList<Process> processes) {
        table.getColumns().clear();
        table.getItems().clear();
        String[] titles = {"Process", "CT", "WT", "TAT", "RT"};
        for (int i = 0; i < titles.length; i++) {
            final int idx = i;
            TableColumn<String[], String> col = new TableColumn<>(titles[i]);
            col.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[idx]));
            table.getColumns().add(col);
        }
        for (Process p : processes) {
            table.getItems().add(new String[]{"P" + p.id, p.completionTime + "", p.waitingTime + "", p.turnaroundTime + "", p.responseTime + ""});
        }
    }

    void runPriorityNonPreemptive(ArrayList<Process> processes) {
        int time = 0, completed = 0;
        while (completed < processes.size()) {
            Process highest = null;
            for (Process p : processes) {
                if (!p.started && p.arrivalTime <= time) {
                    if (highest == null || p.priority < highest.priority) highest = p;
                }
            }
            if (highest != null) {
                highest.started = true;
                highest.responseTime = time - highest.arrivalTime;
                prioritySteps.add("P" + highest.id);
                time += highest.burstTime;
                highest.completionTime = time;
                highest.turnaroundTime = time - highest.arrivalTime;
                highest.waitingTime = highest.turnaroundTime - highest.burstTime;
                completed++;
            } else {
                prioritySteps.add("IDLE");
                time++;
            }
        }
    }

    void runSJFNonPreemptive(ArrayList<Process> processes) {
        int time = 0, completed = 0;
        while (completed < processes.size()) {
            Process shortest = null;
            for (Process p : processes) {
                if (!p.started && p.arrivalTime <= time) {
                    if (shortest == null || p.burstTime < shortest.burstTime) shortest = p;
                }
            }
            if (shortest != null) {
                shortest.started = true;
                shortest.responseTime = time - shortest.arrivalTime;
                sjfSteps.add("P" + shortest.id);
                time += shortest.burstTime;
                shortest.completionTime = time;
                shortest.turnaroundTime = time - shortest.arrivalTime;
                shortest.waitingTime = shortest.turnaroundTime - shortest.burstTime;
                completed++;
            } else {
                sjfSteps.add("IDLE");
                time++;
            }
        }
    }

    void drawGantt(ArrayList<String> steps, HBox ganttBox) {
        ganttBox.getChildren().clear();
        for (String step : steps) {
            StackPane block = new StackPane();
            String color = step.equals("IDLE") ? "#cccccc" : "#" + Integer.toHexString(((Integer.parseInt(step.substring(1)) * 123456) & 0x7FFFFF) | 0x888888);
            block.setStyle("-fx-background-color:" + color + "; -fx-padding:10 20; -fx-border-color:black;");
            block.getChildren().add(new Text(step));
            ganttBox.getChildren().add(block);
        }
    }

    void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }

    public static void main(String[] args) { launch(args); }
}
