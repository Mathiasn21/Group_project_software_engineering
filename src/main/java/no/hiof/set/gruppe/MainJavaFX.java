package no.hiof.set.gruppe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.hiof.set.gruppe.Exceptions.DataFormatException;
import no.hiof.set.gruppe.controller.*;
import java.io.IOException;

/**
 * MainJavaFX is a class that controls all of the windows in the application.
 * Holds information about primaryStage, application and arrangementToSend.
 *
 * @author Gruppe4
 */

public class MainJavaFX extends Application implements SetupWindow {
    private static Scene scene;
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        FXMLLoader loader = new FXMLLoader(MainJavaFX.class.getResource("Arrangement.fxml"));
        Parent editLayout = loader.load();

        ((Controller)loader.getController()).setMainController(this);
        Scene scene = new Scene(editLayout, 230, 450);
        stage.setScene(scene);
        stage.setMinHeight(500);
        stage.setMinWidth(500);
        stage.setTitle("Arrangementer");
        stage.show();
    }

    public void setupWindow(IController controller)throws IOException{
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainJavaFX.class.getResource(controller.getName()));
        Parent editLayout = loader.load();

        Scene editScene = new Scene(editLayout, 230, 450);
        stage.setScene(editScene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stage);
        stage.setTitle(controller.getTitle());
        controller.setMainController(this);
        System.out.println(this);

        stage.show();
    }


    public void setupWindow(IControllerDataTransfer<Object> controller, Object object){
        try{
            System.out.println("setting up data: " + object);
            controller.setDataFields(object);
            setupWindow(controller);
        }catch (DataFormatException | IOException dataEx){
            dataEx.printStackTrace();
        }
    }


    /**
     * Let be. This will be used for later in order to dynamically switching between windows.
     * */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainJavaFX.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
    public void stop(){
        //DataHandler.writeToJSONFile(new File("./files/arrangements.json"));
    }
}
