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
import no.hiof.set.gruppe.GUI.controller.abstractions.ControllerTransferData;
import no.hiof.set.gruppe.core.Repository;
import no.hiof.set.gruppe.core.exceptions.DataFormatException;
import no.hiof.set.gruppe.core.exceptions.ErrorExceptionHandler;
import no.hiof.set.gruppe.model.Group;
import no.hiof.set.gruppe.model.ValidationResult;
import no.hiof.set.gruppe.GUI.model.ViewInformation;
import no.hiof.set.gruppe.model.constantInformation.DummyUsers;
import no.hiof.set.gruppe.core.validations.Validation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This controller controls all functionality and logic pertaining
 * to the NewAlterGroup View
 * @author Gruppe4
 */
public class NewAlterGroupController extends ControllerTransferData {

    //Hele klassen skal bli refaktorert

    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private final String name = "NewAlterGroup.fxml";
    private final String title = "Rediger";
    private ObservableList<DummyUsers>avaliableUsersObservableList, chosenUsersObservableList;
    private DummyUsers currentUser = null;
    private Group groupToEdit;
    private String grName;
    private int id;
    private ArrayList<DummyUsers>members;
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
        if(groupIsEditable){
            alterGroup();
            if(validateGroupData())return;
            closeWindow(cancel);
        }
        else{
            createNewGroup();
            if(validateGroupData())return;
            closeWindow(cancel);
        }
    }

    private void onClickCancel(ActionEvent event){
        closeWindow(cancel);
    }

    private void onClickAvailableMembers(Event event){
        setCurrentUser(availableMembers);
        System.out.println(availableMembers);
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

    private void createNewGroup(){
        if(groupToEdit == null){
            groupToEdit = new Group(inputName.getText()); //ID skal generes automatisk senere
            groupToEdit.addMultipleMembers(chosenUsersObservableList);
            queryGroup();
        }
    }

    private void alterGroup(){
        groupToEdit.setMembers(new ArrayList<>());
        groupToEdit.setName(inputName.getText());
        groupToEdit.addMultipleMembers(chosenUsersObservableList);
        queryGroup();
    }

    private void queryGroup() {
        try {
            Repository.insertGroup(groupToEdit);
        } catch (DataFormatException illegalDataAccess) {
            try { ErrorExceptionHandler.createLogWithDetails(ErrorExceptionHandler.ERROR_ACCESSING_DATA, illegalDataAccess);
            } catch (IOException e) { e.printStackTrace(); }
            createAlert(ErrorExceptionHandler.ERROR_ACCESSING_DATA);
        }
    }

    private boolean validateGroupData(){
        ValidationResult validation = Validation.ofGroup(groupToEdit);
        return !validation.IS_VALID;
    }

    private boolean checkIfRightList(ObservableList<DummyUsers> o) {
        for (DummyUsers user : o) if (currentUser == user) return false;
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

    //Toucha my spaghet?? Trenger refaktorering,
    private void populateListViews(){
        if(!groupIsEditable){
            avaliableUsersObservableList = FXCollections.observableArrayList(Repository.queryAllUsers());
            chosenUsersObservableList = FXCollections.observableArrayList();
        }
        if(groupIsEditable){

            chosenUsersObservableList = FXCollections.observableArrayList(getMembersFromGroup());

            avaliableUsersObservableList.removeAll(chosenUsersObservableList);

            for(int i = 0; i < avaliableUsersObservableList.size(); i++){
                for (DummyUsers dummyUser : chosenUsersObservableList) {
                    if (avaliableUsersObservableList.get(i) == dummyUser) {
                        avaliableUsersObservableList.remove(avaliableUsersObservableList.get(i));
                    }
                }
            }
        }
        availableMembers.setItems(avaliableUsersObservableList);
        chosenMembers.setItems(chosenUsersObservableList);
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
        if(object instanceof Group){
            groupIsEditable = true;
            setGroupToEdit(object);
            inputName.setText(groupToEdit.getName());
            populateListViews();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateListViews();
        setupActionHandlers();
    }

    @Override
    public ViewInformation getViewInformation() {
        return new ViewInformation(name, title);
    }

}
