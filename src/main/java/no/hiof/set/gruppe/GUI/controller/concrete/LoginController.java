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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import no.hiof.set.gruppe.core.exceptions.ErrorExceptionHandler;
import no.hiof.set.gruppe.core.exceptions.InvalidLoginInformation;
import no.hiof.set.gruppe.GUI.controller.abstractions.Controller;
import no.hiof.set.gruppe.core.repository.IRepository;
import no.hiof.set.gruppe.core.repository.Repository;
import no.hiof.set.gruppe.GUI.model.ViewInformation;
import no.hiof.set.gruppe.model.user.LoginInformation;
import no.hiof.set.gruppe.model.user.ProtoUser;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Contains logic corresponding to the login view, currently the main view.
 */
public class LoginController extends Controller {

    // --------------------------------------------------//
    //                2.FXML Fields                      //
    // --------------------------------------------------//

    @FXML
    private Menu cheatLogin;
    @FXML
    private MenuItem adminLogin, arrangLogin, userLogin;
    @FXML
    private Button logInn, cancel;
    @FXML
    private TextField uName, pass;

    // --------------------------------------------------//
    //                3.Local Fields                     //
    // --------------------------------------------------//

    private String name = "";
    private String title = "";
    private final IRepository repository = new Repository();

    // --------------------------------------------------//
    //                4.On action Methods                //
    // --------------------------------------------------//
    /**
     * @param event {@link ActionEvent}
     */
    public void onClickUser(ActionEvent event){
        getCorrectCredentials(event);
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onClicklogin(ActionEvent event) {
        login();
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onClickCancel(ActionEvent event){
        closeWindow(cancel);
    }

    // --------------------------------------------------//
    //            5.Private Functional Methods           //
    // --------------------------------------------------//
    /**
     * @param protoUser {@link ProtoUser}
     */
    private void openCorrespondingStage(@NotNull ProtoUser protoUser) {
        title = protoUser.getName();
        name = protoUser.getViewName();
        createNewView(this);
    }

    private void getCorrectCredentials(@NotNull ActionEvent event){
        ProtoUser protoUser = ProtoUser.USER;
        MenuItem source = (MenuItem)event.getSource();

        if (source.equals(adminLogin)) protoUser = ProtoUser.ADMIN;
        else if (source.equals(arrangLogin)) protoUser = ProtoUser.ORGANIZER;
        applyCredentials(protoUser);
    }

    /**
     * @param protoUser {@link ProtoUser}
     */
    private void applyCredentials(@NotNull ProtoUser protoUser) {
        uName.setText(protoUser.getName());
        pass.setText(protoUser.getPass());
    }

    private void login(){
        String userName = uName.getText();
        String password = pass.getText();

        try{
            ProtoUser protoUser = repository.queryUserDetailsWith(new LoginInformation(userName, password));
            closeWindow(logInn);
            openCorrespondingStage(protoUser);

        }catch (InvalidLoginInformation invalidLogin){
            createAlert(ErrorExceptionHandler.ERROR_LOGIN);
        }
    }

    // --------------------------------------------------//
    //                6.Private Setup Methods            //
    // --------------------------------------------------//

    private void setUpActionHandlers(){
        logInn.setOnAction(this::onClicklogin);
        cancel.setOnAction(this::onClickCancel);

        MenuItem[] credentialsBtns = {adminLogin, arrangLogin, userLogin};
        for (MenuItem credentialsBtn : credentialsBtns) credentialsBtn.setOnAction(this::onClickUser);
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
        setUpActionHandlers();
    }

    /**
     * @return {@link ViewInformation}
     */
    @Override
    public ViewInformation getViewInformation() {
        return new ViewInformation(name, title);
    }
}
