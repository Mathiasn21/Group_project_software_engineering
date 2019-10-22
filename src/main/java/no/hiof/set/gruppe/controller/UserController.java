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
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

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
    private ListView<Arrangement>availableArrangementsListView;
    @FXML
    private ListView<Arrangement> myArrangementsTreeView;
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
        if(currentAvailableArrangement == null)return;
        availableObservableArrangements.remove(currentAvailableArrangement);

        currentSelectedMyArrangement = new TreeItem<>(currentAvailableArrangement);
        myArrangementsTreeView.getRoot().getChildren().get(1).getChildren().add(currentSelectedMyArrangement);

        currentAvailableArrangement = null;
        observableOngoingLayer.add(currentSelectedMyArrangement);
        myArrangementsTreeView.getSelectionModel().selectLast();
        updateView();
    }

    private void onLeaveClick(ActionEvent actionEvent){
        if(currentSelectedMyArrangement == null)return;
        availableObservableArrangements.add((Arrangement) currentSelectedMyArrangement.getValue());
        observableOngoingLayer.remove(currentSelectedMyArrangement);
        updateView();

        TreeItem item = myArrangementsTreeView.getSelectionModel().getSelectedItem();
        item.getParent().getChildren().remove(item);

        currentAvailableArrangement = (Arrangement) currentSelectedMyArrangement.getValue();
        currentSelectedMyArrangement = null;
        availableArrangementsListView.getSelectionModel().selectLast();
    }

    private void onClickListView(MouseEvent event){
        Arrangement selectedItem = selectedArrangement();
        if(selectedItem == null){return;}
        currentAvailableArrangement = selectedItem;
    }

    private void onClickTreeView(MouseEvent event){
        TreeItem<Object> selectedItem = myArrangementsTreeView.getSelectionModel().getSelectedItem();
        if(!(selectedItem.getValue() instanceof Arrangement)){return;}
        currentSelectedMyArrangement = selectedItem;
    }

    // --------------------------------------------------//
    //                5.Private Methods                  //
    // --------------------------------------------------//
    private void search(ActionEvent actionEvent){
        myFiltered.setPredicate(this::lowerCaseTitleSearch);
        myArrangementsTreeView.getRoot()(filteredList);
        listview.refresh();
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
        String search = joinedSearch.getText().toLowerCase();
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
        myArrangementsTreeView.setOnMouseClicked(this::onClickTreeView);
        joinedSearch.setOnAction(this::search);
        joinBtn.setOnAction(this::onJoinClick);
        leaveBtn.setOnAction(this::onLeaveClick);
        logOut.setOnAction(this::returnToMainWindow);
    }


    private void setArrangementListInformation() {
        List<Arrangement> allArrang = DataHandler.getArrangementsData();
        List<Arrangement> userConnectedArrangements = DataHandler.getUserArrangements(User.USER);

        allArrang.removeAll(userConnectedArrangements);allArrang.sort();
        Comparator.comparing()
        //setUpFilteredList();
        availableObservableArrangements = FXCollections.observableArrayList(allArrang);
        myObservableArrangements = FXCollections.observableArrayList(userConnectedArrangements);
    }

    private void setupListView(){
        myFiltered = availableObservableArrangements.filtered(arrangement -> true);
        availableArrangementsListView.setItems(myFiltered);
        availableArrangementsListView.refresh();
    }

    private void populateMyArrangemenTreeView() {

        myArrangementsTreeView.refresh();
    }

    // --------------------------------------------------//
    //                6.Overridden Methods               //
    // --------------------------------------------------//
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setArrangementListInformation();
        populateMyArrangemenTreeView();
        setupListView();
        setupActionHandlers();
    }

    @Override
    public void updateView(){
        availableArrangementsListView.refresh();
        myArrangementsTreeView.refresh();
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

    // --------------------------------------------------//
    //                6.Inner Class                      //
    // --------------------------------------------------//

    private static class ArrangementTopNode{
        private final String name;

        ArrangementTopNode(String name){
            this.name = name;
        }

        @Override
        public String toString(){return name;}
    }
}
