package no.hiof.set.gruppe.tests;

import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.util.ArrangementSort;
import no.hiof.set.gruppe.util.DateTest;
import no.hiof.set.gruppe.util.Validation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.time.LocalDate;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class TestSystemFunctionality {
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
        assertEquals(0, Arrays.compare(arrangementNeededData, arrangementDataFields));
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


    //Needs refactoring, maybe
    @Test
    void classDateLegal(){
        LocalDate date1 = LocalDate.of(2019, 10, 15);
        LocalDate date2 = LocalDate.of(2019, 10, 15);
        LocalDate date3 = LocalDate.of(2019, 10, 16);
        LocalDate date4 = LocalDate.now().plusDays(1);
        LocalDate date5 = LocalDate.now().plusDays(2);

        assertTrue(DateTest.TestExpired.execute(date1, date2));
        assertTrue(DateTest.TestExpired.execute(date1, date3));
        assertTrue(DateTest.TestFuture.execute(date4, date5));
        assertTrue(DateTest.TestOngoing.execute(date1, date4));
    }

    @Test
    void classDateIllegal(){
        LocalDate date1 = LocalDate.now().plusDays(1);
        LocalDate date2 = LocalDate.of(2019, 10, 15);
        LocalDate date3 = LocalDate.of(2019, 10, 16);
        LocalDate date4 = LocalDate.now().minusDays(1);

        assertFalse(DateTest.TestExpired.execute(date1, date1));
        assertFalse(DateTest.TestFuture.execute(date3, date2));
        assertFalse(DateTest.TestOngoing.execute(date2, date4));
    }


    @Test
    void arrangementListSorting(){
        Arrangement arrangement1 = new Arrangement("a","Annet",101,"BergOgDalBaneVegen 46",false,"2019-10-15","2019-10-16","Formål1");
        Arrangement arrangement2 = new Arrangement("A","Sykkelritt",101,"BergOgDalBaneVegen 46",false,"2019-10-15","2019-10-16","Formål2");
        Arrangement arrangement3 = new Arrangement("B","Annet",99,"BergOgDalBaneVegen 48",true,"2019-10-16","2019-10-17","Formål3");
        Arrangement arrangement4 = new Arrangement("c","Skirenn",100,"BergOgDalBaneVegen 49",true,"2019-10-11","2019-10-18","Formål4");

        Arrangement[] alphabeticalASC = {arrangement1, arrangement2, arrangement3, arrangement4};
        Arrangement[] sortDatesASC = {arrangement4, arrangement1, arrangement2, arrangement3};
        Arrangement[] maxParticipantsASC = {arrangement3, arrangement4, arrangement1, arrangement2};
        Arrangement[] sportASC = {arrangement3, arrangement1, arrangement4, arrangement2};

        Arrangement[] arrangementArr = {arrangement1, arrangement2, arrangement3, arrangement4};

        Arrays.sort(arrangementArr, ArrangementSort.COMP_NAME_ASC.getComparator());
        assertArrayEquals(alphabeticalASC, arrangementArr);

        Arrays.sort(arrangementArr, ArrangementSort.COMP_DATE_ASC.getComparator());
        assertArrayEquals(sortDatesASC, arrangementArr);

        Arrays.sort(arrangementArr, ArrangementSort.COMP_PARTICIPANTS_ASC.getComparator());
        assertArrayEquals(maxParticipantsASC, arrangementArr);

        Arrays.sort(arrangementArr, ArrangementSort.COMP_SPORT_ASC.getComparator());
        assertArrayEquals(sportASC, arrangementArr);
    }
}