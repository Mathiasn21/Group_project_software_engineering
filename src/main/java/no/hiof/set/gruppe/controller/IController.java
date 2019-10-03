package no.hiof.set.gruppe.controller;

import no.hiof.set.gruppe.MainJavaFX;
import java.io.IOException;

public interface IController {
    String getTitle();
    String getName();
    void setMainController(MainJavaFX mainController);
    void createNewView(Controller controller) throws IOException;
    void onCloseStoreInformation();
}
