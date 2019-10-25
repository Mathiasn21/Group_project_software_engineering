package no.hiof.set.gruppe.Exceptions;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public enum ErrorExceptionHandler {


    // --------------------------------------------------//
    //                2.Constants                        //
    // --------------------------------------------------//
    ERROR_MISSING_RESOURCES(2, "Missing resources, please make sure no files are missing."),
    ERROR_OPENING_WINDOW (4, "Could not open window."),
    ERROR_LOGGING_ERROR (6, "ERROR, could not log the error."),
    ERROR_LOAD_RESOURCE(7, "ERROR, could not load resources for fxml window."),
    ERROR_FATAL(8, "Something really bad has happened. Program terminated."),
    EXCEPTION_STORAGE_ERROR (9, "Exception, something went wrong withstoring the data.");


    // --------------------------------------------------//
    //                3.Instance variables               //
    // --------------------------------------------------//
    public final int CODE;
    public final String ERROR_MSG;



    // --------------------------------------------------//
    //                4.Constructor                      //
    // --------------------------------------------------//
    /**
     * @param code int
     * @param msg String
     */
    ErrorExceptionHandler(int code, String msg) {
        this.CODE = code;
        this.ERROR_MSG = msg;
    }


    // --------------------------------------------------//
    //                6.Public methods                   //
    // --------------------------------------------------//
    /**
     * Tries to create a log given error. Failing to do so
     * will result in an alert box with: could not log error.
     * @param errMsg ErrorMsg
     * @param error Throwable
     * @throws IOException IOException
     */
    public static void createLogWithDetails(@NotNull ErrorExceptionHandler errMsg, @NotNull Throwable error ) throws IOException {
        File log = new File("./logs/" + errMsg.CODE + "-" + System.currentTimeMillis() + ".txt");
        error.printStackTrace(new PrintStream(log));
    }
}