package no.hiof.set.gruppe.tests.GUI.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.hiof.set.gruppe.GUI.controller.abstractions.IController;
import no.hiof.set.gruppe.MainJavaFX;
import no.hiof.set.gruppe.core.entities.Arrangement;
import no.hiof.set.gruppe.core.infrastructure.exceptions.DataFormatException;
import no.hiof.set.gruppe.core.infrastructure.factory.DataFactory;
import no.hiof.set.gruppe.core.interfaces.IRepository;
import no.hiof.set.gruppe.core.interfaces.IUser;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
class MainJavaFXTest {
    private String startView = "Login.fxml";

    /**
     * @param stage {@link Stage}
     * @throws IOException IOException
     */
    @Start
    void start(@NotNull Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainJavaFX.class.getResource(startView));
        Parent editLayout = loader.load();

        IController controller = loader.getController();
        controller.setMainController(new MainJavaFX());

        Scene scene = new Scene(editLayout);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(controller.getViewInformation().viewTitle);
        stage.show();
    }

    /**
     * @param name {@link String}
     */
    void setStartView(String name){startView = name;}


    void genDataIfNoneExist(IUser user) {
        //Generate data if there is none related to this user
        IRepository repository = MainJavaFX.getRepository();
        if(repository.queryAllEntityConnectedToUserData(Arrangement.class, user).size() == 0){
            Arrangement newArrangement = new DataFactory().generateType(Arrangement.class);
            try {repository.insertUserRelationToData(newArrangement, user);
            } catch (DataFormatException e) {e.printStackTrace();}
        }
    }
}