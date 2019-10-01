package no.hiof.set.gruppe.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import no.hiof.gruppefire.model.Arrangement;
import no.hiof.set.gruppe.MainJavaFX;
import no.hiof.set.gruppe.data.DataHandler;

import java.io.IOException;

/**
 * StartController is a class that controls the "start" view.
 * Holds information about arrangementsFilepath, arrangementList and application.
 *
 * @author Gruppe4
 */

public class StartController{

    public Button newArrangementBtn;
    public Button editBtn;
    public Button deleteBtn;
    /**
     * Location to where the arrangement data is stored.
     */
    private String arrangementsFilepath = "/files/arrangements.json";
    /**
     * A list with arrangements.
     */
    private static ObservableList<Arrangement>arrangementListObservable;
    /**
     * An instance of the MainJavaFX class.
     */
    private MainJavaFX application = MainJavaFX.getApplication();
    /**
     * A listView with all of the arrangements.
     */
    @FXML
    private ListView<Arrangement>listview = new ListView<>();

    /**
     * Calls method populateListView() when the view is opening.
     */
    @FXML
    public void initialize(){
        populateListView();
    }

    /**
     * Calls method newAlterWinow when the new arrangement button is clicked.
     * @throws IOException
     */
    @FXML
    public void newArrangementClicked() throws IOException {

            application.newAlterWindow(new Arrangement(), "Ny");
    }

    /**
     * Opens a new window with the title "edit" when the edit arrangement button is clicked.
     * @throws IOException
     */
    @FXML
    public void editClicked() throws IOException{

        if(listview.getSelectionModel().getSelectedItem() != null){
            application.newAlterWindow(listview.getSelectionModel().getSelectedItem(), "Rediger");
        }
        else
            System.out.println("Du har ikke valgt et arrangement");
    }

    /**
     * Calls deletArrangement() method when the delete button is clicked.
     */
    @FXML
    public void deleteClicked(){
        deleteArrangement();
    }

    /**
     * Puts data into a listView.
     */
    public void populateListView() {
        DataHandler.readFromJSONFil(arrangementsFilepath);
        arrangementListObservable = FXCollections.observableArrayList(DataHandler.getArrangementer());
        listview.setItems(arrangementListObservable);
    }

    /**
     * Adds an arrangement to a list.
     * @param a
     */
    public void addArrangementToList (Arrangement a){
        arrangementListObservable.add(a);
        DataHandler.addArrangementer(a);
    }

    /**
     * Refreshes a listView.
     * @param ar
     */
    public void updateListview(Arrangement ar){
        arrangementListObservable.add(ar);
        arrangementListObservable.remove(ar);
    }

    /**
     * Deletes an existing arrangement.
     */
    public void deleteArrangement(){
        Arrangement selectedItem = listview.getSelectionModel().getSelectedItem();
        arrangementListObservable.remove(selectedItem);
        DataHandler.removeArrangementer(selectedItem);
    }
}
