package gui.popups.action.recordingAction;

import data.FileAccess;
import data.Recording;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.DateTimeStringConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class EditRecordingTimePopup extends RecordingPopup {

    public EditRecordingTimePopup(Recording recording) {
        super(recording);
    }

    @Override
    public void popup(){
        var window = addStage();
        var format = new SimpleDateFormat("HH:mm:ss");

        var startLabel = addLabel("Start time:");
        var startDatePicker = addDatePicker();
        var startTimeField = addTextField(format);
        var startHBox = addHBox(new Node[] {startDatePicker, startTimeField});

        var endLabel = addLabel("End time: ");
        var endDatePicker = addDatePicker();
        var endTimeField = addTextField(format);
        var endHBox = addHBox(new Node[] {endDatePicker, endTimeField});

        var mainButton = addButton("Change recording time");
        var cancelButton = addButton("Cancel");

        // default values for fields
        startDatePicker.setValue(recording.getRecordStartInLocalDateTime().toLocalDate());
        endDatePicker.setValue(recording.getRecordStartInLocalDateTime().toLocalDate());
        startTimeField.setText(String.valueOf(recording.getRecordStartInLocalDateTime().toLocalTime()));
        endTimeField.setText(String.valueOf(recording.getRecordEndInLocalDateTime().toLocalTime()));

        // adding listeners
        startDatePicker.valueProperty().addListener(event -> datePickerListener(startDatePicker, endDatePicker, mainButton, startTimeField, endTimeField));
        endDatePicker.valueProperty().addListener(event -> datePickerListener(startDatePicker, endDatePicker, mainButton, startTimeField, endTimeField));
        startTimeField.textProperty().addListener(event -> datePickerListener(startDatePicker, endDatePicker, mainButton, startTimeField, endTimeField));
        endTimeField.textProperty().addListener(event -> datePickerListener(startDatePicker, endDatePicker, mainButton, startTimeField, endTimeField));

        window.setTitle("Edit recording time");

        mainButtonFunctionality(mainButton, window, startDatePicker, endDatePicker, startTimeField, endTimeField);
        cancelButton.setOnAction(event -> window.close());
        cancelButton.setCancelButton(true);

        var display = addVBox(new Node[]{startLabel, startHBox, endLabel, endHBox, mainButton, cancelButton});

        setScene(window, display);
    }

    private void datePickerListener(DatePicker startDatePicker, DatePicker endDatePicker, Button mainButton, TextField startTextField, TextField endTextField) {
        if (startDatePicker.getValue() == null || endDatePicker.getValue() == null || endDatePicker.getValue().isBefore(startDatePicker.getValue())) {
            mainButton.setDisable(true);
            return;
        }
        if (startTextField.getText().length() < 8 || endTextField.getText().length() < 8) {
            mainButton.setDisable(true);
            return;
        }
        if (startDatePicker.getValue().equals(endDatePicker.getValue()) && startTextField.getText().compareTo(endTextField.getText()) >= 0) {
            mainButton.setDisable(true);
            return;
        }
        mainButton.setDisable(false);
    }

    private TextField addTextField(SimpleDateFormat format){
        var textField = super.addTextField();
        try {
            textField.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format), format.parse("00:00:00")));
        } catch (ParseException e) {
            //TODO think what to do here -- needs to be changed
            throw new RuntimeException();
        }
        return textField;
    }

    private DatePicker addDatePicker(){
        var datePicker = new DatePicker();
        datePickerCellFactory(datePicker);
        return datePicker;
    }

    private void datePickerCellFactory(DatePicker datePicker) {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                var today = LocalDate.now();

                setDisable(empty || date.compareTo(today) > 0 );
            }
        });
    }

    private void mainButtonOnAction(Stage stage, DatePicker startDatePicker, DatePicker endDatePicker, TextField startTimeField, TextField endTimeField) {
        recording.setRecordStart(startDatePicker.getValue().toString() + " " + startTimeField.getText());
        recording.setRecordEnd(endDatePicker.getValue().toString() + " " + endTimeField.getText());
        FileAccess.saveData();
        stage.close();
    }

    private void mainButtonFunctionality(Button button, Stage stage, DatePicker startDatePicker, DatePicker endDatePicker, TextField startTimeField, TextField endTimeField) {
        button.setDefaultButton(true);
        button.setStyle("-fx-background-color: #00B5FE");
        button.setOnAction(event -> mainButtonOnAction(stage, startDatePicker, endDatePicker, startTimeField, endTimeField));
    }
}
