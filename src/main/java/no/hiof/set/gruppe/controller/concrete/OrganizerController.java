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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import no.hiof.set.gruppe.Exceptions.ErrorExceptionHandler;
import no.hiof.set.gruppe.Exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.controller.abstractions.Controller;
import no.hiof.set.gruppe.data.Repository;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.ViewInformation;
import no.hiof.set.gruppe.model.constantInformation.SportCategory;
import no.hiof.set.gruppe.model.user.ProtoUser;
import no.hiof.set.gruppe.util.ArrangementSort;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This controller controls all functionality and logic pertaining
 * to the Organizer View
 * @author Gruppe4
 */
public class OrganizerController extends Controller {

    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private String title = "";
    private String name = "NewAlterArrangement.fxml";
    private ObservableList<Arrangement>arrangementListObservable;
    private FilteredList<Arrangement> filteredList;
    private Arrangement currentArrangement = null;
    private static final ProtoUser PROTO_USER = ProtoUser.ORGANIZER;

    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//
    @FXML
    private Button newArrangementBtn, editBtn, deleteBtn;
    @FXML
    private Text arrangementName, arrangementAdress, arrangementDate, arrangementParticipants, arrangementGorI, arrangementSport, arrangementDescription;
    @FXML
    private TextField arrSearch;
    @FXML
    private ChoiceBox<SportCategory> sortOptions;
    @FXML
    private ListView<Arrangement>listview;
    @FXML
    private MenuItem logOut;

    // --------------------------------------------------//
    //                4.Event Related Methods            //
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
        listview.refresh();
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onClick(ActionEvent event) {
        title = "Ny";
        createNewView(this, null);
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onEditClick(ActionEvent event){
        if(listview.getSelectionModel().getSelectedItem() != null){
            title = "Rediger";
            createNewView(this, currentArrangement);
        }
        else
            System.out.println("Du har ikke valgt et arrangement");
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onDelete(ActionEvent event){
        if(listview.getSelectionModel().getSelectedItem() == null) return;
        deleteArrangement();
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void returnToMainWindow(ActionEvent event) {
        title = "Logg inn";
        name = "Login.fxml";
        ((Stage)deleteBtn.getScene().getWindow()).close();
        System.out.println(getMainController());
        createNewView(this);
    }

    private void search(){
        sortAndSearch();
    }

    // --------------------------------------------------//
    //                5.Private Functional Methods       //
    // --------------------------------------------------//
    private void changedView(){
        currentArrangement = listview.getSelectionModel().getSelectedItem();
        setInformationAboutArrangementInView();
        checkArrangementDate();
        listview.refresh();
    }

    private void setInformationAboutArrangementInView(){
        if(currentArrangement == null)return;
        ArrayList<Text> viewFields = viewFields(arrangementName, arrangementSport, arrangementAdress, arrangementDate, arrangementParticipants, arrangementGorI, arrangementDescription);
        ArrayList<String> data = arrangementData(currentArrangement);
        for(int i = 0; i < data.size(); i++)
            viewFields.get(i).setText(data.get(i));
    }

    private void deleteArrangement(){
        Arrangement selectedItem = listview.getSelectionModel().getSelectedItem();
        try {
            Repository.deleteArrangement(selectedItem, PROTO_USER);
            arrangementListObservable.remove(selectedItem);
            listview.getSelectionModel().selectFirst();
        }
        catch (IllegalDataAccess illegalDataAccess) {
            try {ErrorExceptionHandler.createLogWithDetails(ErrorExceptionHandler.ERROR_ACCESSING_DATA, illegalDataAccess); }
            catch (IOException e) {e.printStackTrace();}
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
        listview.setItems(filteredList);
        listview.refresh();
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
        listview.setOnMouseClicked(this::onClickListView);
        sortOptions.setOnAction(this::sort);
        logOut.setOnAction(this::returnToMainWindow);
    }

    private void setupFilteredList(){
        filteredList = arrangementListObservable.filtered(arrangement -> true);
        listview.setItems(filteredList);
        arrSearch.setText("");
    }

    private void populateListView() {
        arrangementListObservable = FXCollections.observableArrayList(Repository.getUserArrangements(ProtoUser.ORGANIZER));
        arrangementListObservable.sort(ArrangementSort.COMP_DATE_ASC.getComparator());
        setupFilteredList();
        listview.refresh();
    }

    private void populateSportCategories() {
        sortOptions.setItems(FXCollections.observableArrayList(SportCategory.values()));
        sortOptions.getSelectionModel().select(SportCategory.ALL);
    }

    private void checkArrangementDate(){
        if(currentArrangement.getEndDate().isBefore(LocalDate.now())){
            editBtn.setDisable(true);
            deleteBtn.setDisable(true);
        }
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

        MultipleSelectionModel selModel = listview.getSelectionModel();
        try {
            Repository.addArrangement(arrangement, ProtoUser.ORGANIZER);
            arrangementListObservable.add(arrangement);

        } catch (IllegalDataAccess illegalDataAccess) {
            try {ErrorExceptionHandler.createLogWithDetails(ErrorExceptionHandler.ERROR_ACCESSING_DATA, illegalDataAccess);}
            catch (IOException e) {e.printStackTrace();}
        }
        if(selModel.getSelectedItem() == null) selModel.selectLast();
        listview.refresh();
        changedView();
    }

    /**
     * @return Object
     */
    @Override
    public Object getDataObject() {
        currentArrangement = listview.getSelectionModel().getSelectedItem();
        return currentArrangement;
    }

    /**
     * @return {@link ViewInformation}
     */
    @Override
    public ViewInformation getViewInformation() {
        return new ViewInformation(name, title);
    }
}