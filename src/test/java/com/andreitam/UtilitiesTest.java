package com.andreitam;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UtilitiesTest {

    @Test
    public void testPersons() {
        //create utilities instance
        Utilities ut = new Utilities();
        //read from .json and put objects in List
        List<PersonWithBirthDate> personsWithBirthDate = ut.jsonToList(ut.filePathToString("PersonsIn.json"));
        //process objects and put in new List, method mapPerson is written ith java 8
        List<Person> persons = ut.mapPersons(personsWithBirthDate);
        //print new persons
        persons.stream().forEach(System.out::println);
        //write to .json new objects
        ut.writeStringtoFile(ut.listToJson(persons),"PersonsOut.json");
        //assert Keanu Reeves is the oldest from the new objects
        assertEquals("Keanu", persons.get(0).getFirstName());
        assertEquals("Reeves", persons.get(0).getLastName());

    }
}