package no.hiof.set.gruppe.GUI.controller.concrete;

/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. FXML Fields
 * 4. On Action Methods
 * 5. Private Methods
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
import javafx.stage.Stage;
import no.hiof.set.gruppe.GUI.controller.abstractions.Controller;
import no.hiof.set.gruppe.GUI.controller.abstractions.ControllerTransferData;
import no.hiof.set.gruppe.GUI.model.ViewInformation;
import no.hiof.set.gruppe.core.Repository;
import no.hiof.set.gruppe.core.exceptions.DataFormatException;
import no.hiof.set.gruppe.core.exceptions.ErrorExceptionHandler;
import no.hiof.set.gruppe.core.exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.core.predicates.ArrangementSort;
import no.hiof.set.gruppe.core.predicates.DateTest;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.constantInformation.SportCategory;
import no.hiof.set.gruppe.model.user.ProtoUser;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This controller controls all functionality and logic pertaining
 * to the Organizer View
 * @author Gruppe4
 */
public class OrganizerController extends ControllerTransferData {
    // --------------------------------------------------//
    //                2.FXML Fields                      //
    // --------------------------------------------------//
    @FXML
    private Button newArrangementBtn, editBtn, deleteBtn;
    @FXML
    private Text arrangementName, arrangementAddress, arrangementDate, arrangementParticipants, arrangementGorI, arrangementSport, arrangementDescription;
    @FXML
    private TextField arrSearch;
    @FXML
    private ChoiceBox<SportCategory> sortOptions;
    @FXML
    private ListView<Arrangement> listView;
    @FXML
    private MenuItem logOut;
    @FXML
    private Text sportHeader, addressHeader, dateHeader, gOrIHeader, participantsHeader, descriptionHeader;

    // --------------------------------------------------//
    //                3.Local Fields                     //
    // --------------------------------------------------//
    private String title = "";
    private String name = "";
    private ObservableList<Arrangement>arrangementListObservable;
    private FilteredList<Arrangement> filteredList;
    private Arrangement currentArrangement = null;
    private static final ProtoUser PROTO_USER = ProtoUser.ORGANIZER;
    private Text[] allTextFields;

    // --------------------------------------------------//
    //                4.On Action Methods                //
    // --------------------------------------------------//

    /**
     * @param event {@link ActionEvent}
     */
    private void sort(ActionEvent event){
        sortAndSearch();
    }

