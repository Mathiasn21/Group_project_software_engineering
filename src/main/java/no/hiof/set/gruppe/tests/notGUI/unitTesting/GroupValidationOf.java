package no.hiof.set.gruppe.tests.notGUI.unitTesting;

import no.hiof.set.gruppe.core.infrastructure.validations.Validation;
import no.hiof.set.gruppe.core.entities.Group;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

class GroupValidationOf {


    // --------------------------------------------------//
    //                2.Unit Tests                       //
    // --------------------------------------------------//
    /**
     * Tests legal inputs
     */
    @Test
    void assert_Group_Input_isLegal() {
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
    @MethodSource("GenIllegalNames")
    void assert_Group_Name_isIllegal(String str) {
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
    private static Stream<Arguments> GenIllegalNames() {
        return Stream.of(
                arguments(""),
                arguments("ss"),
                arguments("\00"),
                arguments("sss\00"),
                arguments("\00ssss"),
                arguments("ssss\00ssss"),
                arguments("sssssssssssssssssssssssssssssss")
        );
    }
}
