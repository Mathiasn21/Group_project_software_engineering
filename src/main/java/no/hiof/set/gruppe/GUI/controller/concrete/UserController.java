package no.hiof.set.gruppe.GUI.controller.concrete;

/*Guide
 * 1. Import Statements
 * 2. FXML Fields
 * 3. Local fields
 * 4. On Action Methods
 * 5. Private Functional Methods
 * 6. Private Search Methods
 * 7. Private Setup Methods
 * 8. Overridden Methods
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
import no.hiof.set.gruppe.core.exceptions.DataFormatException;
import no.hiof.set.gruppe.GUI.controller.abstractions.Controller;
import no.hiof.set.gruppe.core.Repository;
import no.hiof.set.gruppe.core.exceptions.ErrorExceptionHandler;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.GUI.model.ViewInformation;
import no.hiof.set.gruppe.model.constantInformation.SportCategory;
import no.hiof.set.gruppe.model.user.ProtoUser;
import no.hiof.set.gruppe.core.predicates.DateTest;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * This controller controls all functionality and logic pertaining
 * to the User View
 * @author Gruppe4
 */
public class UserController extends Controller {

    // --------------------------------------------------//
    //                2.FXML Fields                      //
    // --------------------------------------------------//
    @FXML
    private ListView<Arrangement>availableArrangementsListView, myArrangementsView;
    @FXML
    private Text arrangementTitle, arrangementSport,arrangementAddress,arrangementDate,arrangementParticipants,arrangementGroup, arrangementDescription;
    @FXML
    private RadioButton radioExp, radioOng, radioFut, radioAll;
    @FXML
    private Button joinBtn, leaveBtn;
    @FXML
    private TextField searchAv, searchMy;
    @FXML
    private ComboBox<SportCategory> availableSortingOptionsMy, sortingOptions;
    @FXML
    private MenuItem logOut, myGroups;
    @FXML
    private Text sportHeader, addressHeader, dateHeader, participantsHeader, gOrIHeader, descriptionHeader;

    // --------------------------------------------------//
    //                3.Local Fields                     //
    // --------------------------------------------------//
    private String title = "Bruker";
    private String name = ProtoUser.USER.getViewName();
    private ObservableList<Arrangement> myObservableArrangements, availableObservableArrangements;
    private FilteredList<Arrangement> availableFiltered, myFiltered;
    private Arrangement currentAvailableArrangement = null;
    private Arrangement currentSelectedMyArrangement = null;
    private Arrangement currentSelectedArrangement;
    private final ToggleGroup radioBtns = new ToggleGroup();
    private Text[] allTextFields;
    private final Repository repository = new Repository();

    // --------------------------------------------------//
    //                4.On Action Methods                //
    // --------------------------------------------------//

    /**
     * @param actionEvent {@link ActionEvent}
     */
    private void onClickJoin(ActionEvent actionEvent){
        join();
    }

    /**
     * @param actionEvent {@link ActionEvent}
     */
    private void onClickLeave(ActionEvent actionEvent){
        leave();
    }

    /**
     * @param event {@link MouseEvent}
     */
    private void onClickAvailableArrangementsListView(MouseEvent event){
        setCurrentAvailableArrangement();
    }