    /**
     * @param mouseEvent {@link MouseEvent}
     */
    private void onClickListView(MouseEvent mouseEvent) {
        changedView();
        listView.refresh();
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onClick(ActionEvent event) {
        title = "Ny";
        name = "NewAlterArrangement.fxml";
        createNewView(this, null);
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onEditClick(ActionEvent event){
        if(listView.getSelectionModel().getSelectedItem() != null){
            title = "Rediger";
            name = "NewAlterArrangement.fxml";
            createNewView(this, currentArrangement);
        }
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onDelete(ActionEvent event){
        if(listView.getSelectionModel().getSelectedItem() == null) return;
        deleteArrangement();
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void returnToMainWindow(ActionEvent event) {
        title = "Logg inn";
        name = "Login.fxml";
        closeWindow(deleteBtn);
        createNewView(this);
    }

    private void search(){
        sortAndSearch();
    }

    // --------------------------------------------------//
    //                5.Private Methods                  //
    // --------------------------------------------------//
    private void changedView(){
        currentArrangement = listView.getSelectionModel().getSelectedItem();
        if(currentArrangement == null)return;
        setInformationAboutArrangementInView();
        checkArrangementDate();
        listView.refresh();
    }

    private void setInformationAboutArrangementInView(){
        setTextColors(true);
        Controller.setFieldsWithDataFromObject(currentArrangement, allTextFields);
    }

    private void deleteArrangement(){
        Arrangement selectedItem = listView.getSelectionModel().getSelectedItem();
        try {
            Repository.deleteArrangement(selectedItem, PROTO_USER);
            arrangementListObservable.remove(selectedItem);
            listView.getSelectionModel().selectFirst();
            clearFields();
            changedView();
        }
        catch (IllegalDataAccess | DataFormatException illegalDataAccess) {
            try {ErrorExceptionHandler.createLogWithDetails(ErrorExceptionHandler.ERROR_ACCESSING_DATA, illegalDataAccess); }
            catch (IOException e) {e.printStackTrace();}
            Controller.createAlert(ErrorExceptionHandler.ERROR_ACCESSING_DATA);
        }
    }

    // --------------------------------------------------//
    //                6.Private Search Methods           //
    // --------------------------------------------------//
    /**
     * Returns a Boolean based on if the Arrangement name contains
     * And is in same category
     * the given search string.
     * @param arrangement Arrangement
     * @return boolean
     */
    private boolean lowerCaseTitleSearch(@NotNull Arrangement arrangement){
        String title = arrangement.getName().toLowerCase();
        String search = arrSearch.getText().toLowerCase();
        return title.contains(search) && categoryMatch(arrangement);
    }

    /**
     * @param arrangement {@link Arrangement}
     * @return boolean
     */
    private boolean categoryMatch(Arrangement arrangement) {
        SportCategory category = sortOptions.getSelectionModel().getSelectedItem();
        return category.equals(SportCategory.ALL) || arrangement.getSport().equals(category.toString());
    }

    private void sortAndSearch(){
        filteredList.setPredicate(this::lowerCaseTitleSearch);
        listView.setItems(filteredList);
        listView.refresh();
    }

    private void liveSearchUpdate(){
        arrSearch.textProperty().addListener(((s) -> search()));
    }

    // --------------------------------------------------//
    //                7.Private Setup Methods            //
    // --------------------------------------------------//
    private void setupActionHandlers() {
        deleteBtn.setOnAction(this::onDelete);
        editBtn.setOnAction(this::onEditClick);
        newArrangementBtn.setOnAction(this::onClick);
        listView.setOnMouseClicked(this::onClickListView);
        sortOptions.setOnAction(this::sort);
        logOut.setOnAction(this::returnToMainWindow);
    }

    private void setupFilteredList(){
        filteredList = arrangementListObservable.filtered(arrangement -> true);
        listView.setItems(filteredList);
        arrSearch.setText("");
    }

    private void populateListView() {
        arrangementListObservable = FXCollections.observableArrayList(Repository.queryAllUserRelatedArrangements(ProtoUser.ORGANIZER));
        arrangementListObservable.sort(ArrangementSort.COMP_DATE_ASC.getComparator());
        setupFilteredList();
        listView.refresh();
    }

    private void populateSportCategories() {
        sortOptions.setItems(FXCollections.observableArrayList(SportCategory.values()));
        sortOptions.getSelectionModel().select(SportCategory.ALL);
    }

    private void checkArrangementDate(){
        boolean test = DateTest.TestExpired.execute(currentArrangement.getStartDate(), currentArrangement.getEndDate());
        editBtn.setDisable(test);
        deleteBtn.setDisable(test);
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
        setupActionHandlers();
        populateListView();
        populateSportCategories();
        liveSearchUpdate();
        setTextColors(false);
        allTextFields = new Text[]{arrangementName, arrangementSport, arrangementAddress, arrangementDate, arrangementParticipants, arrangementGorI, arrangementDescription};
    }

    /**
     * Refreshes the view
     */
    @Override
    public void updateView(){
        if(currentArrangement == null)return;
        changedView();
    }

    /**
     * Handles data setup and interaction from other controllers.
     * @param object Object
     * @throws DataFormatException Exception
     */
    @Override
    public void setDataFields(Object object) throws DataFormatException {
        if (!(object instanceof Arrangement)) throw new DataFormatException();
        Arrangement arrangement = (Arrangement) object;

        MultipleSelectionModel selModel = listView.getSelectionModel();
        try {
            Repository.insertArrangement(arrangement, ProtoUser.ORGANIZER);
            arrangementListObservable.add(arrangement);

        } catch (IllegalDataAccess illegalDataAccess) {
            try {ErrorExceptionHandler.createLogWithDetails(ErrorExceptionHandler.ERROR_ACCESSING_DATA, illegalDataAccess);}
            catch (IOException e) {e.printStackTrace();}
            Controller.createAlert(ErrorExceptionHandler.ERROR_ACCESSING_DATA);
        }
        if(selModel.getSelectedItem() == null) selModel.selectLast();
        listView.refresh();
        changedView();
    }

    /**
     * @return Object
     */
    @Override
    public Object getDataObject() {
        currentArrangement = listView.getSelectionModel().getSelectedItem();
        return currentArrangement;
    }

    /**
     * @return {@link ViewInformation}
     */
    @Override
    public ViewInformation getViewInformation() {
        return new ViewInformation(name, title);
    }

    private void setTextColors(boolean tf) {
        colorizeText(tf, sportHeader, addressHeader, dateHeader, gOrIHeader, participantsHeader, descriptionHeader);
    }
}