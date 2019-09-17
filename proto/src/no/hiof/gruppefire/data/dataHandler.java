package no.hiof.gruppefire.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import no.hiof.gruppefire.model.Arrangement;

import java.io.File;

public class dataHandler {

    private final static ObservableList<Arrangement>arrangementList = FXCollections.observableArrayList();

    public static ObservableList<Arrangement> getArrangementData(File file){

        return arrangementList;
    }
}
