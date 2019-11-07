package no.hiof.set.gruppe.tests.unitTesting;

/*Guide
 * 1. Import Statements
 * 2. Single Tests
 * 3. Multiple Tests
 * 4. Contracts
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
    //                2.Single Tests                     //
    // --------------------------------------------------//
    /**
     * Tests legal inputs
     */
    @Test
    void LegalInput() {
        Arrangement arrangement = new Arrangement("pez","Annet",420,"Hakkebakkeskogen", false, LocalDate.of(2019,12,10).toString(), LocalDate.of(2019,12,11).toString(), "testdwa dawd aw");
        assertTrue(Validation.ofArrangement(arrangement).IS_VALID);
    }

    // --------------------------------------------------//
    //                3.Multiple Tests                   //
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
    //                4.Contracts                        //
    // --------------------------------------------------//
    /**
     * Generates illegal dates and names
     * @return {@link Stream}
     */
    @NotNull
    @Contract(pure = true)
    private static Stream<Arguments> GenIllegalNameAndDates() {
        return Stream.of(
                arguments("", LocalDate.of(2019,10,9), LocalDate.of(2019,10,8)),
                arguments("\00", LocalDate.of(2019,10,9), LocalDate.of(2019,10,8)),
                arguments("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss", LocalDate.of(2019,10,9), LocalDate.of(2019,10,9))
        );
    }
}