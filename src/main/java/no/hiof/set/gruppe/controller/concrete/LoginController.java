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
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import no.hiof.set.gruppe.Exceptions.ErrorExceptionHandler;
import no.hiof.set.gruppe.Exceptions.InvalidLoginInformation;
import no.hiof.set.gruppe.controller.abstractions.Controller;
import no.hiof.set.gruppe.data.Repository;
import no.hiof.set.gruppe.model.ViewInformation;
import no.hiof.set.gruppe.model.user.LoginInformation;
import no.hiof.set.gruppe.model.user.ProtoUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Contains logic corresponding to the login view, currently the main view.
 */
public class LoginController extends Controller {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//

    private String name = "";
    private String title = "";

    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//
    @FXML
    private Button logInn, adminLogin, arrangLogin, userLogin;
    @FXML
    private TextField uName, pass;


    // --------------------------------------------------//
    //                4.On action Methods                //
    // --------------------------------------------------//
    /**
     * @param event {@link ActionEvent}
     */
    private void getCorrectCredentials(@NotNull ActionEvent event){
        ProtoUser protoUser = ProtoUser.USER;
        Button source = (Button)event.getSource();

        if (source.equals(adminLogin)) protoUser = ProtoUser.ADMIN;
        else if (source.equals(arrangLogin)) protoUser = ProtoUser.ORGANIZER;
        applyCredentials(protoUser);
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void login(ActionEvent event) {
        String userName = uName.getText();
        String password = pass.getText();


        try{
            ProtoUser protoUser = Repository.getUserDetails(new LoginInformation(userName, password));
            ((Stage)logInn.getScene().getWindow()).close();
            openCorrespondingStage(protoUser);

        }catch (InvalidLoginInformation invalidLogin){
            try { ErrorExceptionHandler.createLogWithDetails(ErrorExceptionHandler.ERROR_LOGIN, invalidLogin); }
            catch (IOException e) {e.printStackTrace();}
            createAlert(ErrorExceptionHandler.ERROR_LOGIN);
        }
    }


    // --------------------------------------------------//
    //                5.Functional Methods               //
    // --------------------------------------------------//
    /**
     * @param protoUser {@link ProtoUser}
     */
    private void openCorrespondingStage(@NotNull ProtoUser protoUser) {
        title = protoUser.getName();
        name = protoUser.getViewName();
        System.out.println(getMainController());
        createNewView(this);
    }

    /**
     * @param protoUser {@link ProtoUser}
     */
    private void applyCredentials(@NotNull ProtoUser protoUser) {
        uName.setText(protoUser.getName());
        pass.setText(protoUser.getPass());
    }

    // --------------------------------------------------//
    //                6.Overridden Methods               //
    // --------------------------------------------------//
    /**
     * @param location {@link URL}
     * @param resources {@link ResourceBundle}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Button[] credentialsBtns = {adminLogin, arrangLogin, userLogin};
        for (Button credentialsBtn : credentialsBtns) credentialsBtn.setOnAction(this::getCorrectCredentials);
        
        logInn.setOnAction(this::login);
    }

    /**
     * @return Object
     */
    @Nullable
    @Override
    public Object getDataObject() {
        return null;
    }

    /**
     * Does not have a purpose here.
     * @param object Object
     * @throws DataFormatException dataFormatException{@link DataFormatException}
     */
    @Override
    public void setDataFields(Object object) throws DataFormatException {

    }

    /**
     * @return {@link ViewInformation}
     */
    @Override
    public ViewInformation getViewInformation() {
        return new ViewInformation(name, title);
    }
}
