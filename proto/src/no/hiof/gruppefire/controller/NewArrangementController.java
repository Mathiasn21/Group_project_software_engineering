package no.hiof.gruppefire.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class NewArrangementController {

    @FXML
    public void saveClicked(ActionEvent actionEvent){
        System.out.println("save");
//Hoi


    }

    @FXML
    public void cancelClicked(ActionEvent actionEvent){
        System.out.println("cancel");
    }

}
