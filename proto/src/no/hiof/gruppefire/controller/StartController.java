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
import no.hiof.gruppefire.MainJavaFX;
import no.hiof.gruppefire.data.DataHandler;
import no.hiof.gruppefire.model.Arrangement;
import org.junit.jupiter.api.Test;


import java.io.IOException;

public class StartController{

    private String arrangementsFilepath = "src/no/hiof/gruppefire/files/arrangements.json";
    private static ObservableList<Arrangement>arrangementListObservable;
    private MainJavaFX application = MainJavaFX.getApplication();

    @FXML
    private Button newArrangementBtn;
    @FXML
    private ListView<Arrangement>listview = new ListView<>();

    @FXML
    public void newArrangementClicked() throws IOException {

            application.newAlterWindow(new Arrangement(), "New");
    }

    @FXML
    public void editClicked(ActionEvent actionEvent) throws IOException{

        if(listview.getSelectionModel().getSelectedItem() != null){
            application.newAlterWindow(listview.getSelectionModel().getSelectedItem(), "Edit");
        }

        else
            System.out.println("Du har ikke valgt et arrangement");
    }

    @FXML
    public void deleteClicked(ActionEvent actionEvent){
        deleteArrangement();
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

    public void addArrangementToList (Arrangement a){
        arrangementListObservable.add(a);
        DataHandler.addArrangementer(a);
    }

    public void updateListview(Arrangement ar){
        arrangementListObservable.add(ar);
        arrangementListObservable.remove(ar);
    }

    public void deleteArrangement(){
        Arrangement selectedItem = listview.getSelectionModel().getSelectedItem();
        arrangementListObservable.remove(selectedItem);
        DataHandler.removeArrangementer(selectedItem);
    }
}
