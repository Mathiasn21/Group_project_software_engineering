package no.hiof.set.gruppe.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.hiof.set.gruppe.data.DataHandler;
import no.hiof.set.gruppe.model.User;
import no.hiof.set.gruppe.model.UserConnectedArrangement;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SignUpOffTests {



    @Test
    public void AddUser() {
        List<UserConnectedArrangement>arrayBefore = getUserArray();

        DataHandler.addUserToArrangement(DataHandler.getArrangementsData().get(0), User.USER);
        new DataHandler().storeArrangementsData();

        List<UserConnectedArrangement>arrayAfter = getUserArray();
        assertTrue(arrayAfter.size() > arrayBefore.size());
    }

    @Test
    public void RemoveUser() {
        List<UserConnectedArrangement>arrayBefore = getUserArray();

        DataHandler.deleteUserFromArrangement(DataHandler.getArrangementsData().get(0), User.USER);
        new DataHandler().storeArrangementsData();

        List<UserConnectedArrangement>arrayAfter = getUserArray();
        assertTrue(arrayAfter.size() < arrayBefore.size());
    }

    private List<UserConnectedArrangement> getUserArray() {
        String returnString = readFromFile("userHasArrangements.json");
        List<UserConnectedArrangement> returnArray = listFromJson(UserConnectedArrangement[].class, returnString);

        return returnArray;
    }

    private String readFromFile(String fName){
        String line;
        StringBuilder textFromFile = new StringBuilder();

        String filepath = "/files/" + fName;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("").getAbsolutePath() + filepath))) {
            while ((line = bufferedReader.readLine()) != null) {
                textFromFile.append(line);
            }
            return textFromFile.toString();
        }catch (IOException ioexc) {
            ioexc.printStackTrace();

        }
        return "";
    }

    @NotNull
    private static<T> List<T> listFromJson(Class<T[]> type, String jsonTextFromFile) {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        T[] arrangementArray = gson.fromJson(jsonTextFromFile, type);
        return (Arrays.asList(arrangementArray));
    }

}
