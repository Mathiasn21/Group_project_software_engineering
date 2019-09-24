package no.hiof.gruppefire.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.hiof.gruppefire.data.InputValidation;
import no.hiof.gruppefire.model.Arrangement;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class NewArrangementController {

    @FXML
    private Button cancelBtn;
    @FXML
    private TextField nameInput, sportInput, participantsInput;
    @FXML
    private DatePicker startDateInput, endDateInput;
    @FXML
    public void saveClicked(ActionEvent actionEvent){

        StartController startController = new StartController();
        startController.addArrangementToList(new Arrangement(nameInput.getText(),sportInput.getText(), parseInt(participantsInput.getText()),startDateInput.getValue(),endDateInput.getValue()));

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
}
