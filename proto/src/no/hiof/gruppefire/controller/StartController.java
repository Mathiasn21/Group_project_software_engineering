package no.hiof.gruppefire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.hiof.gruppefire.model.Arrangement;

import java.io.IOException;

public class StartController {

    private final static ObservableList<Arrangement>arrangementList = FXCollections.observableArrayList();
    @FXML
    private Button newArrangementBtn;
    @FXML
    private ListView<Arrangement>listview = new ListView<>();
    @FXML
    public void newArrangementClicked() throws IOException {

            FXMLLoader fxmlInnlaster = new FXMLLoader();
            fxmlInnlaster.setLocation(getClass().getResource("../view/NewArrangement.fxml"));
            Parent redigerLayout = fxmlInnlaster.load();

            Scene redigerScene = new Scene(redigerLayout, 500, 300);
            Stage redigerStage = new Stage();

            redigerStage.initModality(Modality.APPLICATION_MODAL);
            Stage stage = (Stage) newArrangementBtn.getScene().getWindow();
            redigerStage.initOwner(stage);

            redigerStage.setScene(redigerScene);
            redigerStage.setTitle("Registrering new arrangement");
            redigerStage.show();
        }

        public void addArrangementToList (Arrangement a){
            arrangementList.add(a);

            for(Arrangement arrangement : arrangementList){
                System.out.println(arrangement.getName());
            }
            listview.setItems(arrangementList);
        }
    }
