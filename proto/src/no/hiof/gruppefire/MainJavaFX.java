package no.hiof.gruppefire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.hiof.gruppefire.data.DataHandler;

import java.io.File;
import java.io.IOException;

public class MainJavaFX extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage){
        try{
            this.primaryStage = primaryStage;

            primaryStage.setTitle("Arrangements");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("view/Start.fxml"));
            Parent filmOversiktLayout = fxmlLoader.load();
            Scene hovedScene = new Scene(filmOversiktLayout, 500, 300);
            primaryStage.setScene(hovedScene);
            primaryStage.show();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop(){
        String arrangementsFilepath = "src/no/hiof/gruppefire/files/arrangements.json";
        DataHandler.writeToJSONFile(new File(arrangementsFilepath));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
