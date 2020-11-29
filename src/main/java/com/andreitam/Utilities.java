package com.andreitam;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * The class Utilities contains methods to deserialise and serialise Person and PersonWithBirthDate objects
 *
 * @author  Andrei Tamasanu
 * @version 1.0
 * @since   2020-10-23
 */
public class Utilities {
    private Charset charset = Charset.forName("UTF8");
    /**
     * Method for reading a .json file
     * and returning string
     *
     * @param path - the path of the .json file
     * @return String
     */
    public String filePathToString(String path) {
        Path fileIn = FileSystems.getDefault().getPath(path);

        String file = "";
        try (BufferedReader reader = Files.newBufferedReader(fileIn, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                file += line;
            }
        } catch (IOException x) {
            System.err.println("IOException: " + x);
        }
        return file;
    }
    /**
     * Method for reading a string from a .json file
     * and returning List of objects
     * will be used with PersonWithBirthDate but this method is parameterisable
     *
     * @param json - the pjson string
     * @return List
     */
    public List<PersonWithBirthDate> jsonToList(String json) {
        Type targetClassType = new TypeToken<ArrayList<PersonWithBirthDate>>() {}.getType();
        List<PersonWithBirthDate> targetList = new Gson().fromJson(json, targetClassType);
        return targetList;
    }
    /**
     * Method for mapping the List<PersonWithBirthDate></>
     *     to List<Person></>,
     *also sorting depending on date of birth
     * @param personsWithBirthDate<PersonWithBirthDate>
     * @return List<Person>
     */
    public List<Person> mapPersons(List<PersonWithBirthDate> personsWithBirthDate) {
        List<Person> persons = personsWithBirthDate
                .stream()
                .sorted(Comparator.comparing((PersonWithBirthDate p) -> LocalDate.parse(p.getDateOfBirth())))
                .map(p -> new Person(p.getFirstName(),p.getLastName()))
                .collect(Collectors.toCollection(ArrayList::new));
        return persons;
    }
    /**
     * Method for reading a list of obejects
     * and returning a string of .json objects
     *
     * @param objects
     * @return String
     */
    public <T> String listToJson(List<T> objects) {
        String jsonCollectionString = new Gson().toJson(objects);
        return jsonCollectionString;
    }
    /**
     * Method for writing a string to file
     *
     * @param str - .json string
     * @param path - the path of the  file
     */
    public void writeStringtoFile(String str, String path) {
        Path fileOut = FileSystems.getDefault().getPath(path);

        try (BufferedWriter writer = Files.newBufferedWriter(fileOut, charset)) {
            writer.write(str);
        } catch (IOException x) {
            System.err.println("IOException: "+ x);
        }
    }

}
