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
import no.hiof.set.gruppe.controller.abstractions.Controller;
import no.hiof.set.gruppe.model.ViewInformation;
import no.hiof.set.gruppe.model.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        User user = User.USER;
        Button source = (Button)event.getSource();

        if (source.equals(adminLogin)) user = User.ADMIN;
        else if (source.equals(arrangLogin)) {
            user = User.ORGANIZER;
        }
        applyCredentials(user);
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void login(ActionEvent event) {
        String userName = uName.getText();
        String password = pass.getText();
        if(User.isValidUser(userName, password)){
            User user = User.getUser(userName);
            assert user != null;
            ((Stage)logInn.getScene().getWindow()).close();
            openCorrespondingStage(user);
        }
    }


    // --------------------------------------------------//
    //                5.Private Methods                  //
    // --------------------------------------------------//
    /**
     * @param user {@link User}
     */
    private void openCorrespondingStage(@NotNull User user) {
        title = user.getName();
        name = user.getViewName();
        System.out.println(getMainController());
        createNewView(this);
    }

    /**
     * @param user {@link User}
     */
    private void applyCredentials(@NotNull User user) {
        uName.setText(user.getName());
        pass.setText(user.getPass());
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
