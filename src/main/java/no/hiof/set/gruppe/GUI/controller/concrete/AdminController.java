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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import no.hiof.set.gruppe.GUI.controller.abstractions.Controller;
import no.hiof.set.gruppe.GUI.controller.abstractions.ControllerTransferData;
import no.hiof.set.gruppe.core.exceptions.DataFormatException;
import no.hiof.set.gruppe.core.exceptions.ErrorExceptionHandler;
import no.hiof.set.gruppe.core.exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.core.Repository;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.GUI.model.ViewInformation;
import no.hiof.set.gruppe.model.constantInformation.SportCategory;
import no.hiof.set.gruppe.model.user.ProtoUser;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the main logic pertaining to the Admin View
 */
public class AdminController extends ControllerTransferData {

    // --------------------------------------------------//
    //                2.FXML Fields                      //
    // --------------------------------------------------//

     @FXML
     private Button edit, delete;
     @FXML
     private ListView<Arrangement> arrangementListView;
     @FXML
     private TextField searchInput;
     @FXML
     private Text arrangementSport, arrangementName, arrangementAdress, arrangementDate, arrangementGorI, arrangementParticipants, arrangementDescription;
     @FXML
     private MenuItem logOut;
     @FXML
     private Text sportHeader, adresHeader, dateHeader, gOrIHeader, participantsHeader, descriptionHeader;

    // --------------------------------------------------//
    //                3.Local Fields                     //
    // --------------------------------------------------//

    private String title = "Login";
    private String name = "";
    private Arrangement currentArrangement = null;
    private ObservableList<Arrangement> arrangementListObservable;
    private Text[] textFields;
    private FilteredList<Arrangement> filteredArrangements;

    // --------------------------------------------------//
    //                4.On action Methods                //
    // --------------------------------------------------//

    /**
     * @param event {@link ActionEvent}
     */
    private void onDeleteClick(ActionEvent event){
        if(checkIfLegalArrangement()){
            deleteArrangement();
            updateView();
        }
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onEditClick(ActionEvent event){
        if(checkIfLegalArrangement()){
            editArrangement();
        }
    }

    private void onLogOutClick(ActionEvent event){
        returnToMainWindow();
    }

    /**
     * @param mouseEvent {@link MouseEvent}
     */
    private void onListViewClick(MouseEvent mouseEvent){
        if(checkIfLegalArrangement()){
            setCurrentArrangement();
            setInformationAboutArrangementInView();
        }
    }

    // --------------------------------------------------//
    //                5.Private Functional Methods       //
    // --------------------------------------------------//

    /**
     * @return Arrangement
     */
    private Arrangement clickedItemFromListView(){
        return arrangementListView.getSelectionModel().getSelectedItem();
    }

    /**
     * @return boolean
     */
    private boolean checkIfLegalArrangement(){
        return clickedItemFromListView() != null && clickedItemFromListView().getStartDate() != null;
    }

    private void deleteArrangement(){
        try {
            Repository.deleteArrangement(currentArrangement, ProtoUser.ADMIN);
            arrangementListObservable.remove(currentArrangement);
            arrangementListView.refresh();
            clearFields();
            updateView();
        }
        catch (IllegalDataAccess | DataFormatException illegalDataAccess) {
            try { ErrorExceptionHandler.createLogWithDetails(ErrorExceptionHandler.ERROR_ACCESSING_DATA, illegalDataAccess); }
            catch (IOException e) {e.printStackTrace();}
            createAlert(ErrorExceptionHandler.ERROR_ACCESSING_DATA);
        }
    }

    private void editArrangement(){
        title = "Rediger";
        name = "NewAlterArrangement.fxml";
        createNewView(this, currentArrangement);
    }

    private void changedView(){
        currentArrangement = arrangementListView.getSelectionModel().getSelectedItem();
        if(currentArrangement == null)return;
        setInformationAboutArrangementInView();
        arrangementListView.refresh();
    }

    private void returnToMainWindow() {
        title = "Logg inn";
        name = "Login.fxml";
        closeWindow(delete);
        createNewView(this);
    }

    // --------------------------------------------------//
    //                6.Private Search Methods           //
    // --------------------------------------------------//

    /**
     * @param arrangement
     * @return boolean
     */
    private boolean lowerCaseTitleSearch(@NotNull Arrangement arrangement){
        String title = arrangement.getName().toLowerCase();
        String search = searchInput.getText().toLowerCase();
        return title.contains(search);
    }

    /**
     * Handles searching for Arrangements
     */
    private void search(){
        filteredArrangements.setPredicate(this::lowerCaseTitleSearch);
        arrangementListView.setItems(filteredArrangements);
        arrangementListView.refresh();
    }

    private void liveSearchUpdate(){
        searchInput.textProperty().addListener(((s) -> search()));
    }

    // --------------------------------------------------//
    //                7.Private setup Methods            //
    // --------------------------------------------------//

    private void setInformationAboutArrangementInView(){
        setTextColors(true);
        Controller.setFieldsWithDataFromObject(currentArrangement, textFields);
    }

    private void populateListView(){
        arrangementListObservable = FXCollections.observableArrayList(Repository.queryAllArrangements());
    }

    private void setupFilteredListView(){
        filteredArrangements = arrangementListObservable.filtered(arrangement -> true);

        arrangementListView.setItems(filteredArrangements);
        arrangementListView.refresh();
    }

    private void setCurrentArrangement(){
        currentArrangement = clickedItemFromListView();
    }

    private void setupActionHandlers(){
        logOut.setOnAction(this::onLogOutClick);
        edit.setOnAction(this::onEditClick);
        delete.setOnAction(this::onDeleteClick);
        arrangementListView.setOnMouseClicked(this::onListViewClick);
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
        populateListView();
        setupFilteredListView();
        setupActionHandlers();
        liveSearchUpdate();
        setTextColors(false);
        textFields = new Text[]{arrangementName, arrangementSport, arrangementAdress, arrangementDate, arrangementParticipants, arrangementGorI, arrangementDescription};
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
     * @return Object
     */
    @Override
    public Object getDataObject() {
        currentArrangement = arrangementListView.getSelectionModel().getSelectedItem();
        return currentArrangement;
    }

    @Override
    public void setDataFields(Object object) {
        setInformationAboutArrangementInView();
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
        colorizeText(tf, sportHeader, adresHeader, dateHeader, gOrIHeader, participantsHeader, descriptionHeader);
    }
}
