package no.hiof.set.gruppe.tests.notGUI.unitTesting;

import no.hiof.set.gruppe.core.entities.constantInformation.DummyUsers;
import no.hiof.set.gruppe.core.infrastructure.factory.DataFactory;
import no.hiof.set.gruppe.core.infrastructure.factory.IFactory;
import no.hiof.set.gruppe.core.infrastructure.validations.Validation;
import no.hiof.set.gruppe.core.entities.Group;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
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
private static IFactory factory = new DataFactory();

    // --------------------------------------------------//
    //                2.Unit Tests                       //
    // --------------------------------------------------//
    /**
     * Tests legal inputs
     */
    @RepeatedTest(value = 15)
    void assert_factory_Input_isLegal() {
        Group group = factory.generateType(Group.class);
        assertTrue(Validation.of(group).IS_VALID);
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
        group.addAllMembers(Arrays.asList(DummyUsers.values()));
        assertFalse(Validation.of(group).IS_VALID);
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
