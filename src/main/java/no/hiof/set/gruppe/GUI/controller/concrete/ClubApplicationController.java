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
import no.hiof.set.gruppe.GUI.controller.abstractions.Controller;
import no.hiof.set.gruppe.GUI.model.ViewInformation;
import java.net.URL;
import java.util.ResourceBundle;

public class ClubApplicationController extends Controller {

    // --------------------------------------------------//
    //                2.FXML Fields                      //
    // --------------------------------------------------//

    @FXML
    private Button cancelBtn;
    @FXML
    private Button sendBtn;

    // --------------------------------------------------//
    //                3.Local Fields                     //
    // --------------------------------------------------//

    private final String name = "ClubApplication.fxml";
    private final String title = "Klubbsøknad";

    // --------------------------------------------------//
    //                4.On Action Methods                //
    // --------------------------------------------------//

    /**
     * @param event {@link ActionEvent}
     */
    private void onClickCancelBtn(ActionEvent event){
        closeWindow(cancelBtn);
    }

    /**
     * @param event {@link ActionEvent}
     */
    private void onClickSendBtn(ActionEvent event){
        closeWindow(sendBtn);
    }

    // --------------------------------------------------//
    //            5.Private Functional Methods           //
    // --------------------------------------------------//


    // --------------------------------------------------//
    //                6.Private Setup Methods            //
    // --------------------------------------------------//

    private void setupActionHandlers() {
        cancelBtn.setOnAction(this::onClickCancelBtn);
        sendBtn.setOnAction(this::onClickSendBtn);
    }

    // --------------------------------------------------//
    //                7.Overridden Methods               //
    // --------------------------------------------------//

    /**
     * @param url {@link URL}
     * @param resourceBundle {@link ResourceBundle}
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        setupActionHandlers();
    }

    /**
     * @return {@link ViewInformation}
     */
    @Override
    public ViewInformation getViewInformation() { return new ViewInformation(name, title); }
}
