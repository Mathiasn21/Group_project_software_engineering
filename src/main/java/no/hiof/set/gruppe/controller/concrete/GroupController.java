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
    private ObservableList<Group>groupsList;
    private Group currentSeletedGroup = null;
    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//

    @FXML
    private Button newGroupBtn, deleteBtn, editBtn;
    @FXML
    private MenuItem myIndividualArrangementsBtn, myGroupsBtn, logoutBtn;
    @FXML
    private ListView<Group> groupsListview;
    @FXML
    private Text members, groupName;

    // --------------------------------------------------//
    //                4.On Action Methods                //
    // --------------------------------------------------//

    private void onClicknewGroupBtn(ActionEvent event){
        System.out.println("ny");
    }

    private void onClickDeletBtn(ActionEvent event){
        System.out.println("slett");
    }

    private void onClickEditBtn(ActionEvent event){
        System.out.println("rediger");
    }

    private void onClickMyIndividualArrangementsBtn(ActionEvent event){
        System.out.println("mine individuelle arrangementer");
    }

    private void onClickMyGroupsBtn(ActionEvent event){
        System.out.println("mine grupper");
    }

    private void onClickLogoutBtn(ActionEvent event){
        closeWindow(deleteBtn);
    }

    private void onClickGroupsListView(MouseEvent event){
        System.out.println("liste");
    }

    // --------------------------------------------------//
    //                5.Private Functional Methods       //
    // --------------------------------------------------//



    // --------------------------------------------------//
    //                6.Private Search Methods           //
    // --------------------------------------------------//

    // --------------------------------------------------//
    //                7.Private Setup Methods            //
    // --------------------------------------------------//

    private void setupActionHandlers(){
        newGroupBtn.setOnAction(this::onClicknewGroupBtn);
        deleteBtn.setOnAction(this::onClickDeletBtn);
        editBtn.setOnAction(this::onClickEditBtn);
        groupsListview.setOnMouseClicked(this::onClickGroupsListView);

        //Menu items
        myIndividualArrangementsBtn.setOnAction(this::onClickMyIndividualArrangementsBtn);
        myGroupsBtn.setOnAction(this::onClickMyGroupsBtn);
        logoutBtn.setOnAction(this::onClickLogoutBtn);
    }

    // --------------------------------------------------//
    //                8.Overridden Methods               //
    // --------------------------------------------------//

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupActionHandlers();
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
        return null;
    }
}
