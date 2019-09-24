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
import no.hiof.gruppefire.data.InputValidation;
import no.hiof.gruppefire.model.Arrangement;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import static java.lang.Integer.parseInt;

public class NewArrangementController {

    @FXML
    private Button cancelBtn;
    @FXML
    private TextField nameInput, sportInput, participantsInput, adressInput;
    @FXML
    private DatePicker startDateInput, endDateInput;
    @FXML
    private ComboBox<String> groupInput = new ComboBox<>();
    @FXML
    private ComboBox<String>sportComboBoxInput;

    public void initialize(){


        groupOrIndividualsComboBox();
    }

    @FXML
    public void saveClicked(ActionEvent actionEvent){

        StartController startController = new StartController();
        startController.addArrangementToList(new Arrangement(nameInput.getText(),chosenSport(), parseInt(participantsInput.getText()),adressInput.getText(), trueOrFalse(), startDateInput.getValue(),endDateInput.getValue()));

        close();
    }


    @FXML
    public void cancelClicked(ActionEvent actionEvent){
        close();
    }

    private void close(){
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    private void groupOrIndividualsComboBox(){
        ObservableList<String>groupIndividualCombobox = FXCollections.observableArrayList();
        groupIndividualCombobox.add("Group");
        groupIndividualCombobox.add("Individuals");
        groupInput.setItems(groupIndividualCombobox);
    }



    private boolean trueOrFalse(){
        if(groupInput.getSelectionModel().getSelectedItem() == "Group")
            return true;
        return false;
    }

    private String chosenSport(){

        return sportComboBoxInput.getSelectionModel().getSelectedItem();
    }
}
