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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import no.hiof.set.gruppe.data.DataHandler;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.User;
import no.hiof.set.gruppe.model.UserConnectedArrangement;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public class UserController extends Controller{
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//

    private String title, name = "";
    private ObservableList<Arrangement> arrangementListObservableJoined, arrangementListObservableAvailable, arrangementListObservableFinished;
    private FilteredList<Arrangement> filteredList, joinedFilteredList;
    private Arrangement currentSelectedArrangement = null;

    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//

    @FXML
    private ListView<Arrangement> finishedArrangementsListView, availableArrangementsListView = new ListView<>(), joinedArrangementsListView, myArrangementsListView = new ListView<>();
    @FXML
    private Text arrangementTitle, arrangementSport,arrangementAddress,arrangementDate,arrangementParticipants,arrangementGroup, arrangementDescription;
    @FXML
    private Button joinBtn, leaveBtn, logOut;
    @FXML
    private TextField availableSearch, joinedSearch;
    @FXML
    private ComboBox<String> availableSortingOptions, joinedSortingOptions;

    // --------------------------------------------------//
    //                4.On Action Methods                //
    // --------------------------------------------------//

    private void onJoinClick(ActionEvent actionEvent){
        //Trenger logikk
        System.out.println("meld på");

        arrangementListObservableJoined.add(availableArrangementsListView.getSelectionModel().getSelectedItem());
        arrangementListObservableAvailable.remove(availableArrangementsListView.getSelectionModel().getSelectedItem());
    }

    private void onLeaveClick(ActionEvent actionEvent){
        //Trenger logikk
        System.out.println("meld av");

        arrangementListObservableAvailable.add(myArrangementsListView.getSelectionModel().getSelectedItem());
        arrangementListObservableJoined.remove(myArrangementsListView.getSelectionModel().getSelectedItem());
    }

    public void myArrangementListClicked(MouseEvent mouseEvent) {
        if(myArrangementsListView.getSelectionModel().getSelectedItem() != null)
            populateArrangementDisplay(myArrangementsListView.getSelectionModel().getSelectedItem());
    }

    public void availableArrangementListClicked(MouseEvent mouseEvent) {
        if(availableArrangementsListView.getSelectionModel().getSelectedItem() != null)
            populateArrangementDisplay(availableArrangementsListView.getSelectionModel().getSelectedItem());
    }

    // --------------------------------------------------//
    //                5.Private Methods                  //
    // --------------------------------------------------//

    private void returnToMainWindow(ActionEvent event) {
        title = "Logg inn";
        name = "Login.fxml";
        ((Stage)logOut.getScene().getWindow()).close();
        createNewView(this);
    }

    private void setupActionHandlers(){
        joinBtn.setOnAction(this::onJoinClick);
        leaveBtn.setOnAction(this::onLeaveClick);
    }

    //Henter bare det samme som i OrganizerController, burde gjøres om så en enkelt bruker får sine egne arrangementer
    private void populateAvailableArrangementListView(){

        arrangementListObservableAvailable = FXCollections.observableArrayList(DataHandler.getUserArrangements(User.ORGANIZER));
        setUpFilteredListAvailable();
        availableArrangementsListView.refresh();
    }

    private void setUpFilteredListAvailable(){
        filteredList = arrangementListObservableAvailable.filtered(arrangement -> true);
        availableArrangementsListView.setItems(filteredList);
    }

    private void populateJoinedArrangementListView() {
        arrangementListObservableJoined = FXCollections.observableArrayList(DataHandler.getUserArrangements(User.USER));
        setUpFilteredListJoined();
        myArrangementsListView.refresh();
    }

    private void setUpFilteredListJoined() {
        joinedFilteredList = arrangementListObservableJoined.filtered(arrangement -> true);
        myArrangementsListView.setItems(joinedFilteredList);
    }

    private void populateArrangementDisplay(Arrangement displayArrangement) {
        arrangementTitle.setText(displayArrangement.getName());
        arrangementSport.setText(displayArrangement.getSport());
        arrangementAddress.setText(displayArrangement.getAdress());
        arrangementDate.setText(displayArrangement.getStartDate() + " - " + displayArrangement.getEndDate());
        arrangementParticipants.setText(Integer.toString(displayArrangement.getParticipants()));
        arrangementGroup.setText("");
        arrangementDescription.setText(displayArrangement.getDescription());
    }

    private Collection<UserConnectedArrangement> genUConnArrangement() {
        Collection<UserConnectedArrangement> result = new ArrayList<>();
        for(Arrangement arr : arrangementListObservableJoined){
            result.add(new UserConnectedArrangement(arr.getID(), User.USER.getName()));
        }
        return result;
    }


    /*
    //Needs Rework
    private void populateListView() {
        arrangementListObservableJoined = FXCollections.observableArrayList(DataHandler.getUserArrangements(User.USER));
        joinedArrangementsListView.setItems(arrangementListObservableJoined);

        arrangementListObservableAvailable = FXCollections.observableArrayList(DataHandler.getUserArrangements(User.USER));
        availableArrangementsListView.setItems(arrangementListObservableAvailable);

        joinedArrangementsListView.refresh();
        availableArrangementsListView.refresh();
    }
    */

    // --------------------------------------------------//
    //                6.Overridden Methods               //
    // --------------------------------------------------//

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logOut.setOnAction(this::returnToMainWindow);
        populateAvailableArrangementListView();
        populateJoinedArrangementListView();
        setupActionHandlers();
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

    @Override
    public void onCloseStoreInformation() {
        // Går for øyeblikket bare ann å legge til. Burde endre dette til å oppdatere istedenfor
        DataHandler.updateUserArrangements(genUConnArrangement(), User.USER);
    }
}
