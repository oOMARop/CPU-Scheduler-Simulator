package gui;

import model.Process;
import metrics.MetricsCalculator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class MainGUI extends Application {

    TableView<String[]> inputTable = new TableView<>();
    TableView<String[]> resultTable = new TableView<>();
    HBox ganttBox = new HBox(5);

    Label metricsLabel = new Label();
    Label comparisonLabel = new Label();

    @Override
    public void start(Stage stage) {

        Label label = new Label("Enter number of processes:");
        TextField numberField = new TextField();

        // numbers only
        numberField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                numberField.setText(newVal.replaceAll("[^\\d]", ""));
            }
        });

        ComboBox<String> algorithmBox = new ComboBox<>();
        algorithmBox.getItems().addAll("Priority", "SJF");
        algorithmBox.setValue("Priority");

        Button createBtn = new Button("Create Table");
        Button runBtn = new Button("Run Scheduling");

        // ================= SCENARIO BUTTONS =================

        Button scenario1Btn = new Button("Scenario 1");
        Button scenario2Btn = new Button("Scenario 2");
        Button scenario3Btn = new Button("Scenario 3");
        Button clearBtn = new Button("Clear");

        // Scenario 1 -> SJF Better
        scenario1Btn.setOnAction(e -> {

            algorithmBox.setValue("SJF");

            numberField.setText("3");

            createBtn.fire();

            inputTable.getItems().set(0,
                    new String[]{"P1", "0", "7", "2"});

            inputTable.getItems().set(1,
                    new String[]{"P2", "2", "4", "1"});

            inputTable.getItems().set(2,
                    new String[]{"P3", "4", "1", "3"});
        });

        // Scenario 2 -> Priority Better
        scenario2Btn.setOnAction(e -> {

            algorithmBox.setValue("Priority");

            numberField.setText("4");

            createBtn.fire();

            inputTable.getItems().set(0,
                    new String[]{"P1", "0", "8", "3"});

            inputTable.getItems().set(1,
                    new String[]{"P2", "1", "2", "1"});

            inputTable.getItems().set(2,
                    new String[]{"P3", "2", "1", "2"});

            inputTable.getItems().set(3,
                    new String[]{"P4", "3", "3", "1"});
        });

        // Scenario 3 -> Equal
        scenario3Btn.setOnAction(e -> {

            algorithmBox.setValue("Priority");

            numberField.setText("2");

            createBtn.fire();

            inputTable.getItems().set(0,
                    new String[]{"P1", "0", "5", "1"});

            inputTable.getItems().set(1,
                    new String[]{"P2", "0", "5", "1"});
        });

        // Clear Button
        clearBtn.setOnAction(e -> {

            inputTable.getItems().clear();
            inputTable.getColumns().clear();

            resultTable.getItems().clear();
            resultTable.getColumns().clear();

            ganttBox.getChildren().clear();

            metricsLabel.setText("");
            comparisonLabel.setText("");

            numberField.clear();
        });

        // ================= CREATE TABLE =================

        createBtn.setOnAction(e -> {
            try {

                if (numberField.getText().isEmpty()) {
                    showError("Enter number!");
                    return;
                }

                int n = Integer.parseInt(numberField.getText());

                if (n <= 0) {
                    showError("Number must be > 0");
                    return;
                }

                inputTable.getColumns().clear();
                inputTable.getItems().clear();

                String[] titles = {"Process", "Arrival", "Burst", "Priority"};

                for (int i = 0; i < titles.length; i++) {

                    final int idx = i;

                    TableColumn<String[], String> col =
                            new TableColumn<>(titles[i]);

                    col.setCellValueFactory(data ->
                            new javafx.beans.property.SimpleStringProperty(
                                    data.getValue()[idx]
                            )
                    );

                    col.setCellFactory(tc -> new EditingCell(idx));

                    inputTable.getColumns().add(col);
                }

                for (int i = 0; i < n; i++) {
                    inputTable.getItems().add(
                            new String[]{"P" + (i + 1), "", "", ""}
                    );
                }

                inputTable.setEditable(true);

            } catch (Exception ex) {
                showError("Invalid number!");
            }
        });

        // ================= RUN =================

        runBtn.setOnAction(e -> {

            try {

                int n = inputTable.getItems().size();

                ArrayList<Process> processes = new ArrayList<>();
                ArrayList<Process> processesCopy = new ArrayList<>();

                for (int i = 0; i < n; i++) {

                    String[] row = inputTable.getItems().get(i);

                    // empty check
                    if (row[1].isEmpty()
                            || row[2].isEmpty()
                            || row[3].isEmpty()) {

                        showError("Fill all fields!");
                        return;
                    }

                    int at = Integer.parseInt(row[1]);
                    int bt = Integer.parseInt(row[2]);
                    int p = Integer.parseInt(row[3]);

                    // validation
                    if (at < 0 || bt <= 0 || p < 0) {

                        showError(
                                "Arrival ≥ 0 | Burst > 0 | Priority ≥ 0"
                        );

                        return;
                    }

                    processes.add(
                            new Process(i + 1, at, bt, p)
                    );

                    processesCopy.add(
                            new Process(i + 1, at, bt, p)
                    );
                }

                String selectedAlgo = algorithmBox.getValue();

                // ================= RUN SELECTED ALGORITHM =================

                if (selectedAlgo.equals("Priority")) {
                    runPriority(processes);
                } else {
                    runSJF(processes);
                }

                // ================= RESULT TABLE =================

                resultTable.getColumns().clear();
                resultTable.getItems().clear();

                String[] titles =
                        {"Process", "CT", "WT", "TAT", "RT"};

                for (int i = 0; i < titles.length; i++) {

                    final int idx = i;

                    TableColumn<String[], String> col =
                            new TableColumn<>(titles[i]);

                    col.setCellValueFactory(data ->
                            new javafx.beans.property.SimpleStringProperty(
                                    data.getValue()[idx]
                            )
                    );

                    resultTable.getColumns().add(col);
                }

                for (Process p : processes) {

                    resultTable.getItems().add(
                            new String[]{
                                    "P" + p.id,
                                    p.completionTime + "",
                                    p.waitingTime + "",
                                    p.turnaroundTime + "",
                                    p.responseTime + ""
                            }
                    );
                }

                // ================= METRICS =================

                double avgWT =
                        MetricsCalculator
                                .calculateAverageWaitingTime(processes);

                double avgTAT =
                        MetricsCalculator
                                .calculateAverageTurnaroundTime(processes);

                double avgRT =
                        MetricsCalculator
                                .calculateAverageResponseTime(processes);

                metricsLabel.setText(
                        "Avg WT = " + avgWT +
                        " | Avg TAT = " + avgTAT +
                        " | Avg RT = " + avgRT
                );

                // ================= COMPARISON =================

                runPriority(processesCopy);

                double prWT =
                        MetricsCalculator
                                .calculateAverageWaitingTime(processesCopy);

                double prTAT =
                        MetricsCalculator
                                .calculateAverageTurnaroundTime(processesCopy);

                double prRT =
                        MetricsCalculator
                                .calculateAverageResponseTime(processesCopy);

                ArrayList<Process> sjfList = new ArrayList<>();

                for (Process p : processesCopy) {

                    sjfList.add(
                            new Process(
                                    p.id,
                                    p.arrivalTime,
                                    p.burstTime,
                                    p.priority
                            )
                    );
                }

                runSJF(sjfList);

                double sjfWT =
                        MetricsCalculator
                                .calculateAverageWaitingTime(sjfList);

                double sjfTAT =
                        MetricsCalculator
                                .calculateAverageTurnaroundTime(sjfList);

                double sjfRT =
                        MetricsCalculator
                                .calculateAverageResponseTime(sjfList);

                comparisonLabel.setText(
                        "Better WT: "
                                + (sjfWT < prWT ? "SJF" : "Priority")
                                + "\n" +

                        "Better TAT: "
                                + (sjfTAT < prTAT ? "SJF" : "Priority")
                                + "\n" +

                        "Better RT: "
                                + (sjfRT < prRT ? "SJF" : "Priority")
                );

                // ================= GANTT =================

                drawGantt(processes);

            } catch (Exception ex) {
                showError("Invalid input!");
            }
        });

        // ================= SCENARIO BOX =================

        HBox scenarioBox = new HBox(10);

        scenarioBox.getChildren().addAll(
                scenario1Btn,
                scenario2Btn,
                scenario3Btn,
                clearBtn
        );

        // ================= ROOT =================

        VBox root = new VBox(10);

        root.getChildren().addAll(
                label,
                numberField,
                algorithmBox,
                scenarioBox,
                createBtn,
                inputTable,
                runBtn,
                resultTable,
                new Label("Metrics:"),
                metricsLabel,
                new Label("Comparison:"),
                comparisonLabel,
                new Label("Gantt Chart:"),
                ganttBox
        );

        Scene scene = new Scene(root, 900, 650);

        stage.setScene(scene);

        stage.setTitle("Scheduling Project");

        stage.show();
    }

    // ================= PRIORITY =================

    void runPriority(ArrayList<Process> processes) {

        int time = 0;
        int completed = 0;

        while (completed < processes.size()) {

            Process highest = null;

            for (Process p : processes) {

                if (p.arrivalTime <= time
                        && p.remainingTime > 0) {

                    if (highest == null
                            || p.priority < highest.priority) {

                        highest = p;
                    }
                }
            }

            if (highest != null) {

                if (!highest.started) {

                    highest.responseTime =
                            time - highest.arrivalTime;

                    highest.started = true;
                }

                highest.remainingTime--;

                time++;

                if (highest.remainingTime == 0) {

                    highest.completionTime = time;

                    highest.turnaroundTime =
                            time - highest.arrivalTime;

                    highest.waitingTime =
                            highest.turnaroundTime
                                    - highest.burstTime;

                    completed++;
                }

            } else {
                time++;
            }
        }
    }

    // ================= SJF =================

    void runSJF(ArrayList<Process> processes) {

        int time = 0;
        int completed = 0;

        while (completed < processes.size()) {

            Process shortest = null;

            for (Process p : processes) {

                if (p.arrivalTime <= time
                        && p.remainingTime > 0) {

                    if (shortest == null
                            || p.remainingTime
                            < shortest.remainingTime) {

                        shortest = p;
                    }
                }
            }

            if (shortest != null) {

                if (!shortest.started) {

                    shortest.responseTime =
                            time - shortest.arrivalTime;

                    shortest.started = true;
                }

                shortest.remainingTime--;

                time++;

                if (shortest.remainingTime == 0) {

                    shortest.completionTime = time;

                    shortest.turnaroundTime =
                            time - shortest.arrivalTime;

                    shortest.waitingTime =
                            shortest.turnaroundTime
                                    - shortest.burstTime;

                    completed++;
                }

            } else {
                time++;
            }
        }
    }

    // ================= GANTT =================

    void drawGantt(ArrayList<Process> processes) {

        ganttBox.getChildren().clear();

        for (Process p : processes) {

            for (int i = 0; i < p.burstTime; i++) {

                StackPane block = new StackPane();

                String color =
                        "#"
                        + Integer.toHexString(
                                (p.id * 999999) & 0xffffff
                        );

                block.setStyle(
                        "-fx-background-color:" + color + ";" +
                        "-fx-padding:10;" +
                        "-fx-border-color:black;"
                );

                block.getChildren().add(
                        new Text("P" + p.id)
                );

                ganttBox.getChildren().add(block);
            }
        }
    }

    // ================= ERROR =================

    void showError(String msg) {

        Alert alert =
                new Alert(Alert.AlertType.ERROR);

        alert.setContentText(msg);

        alert.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}