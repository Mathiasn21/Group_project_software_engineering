package no.hiof.set.gruppe.tests.unitTesting;

import no.hiof.set.gruppe.core.validations.Validation;
import no.hiof.set.gruppe.model.Group;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/*Guide
 * 1. Import Statements
 * 2. Unit Tests
 * 3. Parameterized Tests
 * 4. Helper Methods
 * */
// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//

class TestGroupValidationOf {

    // --------------------------------------------------//
    //                2.Unit Tests                       //
    // --------------------------------------------------//
    /**
     * Tests legal inputs
     */
    @Test
    void LegalInput() {
        Group group = new Group("TestGroup");
        assertTrue(Validation.ofGroup(group).IS_VALID);
    }

    // --------------------------------------------------//
    //                3.Parameterized Tests              //
    // --------------------------------------------------//
    /**
     * Tests for illegal names and illegal dates.
     * @param str String
     */
    @ParameterizedTest
    @MethodSource("GenIllegalNameAndDates")
    void IllegalInputNamesAndDates(String str) {
        Group group = new Group(str);
        assertFalse(Validation.ofGroup(group).IS_VALID);
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
                arguments(""),
                arguments("\00"),
                arguments("sssssssssssssssssssssssssssssss")
        );
    }
}
