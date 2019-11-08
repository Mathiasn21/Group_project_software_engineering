package no.hiof.set.gruppe.GUI.controller.concrete;

/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. FXML Fields
 * 4. On Action Methods
 * 5. Private Functional Methods
 * 6. Private Setup Methods
 * 7. Overridden Methods
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
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import no.hiof.set.gruppe.GUI.controller.abstractions.Controller;
import no.hiof.set.gruppe.data.Repository;
import no.hiof.set.gruppe.model.Group;
import no.hiof.set.gruppe.model.ValidationResult;
import no.hiof.set.gruppe.GUI.controller.model.ViewInformation;
import no.hiof.set.gruppe.model.constantInformation.DummyUsers;
import no.hiof.set.gruppe.core.validations.Validation;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This controller controls all functionality and logic pertaining
 * to the NewAlterGroup View
 * @author Gruppe4
 */
public class NewAlterGroupController extends Controller {

    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//

    private ObservableList<DummyUsers>avaliableUsersObservableList, chosenUsersObservableList;
    private DummyUsers currentUser = null;
    private Group groupToEdit;
    private String grName;
    private int id;
    private ArrayList<DummyUsers>members;
    private final String name = "NewAlterGroup.fxml";
    private final String title = "Rediger";
    private boolean groupIsEditable = false;

    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//

    @FXML
    private TextField inputName;
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
        createGroup();
        if(validateGroupData())return;
        closeWindow(cancel);
    }

    private void onClickCancel(ActionEvent event){
        closeWindow(cancel);
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

    private void addChosenMember(){
        if(currentUser == null || checkIfRightList(avaliableUsersObservableList))return;
        chosenUsersObservableList.add(currentUser);
        avaliableUsersObservableList.remove(currentUser);
        chosenMembers.setItems(chosenUsersObservableList);
        currentUser = null;
    }

    private void removeChosenMember(){
        if(currentUser == null || checkIfRightList(chosenUsersObservableList))return;
        chosenUsersObservableList.remove(currentUser);
        avaliableUsersObservableList.add(currentUser);
        currentUser = null;
    }

    private void createGroup(){
        if(groupToEdit == null){
            groupToEdit = new Group(inputName.getText(), 1); //ID skal generes automatisk senere
            groupToEdit.addMulipleMembers(chosenUsersObservableList);
            Repository.addGroup(groupToEdit);
        }
    }

    private boolean validateGroupData(){
        ValidationResult validation = Validation.ofGroup(groupToEdit);
        return !validation.IS_VALID;
    }

    private boolean checkIfRightList(ObservableList<DummyUsers> o) {
        for (DummyUsers user : o) {
            if (currentUser == user)return false;
        }
        return true;
    }

    private ArrayList<DummyUsers> getMembersFromGroup(){
        return new ArrayList<>(groupToEdit.getMembers());
    }

    // --------------------------------------------------//
    //                5.Private Setup Methods            //
    // --------------------------------------------------//

    private void setupActionHandlers(){
        addMember.setOnAction(this::onClickAddMember);
        removeMember.setOnAction(this::onClickRemoveMember);
        save.setOnAction(this::onClickSave);
        cancel.setOnAction(this::onClickCancel);
        availableMembers.setOnMouseClicked(this::onClickAvailableMembers);
        chosenMembers.setOnMouseClicked(this::onClickChosenMembers);
    }

    private void getArrangementData(){
        grName = groupToEdit.getName();
        id = groupToEdit.getId();
        members = groupToEdit.getMembers();
    }

    //Toucha my spaghet?? Trenger refaktorering,
    private void populateListviews(){
        if(!groupIsEditable){
            avaliableUsersObservableList = FXCollections.observableArrayList(Repository.getAllUsers());
        }
        if(groupIsEditable){

            chosenUsersObservableList = FXCollections.observableArrayList(getMembersFromGroup());

            for(int i = 0; i < avaliableUsersObservableList.size(); i++){
                for (DummyUsers dummyUsers : chosenUsersObservableList) {
                    if (avaliableUsersObservableList.get(i) == dummyUsers) {
                        avaliableUsersObservableList.remove(avaliableUsersObservableList.get(i));
                    }
                }
            }

            chosenMembers.setItems(chosenUsersObservableList);
        }
        availableMembers.setItems(avaliableUsersObservableList);
    }

    private void setCurrentUser(ListView<DummyUsers> list){
        currentUser = list.getSelectionModel().getSelectedItem();
    }

    private void setGroupToEdit(Object object){
        if(object instanceof Group) {
            groupToEdit = (Group)object;
        }
    }


    // --------------------------------------------------//
    //                6.Overridden Methods               //
    // --------------------------------------------------//

    @Override
    public Object getDataObject() {
        return null;
    }

    @Override
    public void setDataFields(Object object) {
        groupIsEditable = true;
        setGroupToEdit(object);
        inputName.setText(groupToEdit.getName());
        populateListviews();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateListviews();
        setupActionHandlers();
    }

    @Override
    public ViewInformation getViewInformation() {
        return new ViewInformation(name, title);
    }

    @Override
    public void setTextColors(boolean tf) {

    }
}
