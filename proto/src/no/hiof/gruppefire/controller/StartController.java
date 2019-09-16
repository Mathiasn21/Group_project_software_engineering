package no.hiof.gruppefire.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class StartController {

    @FXML
    private Button newArrangementBtn;
    @FXML
    public void newArrangementClicked() throws IOException {

            FXMLLoader fxmlInnlaster = new FXMLLoader();
            fxmlInnlaster.setLocation(getClass().getResource("../view/NewArrangement.fxml"));
            Parent redigerLayout = fxmlInnlaster.load();

            Scene redigerScene = new Scene(redigerLayout, 300, 300);
            Stage redigerStage = new Stage();

            redigerStage.initModality(Modality.APPLICATION_MODAL);
            Stage stage = (Stage) newArrangementBtn.getScene().getWindow();
            redigerStage.initOwner(stage);

            redigerStage.setScene(redigerScene);
            redigerStage.setTitle("Redigering");
            redigerStage.show();
        }
    }
