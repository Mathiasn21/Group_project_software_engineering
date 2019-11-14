package no.hiof.set.gruppe.GUI.controller.concrete;

/*Guide
 * 1. Import Statements
 * 2. FXML Fields
 * 3. Local fields
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

    // --------------------------------------------------//
    //                2.FXML Fields                      //
    // --------------------------------------------------//

    @FXML
    private TextField inputName;
    @FXML
    private ListView<DummyUsers> availableMembers, chosenMembers;
    @FXML
    private Button addMember, removeMember, save, cancel;

    // --------------------------------------------------//
    //                3.Local Fields                     //
    // --------------------------------------------------//

    private final String name = "NewAlterGroup.fxml";
    private final String title = "Rediger";
    private ObservableList<DummyUsers>avaliableUsersObservableList, chosenUsersObservableList;
    private DummyUsers currentUser = null;
    private Group groupToEdit;
    private String grName;
    private ArrayList<DummyUsers>members;
    private boolean createdNewGroup = false;

    // --------------------------------------------------//
    //                4.On Action Methods                //
    // --------------------------------------------------//

    /**
     * @param event {@link ActionEvent}
     */
    private void onClickAddMember(ActionEvent event){
        addChosenMember();
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onClickRemoveMember(ActionEvent event){
        removeChosenMember();
    }

    /**
     * Only saves the data if input is Valid.
     * Uses {@link Validation} in order to validate the information.
     * @param event {@link ActionEvent}
     */
    private void onClickSave(ActionEvent event){
        getGroupData();
        setGropData();
        if(validateGroupData())return;
        closeWindow(cancel);
        queryGroup();
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onClickCancel(ActionEvent event){
        groupToEdit = null;
        createdNewGroup = false;
        closeWindow(cancel);
    }

    /**
     * @param event {@link Event}
     */
    private void onClickAvailableMembers(Event event){
        setCurrentUser(availableMembers);
        checkNumberOfClicks(availableMembers);
    }

    /**
     * @param event {@link Event}
     */
    private void onClickChosenMembers (Event event){
        setCurrentUser(chosenMembers);
        checkNumberOfClicks(chosenMembers);
    }

    // --------------------------------------------------//
    //            5.Private Functional Methods           //
    // --------------------------------------------------//

    private void getGroupData(){
        grName = inputName.getText();
        members = new ArrayList<>(chosenUsersObservableList);
    }

    private void setGropData(){
        if(groupToEdit == null){
            groupToEdit = new Group();
            createdNewGroup = true;
        }
        groupToEdit.setName(grName);
        groupToEdit.setMembers(members);
    }

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

    private boolean validateGroupData(){
        ValidationResult validation = Validation.ofGroup(groupToEdit);
        return !validation.IS_VALID;
    }

    /**
     * Checks which List is clicked.
     * @param o
     * @return boolean
     */
    private boolean checkIfRightList(ObservableList<DummyUsers> o) {
        for (DummyUsers user : o) if (currentUser == user) return false;
        return true;
    }

    /**
     * @param listView
     */
    private void checkNumberOfClicks(ListView listView){
        listView.setOnMouseClicked(click -> {
            if(click.getClickCount() == 1){
                setCurrentUser(listView);
            }
            if(click.getClickCount() == 2) {
                setCurrentUser(listView);
                addChosenMember();
                removeChosenMember();
            }
        });
    }

    /**
     * Quarries edited group.
     */
    private void queryGroup() {
        ErrorExceptionHandler err;
        try {if(!createdNewGroup)Repository.mutateObject(groupToEdit);
        } catch (DataFormatException e) {
            err = ErrorExceptionHandler.ERROR_WRONG_DATA_OBJECT;
            try { ErrorExceptionHandler.createLogWithDetails(err, e);
            } catch (IOException IOException) { err = ErrorExceptionHandler.ERROR_LOGGING_ERROR;}
            createAlert(err);
        }
    }

    // --------------------------------------------------//
    //                6.Private Setup Methods            //
    // --------------------------------------------------//

    private void setupActionHandlers(){
        addMember.setOnAction(this::onClickAddMember);
        removeMember.setOnAction(this::onClickRemoveMember);
        save.setOnAction(this::onClickSave);
        cancel.setOnAction(this::onClickCancel);
        availableMembers.setOnMouseClicked(this::onClickAvailableMembers);
        chosenMembers.setOnMouseClicked(this::onClickChosenMembers);
    }

    private void populateListView(Group group){
        chosenUsersObservableList = FXCollections.observableArrayList(group.getMembers());
        avaliableUsersObservableList = FXCollections.observableArrayList(Repository.queryAllUsers());
        filterMemberLists();
        fillListViews();
    }

    /**
     * Removes already chosen members from available members.
     */
    private void filterMemberLists(){
        avaliableUsersObservableList.removeAll(chosenUsersObservableList);
    }

    private void setAvaliableMembers(){
        if(groupToEdit != null)return;
        avaliableUsersObservableList = FXCollections.observableArrayList(Repository.queryAllUsers());
        chosenUsersObservableList = FXCollections.observableArrayList();
        fillListViews();
    }

    private void fillListViews(){
        availableMembers.setItems(avaliableUsersObservableList);
        chosenMembers.setItems(chosenUsersObservableList);
    }

    private void setCurrentUser(ListView<DummyUsers> list){
        currentUser = list.getSelectionModel().getSelectedItem();
    }

    // --------------------------------------------------//
    //                7.Overridden Methods               //
    // --------------------------------------------------//

    /**
     * @param location {@link URL}
     * @param resources {@link ResourceBundle}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupActionHandlers();
        setAvaliableMembers();
    }

    /**
     * @return Object
     */
    @Override
    public Object getDataObject() {
        return groupToEdit;
    }

    /**
     * @return boolean
     */
    @Override
    public boolean hasNewObject(){
        return createdNewGroup;
    }

    /**
     * Setups the view and its data fields.
     * @param object Object
     */
    @Override
    public void setDataFields(Object object) {
        if(object instanceof Group){
            Group group = (Group)object;
            groupToEdit = group;
            inputName.setText(group.getName());
            populateListView(group);
        }
    }

    /**
     * @return {@link ViewInformation}
     */
    @Override
    public ViewInformation getViewInformation() {
        return new ViewInformation(name, title);
    }

}
