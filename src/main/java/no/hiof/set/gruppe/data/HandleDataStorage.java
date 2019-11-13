package no.hiof.set.gruppe.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.hiof.set.gruppe.core.exceptions.DataFormatException;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.Group;
import no.hiof.set.gruppe.model.user.UserConnectedArrangement;
import no.hiof.set.gruppe.util.TypeClassMapToObject;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class    HandleDataStorage implements IHandleData{
    private static final String arrangementFName = "arrangements.json";
    private static final String userHasArrangements = "userHasArrangements.json";
    private static final String groupsFName = "groups.json";

    static {
        TypeClassMapToObject.mutateObjectMapperList(new TypeClassMapToFiles<>(Arrangement.class, arrangementFName));
        TypeClassMapToObject.mutateObjectMapperList(new TypeClassMapToFiles<>(Group.class, groupsFName));
        TypeClassMapToObject.mutateObjectMapperList(new TypeClassMapToFiles<>(UserConnectedArrangement.class, userHasArrangements));
    }

    @Override
    public final <T> void storeDataGivenType(Class<T[]> tClass, T[] tArray) throws DataFormatException {
        writeToFile(toJson(tClass, tArray), (String) TypeClassMapToObject.getCorrespondingMapperGivenType(tClass).getObject());
    }

    @Override
    public final <T> List<T> queryAllDataGivenType(Class<T[]> tClassArr) throws IOException, DataFormatException {
        String jsonFromFile = HandleDataStorage.readFromFile((String) TypeClassMapToObject.getCorrespondingMapperGivenType(tClassArr).getObject());
        return new ArrayList<>(HandleDataStorage.listFromJson(tClassArr, jsonFromFile));
    }

    
    /**
     * Standard method for writing data to a given file.
     * This methods does not append but overwrites!
     * Utilizes a relative path for top level directory plus a filename.extension
     *
     * @param str   String
     * @param fName String
     */
    private static void writeToFile(String str, String fName) {
        String filepath = "/files/" + fName;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("").getAbsolutePath() + filepath))) {
            bufferedWriter.write(str);
        } catch (IOException ioexc) {
            ioexc.printStackTrace();
        }
    }

    /**
     * Utilizes parametrization combined with generics, in order to
     * convert a given T[] object and its specified Class template to json format.
     *
     * @param type  ClassT[]
     * @param array T[]
     * @param <T>   T
     * @return String
     */
    private static <T> String toJson(Class<T[]> type, T[] array) {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(array, type);
    }

    /**
     * Standard reading from a file. Utilizes a relative path given a filename.extension.
     * Files must exist in the top level directory.
     *
     * @param fName String
     * @return String
     * @throws IOException IOException {@link IOException}
     */
    @NotNull
    private static String readFromFile(String fName) throws IOException {
        String line;
        StringBuilder textFromFile = new StringBuilder();

        String filepath = "/files/" + fName;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("").getAbsolutePath() + filepath), StandardCharsets.UTF_8));
        while ((line = bufferedReader.readLine()) != null) {
            textFromFile.append(line);
        }
        return textFromFile.toString();
    }

    /**
     * This method retrieves a list, given a type Class and a json string.
     *
     * @param type             T[]
     * @param jsonTextFromFile String
     * @param <T>              T
     * @return {@link List}
     */
    @NotNull
    private static <T> List<T> listFromJson(Class<T[]> type, String jsonTextFromFile) {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        T[] arrangementArray = gson.fromJson(jsonTextFromFile, type);
        return (Arrays.asList(arrangementArray));
    }
}