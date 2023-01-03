import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    /**
     * FXML UI; Text area that is referred to as the "program display,"
     * shows the previously logged contact
     */
    @FXML
    private TextArea displayArea;

    /**
     * FXML UI; Text fields for user input
     */
    @FXML
    private TextField callBox, sentBox, recBox, timeBox, dayBox, bandBox, freqBox, modeBox;

    /**
     * LocalDateTime object used to get the current time from the
     * system clocks with a UTC offset
     */
    private LocalDateTime currentDate;

    /**
     * File object representing the CSV output file
     */
    private File log;

    /**
     * PrintWriter which gets attached to the CSV output file
     */
    private PrintWriter out;

    /**
     * Action triggered when the user selects the option to automatically
     * fill the current time. Uses UTC time with HH:mm:ss formatting,
     * performed by a DateTimeFormatter object.
     * Uses the system clock with the UTC offset.
     */
    public void setTime() {
        currentDate = LocalDateTime.now(Clock.system(ZoneOffset.UTC));

        LocalTime time = currentDate.toLocalTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        timeBox.setText(formatter.format(time));
    }

    /**
     * Action triggered when the user selects the option to automatically
     * fill the current date. Uses the UTC date, which is standard for
     * amateur radio: YYYY-mm-dd
     * Uses the system clock with a UTC offset.
     */
    public void setDate() {
        currentDate = LocalDateTime.now(Clock.system(ZoneOffset.UTC));

        LocalDate date = currentDate.toLocalDate();

        dayBox.setText(date.toString());
    }

    /**
     * Formats two strings; one for the program display and another for the
     * CSV output file. Does so with stringbuilders.
     */
    public void addLog() {

        // Create string builder objects to build the output
        StringBuilder displayString = new StringBuilder();
        StringBuilder csvOut = new StringBuilder();

        // Always have this text at the top of the program display
        displayString.append("Last Contact:\n");

        // Append the callsign information to the program display and CSV output
        // If the input is null, append {Not Provided}
        displayString.append("Callsign:\t");
        if(!callBox.getText().isEmpty()) {
            // Read directly from the input box if not null
            // and append to the program display and CSV out

            // Program display
            displayString.append(callBox.getText());
            displayString.append("\n");

            // CSV output
            csvOut.append("\"");
            csvOut.append(callBox.getText());
            csvOut.append("\",");
        } else {

            // Append {Not Provided} if the text box is empty
            displayString.append("{Not Provided}\n");
            csvOut.append("\"{Not Provided}\",");
        }

        // Append the radio band information to both the program
        // display and CSV output if present
        displayString.append("Band:  \t");
        if(!bandBox.getText().isEmpty()) {

            // Program Display
            displayString.append(bandBox.getText());
            displayString.append("\n");

            // CSV output
            csvOut.append("\"");
            csvOut.append(bandBox.getText());
            csvOut.append("\",");
        } else {
            // If the text boxes are empty
            displayString.append("{Not Provided}\n");
            csvOut.append("\"{Not Provided}\",");
        }

        // Append the specified frequency to the
        // program display and CSV output
        displayString.append("Freq.:  \t");
        if(!freqBox.getText().isEmpty()) {

            // Program Display
            displayString.append(freqBox.getText());
            displayString.append("\n");

            // CSV output
            csvOut.append("\"");
            csvOut.append(freqBox.getText());
            csvOut.append("\",");
        } else {
            displayString.append("{Not Provided}\n");
            csvOut.append("\"{Not Provided}\",");
        }

        // Append the transmission mode to the
        // program display and CSV output
        displayString.append("Mode:  \t");
        if(!modeBox.getText().isEmpty()) {

            // Program display
            displayString.append(modeBox.getText());
            displayString.append("\n");

            // CSV output
            csvOut.append("\"");
            csvOut.append(modeBox.getText());
            csvOut.append("\",");
        } else {
            displayString.append("{Not Provided}\n");
            csvOut.append("\"{Not Provided}\",");
        }

        // Append the supplied time to the
        // program display and CSV output
        displayString.append("Time:\t");
        if(!timeBox.getText().isEmpty()) {

            // Program display
            displayString.append(timeBox.getText());
            displayString.append("\n");

            // CSV output
            csvOut.append("\"");
            csvOut.append(timeBox.getText());
            csvOut.append("\",");
        } else {
            displayString.append("{Not Provided}\n");
            csvOut.append("\"{Not Provided}\",");
        }

        // Append the supplied date to the
        // program display and CSV output
        displayString.append("Date:\t");
        if(!dayBox.getText().isEmpty()) {
            // Program display
            displayString.append(dayBox.getText());
            displayString.append("\n");

            // CSV output
            csvOut.append("\"");
            csvOut.append(dayBox.getText());
            csvOut.append("\",");
        } else {
            displayString.append("{Not Provided}\n");
            csvOut.append("\"{Not Provided}\",");
        }

        // Append the "sent" signal report to the
        // program display and CSV output
        // (The signal report from the other station)
        displayString.append("Sent:   \t");
        if(!sentBox.getText().isEmpty()) {
            displayString.append(sentBox.getText());
            displayString.append("\n");

            csvOut.append("\"");
            csvOut.append(sentBox.getText());
            csvOut.append("\",");
        } else {
            displayString.append("{Not Provided}\n");
            csvOut.append("\"{Not Provided}\",");
        }

        // Append the "received" signal report to the
        // program display and CSV output
        // (How the user hears the other station)
        displayString.append("Received:\t");
        if(!recBox.getText().isEmpty()) {
            displayString.append(recBox.getText());
            displayString.append("\n");

            csvOut.append("\"");
            csvOut.append(recBox.getText());
            csvOut.append("\"\n");
        } else {
            displayString.append("{Not Provided}\n");
            csvOut.append("\"{Not Provided}\"\n");
        }

        // Convert the string builder to an actual string and
        // update the program display
        displayArea.setText(displayString.toString());

        // Convert the CSV string builder to a string and
        // write it to the printwriter associated with the CSV file
        out.write(csvOut.toString());
        out.flush();

        // Clear all entries except band/frequency/mode from
        // the text boxes
        callBox.setText("");
        sentBox.setText("");
        recBox.setText("");
        timeBox.setText("");
        dayBox.setText("");
    }

    /**
     * Clears all text input boxes
     */
    public void clear() {
        callBox.setText("");
        sentBox.setText("");
        recBox.setText("");
        timeBox.setText("");
        dayBox.setText("");
        freqBox.setText("");
        bandBox.setText("");
        modeBox.setText("");
    }

    /**
     * Creates a new CSV file to store logged contacts.
     * Does so by calling initialize()
     */
    public void newLog() {
        initialize(null, null);
    }

    /**
     * Initialize the program by prompting the user to create a log file.
     * Also reused when clicking the "New Log" button
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Create log file");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV File", "*.csv"));
        log = chooser.showSaveDialog(null);

        try {
            log.createNewFile();
            out = new PrintWriter(log);
            out.write("Callsign,Band,Frequency,Mode,Time,Date,Sent,Received\n");
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

}
