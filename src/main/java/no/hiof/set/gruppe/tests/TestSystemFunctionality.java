package no.hiof.set.gruppe.tests;

import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.util.Validation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSystemFunctionality {
    private static final Arrangement arrangement = new Arrangement(
            "Bernts Fantastiske Test",
            "Annet",
            420,
            "BergOgDalBaneVegen 46",
            false,
            "2019-10-15",
            "2019-10-16",
            "Dette varer i hele 1 dager. Og, server null formål.");

    //test returning the proper list in order from arrangement.

    //Test that correct data still exists in the arrangement object.?????

    @Test
    void minimumDataExistsInArrangement(){

        String[] arrangementNeededData = {
                arrangement.getName(),
                arrangement.getSport(),
                arrangement.getAddress(),
                arrangement.getStartDate() + " til " + arrangement.getEndDate(),
                String.valueOf(arrangement.getParticipants()),
                arrangement.getDescription()
        };
        String[] arrangementDataFields = arrangement.getAllDataAsStringArr();
        assertTrue(Arrays.compare(arrangementNeededData, arrangementDataFields) == 0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1\00", "\00 1", "1s", "s1", "ss'¨¨¨^", "^^^''''"})
    void illegalNumberFormatFromString(String str){
        assertFalse(Validation.ofNumber(str));
    }

    @ParameterizedTest
    @ValueSource(strings = {"01", "000", "11", "22222222222"})
    void legalNumberFormatFromString(String str){
        assertTrue(Validation.ofNumber(str));
    }
}