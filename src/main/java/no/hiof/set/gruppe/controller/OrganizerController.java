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
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.data.DataHandler;
import no.hiof.set.gruppe.model.SportCategory;
import no.hiof.set.gruppe.model.User;

import java.net.URL;
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


    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//

    @FXML
    private Button newArrangementBtn, editBtn, deleteBtn, logOut;
    @FXML
    private Text arrangementNameView, arrangementAdressView, arrangementDateView, arrangementParticipantsView, arrangementGorIView, arrangementSportView, arrangementDescriptionView;
    @FXML
    private TextField arrSearch;
    @FXML
    private ChoiceBox<SportCategory> sortOptions;
    @FXML
    private ListView<Arrangement>listview;

    // --------------------------------------------------//
    //                4.On Action Methods                //
    // --------------------------------------------------//
    private void search(ActionEvent actionEvent){
        filteredList.setPredicate(this::lowerCaseTitleSearch);
        listview.setItems(filteredList);
        listview.refresh();
    }

    private void onClick(ActionEvent event) {
        title = "Ny";
        createNewView(this, null);
    }

    private void onEditClick(ActionEvent event){
        if(listview.getSelectionModel().getSelectedItem() != null){
            title = "Rediger";
            createNewView(this, currentArrangement);
        }
        else
            System.out.println("Du har ikke valgt et arrangement");
    }

    private void onDelete(ActionEvent event){
        if(listview.getSelectionModel().getSelectedItem() == null) return;
        deleteArrangement();
    }

    // --------------------------------------------------//
    //                5.Private Methods                  //
    // --------------------------------------------------//
    private void changedView(){
        Arrangement arrangement = listview.getSelectionModel().getSelectedItem();
        if(currentArrangement == null){
            currentArrangement = arrangement;
        }
        setInformationAboutArrangementInView();
        listview.refresh();
    }

    private void onClickListView(MouseEvent mouseEvent) {
        changedView();
        listview.refresh();
    }

    private void setupActionHandlers() {
        deleteBtn.setOnAction(this::onDelete);
        editBtn.setOnAction(this::onEditClick);
        newArrangementBtn.setOnAction(this::onClick);
        listview.setOnMouseClicked(this::onClickListView);
        arrSearch.setOnAction(this::search);
        sortOptions.setOnAction(this::search);
        logOut.setOnAction(this::returnToMainWindow);
    }

    private void setupFilteredList(){
        filteredList = arrangementListObservable.filtered(arrangement -> true);
        listview.setItems(filteredList);
        arrSearch.setText("");
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
        String search = arrSearch.getText().toLowerCase();
        return title.contains(search) && categoryMatch(arrangement);
    }

    private boolean categoryMatch(Arrangement arrangement) {
        SportCategory category = sortOptions.getSelectionModel().getSelectedItem();
        return category.equals(SportCategory.ALL) || arrangement.getSport().equals(category.toString());
    }

    private void populateListView() {
        arrangementListObservable = FXCollections.observableArrayList(DataHandler.getUserArrangements(User.ORGANIZER));
        setupFilteredList();
        listview.refresh();
    }

    private void populateSportCategories() {
        sortOptions.setItems(FXCollections.observableArrayList(SportCategory.values()));
        sortOptions.getSelectionModel().select(SportCategory.ALL);
    }

    private void deleteArrangement(){
        Arrangement selectedItem = listview.getSelectionModel().getSelectedItem();
        arrangementListObservable.remove(selectedItem);
        DataHandler.deleteArrangement(selectedItem);
        listview.getSelectionModel().selectFirst();
    }


    private void setInformationAboutArrangementInView(){
        if(currentArrangement == null)return;
        arrangementNameView.setText(currentArrangement.getName());
        arrangementSportView.setText(currentArrangement.getSport());
        arrangementAdressView.setText(currentArrangement.getAddress());
        arrangementDateView.setText(currentArrangement.getStartDate().toString() + " til " + currentArrangement.getEndDate().toString());
        arrangementParticipantsView.setText(Integer.toString(currentArrangement.getParticipants()));
        arrangementGorIView.setText(groupsOrIndividuals(currentArrangement));
        arrangementDescriptionView.setText(currentArrangement.getDescription());
    }

    private String groupsOrIndividuals(Arrangement arrangement){
        return arrangement.isGroup() ? "Lagkonkurranse" : "Individuell konkurranse";
    }

    private void returnToMainWindow(ActionEvent event) {
        title = "Logg inn";
        name = "Login.fxml";
        ((Stage)logOut.getScene().getWindow()).close();
        System.out.println(getMainController());
        createNewView(this);
    }

    // --------------------------------------------------//
    //                6.Overridden Methods               //
    // --------------------------------------------------//

    //handles the title and name of current view. Here name is the local path
    @Override
    public String getTitle() {
        return title;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public Object getDataObject() {
        currentArrangement = listview.getSelectionModel().getSelectedItem();
        return currentArrangement;
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
        arrangementListObservable.add(arrangement);
        DataHandler.addArrangement(arrangement, User.ORGANIZER);
        if(selModel.getSelectedItem() == null) selModel.selectLast();
        listview.refresh();
        changedView();
    }

    @Override
    public void onCloseStoreInformation() {
        new DataHandler().storeArrangementsData();
    }

    @Override
    public void updateView(){
        if(currentArrangement == null)return;
        changedView();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupActionHandlers();
        populateListView();
        populateSportCategories();
    }
}
