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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import no.hiof.set.gruppe.controller.abstractions.Controller;
import no.hiof.set.gruppe.model.ViewInformation;
import java.net.URL;
import java.util.ResourceBundle;

public class NewAlterGroupController extends Controller {

    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//

    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//

    @FXML
    private TextField name;
    @FXML
    private ListView availableMembers, chosenMembers;
    @FXML
    private Button addMember, removeMember, save, cancel;


    // --------------------------------------------------//
    //                4.On Action Methods                //
    // --------------------------------------------------//

    private void onClickAddMember(ActionEvent event){
        System.out.println("add");
    }

    private void onClickRemoveMember(ActionEvent event){
        System.out.println("remove");
    }

    private void onClickSave(ActionEvent event){
        System.out.println("save");
    }

    private void onClickCancel(ActionEvent event){
        System.out.println("cancel");
    }

    private void onClickAvailableMembers(Event event){
        System.out.println("avaliable");
    }

    private void onClickChosenMembers (Event event){
        System.out.println("chosen");
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
        setupActionHandlers();
    }

    @Override
    public ViewInformation getViewInformation() {
        return null;
    }
}
