package no.hiof.gruppefire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.hiof.gruppefire.MainJavaFX;
import no.hiof.gruppefire.data.DataHandler;
import no.hiof.gruppefire.data.InputValidation;
import no.hiof.gruppefire.model.Arrangement;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import static java.lang.Integer.parseInt;

public class NewAlterArrangementController {

    private Stage stage;

    private MainJavaFX application = MainJavaFX.getApplication();

    private String title;

    @FXML
    private Button cancelBtn;
    @FXML
    private TextField nameInput,participantsInput, adressInput;
    @FXML
    private DatePicker startDateInput, endDateInput;
    @FXML
    private ComboBox<String> groupInput = new ComboBox<>();
    @FXML
    private ComboBox<String>sportComboBoxInput;


    @FXML
    public void saveClicked(ActionEvent actionEvent){

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

    @FXML
    public void cancelClicked(ActionEvent actionEvent){
        close();
    }

    public void initialize(){
        Arrangement arrangement = application.getArrangementToEdit();

        groupOrIndividualsComboBox();
        SportComboBox();

        if(arrangement != null){

            nameInput.setText(arrangement.getName());
            sportComboBoxInput.getSelectionModel().select(arrangement.getSport());
            if(arrangement.getParticipants() > 0)
                participantsInput.setText(Integer.toString(arrangement.getParticipants()));
            adressInput.setText(arrangement.getAdress());
            if(arrangement.isGruppe())
                groupInput.getSelectionModel().select("Groups");
            else
                groupInput.getSelectionModel().select("Individuals");
            startDateInput.setValue(arrangement.getStartDate());
            endDateInput.setValue(arrangement.getEndDate());
        }
    }

    private void groupOrIndividualsComboBox(){
        ObservableList<String>groupIndividualCombobox = FXCollections.observableArrayList();
        groupIndividualCombobox.add("Groups");
        groupIndividualCombobox.add("Individuals");
        groupInput.setItems(groupIndividualCombobox);
    }

    private void SportComboBox(){
        ObservableList<String>sportsComboBox = FXCollections.observableArrayList(DataHandler.sportsToComboBox());
        sportComboBoxInput.setItems(sportsComboBox);
    }

    private boolean trueOrFalse(){
        if(groupInput.getSelectionModel().getSelectedItem() == "Groups")
            return true;
        return false;
    }

    private String chosenSport(){
        return sportComboBoxInput.getSelectionModel().getSelectedItem();
    }

    private void addArrangementToList(){
        StartController startController = new StartController();

        startController.addArrangementToList(new Arrangement(
                nameInput.getText(),
                chosenSport(),
                parseInt(participantsInput.getText()),
                adressInput.getText(), trueOrFalse(),
                startDateInput.getValue(),
                endDateInput.getValue()));
    }

    public void close(){
        application.close(stage);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
