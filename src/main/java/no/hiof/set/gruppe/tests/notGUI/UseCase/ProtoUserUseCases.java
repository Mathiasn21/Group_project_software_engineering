package no.hiof.set.gruppe.tests.notGUI.UseCase;

/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. Single Tests
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//

import no.hiof.set.gruppe.core.infrastructure.exceptions.DataFormatException;
import no.hiof.set.gruppe.core.infrastructure.exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.core.interfaces.IRepository;
import no.hiof.set.gruppe.core.infrastructure.repository.Repository;
import no.hiof.set.gruppe.core.entities.Arrangement;
import no.hiof.set.gruppe.core.entities.Group;
import no.hiof.set.gruppe.core.entities.constantInformation.DummyUsers;
import no.hiof.set.gruppe.core.entities.user.ProtoUser;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing the functional requirements by testing
 * the use cases of a PROTO_USER.
 */
 @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProtoUserUseCases {
    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private static final ProtoUser PROTO_USER = ProtoUser.USER;
    private static final List<Arrangement> userArrangements;
    private static final List<Arrangement> notUserArrangements;
    private static Arrangement arrangementToTest;
    private static final IRepository repository = new Repository();


    //setup
    static {
        userArrangements = repository.queryAllEntityConnectedToUserData(Arrangement.class, PROTO_USER);
        notUserArrangements = repository.queryAllDataOfGivenType(Arrangement.class);
        notUserArrangements.removeAll(userArrangements);
    }

    // --------------------------------------------------//
    //                3.Single Tests                     //
    // --------------------------------------------------//
    @Test
    @Order(1)
    void first_insert_userRelation_to_arrangement_and_keepDataIntegrity() throws DataFormatException {
        arrangementToTest = notUserArrangements.get(0);
        userArrangements.add(arrangementToTest);

        repository.insertUserRelationToData(arrangementToTest, PROTO_USER);
        assertDataIntegrity(repository.queryAllEntityConnectedToUserData(Arrangement.class, PROTO_USER));
    }


    @Test
    @Order(2)
    void then_delete_userRelation_from_arrangement_and_keepDataIntegrity() throws DataFormatException {
        repository.deleteUserRelationToData(arrangementToTest, PROTO_USER);
        userArrangements.remove(arrangementToTest);
        assertDataIntegrity(repository.queryAllEntityConnectedToUserData(Arrangement.class, PROTO_USER));
    }


    private static final Class<Group> GroupClazz = Group.class;
    private static Group group = new Group("Test");
    private static List<Group> expectedGroups = repository.queryAllDataOfGivenType(GroupClazz);
    @Test
    @Order(3)
    void first_insert_new_group_and_keepDataIntegrity() throws DataFormatException, IllegalDataAccess {
        expectedGroups.add(group);
        repository.insertData(group, PROTO_USER);
        assertDataIntegrity(expectedGroups, repository.queryAllDataOfGivenType(GroupClazz));
        assertEquals(group, repository.queryDataWithID(group.getID(), GroupClazz));
    }

    @Test
    @Order(4)
    void then_mutate_group_with_newMembers_and_keepDataIntegrity() throws DataFormatException {
        DummyUsers[] users = DummyUsers.values();

        int number = users.length - 1;
        int min = Math.min(4, number);

        group.addMember(users[number]);
        for(int i = 0; i <= min; i++) group.addMember(users[i]);
        executeMutationAndAssertIntegrity();
    }

    @Test
    @Order(5)
    void then_mutate_group_with_removedMembers_and_keepDataIntegrity() throws DataFormatException {
        group.removeMembers(group.getMembers().toArray(DummyUsers[]::new));
        executeMutationAndAssertIntegrity();
    }

    @Test
    @Order(6)
    void andLast_delete_group_and_keepDataIntegrity() throws DataFormatException, IllegalDataAccess {
        repository.deleteData(group, PROTO_USER);
        expectedGroups.remove(group);
        assertDataIntegrity(expectedGroups, repository.queryAllDataOfGivenType(GroupClazz));
    }


    private void executeMutationAndAssertIntegrity() throws DataFormatException {
        repository.mutateData(group);
        assertDataIntegrity(expectedGroups, repository.queryAllDataOfGivenType(GroupClazz));

        List<DummyUsers> expectedMembers = group.getMembers();
        List<DummyUsers> gottenMembers = repository.queryDataWithID(group.getID(), GroupClazz).getMembers();
        assertTrue(expectedMembers.containsAll(gottenMembers));
        assertEquals(expectedMembers.size(), gottenMembers.size());
    }

    private void assertDataIntegrity(List<Arrangement> expectedArrangementList) {
        assertTrue(userArrangements.containsAll(expectedArrangementList));
        assertEquals(expectedArrangementList.size(), userArrangements.size());
    }

    private void assertDataIntegrity(List<Group> expectedGroupList, List<Group> gottenGroupList) {
        assertTrue(gottenGroupList.containsAll(expectedGroupList));
        assertEquals(expectedGroupList.size(), gottenGroupList.size());
    }
 }
