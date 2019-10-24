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
import no.hiof.set.gruppe.model.user.User;
import java.net.URL;
import java.util.ResourceBundle;

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


    // --------------------------------------------------//
    //                5.Private Methods                  //
    // --------------------------------------------------//

    private int checkCredentials(String user){
        //should take an input that is a user enum
        //Test for valid and what the valid credentials are, eg admin, organizer or user
        //Might be a enum that returns a given view name
        return 0;
    }

    private void getCorrectCredentials(ActionEvent event){
        User user = User.USER;
        Button source = (Button)event.getSource();

        if (source.equals(adminLogin)) user = User.ADMIN;
        else if (source.equals(arrangLogin)) {
            user = User.ORGANIZER;
        }
        applyCredentials(user);
    }

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

    private void openCorrespondingStage(User user) {
        title = user.getName();
        name = user.getViewName();
        System.out.println(getMainController());
        createNewView(this);
    }

    private void applyCredentials(User user) {
        uName.setText(user.getName());
        pass.setText(user.getPass());
    }

    // --------------------------------------------------//
    //                6.Overridden Methods               //
    // --------------------------------------------------//

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Button[] credentialsBtns = {adminLogin, arrangLogin, userLogin};
        for (Button credentialsBtn : credentialsBtns) credentialsBtn.setOnAction(this::getCorrectCredentials);
        
        logInn.setOnAction(this::login);
    }

    @Override
    public Object getDataObject() {
        return null;
    }

    @Override
    public void setDataFields(Object object) throws DataFormatException {

    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getName() {
        return name;
    }



    /*
    * Needs valid logic for input control
    * */
}
