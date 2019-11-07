package no.hiof.set.gruppe.tests.GUI.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.hiof.set.gruppe.MainJavaFX;
import no.hiof.set.gruppe.GUI.controller.abstractions.IController;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
class MainJavaFXTest {
    private String startView = "Login.fxml";

    @Start
    void start(@NotNull Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainJavaFX.class.getResource(startView));
        System.out.println(MainJavaFX.class.getResource(startView));
        Parent editLayout = loader.load();

        IController controller = loader.getController();
        controller.setMainController(new MainJavaFX());

        Scene scene = new Scene(editLayout);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(controller.getViewInformation().viewTitle);
        stage.show();
    }

    void setStartView(String name){startView = name;}
}