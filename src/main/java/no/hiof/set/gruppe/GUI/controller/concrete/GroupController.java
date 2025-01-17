package no.hiof.set.gruppe.GUI.controller.concrete;

/*Guide
 * 1. Import Statements
 * 2. FXML Fields
 * 3. Local fields
 * 4. On Action Methods
 * 5. Private Functional Methods
 * 6. Private Search Methods
 * 7. Private Setup Methods
 * 8. Overridden Methods
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import no.hiof.set.gruppe.GUI.controller.abstractions.Controller;
import no.hiof.set.gruppe.GUI.controller.abstractions.ControllerTransferData;
import no.hiof.set.gruppe.GUI.model.ViewInformation;
import no.hiof.set.gruppe.MainJavaFX;
import no.hiof.set.gruppe.core.infrastructure.exceptions.DataFormatException;
import no.hiof.set.gruppe.core.infrastructure.exceptions.ErrorExceptionHandler;
import no.hiof.set.gruppe.core.infrastructure.exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.core.entities.Group;
import no.hiof.set.gruppe.core.entities.constantInformation.DummyUsers;
import no.hiof.set.gruppe.core.entities.user.ProtoUser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This controller controls all functionality and logic pertaining
 * to the Group View
 * @author Gruppe4
 */
public class GroupController extends ControllerTransferData {

    // --------------------------------------------------//
    //                2.FXML Fields                      //
    // --------------------------------------------------//

    @FXML
    private Button newGroupBtn, deleteBtn, editBtn, applicationBtn;
    @FXML
    private MenuItem myIndividualArrangementsBtn, logoutBtn;
    @FXML
    private ListView<Group> groupsListview;
    @FXML
    private Text members, groupName, groupNameStatic, membersStatic;

    // --------------------------------------------------//
    //                3.Local Fields                     //
    // --------------------------------------------------//

    private String title = "";
    private String name = "";
    private ObservableList<Group> groupsList;
    private Group selectedGroup = null;

    // --------------------------------------------------//
    //                4.On Action Methods                //
    // --------------------------------------------------//

    /**
     * @param event {@link ActionEvent}
     */
    private void onClickNewGroupBtn(ActionEvent event) {
        newGroup();
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onClickDeletBtn(ActionEvent event) {
        deleteGroup();
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onClickEditBtn(ActionEvent event) {
        if(selectedGroup == null)return;
        alterGroup();
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onClickMyIndividualArrangementsBtn(ActionEvent event) {
        switchView("Mine arrangementer","User.fxml");
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onClickLogoutBtn(ActionEvent event) {
        switchView("Logg inn", "Login.fxml");
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onClickCreateApplication(ActionEvent event){
        newApplication();
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onClickGroupsListView(MouseEvent event) {
        changedView();
        groupsListview.refresh();
    }

    // --------------------------------------------------//
    //                5.Private Functional Methods       //
    // --------------------------------------------------//
    private void deleteGroup(){
        Group selectedItem = groupsListview.getSelectionModel().getSelectedItem();
        try {
            getRepository().deleteData(selectedItem, ProtoUser.USER);
            groupsList.remove(selectedItem);
            clearFields();
            changedView();
        } catch (DataFormatException | IllegalDataAccess illegalDataAccess) {
            try { ErrorExceptionHandler.createLogWithDetails(ErrorExceptionHandler.ERROR_ACCESSING_DATA, illegalDataAccess); }
            catch (IOException e) {e.printStackTrace();}
            createAlert(ErrorExceptionHandler.ERROR_ACCESSING_DATA);
        }
    }

    private void changedView(){
        selectedGroup = groupsListview.getSelectionModel().getSelectedItem();
        if(selectedGroup == null)return;
        setGroupInformation();
        groupsListview.refresh();
    }

    private void alterGroup(){
        title = "Rediger gruppe";
        name = "NewAlterGroup.fxml";
        createNewView(this, selectedGroup);
    }

    private void newGroup(){
        title = "Ny gruppe";
        name = "NewAlterGroup.fxml";
        createNewView(this, null);
    }

    private void newApplication(){
        name = "ClubApplication.fxml";
        title = "Klubbsøknad";
        createNewView(this);
    }

    private void switchView(String newTitle, String newName){
        title = newTitle;
        name = newName;
        closeWindow(editBtn);
        createNewView(this);
    }

    // --------------------------------------------------//
    //                6.Private Search Methods           //
    // --------------------------------------------------//

    // --------------------------------------------------//
    //                7.Private Setup Methods            //
    // --------------------------------------------------//

    private void setupActionHandlers() {
        newGroupBtn.setOnAction(this::onClickNewGroupBtn);
        deleteBtn.setOnAction(this::onClickDeletBtn);
        editBtn.setOnAction(this::onClickEditBtn);
        groupsListview.setOnMouseClicked(this::onClickGroupsListView);
        applicationBtn.setOnAction(this::onClickCreateApplication);
        myIndividualArrangementsBtn.setOnAction(this::onClickMyIndividualArrangementsBtn);
        logoutBtn.setOnAction(this::onClickLogoutBtn);
    }

    private void populateListView(){
        groupsList = FXCollections.observableArrayList(getRepository().queryAllDataOfGivenType(Group.class));
        groupsListview.setItems(groupsList);
    }

    private void setGroupInformation(){
        setTextColors(true);

        groupName.setText(selectedGroup.getName());

        StringBuilder stringMembers = new StringBuilder();
        for(DummyUsers dummyUsers : selectedGroup.getMembers()) stringMembers.append(dummyUsers).append("\n");
        members.setText(stringMembers.toString());
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
        setRepository(MainJavaFX.getRepository());
        setupActionHandlers();
        populateListView();
        setTextColors(false);
    }

    /**
     * @return Object
     */
    @Override
    public Object getDataObject() {
        selectedGroup = groupsListview.getSelectionModel().getSelectedItem();
        return selectedGroup;
    }

    /**
     * Handles data setup and interaction from other controllers.
     * @param object Object
     * @throws DataFormatException Exception
     */
    @Override
    public void setDataFields(Object object) throws DataFormatException {
        if(!(object instanceof Group)) throw new DataFormatException();
        Group group = (Group) object;
        SelectionModel model = groupsListview.getSelectionModel();

        try {
            getRepository().insertData(group, ProtoUser.USER);
            groupsList.add(group);

        } catch (IllegalAccessError | IllegalDataAccess illegalAccessError) {
            try{ErrorExceptionHandler.createLogWithDetails(ErrorExceptionHandler.ERROR_ACCESSING_DATA, illegalAccessError);}
            catch (IOException e) {e.printStackTrace();}
            Controller.createAlert(ErrorExceptionHandler.ERROR_ACCESSING_DATA);
        }
        if(model.getSelectedItem() == null) model.selectLast();
        groupsListview.refresh();
        changedView();
    }

    /**
     * Refreshes the view
     */
    @Override
    public void updateView(){
        if(selectedGroup == null)return;
        changedView();
    }

    /**
     * @return {@link ViewInformation}
     */
    @Override
    public ViewInformation getViewInformation() {
        return new ViewInformation(name, title);
    }

    /**
     * @param tf
     */
    private void setTextColors(boolean tf){
        colorizeText(tf, groupNameStatic, membersStatic);
    }
}
