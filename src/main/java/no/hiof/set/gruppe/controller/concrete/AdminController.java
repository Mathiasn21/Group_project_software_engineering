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
import no.hiof.set.gruppe.controller.abstractions.Controller;
import no.hiof.set.gruppe.data.DataHandler;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.user.User;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the main logic pertaining to the Admin View
 */
public class AdminController extends Controller {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private String name = "Login";
    private String title = "Login";
    private FilteredList<Arrangement> filteredList;
    private Arrangement currentArrangement = null;
    private ObservableList<Arrangement> arrangementListObservable;

    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//

     @FXML
     private Button logOut, delete;
     @FXML
     private ListView<Arrangement> arrangementListView;
     @FXML
     private TextField search;
     @FXML
     private Text arrangementSportView, arrangementNameView, arrangementAdressView, arrangementDateView, arrangementGorIView, arrangementParticipantsView, arrangementDescriptionView;

    // --------------------------------------------------//
    //                4.On action Methods                //
    // --------------------------------------------------//

    private void onDeleteClick(ActionEvent event){

    }

    private void onListViewClick(MouseEvent mouseEvent){
        setCurrentArrangement();
        setInformationAboutArrangementInView();
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

    private void setupActionHandlers(){
        logOut.setOnAction(this::returnToMainWindow);
        delete.setOnAction(this::onDeleteClick);
        arrangementListView.setOnMouseClicked(this::onListViewClick);
    }


    private void populateListView(){
        arrangementListObservable = FXCollections.observableArrayList(DataHandler.getArrangementsData());
        arrangementListView.setItems(arrangementListObservable);
    }

    private void setCurrentArrangement(){
        Arrangement incomingArrangement = arrangementListView.getSelectionModel().getSelectedItem();
        if(incomingArrangement == null || incomingArrangement.getStartDate() == null)return;
        currentArrangement = incomingArrangement;
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

    @NotNull
    private String groupsOrIndividuals(@NotNull Arrangement arrangement){
        return arrangement.isGroup() ? "Lagkonkurranse" : "Individuell konkurranse";
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
    public Object getDataObject() {
        return null;
    }

    @Override
    public void setDataFields(Object object) throws DataFormatException {

    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getName() {
        return name;
    }
}
