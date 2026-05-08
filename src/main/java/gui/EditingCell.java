package gui;

import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

class EditingCell extends TableCell<String[], String> {

    private TextField textField;
    private final int columnIndex;

    public EditingCell(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    @Override
    public void startEdit() {
        super.startEdit();
        textField = new TextField(getItem());
        textField.setOnAction(e -> commitEdit(textField.getText()));
        setGraphic(textField);
        setText(null);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem());
        setGraphic(null);
    }

    @Override
    public void commitEdit(String newValue) {
        super.commitEdit(newValue);
        String[] row = getTableView().getItems().get(getIndex());
        row[columnIndex] = newValue;
        getTableView().refresh();
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item);
        }
    }
}