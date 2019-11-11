package no.hiof.set.gruppe.core.exceptions;

/*Guide
 * 1. Import Statements
 * 2. Constants
 * 3. Instance variables
 * 4. Constructs
 * 5. Public Methods
 * */


// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
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
    ERROR_WRONG_DATA_OBJECT(8, "ERROR, Wrong dataformat on object."),
    ERROR_FATAL(9, "Something really bad has happened. Program terminated."),
    EXCEPTION_STORAGE_ERROR (10, "Exception, something went wrong with storing the data."),
    ERROR_READING_DATA(11, "ERROR, unable to read data from database"),
    ERROR_ACCESSING_DATA(12, "ERROR user cannot access this data."),
    ERROR_LOGIN(13, "ERROR, invalid user information.");

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
    @Contract(pure = true)
    ErrorExceptionHandler(int code, String msg) {
        this.CODE = code;
        this.ERROR_MSG = msg;
    }


    // --------------------------------------------------//
    //                5.Public methods                   //
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
