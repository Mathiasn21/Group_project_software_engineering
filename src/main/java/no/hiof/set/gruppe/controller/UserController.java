package no.hiof.set.gruppe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.hiof.set.gruppe.Exceptions.DataFormatException;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController extends Controller{
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private String title = "";
    private String name = "";


    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//
    @FXML
    private ListView<String> finishedArrangementsListView;
    @FXML
    private ListView<String> availableArrangementsListView;
    @FXML
    private ListView<String> joinedArrangementsListView;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logOut.setOnAction(this::returnToMainWindow);
        System.out.println(getMainController());
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
