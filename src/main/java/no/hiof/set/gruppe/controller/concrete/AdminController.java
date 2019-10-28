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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import no.hiof.set.gruppe.Exceptions.ErrorExceptionHandler;
import no.hiof.set.gruppe.Exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.controller.abstractions.Controller;
import no.hiof.set.gruppe.data.DataHandler;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.ViewInformation;
import no.hiof.set.gruppe.model.user.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controls the main logic pertaining to the Admin View
 */
public class AdminController extends Controller {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private String title = "Login";
    private String name = "NewAlterArrangement.fxml";
    private FilteredList<Arrangement> filteredList;
    private Arrangement currentArrangement = null;
    private ObservableList<Arrangement> arrangementListObservable;

    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//

     @FXML
     private Button logOut, edit, delete;
     @FXML
     private ListView<Arrangement> arrangementListView;
     @FXML
     private TextField search;
     @FXML
     private Text arrangementSport, arrangementName, arrangementAdress, arrangementDate, arrangementGorI, arrangementParticipants, arrangementDescription;


    // --------------------------------------------------//
    //                4.On action Methods                //
    // --------------------------------------------------//
    private void onDeleteClick(ActionEvent event){
        if(checkIfLegalArrangement()){
            deleteArrangement();
            updateView();
        }
    }

    private void onEditClick(ActionEvent event){
        if(checkIfLegalArrangement()){
            editArrangement();
        }
    }

    private void onListViewClick(MouseEvent mouseEvent){
        if(checkIfLegalArrangement()){
            setCurrentArrangement();
            setInformationAboutArrangementInView();
        }
    }

    // --------------------------------------------------//
    //                5.Private Methods                  //
    // --------------------------------------------------//

    private void setupActionHandlers(){
        logOut.setOnAction(this::returnToMainWindow);
        edit.setOnAction(this::onEditClick);
        delete.setOnAction(this::onDeleteClick);
        arrangementListView.setOnMouseClicked(this::onListViewClick);
    }

    private void returnToMainWindow(ActionEvent event) {
        title = "Logg inn";
        name = "Login.fxml";
        ((Stage)logOut.getScene().getWindow()).close();
        createNewView(this);
    }

    private void populateListView(){
        arrangementListObservable = FXCollections.observableArrayList(DataHandler.getArrangementsData());
        arrangementListView.setItems(arrangementListObservable);
    }

    private Arrangement clickedItemFromListView(){
        return arrangementListView.getSelectionModel().getSelectedItem();
    }

    private boolean checkIfLegalArrangement(){
        return clickedItemFromListView() != null && clickedItemFromListView().getStartDate() != null;
    }

    private void setCurrentArrangement(){
        currentArrangement = clickedItemFromListView();
    }

    private void deleteArrangement(){
        try {
            DataHandler.deleteArrangement(currentArrangement, User.ADMIN);
            arrangementListObservable.remove(currentArrangement);
            arrangementListView.refresh();
        }
        catch (IllegalDataAccess illegalDataAccess) {
            try { ErrorExceptionHandler.createLogWithDetails(ErrorExceptionHandler.ERROR_ACCESSING_DATA, illegalDataAccess); }
            catch (IOException e) {e.printStackTrace();}
        }
    }

    private void editArrangement(){
        title = "Rediger";
        createNewView(this, currentArrangement);
    }

    private void setInformationAboutArrangementInView(){
        ArrayList<Text> viewFields = viewFields(arrangementName, arrangementSport, arrangementAdress, arrangementDate, arrangementParticipants, arrangementGorI, arrangementDescription);
        ArrayList<String> data = arrangementData(currentArrangement);
        for(int i = 0; i < data.size(); i++)
            viewFields.get(i).setText(data.get(i));
    }

    // --------------------------------------------------//
    //                6.Overridden Methods               //
    // --------------------------------------------------//
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupActionHandlers();
        populateListView();
    }

    @Override
    public void updateView(){
        currentArrangement = arrangementListView.getSelectionModel().getSelectedItem();
        setInformationAboutArrangementInView();
        arrangementListView.refresh();
    }

    @Override
    public Object getDataObject() {
        return null;
    }

    @Override
    public void setDataFields(Object object) throws DataFormatException {

    }

    //new method for returning information about the view
    @Override
    public ViewInformation getViewInformation() {
        return new ViewInformation(name, title);
    }
}
