package no.hiof.set.gruppe.tests.notGUI.unitTesting;

/*Guide
 * 1. Import Statements
 * 2. Unit Tests
 * 3. Parameterized Tests
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//

import no.hiof.set.gruppe.core.infrastructure.predicates.ArrangementSort;
import no.hiof.set.gruppe.core.infrastructure.predicates.DateTest;
import no.hiof.set.gruppe.core.infrastructure.validations.Validation;
import no.hiof.set.gruppe.core.entities.Arrangement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SystemFunctionality {
    // --------------------------------------------------//
    //                1.Import Statements                //
    // --------------------------------------------------//
    private static final Arrangement arrangement = new Arrangement(
            "Bernts Fantastiske Test",
            "Annet",
            420,
            "BergOgDalBaneVegen 46",
            false,
            "2019-10-15",
            "2019-10-16",
            "Dette varer i hele 1 dager. Og, server null formål."
    );

    // --------------------------------------------------//
    //                2.Unit Tests                       //
    // --------------------------------------------------//

    @Test
    void assert_arrangement_has_requiredData(){
        String[] arrangementNeededData = {
                arrangement.getName(),
                arrangement.getSport(),
                arrangement.getAddress(),
                arrangement.getStartDate() + " til " + arrangement.getEndDate(),
                String.valueOf(arrangement.getParticipants()),
                arrangement.isGroup() ? "Lagkonkurranse" : "Individuell konkurranse",
                arrangement.getDescription()
        };
        String[] arrangementDataFields = arrangement.getAllDataAsStringArr();
        assertEquals(0, Arrays.compare(arrangementNeededData, arrangementDataFields));
    }

    @Test
    void assert_DateTest_validates_legal_dates(){
        LocalDate date1 = LocalDate.now().minusDays(2);
        LocalDate date2 = LocalDate.now().minusDays(2);
        LocalDate date3 = LocalDate.now().minusDays(1);
        LocalDate date4 = LocalDate.now().plusDays(1);
        LocalDate date5 = LocalDate.now().plusDays(2);

        assertTrue(DateTest.TestExpired.execute(date1, date2));
        assertTrue(DateTest.TestExpired.execute(date1, date3));
        assertTrue(DateTest.TestFuture.execute(date4, date5));
        assertTrue(DateTest.TestOngoing.execute(date1, date4));
    }

    @Test
    void assert_DateTest_validates_illegal_dates(){
        LocalDate date1 = LocalDate.now().plusDays(1);
        LocalDate date2 = LocalDate.now().minusMonths(1).minusDays(1);
        LocalDate date3 = LocalDate.now().minusMonths(1);
        LocalDate date4 = LocalDate.now().minusDays(1);

        assertFalse(DateTest.TestExpired.execute(date1, date1));
        assertFalse(DateTest.TestFuture.execute(date3, date2));
        assertFalse(DateTest.TestOngoing.execute(date2, date4));
    }

    @Test
    void assert_DateTest_validates_sorts_correct(){
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

    // --------------------------------------------------//
    //                2.Parameterized Tests              //
    // --------------------------------------------------//
    /**
     * @param str {@link String}
     */
    @ParameterizedTest
    @ValueSource(strings = {"1\00", "\00 1", "\00", "1s", "s1", "ss'¨¨¨^", "^^^''''"})
    void assert_Validation_ofNumber_isIllegal(String str){
        assertFalse(Validation.ofNumber(str));
    }

    /**
     * @param str {@link String}
     */
    @ParameterizedTest
    @ValueSource(strings = {"01", "000", "11", "22222222222"})
    void assert_Validation_ofNumber_isLegal(String str){
        assertTrue(Validation.ofNumber(str));
    }

}