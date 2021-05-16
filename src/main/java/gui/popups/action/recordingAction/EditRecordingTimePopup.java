package gui.popups.action.recordingAction;

import data.FileAccess;
import data.Recording;
import gui.popups.notification.ErrorPopup;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.DateTimeStringConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditRecordingTimePopup extends RecordingPopup {

    public EditRecordingTimePopup(Recording recording) {
        super(recording);
    }

    /**
     * Pops up the popup for the user to interact with
     */
    @Override
    public void popup(){
        var window = addStage();
        var timeFormat = new SimpleDateFormat("HH:mm:ss");

        var startLabel = addLabel("Start time:");
        var startDatePicker = new DatePicker();
        startDatePickerCellFactory(startDatePicker);
        var startTimeField = addTextField(timeFormat);
        var startHBox = addHBox(new Node[] {startDatePicker, startTimeField});

        var endLabel = addLabel("End time: ");
        var endDatePicker = new DatePicker();
        endDatePickerCellFactory(startDatePicker, endDatePicker);
        var endTimeField = addTextField(timeFormat);
        var endHBox = addHBox(new Node[] {endDatePicker, endTimeField});

        var mainButton = addButton("Change recording time");
        var cancelButton = addButton("Cancel");

        // default values for fields
        startDatePicker.setValue(recording.getRecordStartInLocalDateTime().toLocalDate());
        endDatePicker.setValue(recording.getRecordEndInLocalDateTime().toLocalDate());
        startTimeField.setText(recording.getRecordStartInLocalDateTime().toLocalTime().format(DateTimeFormatter.ofPattern(timeFormat.toPattern())));
        endTimeField.setText(recording.getRecordEndInLocalDateTime().toLocalTime().format(DateTimeFormatter.ofPattern(timeFormat.toPattern())));

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

    /**
     * Listens if no rule is broken with the end and start time and if something is broken, disables the main button
     * @param startDatePicker - end date
     * @param endDatePicker - start date
     * @param mainButton - the main button, which is going to be set disabled or not
     * @param startTextField - start time
     * @param endTextField - end time
     */
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

    /**
     * Method for creating a textField with the textFormatter
     * @param format - "HH:mm:ss"
     * @return textField with the given properties
     */
    private TextField addTextField(SimpleDateFormat format){
        var textField = super.addTextField();
        try {
            textField.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format), format.parse("00:00:00")));
        } catch (ParseException e) {
            new ErrorPopup("Formatting error with the text field!").popup();
            return null;
        }
        return textField;
    }

    /**
     * Sets the default cell factory for the start date picker
     * @param startDatePicker the DatePicker to add the custom cell factory to
     */
    private void startDatePickerCellFactory(DatePicker startDatePicker) {
        startDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                var today = LocalDate.now();

                setDisable(empty || date.compareTo(today) > 0 );
            }
        });
    }

    /**
     * Sets the default cell factory for the end date picker
     * @param endDatePicker the DatePicker to add the custom cell factory to
     */
    private void endDatePickerCellFactory(DatePicker startDatePicker, DatePicker endDatePicker) {
        endDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                var today = LocalDate.now();

                setDisable(empty || date.compareTo(today) > 0 || date.compareTo(startDatePicker.getValue()) < 0);
            }
        });
    }

    /**
     * Action for the main button when action is called (enter or click)
     * @param stage - the stage to be closed
     * @param startDatePicker - start date
     * @param endDatePicker - end date
     * @param startTimeField - start time
     * @param endTimeField - end time
     */
    private void mainButtonOnAction(Stage stage, DatePicker startDatePicker, DatePicker endDatePicker, TextField startTimeField, TextField endTimeField) {
        recording.setRecordStart(startDatePicker.getValue().toString() + " " + startTimeField.getText());
        recording.setRecordEnd(endDatePicker.getValue().toString() + " " + endTimeField.getText());
        FileAccess.saveData();
        stage.close();
    }

    /**
     * Default functionality and configurations for the main button
     * @param button - the main button
     * @param stage - the stage to be closed
     * @param startDatePicker - start date
     * @param endDatePicker - end date
     * @param startTimeField - start time
     * @param endTimeField - end time
     */
    private void mainButtonFunctionality(Button button, Stage stage, DatePicker startDatePicker, DatePicker endDatePicker, TextField startTimeField, TextField endTimeField) {
        button.setDefaultButton(true);
        button.setStyle("-fx-background-color: #00B5FE");
        button.setOnAction(event -> mainButtonOnAction(stage, startDatePicker, endDatePicker, startTimeField, endTimeField));
    }
}
