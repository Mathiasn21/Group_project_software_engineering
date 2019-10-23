package no.hiof.set.gruppe.controller;

/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. FXML Fields
 * 4. On Action Methods
 * 5. Private Methods
 * 6. Overridden Methods
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import no.hiof.set.gruppe.data.DataHandler;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.User;

import java.net.URL;
import java.util.*;

public class UserController extends Controller{
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//

    private String title, name = "";
    private ObservableList<Arrangement> myObservableArrangements, availableObservableArrangements;
    private FilteredList<Arrangement> availableFiltered, myFiltered;
    private Arrangement currentAvailableArrangement = null;
    private Arrangement currentSelectedMyArrangement = null;

    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//
    @FXML
    private ListView<Arrangement>availableArrangementsListView, myArrangementsView;
    @FXML
    private Text arrangementTitle, arrangementSport,arrangementAddress,arrangementDate,arrangementParticipants,arrangementGroup, arrangementDescription;
    @FXML
    private Button joinBtn, leaveBtn, logOut;
    @FXML
    private TextField search, searchMy;
    @FXML
    private ComboBox<String> availableSortingOptionsMy, sortingOptions;

    // --------------------------------------------------//
    //                4.On Action Methods                //
    // --------------------------------------------------//
    private void onJoinClick(ActionEvent actionEvent){
        if(currentAvailableArrangement == null)return;
        availableObservableArrangements.remove(currentAvailableArrangement);
        myObservableArrangements.add(currentAvailableArrangement);

        DataHandler.addUserToArrangement(currentAvailableArrangement, User.USER);
        currentSelectedMyArrangement = currentAvailableArrangement;
        currentAvailableArrangement = null;
        updateView();
    }

    private void onLeaveClick(ActionEvent actionEvent){
        if(currentSelectedMyArrangement == null)return;
        myObservableArrangements.remove(currentSelectedMyArrangement);
        availableObservableArrangements.add(currentSelectedMyArrangement);

        DataHandler.deleteUserFromArrangement(currentSelectedMyArrangement, User.USER);
        currentAvailableArrangement = currentSelectedMyArrangement;
        currentSelectedMyArrangement = null;
    }

    private void onClickListView(MouseEvent event){
        Arrangement selectedItem = availableArrangementsListView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){return;}
        currentAvailableArrangement = selectedItem;
    }

    private void onClickMyView(MouseEvent event){
        Arrangement selectedItem = myArrangementsView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){return;}
        currentSelectedMyArrangement = selectedItem;
    }

    // --------------------------------------------------//
    //                5.Private Methods                  //
    // --------------------------------------------------//
    private void search(ActionEvent actionEvent){
        myFiltered.setPredicate(this::lowerCaseTitleSearch);
        myArrangementsView.setItems(myFiltered);
        myArrangementsView.refresh();
    }
    private void searchOrg(ActionEvent actionEvent){
        availableFiltered.setPredicate(this::lowerCaseTitleSearch);
        availableArrangementsListView.setItems(availableFiltered);
        availableArrangementsListView.refresh();
    }

    /**
     * Returns a Boolean based on if the Arrangement name contains
     * And is in same category
     * the given search string.
     * @param arrangement Arrangement
     * @return boolean
     */
    private boolean lowerCaseTitleSearch(Arrangement arrangement){
        String title = arrangement.getName().toLowerCase();
        String search = searchMy.getText().toLowerCase();
        return title.contains(search);
    }


    private void returnToMainWindow(ActionEvent event) {
        title = "Logg inn";
        name = "Login.fxml";
        ((Stage)logOut.getScene().getWindow()).close();
        createNewView(this);
    }

    private Arrangement selectedArrangement(){
        return availableArrangementsListView.getSelectionModel().getSelectedItem();
    }

    private void setupActionHandlers(){
        availableArrangementsListView.setOnMouseClicked(this::onClickListView);
        myArrangementsView.setOnMouseClicked(this::onClickMyView);
        searchMy.setOnAction(this::search);
        joinBtn.setOnAction(this::onJoinClick);
        leaveBtn.setOnAction(this::onLeaveClick);
        logOut.setOnAction(this::returnToMainWindow);
    }


    private void setArrangementListInformation() {
        List<Arrangement> allArrang = DataHandler.getArrangementsData();
        List<Arrangement> userConnectedArrangements = DataHandler.getUserArrangements(User.USER);

        allArrang.removeAll(userConnectedArrangements);
        availableObservableArrangements = FXCollections.observableArrayList(allArrang);
        myObservableArrangements = FXCollections.observableArrayList(userConnectedArrangements);
    }

    private void setupListView(){
        myFiltered = myObservableArrangements.filtered(arrangement -> true);
        availableFiltered = availableObservableArrangements.filtered(arrangement -> true);

        availableArrangementsListView.setItems(availableFiltered);
        availableArrangementsListView.refresh();

        myArrangementsView.setItems(myFiltered);
        myArrangementsView.refresh();
    }

    private void populateMyArrangementView() {
        myArrangementsView.refresh();
    }

    // --------------------------------------------------//
    //                6.Overridden Methods               //
    // --------------------------------------------------//
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setArrangementListInformation();
        populateMyArrangementView();
        setupListView();
        setupActionHandlers();
    }


    @Override
    public void onCloseStoreInformation(){
        DataHandler.storeArrangementsData();
    }


    @Override
    public void updateView(){
        availableArrangementsListView.refresh();
        myArrangementsView.refresh();
    }

    @Override
    public Object getDataObject() {
        return null;
    }

    @Override
    public void setDataFields(Object object) throws DataFormatException {

    }

    //change and create a new data object pertaining the view information
    @Override
    public String getTitle() {return title;}

    @Override
    public String getName() {return name;}
}
