package no.hiof.set.gruppe.controller.concrete;

/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. FXML Fields
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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import no.hiof.set.gruppe.controller.abstractions.Controller;
import no.hiof.set.gruppe.data.Repository;
import no.hiof.set.gruppe.model.Group;
import no.hiof.set.gruppe.model.ViewInformation;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This controller controls all functionality and logic pertaining
 * to the Group View
 * @author Gruppe4
 */
public class GroupController extends Controller {

    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private String title = "";
    private String name = "";
    private ObservableList<Group> groupsList;
    private Group selectedGroup = null;
    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//

    @FXML
    private Button newGroupBtn, deleteBtn, editBtn;
    @FXML
    private MenuItem myIndividualArrangementsBtn, logoutBtn;
    @FXML
    private ListView<Group> groupsListview;
    @FXML
    private Text members, groupName;

    // --------------------------------------------------//
    //                4.On Action Methods                //
    // --------------------------------------------------//

    //Håndtering av views må endres
    private void onClicknewGroupBtn(ActionEvent event) {
        title = "Ny gruppe";
        name = "NewAlterGroup.fxml";
        createNewView(this);
    }

    private void onClickDeletBtn(ActionEvent event) {
        System.out.println("slett");
    }

    private void onClickEditBtn(ActionEvent event) {
        System.out.println("rediger");
    }

    private void onClickMyIndividualArrangementsBtn(ActionEvent event) {
        title = "Mine arrangementer";
        name = "User.fxml";
        closeWindow(editBtn);
        createNewView(this);
    }

    private void onClickLogoutBtn(ActionEvent event) {
        title = "Logg inn";
        name = "Login.fxml";
        closeWindow(editBtn);
        createNewView(this);
    }

    private void onClickGroupsListView(MouseEvent event) {
        setSelectedGroup();
        System.out.println(selectedGroup);
    }

    // --------------------------------------------------//
    //                5.Private Functional Methods       //
    // --------------------------------------------------//

    private void setSelectedGroup(){
        selectedGroup = groupsListview.getSelectionModel().getSelectedItem();
    }

    // --------------------------------------------------//
    //                6.Private Search Methods           //
    // --------------------------------------------------//

    // --------------------------------------------------//
    //                7.Private Setup Methods            //
    // --------------------------------------------------//

    private void setupActionHandlers() {
        newGroupBtn.setOnAction(this::onClicknewGroupBtn);
        deleteBtn.setOnAction(this::onClickDeletBtn);
        editBtn.setOnAction(this::onClickEditBtn);
        groupsListview.setOnMouseClicked(this::onClickGroupsListView);

        //Menu items
        myIndividualArrangementsBtn.setOnAction(this::onClickMyIndividualArrangementsBtn);
        logoutBtn.setOnAction(this::onClickLogoutBtn);
    }

    private void populateListView(){
        groupsList = FXCollections.observableArrayList(Repository.getAllGroups());
        groupsListview.setItems(groupsList);
    }

    // --------------------------------------------------//
    //                8.Overridden Methods               //
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
    public void setDataFields(Object controller) throws DataFormatException {

    }

    @Override
    public ViewInformation getViewInformation() {
        return new ViewInformation(name, title);
    }
}
