package no.hiof.set.gruppe.GUI.controller.abstractions;

public abstract class ControllerTransferData extends Controller implements IControllerDataTransfer{

    protected void createNewView(IControllerDataTransfer controller, Object object) { getMainController().setupWindow(controller, object); }
    public boolean hasNewObject(){return false;}

    @Override
    public void updateView() {/*empty method*/}
}