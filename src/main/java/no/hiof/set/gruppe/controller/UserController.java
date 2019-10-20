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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import no.hiof.set.gruppe.data.DataHandler;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.User;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class UserController extends Controller{
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//

    private String title, name = "";
    private ObservableList<Arrangement> myObservableArrangements, availableObservableArrangements;
    private ObservableList<TreeItem<Object>> observableExpiredLayer = FXCollections.observableArrayList(), observableOngoingLayer = FXCollections.observableArrayList();
    private FilteredList<Arrangement> availableFiltered, myFiltered;
    private Arrangement currentAvailableArrangement = null;
    private Arrangement currentSelectedMyArrangement = null;

    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//

    @FXML
    private ListView<Arrangement>availableArrangementsListView;
    @FXML
    private TreeView<Object> myArrangementsTreeView;
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
        myObservableArrangements.add(selectedArrangement());
        availableObservableArrangements.remove(selectedArrangement());
        //add userConnectection
    }

    private void onLeaveClick(ActionEvent actionEvent){
        availableObservableArrangements.add(selectedArrangement());
        myObservableArrangements.remove(myArrangementsTreeView.getSelectionModel().getSelectedItem().getValue());
    }

    private void onClickListView(ActionEvent event){
        Arrangement selectedItem = selectedArrangement();
        if(selectedItem == null){return;}
        currentAvailableArrangement = selectedItem;
    }

    private void onClickTreeView(ActionEvent event){
        Object selectedItem = myArrangementsTreeView.getSelectionModel().getSelectedItem().getValue();
        if(!(selectedItem instanceof Arrangement)){return;}
        currentSelectedMyArrangement = (Arrangement)selectedItem;
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

    private Arrangement selectedArrangement(){
        return availableArrangementsListView.getSelectionModel().getSelectedItem();
    }

    private void setupActionHandlers(){
        joinBtn.setOnAction(this::onJoinClick);
        leaveBtn.setOnAction(this::onLeaveClick);
        logOut.setOnAction(this::returnToMainWindow);
    }

    private void setArrangementListInformation() {
        List<Arrangement> allArrang = DataHandler.getArrangementsData();
        List<Arrangement> userConnectedArrangements = DataHandler.getUserArrangements(User.USER);

        allArrang.removeAll(userConnectedArrangements);
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
        TreeItem<Object> root = new TreeItem<>();
        TreeItem<Object> myExpiredTopNode = new TreeItem<>(new ArrangementTopNode("Expirert"));
        TreeItem<Object> myOngoingTopNode = new TreeItem<>(new ArrangementTopNode("Pågående"));

        myArrangementsTreeView.setShowRoot(false);
        myArrangementsTreeView.setRoot(root);
        root.setExpanded(true);

        //generate TreeItems containing Arrangements
        for(Arrangement arrangement : myObservableArrangements){
            TreeItem<Object> arrangementTreeItem = new TreeItem<>(arrangement);

            if(arrangement.getEndDate().isBefore(LocalDate.now())) observableExpiredLayer.add(arrangementTreeItem);
            else observableOngoingLayer.add(arrangementTreeItem);
        }

        FilteredList<TreeItem<Object>> filteredExpiredLayer = new FilteredList<>(observableExpiredLayer, item -> true);
        FilteredList<TreeItem<Object>> filteredOngoingLayer = new FilteredList<>(observableOngoingLayer, item -> true);

        myExpiredTopNode.getChildren().addAll(filteredExpiredLayer);
        myOngoingTopNode.getChildren().addAll(filteredOngoingLayer);

        root.getChildren().add(myExpiredTopNode);
        root.getChildren().add(myOngoingTopNode);

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
