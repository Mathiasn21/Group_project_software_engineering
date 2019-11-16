package no.hiof.set.gruppe.tests.notGUI.unitTesting;
/*Guide
 * 1. Import Statements
 * 2. Unit Tests
 * 3. Parameterized Tests
 * 4. Helper Methods
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import no.hiof.set.gruppe.core.validations.Validation;
import no.hiof.set.gruppe.model.Arrangement;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.Assert.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.time.LocalDate;
import java.util.stream.Stream;

class TestArrangementValidationOf {


    // --------------------------------------------------//
    //                2.Unit Tests                       //
    // --------------------------------------------------//
    /**
     * Tests legal inputs
     */
    @Test
    void LegalInput() {
        Arrangement arrangement = new Arrangement("pez1","Annet",420,"Hakkebakkeskogen", false, LocalDate.now().plusDays(1).toString(), LocalDate.now().plusDays(1).toString(), "testdwa dawd aw");
        assertTrue(Validation.ofArrangement(arrangement).IS_VALID);
    }

    // --------------------------------------------------//
    //                3.Parameterized Tests              //
    // --------------------------------------------------//
    /**
     * Tests for illegal names and illegal dates.
     * @param str String
     * @param startDate {@link LocalDate}
     * @param endDate {@link LocalDate}
     */
    @ParameterizedTest
    @MethodSource("GenIllegalNameAndDates")
    void IllegalInputNamesAndDates(String str, @NotNull LocalDate startDate, @NotNull LocalDate endDate) {
        Arrangement arrangement = new Arrangement(str,"Annet",420, str, false, startDate.toString(), endDate.toString(), "test");
        assertFalse(Validation.ofArrangement(arrangement).IS_VALID);
    }

    // --------------------------------------------------//
    //                4.Helper Methods                   //
    // --------------------------------------------------//
    /**
     * Generates illegal dates and names
     * @return {@link Stream}
     */
    @NotNull
    @Contract(pure = true)
    private static Stream<Arguments> GenIllegalNameAndDates() {
        return Stream.of(
                arguments("", LocalDate.of(2020,10,5), LocalDate.of(2020,10,8)),
                arguments("ljljlj \00ljljljl", LocalDate.of(2020,10,5), LocalDate.of(2020,10,8)),
                arguments("\00ljljljl", LocalDate.of(2020,10,5), LocalDate.of(2020,10,8)),
                arguments("ljljlj\00", LocalDate.of(2020,10,5), LocalDate.of(2020,10,8)),
                arguments("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss", LocalDate.of(2020,10,5), LocalDate.of(2020,10,9)),

                arguments("hhhhhhhh", LocalDate.now().minusDays(1), LocalDate.now()),
                arguments("hhhhhhhh", LocalDate.now(), LocalDate.now().minusDays(1))
        );
    }
}