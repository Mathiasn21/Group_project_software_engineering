package no.hiof.set.gruppe.controller.concrete;

import no.hiof.set.gruppe.Exceptions.DataFormatException;
import no.hiof.set.gruppe.controller.abstractions.Controller;
import no.hiof.set.gruppe.model.ViewInformation;
import java.net.URL;
import java.util.ResourceBundle;

public class GroupController extends Controller {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public Object getDataObject() {
        return null;
    }

    @Override
    public void setDataFields(Object controller) throws DataFormatException {

    }

    @Override
    public ViewInformation getViewInformation() {
        return null;
    }
}