    /**
     * @param event {@link MouseEvent}
     */
    private void onClickMyView(MouseEvent event){
        setCurrentMyArrangement();
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onClickLogOut(ActionEvent event) {
        switchView("Logg inn", "Login.fxml");
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onClickMyGroups(ActionEvent event){
        switchView("Mine grupper", "Groups.fxml");
    }

    // --------------------------------------------------//
    //            5.Private Functional Methods           //
    // --------------------------------------------------//

    private void join(){
        if(currentAvailableArrangement == null)return;
        availableObservableArrangements.remove(currentAvailableArrangement);
        myObservableArrangements.add(currentAvailableArrangement);

        try {
            repository.insertUserToArrangement(currentAvailableArrangement, ProtoUser.USER);
        } catch (DataFormatException illegalDataAccess) {
            try { ErrorExceptionHandler.createLogWithDetails(ErrorExceptionHandler.ERROR_ACCESSING_DATA, illegalDataAccess); }
            catch (IOException e) {e.printStackTrace();}
            createAlert(ErrorExceptionHandler.ERROR_ACCESSING_DATA);
        }
        currentSelectedMyArrangement = currentAvailableArrangement;
        currentAvailableArrangement = null;

        availableArrangementsListView.refresh();
        myArrangementsView.refresh();
    }

    private void leave(){
        if(currentSelectedMyArrangement == null)return;
        myObservableArrangements.remove(currentSelectedMyArrangement);
        availableObservableArrangements.add(currentSelectedMyArrangement);

        try {
            repository.deleteUserFromArrangement(currentSelectedMyArrangement, ProtoUser.USER);
        } catch (DataFormatException illegalDataAccess) {
            try { ErrorExceptionHandler.createLogWithDetails(ErrorExceptionHandler.ERROR_ACCESSING_DATA, illegalDataAccess); }
            catch (IOException e) {e.printStackTrace();}
            createAlert(ErrorExceptionHandler.ERROR_ACCESSING_DATA);
        }
        currentAvailableArrangement = currentSelectedMyArrangement;
        currentSelectedMyArrangement = null;
    }

    /**
     * @param titleParam
     * @param nameParam
     */
    private void switchView(String titleParam, String nameParam){
        title = titleParam;
        name = nameParam;
        closeWindow(leaveBtn);
        createNewView(this);
    }

    // --------------------------------------------------//
    //                6.Private Search Methods           //
    // --------------------------------------------------//
    //Could be truncated to one method
    /**
     * @param actionEvent {@link ActionEvent}
     */
    private void search(ActionEvent actionEvent){
        sortAndSearchMy();
    }

    /**
     * @param actionEvent {@link ActionEvent}
     */
    private void searchOrg(ActionEvent actionEvent){
        availableFiltered.setPredicate(this::lowerCaseTitleSearch);
        availableArrangementsListView.setItems(availableFiltered);
        availableArrangementsListView.refresh();
    }

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

    /**
     * Handles searching for signed up Arrangements
     */
    private void sortAndSearchMy(){
        myFiltered.setPredicate(this::lowerCaseTitleSearchMy);
        myArrangementsView.setItems(myFiltered);
        myArrangementsView.refresh();
    }

    private void liveSearchUpdateMy(){
        searchMy.textProperty().addListener(((s) -> searchMyArrangement()));
    }

    private void searchMyArrangement(){
        sortAndSearchMy();
    }

    /**
     * Handles searching for Available Arrangements.
     */
    private void sortAndSearchAv(){
        availableFiltered.setPredicate(this::lowerCaseTitleSearch);
        availableArrangementsListView.setItems(availableFiltered);
        availableArrangementsListView.refresh();
    }

    private void liveSearchUpdateAv(){
        searchAv.textProperty().addListener(((s) -> searchAvArrangement()));
    }

    private void searchAvArrangement(){
        sortAndSearchAv();
    }

    // --------------------------------------------------//
    //                7.Private Setup Methods            //
    // --------------------------------------------------//

    private void setArrangementListInformation() {
        List<Arrangement> allArrang = repository.queryAllArrangements();
        List<Arrangement> userConnectedArrangements = repository.queryAllUserRelatedArrangements(ProtoUser.USER);

        allArrang.removeAll(userConnectedArrangements);
        allArrang.removeIf((arrangement) -> DateTest.TestExpired.execute(arrangement.getStartDate(), arrangement.getEndDate()));

        availableObservableArrangements = FXCollections.observableArrayList(allArrang);
        myObservableArrangements = FXCollections.observableArrayList(userConnectedArrangements);
    }

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
        availableArrangementsListView.setOnMouseClicked(this::onClickAvailableArrangementsListView);
        myArrangementsView.setOnMouseClicked(this::onClickMyView);
        searchMy.setOnAction(this::search);
        searchAv.setOnAction(this::searchOrg);
        joinBtn.setOnAction(this::onClickJoin);
        leaveBtn.setOnAction(this::onClickLeave);
        logOut.setOnAction(this::onClickLogOut);
        myGroups.setOnAction(this::onClickMyGroups);
    }

    private void setupToggleButtons() {
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

    private void setInformationAboutArrangementInView(){
        if(currentSelectedArrangement == null)return;
        setTextColors(true);
        Controller.setFieldsWithDataFromObject(currentSelectedArrangement, allTextFields);
    }

    private void setCurrentAvailableArrangement(){
        Arrangement selectedItem = availableArrangementsListView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){return;}
        currentAvailableArrangement = selectedItem;
        currentSelectedArrangement = currentAvailableArrangement;
        setInformationAboutArrangementInView();
    }

    private void setCurrentMyArrangement(){
        Arrangement selectedItem = myArrangementsView.getSelectionModel().getSelectedItem();
        if(selectedItem == null)return;
        currentSelectedMyArrangement = selectedItem;
        currentSelectedArrangement = currentSelectedMyArrangement;

        if(DateTest.TestExpired.execute(currentSelectedMyArrangement.getStartDate() ,currentSelectedMyArrangement.getEndDate()))leaveBtn.setDisable(true);
        else leaveBtn.setDisable(false);
        setInformationAboutArrangementInView();
    }

    // --------------------------------------------------//
    //                8.Overridden Methods               //
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
        liveSearchUpdateMy();
        liveSearchUpdateAv();
        setupToggleButtons();
        setTextColors(false);
        allTextFields = new Text[]{arrangementTitle, arrangementSport, arrangementAddress, arrangementDate, arrangementParticipants, arrangementGroup, arrangementDescription};
    }

    /**
     * @return {@link ViewInformation}
     */
    @Override
    public ViewInformation getViewInformation() {
        return new ViewInformation(name, title);
    }

    /**
     * @param tf
     */
    private void setTextColors(boolean tf) {
        colorizeText(tf, sportHeader, addressHeader, dateHeader, participantsHeader, gOrIHeader, descriptionHeader);
    }
}
