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
import no.hiof.set.gruppe.data.Repository;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.ViewInformation;
import no.hiof.set.gruppe.model.constantInformation.SportCategory;
import no.hiof.set.gruppe.model.user.ProtoUser;
import no.hiof.set.gruppe.util.DateTest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    /**
     * @param actionEvent {@link ActionEvent}
     */
    private void onJoinClick(ActionEvent actionEvent){
        if(currentAvailableArrangement == null)return;
        availableObservableArrangements.remove(currentAvailableArrangement);
        myObservableArrangements.add(currentAvailableArrangement);

        Repository.addUserToArrangement(currentAvailableArrangement, ProtoUser.USER);
        currentSelectedMyArrangement = currentAvailableArrangement;
        currentAvailableArrangement = null;
        updateView();
    }

    /**
     * @param actionEvent {@link ActionEvent}
     */
    private void onLeaveClick(ActionEvent actionEvent){
        if(currentSelectedMyArrangement == null)return;
        myObservableArrangements.remove(currentSelectedMyArrangement);
        availableObservableArrangements.add(currentSelectedMyArrangement);

        Repository.deleteUserFromArrangement(currentSelectedMyArrangement, ProtoUser.USER);
        currentAvailableArrangement = currentSelectedMyArrangement;
        currentSelectedMyArrangement = null;
    }

    //onclick could be truncated
    /**
     * @param event {@link MouseEvent}
     */
    private void onClickListView(MouseEvent event){
        Arrangement selectedItem = availableArrangementsListView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){return;}
        currentAvailableArrangement = selectedItem;
        currentSelectedArrangement = currentAvailableArrangement;
        setInformationAboutArrangementInView();
    }

    /**
     * @param event {@link MouseEvent}
     */
    private void onClickMyView(MouseEvent event){
        Arrangement selectedItem = myArrangementsView.getSelectionModel().getSelectedItem();
        if(selectedItem == null)return;
        currentSelectedMyArrangement = selectedItem;
        currentSelectedArrangement = currentSelectedMyArrangement;

        if(DateTest.TestExpired.execute(currentSelectedMyArrangement.getStartDate() ,currentSelectedMyArrangement.getEndDate()))leaveBtn.setDisable(true);
        else leaveBtn.setDisable(false);
        setInformationAboutArrangementInView();
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void returnToMainWindow(ActionEvent event) {
        title = "Logg inn";
        name = "Login.fxml";
        ((Stage)logOut.getScene().getWindow()).close();
        createNewView(this);
    }

    // --------------------------------------------------//
    //                5.Private Functional Methods       //
    // --------------------------------------------------//
    private void setInformationAboutArrangementInView(){
        ArrayList<Text> viewFields = viewFields(arrangementTitle, arrangementSport,arrangementAddress,arrangementDate,arrangementParticipants,arrangementGroup, arrangementDescription);
        ArrayList<String> data = arrangementData(currentSelectedArrangement);
        for(int i = 0; i < data.size(); i++)
            viewFields.get(i).setText(data.get(i));
    }

    // --------------------------------------------------//
    //                6.Private Search Methods           //
    // --------------------------------------------------//
    //Could be truncated to one method
    /**
     * @param actionEvent {@link ActionEvent}
     */
    private void search(ActionEvent actionEvent){
        myFiltered.setPredicate(this::lowerCaseTitleSearchMy);
        myArrangementsView.setItems(myFiltered);
        myArrangementsView.refresh();
    }

    /**
     * @param actionEvent {@link ActionEvent}
     */
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
     * @param arrangement {@link Arrangement}
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
     * @param arrangement {@link Arrangement}
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
    /**
     * @param arrangement {@link Arrangement}
     * @return boolean
     */
    private boolean categoryMatch(Arrangement arrangement) {
        SportCategory category = availableSortingOptionsMy.getSelectionModel().getSelectedItem();
        return category.equals(SportCategory.ALL) || arrangement.getSport().equals(category.toString());
    }
    /**
     * @param arrangement {@link Arrangement}
     * @return boolean
     */
    private boolean categoryMatchMy(Arrangement arrangement) {
        SportCategory category = sortingOptions.getSelectionModel().getSelectedItem();
        return category.equals(SportCategory.ALL) || arrangement.getSport().equals(category.toString());
    }

    // --------------------------------------------------//
    //                7.Private Setup Methods            //
    // --------------------------------------------------//
    private void setArrangementListInformation() {
        List<Arrangement> allArrang = Repository.getArrangementsData();
        List<Arrangement> userConnectedArrangements = Repository.getUserArrangements(ProtoUser.USER);

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
    /**
     * @param location {@link URL}
     * @param resources {@link ResourceBundle}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setArrangementListInformation();
        setSortingOptions();
        populateMyArrangementView();
        setupListView();
        setupActionHandlers();
        setupToggleBtns();
    }

    /**
     * Just refreshes the view
     */
    @Override
    public void updateView(){
        availableArrangementsListView.refresh();
        myArrangementsView.refresh();
    }

    /**
     * @return Object
     */
    @Override
    @Nullable
    public Object getDataObject() {
        return null;
    }

    /**
     * @param object Object
     * @throws DataFormatException wrongDataFormat {@link DataFormatException}
     */
    @Override
    public void setDataFields(Object object) throws DataFormatException {

    }

    /**
     * @return {@link ViewInformation}
     */
    //new method for returning information about the view
    @Override
    public ViewInformation getViewInformation() {
        return new ViewInformation(name, title);
    }
}
