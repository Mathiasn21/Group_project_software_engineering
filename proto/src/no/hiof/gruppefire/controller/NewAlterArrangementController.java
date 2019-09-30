package no.hiof.gruppefire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import no.hiof.gruppefire.MainJavaFX;
import no.hiof.gruppefire.data.DataHandler;
import no.hiof.gruppefire.data.InputValidation;
import no.hiof.gruppefire.model.Arrangement;
import static java.lang.Integer.parseInt;

/**
 * NewArrangementController is a class that controls the "NewAlterArrangement" view.
 * Holds information about stage, application and arrangement.
 *
 * @author Gruppe4
 */

public class NewAlterArrangementController {

    private Stage stage;
    private Arrangement arrangementToEdit;
    /**
     * An instance of the MainJavaFX class.
     */
    private MainJavaFX application = MainJavaFX.getApplication();

    @FXML
    private TextField nameInput,participantsInput, adressInput;
    @FXML
    private DatePicker startDateInput, endDateInput;
    /**
     *Dropdown menu with participants options.
     */
    @FXML
    private ComboBox<String> groupInput = new ComboBox<>();

    /**
     *Dropdown menu with sports options.
     */
    @FXML
    private ComboBox<String>sportComboBoxInput;


    /**
     * Gets inputs from NewAlterArrangement view and sends the input data to validation.
     * Calls methods addArrangementToList() and close().
     */
    @FXML
    public void saveClicked(){

        if(InputValidation.arrangementInputValidation(
                nameInput.getText(),
                chosenSport(),
                parseInt(participantsInput.getText()),
                adressInput.getText(),
                trueOrFalse(),
                startDateInput.getValue(),
                endDateInput.getValue())){

            addArrangementToList();
            close();
        }
        else
            System.out.println("what");
    }

    /**
     * Calling the close() method when the cancel button is clicked.
     */
    @FXML
    public void cancelClicked(){
        close();
    }

    /**
     * Sets data in the fields when window is opening.
     * Calls methods groupOrIndividualsComboBox() and SportComboBox().
     */
    public void initialize(){
        arrangementToEdit = application.getArrangementToEdit();

        groupOrIndividualsComboBox();
        SportComboBox();

        if(arrangementToEdit != null){

            nameInput.setText(arrangementToEdit.getName());
            sportComboBoxInput.getSelectionModel().select(arrangementToEdit.getSport());
            if(arrangementToEdit.getParticipants() > 0)
                participantsInput.setText(Integer.toString(arrangementToEdit.getParticipants()));
            adressInput.setText(arrangementToEdit.getAdress());
            if(arrangementToEdit.isGruppe())
                groupInput.getSelectionModel().select("Lag");
            else
                groupInput.getSelectionModel().select("Individuell");
            startDateInput.setValue(arrangementToEdit.getStartDate());
            endDateInput.setValue(arrangementToEdit.getEndDate());
        }
    }

    /**
     * Fills a combobox with alternatives.
     */
    private void groupOrIndividualsComboBox(){
        ObservableList<String>groupIndividualCombobox = FXCollections.observableArrayList();
        groupIndividualCombobox.add("Lag");
        groupIndividualCombobox.add("Individuell");
        groupInput.setItems(groupIndividualCombobox);
    }

    /**
     * Fills a combobox with sports alternatives.
     */
    private void SportComboBox(){
        ObservableList<String>sportsComboBox = FXCollections.observableArrayList(DataHandler.sportsToComboBox());
        sportComboBoxInput.setItems(sportsComboBox);
    }

    /**
     *Checks if an Arrangement includes groups or not.
     * @return true or false
     */
    private boolean trueOrFalse(){
        if(groupInput.getSelectionModel().getSelectedItem() == "Lag")
            return true;
        return false;
    }

    /**
     * Gets the chosen sport from a dropdown list.
     * @return the chosen sport.
     */
    private String chosenSport(){
        return sportComboBoxInput.getSelectionModel().getSelectedItem();
    }

    /**
     * Adds a new arrangement to a list, or edits an existing arrangement.
     */
    private void addArrangementToList(){
        StartController startController = new StartController();

        if(arrangementToEdit.getName() == null){
            startController.addArrangementToList(new Arrangement(
                    nameInput.getText(),
                    chosenSport(),
                    parseInt(participantsInput.getText()),
                    adressInput.getText(),
                    trueOrFalse(),
                    startDateInput.getValue(),
                    endDateInput.getValue()));
        }
        else{
            arrangementToEdit.setName(nameInput.getText());
            arrangementToEdit.setSport(chosenSport());
            arrangementToEdit.setParticipants(parseInt(participantsInput.getText()));
            arrangementToEdit.setAdress(adressInput.getText());
            arrangementToEdit.setGruppe(trueOrFalse());
            arrangementToEdit.setStartDate(startDateInput.getValue());
            arrangementToEdit.setEndDate(endDateInput.getValue());

            startController.updateListview(arrangementToEdit);
        }
    }

    /**
     * Closes running window.
     */
    public void close(){
        application.close(stage);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
