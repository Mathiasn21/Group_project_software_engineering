package no.hiof.gruppefire.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.hiof.gruppefire.data.DataHandler;
import no.hiof.gruppefire.model.Arrangement;
import org.junit.jupiter.api.Test;


import java.io.IOException;

public class StartController{

    private String arrangementsFilepath = "src/no/hiof/gruppefire/files/arrangements.json";

    private static ObservableList<Arrangement>arrangementListObservable;

    @FXML
    private Button newArrangementBtn;
    @FXML
    private ListView<Arrangement>listview = new ListView<>();

    @FXML
    public void newArrangementClicked() throws IOException {
            FXMLLoader fxmlInnlaster = new FXMLLoader();
            fxmlInnlaster.setLocation(getClass().getResource("../view/NewArrangement.fxml"));
            Parent redigerLayout = fxmlInnlaster.load();

            Scene redigerScene = new Scene(redigerLayout, 300, 350);
            Stage redigerStage = new Stage();

            redigerStage.initModality(Modality.APPLICATION_MODAL);
            Stage stage = (Stage) newArrangementBtn.getScene().getWindow();
            redigerStage.initOwner(stage);

            redigerStage.setScene(redigerScene);
            redigerStage.setTitle("Registrering new arrangement");
            redigerStage.show();
    }

    @FXML
    public void editClicked(ActionEvent actionEvent){
        System.out.println("edit");
    }

    @FXML
    public void deleteClicked(ActionEvent actionEvent){
        Arrangement selectedItem = listview.getSelectionModel().getSelectedItem();
        arrangementListObservable.remove(selectedItem);
        DataHandler.removeArrangementer(selectedItem);
    }

    @FXML
    public void initialize(){
        populateListView();
    }

    public void populateListView() {
        DataHandler.readFromJSONFil(arrangementsFilepath);
        arrangementListObservable = FXCollections.observableArrayList(DataHandler.getArrangementer());
        listview.setItems(arrangementListObservable);
    }

    @Test
    public void addArrangementToList (Arrangement a){
        arrangementListObservable.add(a);
        DataHandler.addArrangementer(a);
    }


}
