package no.hiof.set.gruppe.controller;

/*Guide
 * 1. Import Statements
 * 2. Constructors
 * 3. Getters
 * 4. Setters
 * 5. Overridden Methods
 * */


// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import no.hiof.set.gruppe.MainJavaFX;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.data.DataHandler;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * StartController is a class that controls the "start" view.
 * Holds information about arrangementsFilepath, arrangementList and application.
 *
 * @author Gruppe4
 */
public class StartController extends Controller {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private String title = "";
    private String name = "NewAlterArrangement.fxml";
    private ObservableList<Arrangement>arrangementListObservable;
    private Arrangement currentArrangement = null;
    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//
    @FXML
    private Button newArrangementBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private ListView<Arrangement>listview = new ListView<>();

    // --------------------------------------------------//
    //                3.On Action Methods                //
    // --------------------------------------------------//
    private void onClick(ActionEvent event) {
        title = "Ny";
        createNewView(this);
    }

    private void onEditClick(ActionEvent event){
        if(listview.getSelectionModel().getSelectedItem() != null){
            title = "Rediger";
            createNewView(this, currentArrangement);
            System.out.println("Engaging edit btn");
        }
        else
            System.out.println("Du har ikke valgt et arrangement");
    }

    private void onDelete(ActionEvent event){
        if(listview.getSelectionModel().getSelectedItem() == null)
            return;
        deleteArrangement();
    }

    // --------------------------------------------------//
    //                2.Private Methods                  //
    // --------------------------------------------------//
    private void populateListView() {
        arrangementListObservable = FXCollections.observableArrayList(DataHandler.getArrangementsData());
        listview.setItems(arrangementListObservable);
        listview.refresh();
    }
    private void addArrangementToList (Arrangement a){
        arrangementListObservable.add(a);
        DataHandler.addArrangementer(a);
    }

    private void deleteArrangement(){
        Arrangement selectedItem = listview.getSelectionModel().getSelectedItem();
        arrangementListObservable.remove(selectedItem);
        DataHandler.removeArrangementer(selectedItem);
    }

    // --------------------------------------------------//
    //                5.Overridden Methods               //
    // --------------------------------------------------//
    @Override
    public String getTitle() {
        return title;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getDataObject() {
        return listview.getSelectionModel().getSelectedItem();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deleteBtn.setOnAction(this::onDelete);
        editBtn.setOnAction(this::onEditClick);
        newArrangementBtn.setOnAction(this::onClick);
        listview.setOnMouseClicked(this::onClickListView);
        populateListView();
        //listview.refresh();
    }

    private void onClickListView(MouseEvent mouseEvent) {
        Arrangement arrangement = listview.getSelectionModel().getSelectedItem();
        if(currentArrangement == null || currentArrangement.equals(arrangement)){
            currentArrangement = arrangement;
        }
        System.out.println(currentArrangement);
    }
}
