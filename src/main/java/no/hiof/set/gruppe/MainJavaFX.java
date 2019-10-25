package no.hiof.set.gruppe;

/*Guide Controllers
 * 1. Import Statements
 * 2. Local Fields
 * 3. Overridden Methods
 * 4. Public Methods
 * 5. Static Methods
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import no.hiof.set.gruppe.Exceptions.ErrorExceptionHandler;
import no.hiof.set.gruppe.controller.abstractions.Controller;
import no.hiof.set.gruppe.controller.abstractions.IController;
import no.hiof.set.gruppe.controller.abstractions.IControllerDataTransfer;
import no.hiof.set.gruppe.controller.abstractions.SetupWindow;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * MainJavaFX is a class that controls all of the windows in the application.
 * Its primary goal is to controll and mediate between all other controllers.
 *
 * @author Gruppe4
 */
public class MainJavaFX extends Application implements SetupWindow {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }


    // --------------------------------------------------//
    //                3.Overridden Methods               //
    // --------------------------------------------------//
    @Override
    public void start(@NotNull Stage stage) throws IOException {
        this.stage = stage;

        FXMLLoader loader = new FXMLLoader(MainJavaFX.class.getResource("Login.fxml"));
        Parent editLayout = loader.load();

        IController controller = (loader.getController());
        controller.setMainController(this);

        stage.setOnHidden((Event) -> controller.onCloseStoreInformation());
        Scene scene = new Scene(editLayout);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Logg inn");
        stage.show();
    }


    // --------------------------------------------------//
    //                4.Public Methods                   //
    // --------------------------------------------------//

    /**
     * This function is responsible for setting up a new window.
     * This is custom made for controllers that implement the interface: {@link IController}
     *
     * @param controller {@link IController}
     */
    public void setupWindow(@NotNull IController controller) throws IOException {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainJavaFX.class.getResource(controller.getName()));
        Parent editLayout = loader.load();

        //handling onclose for given stage
        IController finalController = controller;
        stage.setOnHidden((Event) -> finalController.onCloseStoreInformation());

        Scene editScene = new Scene(editLayout);
        stage.setScene(editScene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stage);
        stage.setTitle(controller.getTitle());

        //setting next controller
        controller = loader.getController();
        controller.setMainController(this);
        System.out.println(this);

        stage.show();
    }

    /**
     * This function is responsible for setting up a new window.
     * This is custom made for controllers that implement the interface: {@link IControllerDataTransfer}
     * And as such mediates between controllers that requires this.
     *
     * @param controller {@link IControllerDataTransfer}
     * @param object     {@link Object}
     */
    public void setupWindow(@NotNull IControllerDataTransfer controller, Object object) {
        boolean errorOccured = true;
        ErrorExceptionHandler err = null;
        Throwable thrown = null;
        try {
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainJavaFX.class.getResource(controller.getName()));
            Parent editLayout = loader.load();

            IControllerDataTransfer oldController = controller;
            controller = loader.getController();
            controller.setDataFields(object);
            IControllerDataTransfer finalController = controller;
            stage.setOnHidden((Event) -> {
                if (finalController.hasNewObject()) {
                    try {
                        oldController.setDataFields(finalController.getDataObject());
                    } catch (DataFormatException e) {
                        e.printStackTrace();
                    }
                }
                oldController.updateView();
                finalController.onCloseStoreInformation();
            });

            Scene editScene = new Scene(editLayout);
            stage.setScene(editScene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.stage);
            stage.setTitle(controller.getTitle());
            stage.setResizable(false);
            controller.setMainController(this);
            System.out.println(this);

            stage.show();
            errorOccured = false;

        } catch (DataFormatException datFormEx) {
            err = ErrorExceptionHandler.ERROR_WRONG_DATA_OBJECT;
            thrown = datFormEx;

        } catch (IOException IOEx) {
            err = ErrorExceptionHandler.ERROR_LOAD_RESOURCE;
            thrown = IOEx;

        } finally {
            try {
                if (errorOccured && (err != null)) ErrorExceptionHandler.createLogWithDetails(err, thrown);
            } catch (Exception e) {
                Controller.createAlert(ErrorExceptionHandler.ERROR_LOGGING_ERROR);
            }
        }
    }
}
