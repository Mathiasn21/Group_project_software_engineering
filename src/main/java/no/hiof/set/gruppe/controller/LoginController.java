package no.hiof.set.gruppe.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Controller implements ILoginValidation {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private String name = "Login";
    private String title = "Innlogging";


    // --------------------------------------------------//
    //                3.FXML Fields                      //
    // --------------------------------------------------//
    @FXML
    private Button adminLogin;
    @FXML
    private Button arrangLogin;
    @FXML
    private Button userLogin;
    @FXML
    private TextField username;
    @FXML
    private TextField password;



    // --------------------------------------------------//
    //                2.Private Methods                  //
    // --------------------------------------------------//
    private int checkCredentials(String user){
        //should take an input that is a user enum
        //Test for valid and what the valid credentials are, eg admin, organizer or user
        //Might be a enum that returns a given view name
        return 0;
    }


    // --------------------------------------------------//
    //                5.Overridden Methods               //
    // --------------------------------------------------//
    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
    * Needs valid logic
    * */
    @Override
    public boolean isValidUName(String str) {
        return false;
    }

    @Override
    public boolean isValidPass(String str) {
        return false;
    }
}
