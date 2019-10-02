package no.hiof.set.gruppe.controller;

import java.io.IOException;

public interface SetupWindow {
    void setupWindow(IController controller)throws IOException;
    void setupWindow(IControllerDataTransfer<Object> controller, Object object);
}
