package no.hiof.set.gruppe.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import no.hiof.set.gruppe.data.DataHandler;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController extends Controller{
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private String title = "";
    private String name = "";


    private ObservableList<Arrangement> arrangementListObservableJoined;
    private ObservableList<Arrangement> arrangementListObservableAvailable;
    private ObservableList<Arrangement> arrangementListObservableFinished;
    private FilteredList<Arrangement> filteredList;
    private Arrangement currentSelectedArrangement = null;

    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//
    @FXML
    private ListView<Arrangement> finishedArrangementsListView;
    @FXML
    private ListView<Arrangement> availableArrangementsListView;
    @FXML
    private ListView<Arrangement> joinedArrangementsListView;

    @FXML
    private Text arrangementTitle;
    @FXML
    private Text arrangementSport;
    @FXML
    private Text arrangementAddress;
    @FXML
    private Text arrangementDate;
    @FXML
    private Text arrangementParticipants;
    @FXML
    private Text arrangementGroup;

    @FXML
    private Button joinBtn;
    @FXML
    private Button leaveBtn;
    @FXML
    private Button logOut;

    @FXML
    private TextField availableSearch;
    @FXML
    private TextField joinedSearch;

    @FXML
    private ComboBox<String> availableSortingOptions;
    @FXML
    private ComboBox<String> joinedSortingOptions;

    // --------------------------------------------------//
    //                4.On Action Methods                //
    // --------------------------------------------------//



    // --------------------------------------------------//
    //                2.Private Methods                  //
    // --------------------------------------------------//
    private void returnToMainWindow(ActionEvent event) {
        title = "Logg inn";
        name = "Login.fxml";
        ((Stage)logOut.getScene().getWindow()).close();
        createNewView(this);
    }


    //Needs Rework
    private void populateListView() {
        arrangementListObservableJoined = FXCollections.observableArrayList(DataHandler.getUserArrangements(User.USER));
        joinedArrangementsListView.setItems(arrangementListObservableJoined);

        arrangementListObservableAvailable = FXCollections.observableArrayList(DataHandler.getUserArrangements(User.USER));
        availableArrangementsListView.setItems(arrangementListObservableAvailable);

        joinedArrangementsListView.refresh();
        availableArrangementsListView.refresh();
    }
    // --------------------------------------------------//
    //                4.Overridden Methods               //
    // --------------------------------------------------//

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logOut.setOnAction(this::returnToMainWindow);
        populateListView();
    }

    @Override
    public Object getDataObject() {
        return null;
    }

    @Override
    public void setDataFields(Object object) throws DataFormatException {

    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getName() {
        return name;
    }
}