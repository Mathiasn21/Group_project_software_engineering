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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import no.hiof.set.gruppe.controller.abstractions.Controller;
import no.hiof.set.gruppe.data.Repository;
import no.hiof.set.gruppe.model.ViewInformation;
import no.hiof.set.gruppe.model.constantInformation.DummyUsers;
import java.net.URL;
import java.util.ResourceBundle;

public class NewAlterGroupController extends Controller {

    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//

    private ObservableList<DummyUsers>avaliableUsersObservableList, chosenUsersObservableList;
    private DummyUsers currentUser = null;

    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//

    @FXML
    private TextField name;
    @FXML
    private ListView<DummyUsers> availableMembers, chosenMembers;
    @FXML
    private Button addMember, removeMember, save, cancel;


    // --------------------------------------------------//
    //                4.On Action Methods                //
    // --------------------------------------------------//

    private void onClickAddMember(ActionEvent event){
        addChosenMember();
    }

    private void onClickRemoveMember(ActionEvent event){
        removeChosenMember();
    }

    private void onClickSave(ActionEvent event){
        System.out.println("save");
    }

    private void onClickCancel(ActionEvent event){
        close();
    }

    private void onClickAvailableMembers(Event event){
        setCurrentUser(availableMembers);
    }

    private void onClickChosenMembers (Event event){
        setCurrentUser(chosenMembers);
    }

    // --------------------------------------------------//
    //                5.Private Methods                  //
    // --------------------------------------------------//

    private void setupActionHandlers(){
        addMember.setOnAction(this::onClickAddMember);
        removeMember.setOnAction(this::onClickRemoveMember);
        save.setOnAction(this::onClickSave);
        cancel.setOnAction(this::onClickCancel);
        availableMembers.setOnMouseClicked(this::onClickAvailableMembers);
        chosenMembers.setOnMouseClicked(this::onClickChosenMembers);
    }


    private void populateAvaliabeMembers(){
        avaliableUsersObservableList = FXCollections.observableArrayList(Repository.getAllUsers());
        availableMembers.setItems(avaliableUsersObservableList);
    }

    private void addChosenMember(){
        if(currentUser == null || check(avaliableUsersObservableList))return;
        chosenUsersObservableList.add(currentUser);
        avaliableUsersObservableList.remove(currentUser);
        chosenMembers.setItems(chosenUsersObservableList);
        currentUser = null;
    }

    private void removeChosenMember(){
        if(currentUser == null || check(chosenUsersObservableList))return;
        chosenUsersObservableList.remove(currentUser);
        avaliableUsersObservableList.add(currentUser);
        currentUser = null;
    }

    private void setCurrentUser(ListView<DummyUsers> list){
        currentUser = list.getSelectionModel().getSelectedItem();
    }

    private boolean check(ObservableList<DummyUsers> o) {
        for (int i = 0; i < o.size(); i++) {
            if (currentUser == o.get(i))return false;
        }
        return true;
    }

    private void close(){
        ((Stage)cancel.getScene().getWindow()).close();
    }

    // --------------------------------------------------//
    //                6.Overridden Methods               //
    // --------------------------------------------------//

    @Override
    public Object getDataObject() {
        return null;
    }

    @Override
    public void setDataFields(Object controller) throws DataFormatException {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateAvaliabeMembers();
        setupActionHandlers();
        chosenUsersObservableList = FXCollections.observableArrayList();
    }

    @Override
    public ViewInformation getViewInformation() {
        return null;
    }
}
