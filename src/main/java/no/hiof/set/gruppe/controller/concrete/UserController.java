package no.hiof.set.gruppe.controller.concrete;

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
import no.hiof.set.gruppe.controller.abstractions.Controller;
import no.hiof.set.gruppe.data.DataHandler;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.constantInformation.SportCategory;
import no.hiof.set.gruppe.model.user.User;
import no.hiof.set.gruppe.util.DateTest;
import org.jetbrains.annotations.NotNull;
import java.net.URL;
import java.util.*;

public class UserController extends Controller {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private String title, name = "";
    private ObservableList<Arrangement> myObservableArrangements, availableObservableArrangements;
    private FilteredList<Arrangement> availableFiltered, myFiltered;
    private Arrangement currentAvailableArrangement = null;
    private Arrangement currentSelectedMyArrangement = null;
    private Arrangement currentSelectedArrangement;
    private final ToggleGroup radioBtns = new ToggleGroup();

    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//
    @FXML
    private ListView<Arrangement>availableArrangementsListView, myArrangementsView;
    @FXML
    private Text arrangementTitle, arrangementSport,arrangementAddress,arrangementDate,arrangementParticipants,arrangementGroup, arrangementDescription;
    @FXML
    private RadioButton radioExp, radioOng, radioFut, radioAll;
    @FXML
    private Button joinBtn, leaveBtn, logOut;
    @FXML
    private TextField searchAv, searchMy;
    @FXML
    private ComboBox<SportCategory> availableSortingOptionsMy, sortingOptions;

    // --------------------------------------------------//
    //                4.Event Related Methods            //
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

    //onclick could be truncated
    private void onClickListView(MouseEvent event){
        Arrangement selectedItem = availableArrangementsListView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){return;}
        currentAvailableArrangement = selectedItem;
        currentSelectedArrangement = currentAvailableArrangement;
        setDataFields();
    }
    private void onClickMyView(MouseEvent event){
        Arrangement selectedItem = myArrangementsView.getSelectionModel().getSelectedItem();
        if(selectedItem == null)return;
        currentSelectedMyArrangement = selectedItem;
        currentSelectedArrangement = currentSelectedMyArrangement;

        if(DateTest.TestExpired.execute(currentSelectedMyArrangement.getStartDate() ,currentSelectedMyArrangement.getEndDate()))leaveBtn.setDisable(true);
        else leaveBtn.setDisable(false);
        setDataFields();
    }
    private void returnToMainWindow(ActionEvent event) {
        title = "Logg inn";
        name = "Login.fxml";
        ((Stage)logOut.getScene().getWindow()).close();
        createNewView(this);
    }


    // --------------------------------------------------//
    //                5.Private Functional Methods       //
    // --------------------------------------------------//
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


    // --------------------------------------------------//
    //                6.Private Search Methods           //
    // --------------------------------------------------//
    //Could be truncated to one method
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

    //could be truncated to one method
    /**
     * Returns a Boolean based on if the Arrangement name contains
     * And is in same category
     * the given search string.
     * @param arrangement Arrangement
     * @return boolean
     */
    private boolean lowerCaseTitleSearch(@NotNull Arrangement arrangement){
        String title = arrangement.getName().toLowerCase();
        String search = searchAv.getText().toLowerCase();
        return title.contains(search) && categoryMatch(arrangement);
    }

    /**
     * Returns a Boolean based on match with given search string, as well
     * as a given category and a given date predicate.
     * @param arrangement Arrangement
     * @return boolean
     */
    private boolean lowerCaseTitleSearchMy(@NotNull Arrangement arrangement){
        String title = arrangement.getName().toLowerCase();
        String search = searchMy.getText().toLowerCase();
        DateTest predicate = ((DateTest)radioBtns.getSelectedToggle().getUserData());

        return title.contains(search) &&
                categoryMatchMy(arrangement) &&
                predicate.execute(arrangement.getStartDate(), arrangement.getEndDate());
    }

    //should be truncated
    private boolean categoryMatch(Arrangement arrangement) {
        SportCategory category = availableSortingOptionsMy.getSelectionModel().getSelectedItem();
        return category.equals(SportCategory.ALL) || arrangement.getSport().equals(category.toString());
    }
    private boolean categoryMatchMy(Arrangement arrangement) {
        SportCategory category = sortingOptions.getSelectionModel().getSelectedItem();
        return category.equals(SportCategory.ALL) || arrangement.getSport().equals(category.toString());
    }

    // --------------------------------------------------//
    //                7.Private Setup Methods            //
    // --------------------------------------------------//
    private void setArrangementListInformation() {
        List<Arrangement> allArrang = DataHandler.getArrangementsData();
        List<Arrangement> userConnectedArrangements = DataHandler.getUserArrangements(User.USER);

        allArrang.removeAll(userConnectedArrangements);
        allArrang.removeIf((arrangement) -> DateTest.TestExpired.execute(arrangement.getStartDate(), arrangement.getEndDate()));

        availableObservableArrangements = FXCollections.observableArrayList(allArrang);
        myObservableArrangements = FXCollections.observableArrayList(userConnectedArrangements);
    }

    //Could be removed?
    private void populateMyArrangementView() {
        myArrangementsView.refresh();
    }

    private void setupListView(){
        myFiltered = myObservableArrangements.filtered(arrangement -> true);
        availableFiltered = availableObservableArrangements.filtered(arrangement -> true);

        availableArrangementsListView.setItems(availableFiltered);
        availableArrangementsListView.refresh();

        myArrangementsView.setItems(myFiltered);
        myArrangementsView.refresh();
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

    private void setupToggleBtns() {
        //Should be extracted
        radioAll.setToggleGroup(radioBtns);
        radioAll.setUserData(DateTest.ALL);

        radioFut.setToggleGroup(radioBtns);
        radioFut.setUserData(DateTest.TestFuture);

        radioExp.setToggleGroup(radioBtns);
        radioExp.setUserData(DateTest.TestExpired);

        radioOng.setToggleGroup(radioBtns);
        radioOng.setUserData(DateTest.TestOngoing);

        radioBtns.selectToggle(radioAll);
        radioBtns.selectedToggleProperty().addListener((value, old, ne) -> {
            if(old == null || ne == null) return;
            old.setSelected(false);
            ne.setSelected(true);

            if(ne.equals(radioExp))leaveBtn.setDisable(true);
            else leaveBtn.setDisable(false);

            search(new ActionEvent());
        });
    }

    private void setSortingOptions() {
        sortingOptions.setItems(FXCollections.observableArrayList(SportCategory.values()));
        sortingOptions.getSelectionModel().select(SportCategory.ALL);

        availableSortingOptionsMy.setItems(FXCollections.observableArrayList(SportCategory.values()));
        availableSortingOptionsMy.getSelectionModel().select(SportCategory.ALL);
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
        setupToggleBtns();

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

    //change and create a new data object containing the necessary information
    @Override
    public String getTitle() {return title;}

    @Override
    public String getName() {return name;}
}
