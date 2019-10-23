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
import no.hiof.set.gruppe.model.SportCategory;
import no.hiof.set.gruppe.model.User;

import java.net.URL;
import java.time.LocalDate;
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
    private Arrangement currentSelectedArrangement;
    private Text[] allTextFields;

    private final ToggleGroup radioBtns = new ToggleGroup();
    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//
    @FXML
    private ListView<Arrangement>availableArrangementsListView, myArrangementsView;
    @FXML
    private Text arrangementTitle, arrangementSport,arrangementAddress,arrangementDate,arrangementParticipants,arrangementGroup, arrangementDescription;
    @FXML
    private RadioButton radioExp, radioOng, radioFut;
    @FXML
    private Button joinBtn, leaveBtn, logOut;
    @FXML
    private TextField searchAv, searchMy;
    @FXML
    private ComboBox<SportCategory> availableSortingOptionsMy, sortingOptions;

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
        currentSelectedArrangement = currentAvailableArrangement;
        setDataFields();
    }

    private void onClickMyView(MouseEvent event){
        Arrangement selectedItem = myArrangementsView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){return;}
        currentSelectedMyArrangement = selectedItem;
        currentSelectedArrangement = currentSelectedMyArrangement;
        setDataFields();
    }

    // --------------------------------------------------//
    //                5.Private Methods                  //
    // --------------------------------------------------//
    private void setSortingOptions() {
        sortingOptions.setItems(FXCollections.observableArrayList(SportCategory.values()));
        sortingOptions.getSelectionModel().select(SportCategory.ALL);
        availableSortingOptionsMy.setItems(FXCollections.observableArrayList(SportCategory.values()));
        availableSortingOptionsMy.getSelectionModel().select(SportCategory.ALL);
    }


    private void search(ActionEvent actionEvent){
        myFiltered.setPredicate(this::lowerCaseTitleSearchMy);
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
        String search = searchAv.getText().toLowerCase();
        RadioPredicate predicate = ((RadioPredicate)radioBtns.getUserData());
        return title.contains(search) &&
                categoryMatch(arrangement) &&
                predicate.execute(arrangement.getStartDate(), arrangement.getEndDate());
    }

    /**
     * Returns a Boolean based on if the Arrangement name contains
     * And is in same category
     * the given search string.
     * @param arrangement Arrangement
     * @return boolean
     */
    private boolean lowerCaseTitleSearchMy(Arrangement arrangement){
        String title = arrangement.getName().toLowerCase();
        String search = searchMy.getText().toLowerCase();
        return title.contains(search) && categoryMatchMy(arrangement);
    }


    private boolean categoryMatch(Arrangement arrangement) {
        SportCategory category = availableSortingOptionsMy.getSelectionModel().getSelectedItem();
        return category.equals(SportCategory.ALL) || arrangement.getSport().equals(category.toString());
    }

    private boolean categoryMatchMy(Arrangement arrangement) {
        SportCategory category = sortingOptions.getSelectionModel().getSelectedItem();
        return category.equals(SportCategory.ALL) || arrangement.getSport().equals(category.toString());
    }


    private void returnToMainWindow(ActionEvent event) {
        title = "Logg inn";
        name = "Login.fxml";
        ((Stage)logOut.getScene().getWindow()).close();
        createNewView(this);
    }

    private void setupActionHandlers(){
        availableArrangementsListView.setOnMouseClicked(this::onClickListView);
        myArrangementsView.setOnMouseClicked(this::onClickMyView);
        searchMy.setOnAction(this::search);
        searchAv.setOnAction(this::searchOrg);
        joinBtn.setOnAction(this::onJoinClick);
        leaveBtn.setOnAction(this::onLeaveClick);
        logOut.setOnAction(this::returnToMainWindow);
    }

    private void setDataFields(){
        if(currentSelectedArrangement == null){return;}
        arrangementTitle.setText(currentSelectedArrangement.getName());
        arrangementSport.setText(currentSelectedArrangement.getSport());
        arrangementAddress.setText(currentSelectedArrangement.getAddress());
        arrangementDate.setText(currentSelectedArrangement.getStartDate().toString());
        arrangementParticipants.setText(String.valueOf(currentSelectedArrangement.getParticipants()));
        arrangementGroup.setText(currentSelectedArrangement.getSport());
        arrangementDescription.setText(currentSelectedArrangement.getDescription());
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
        setSortingOptions();
        populateMyArrangementView();
        setupListView();
        setupActionHandlers();

        //Should be extracted
        radioFut.setToggleGroup(radioBtns);
        radioFut.setUserData(RadioPredicate.TestBefore);
        radioExp.setToggleGroup(radioBtns);
        radioOng.setToggleGroup(radioBtns);
        radioBtns.selectedToggleProperty().addListener((value, old, ne) -> {
            if(old == null || ne == null) return;
            old.setSelected(false);
            ne.setSelected(true);
            search(new ActionEvent());
        });
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


    private enum RadioPredicate{

        TestBefore("Expirert", (startDate, endDate)->{ return endDate.isBefore(LocalDate.now());}),
        TestOngoing("Fremtidige", (startDate, endDate)->{ return endDate.isAfter(LocalDate.now());}),
        TestFuture("Fremtidige", (startDate, endDate)->{ return endDate.isAfter(LocalDate.now());}),
        ALL("Fremtidige", (startDate, endDate)->{ return true;});

        private final String name;
        private final DatePredicate predicate;

        RadioPredicate(String name, DatePredicate predicate) {
            this.name = name;
            this.predicate = predicate;
        }

        @Override
        public String toString(){return name;}

        public boolean execute(LocalDate startDate, LocalDate endDate){return predicate.testDate(startDate, endDate);}
    }
}
