package no.hiof.set.gruppe.data;

import com.google.api.client.util.ArrayMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.hiof.set.gruppe.core.entities.Arrangement;
import no.hiof.set.gruppe.core.entities.Group;
import no.hiof.set.gruppe.core.entities.UserConnectedArrangement;
import no.hiof.set.gruppe.core.infrastructure.exceptions.DataFormatException;
import no.hiof.set.gruppe.core.interfaces.IBaseEntity;
import no.hiof.set.gruppe.core.interfaces.IHandleData;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HandleDataStorage implements IHandleData {
    private static final Class<?>[] knownClasskeys = {Arrangement.class, UserConnectedArrangement.class, Group.class};
    private static final String[] knownDataFiles = {"arrangements.json", "userHasArrangements.json", "groups.json"};
    private static final Map<Class<?>, String> objectMapper = new ArrayMap<>();

    static{
        for(int i = 0; i < knownClasskeys.length; i++)objectMapper.put(knownClasskeys[i], knownDataFiles[i]);
    }

    @Override
    public final <T extends IBaseEntity> void storeDataGivenType(Class<T> aClass, List<? extends IBaseEntity> list) throws DataFormatException {
        String file = objectMapper.get(aClass);
        if(file == null)throw new DataFormatException();
        writeToFile(toJson(list), file);
    }

    @NotNull
    @Contract("_ -> new")
    @Override
    public final <T> List<T> queryAllDataGivenType(Class<T> tClassArr) throws IOException {
        String fName = objectMapper.get(tClassArr);
        if (fName == null)return new ArrayList<>();

        String jsonFromFile = HandleDataStorage.readFromFile(fName);
        return new ArrayList<>(HandleDataStorage.listFromJson(tClassArr, jsonFromFile));
    }

    /**
     * Standard method for writing data to a given file.
     * This methods does not append but overwrites!
     * Utilizes a relative path for top level directory, plus filename.extension
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
     * @param list T[]
     * @return String
     */
    private static String toJson(List<? extends IBaseEntity> list) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(list);
    }

    /**
     * Standard reading from a file. Utilizes a relative path given filename.extension.
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
    @SuppressWarnings("unchecked")//Will always be possible otherwise and exception is thrown waaay before this method
    private static <T> List<T> listFromJson(Class<T> type, String jsonTextFromFile) {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        Class<T[]> arrClass = (Class<T[]>) Array.newInstance(type, 0).getClass();
        T[] arrangementArray = gson.fromJson(jsonTextFromFile, arrClass);
        return (Arrays.asList(arrangementArray));
    }
}