package no.hiof.gruppefire.controller;

import javafx.application.Application;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StartController{

    private String arrangementsFilepath = "src/no/hiof/gruppefire/files/arrangements.json";


    private final static ArrayList<Arrangement>arrangementList = new ArrayList<>();

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
    public void refreshClicked(ActionEvent actionEvent){
        listview.setItems(getArrangementListObservable());
    }

    @FXML
    public void deleteClicked(ActionEvent actionEvent){
        System.out.println("delete");
    }

    @FXML
    public void initialize(){
        listview.setItems(getArrangementListObservable());
    }

    public ObservableList<Arrangement> getArrangementListObservable() {

        ObservableList<Arrangement>arrangementListObservable = FXCollections.observableArrayList(DataHandler.readFromJSONFil(arrangementsFilepath));

        return arrangementListObservable;
    }

    public void addArrangementToList (Arrangement a){

        arrangementList.add(a);

        DataHandler.writeToJSONFile(arrangementList, new File(arrangementsFilepath));
    }


}
