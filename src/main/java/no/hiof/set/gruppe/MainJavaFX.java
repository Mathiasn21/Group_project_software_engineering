package no.hiof.set.gruppe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.hiof.gruppefire.model.Arrangement;
import no.hiof.set.gruppe.controller.NewAlterArrangementController;
import no.hiof.set.gruppe.data.DataHandler;

import java.io.File;
import java.io.IOException;

/**
 * MainJavaFX is a class that controls all of the windows in the application.
 * Holds information about primaryStage, application and arrangementToSend.
 *
 * @author Gruppe4
 */

public class MainJavaFX extends Application {

    private static MainJavaFX application;
    public Arrangement arrangementToSend;

    private static Scene scene;
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("start"));
        stage.setScene(scene);
        stage.setTitle("Arrangementer");
        stage.show();

        stage.setMinHeight(500);
        this.stage = stage;
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainJavaFX.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Creates a new window to either edit or create a new arrangement.
     * @param arrangement
     * @param title
     * @throws IOException
     */
    public void newAlterWindow(Arrangement arrangement, String title) throws IOException{

        arrangementToSend = arrangement;

        Stage editStage = new Stage();

        FXMLLoader fxmlInnlaster = new FXMLLoader();
        fxmlInnlaster.setLocation(getClass().getResource("./view/NewAlterArrangement.fxml"));
        Parent editLayout = fxmlInnlaster.load();

        NewAlterArrangementController newAlterArrangementController = fxmlInnlaster.getController();

        Scene editScene = new Scene(editLayout, 230, 450);
        newAlterArrangementController.setStage(editStage);

        editStage.setScene(editScene);
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.initOwner(stage);
        editStage.setTitle(title);
        editStage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Closing a Stage s.
     * @param s Stage
     */
    public void close(Stage s){
        s.close();
    }

    /**
     * Calls {@link DataHandler#writeToJSONFile(File)} when the application is closing.
     */
    @Override
    public void stop(){
        DataHandler.writeToJSONFile(new File("./files/arrangements.json"));
    }

    public static MainJavaFX getApplication() {
        return application;
    }

    public Arrangement getArrangementToEdit() {
        return arrangementToSend;
    }
}
