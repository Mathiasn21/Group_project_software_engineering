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
    String name = "Admin";
    String title = "Admin Interface";


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
        return null;
    }

    @Override
    public String getName() {
        return null;
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
