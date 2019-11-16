package no.hiof.set.gruppe.tests.notGUI.UseCase;

/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. Single Tests
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//

import no.hiof.set.gruppe.core.exceptions.DataFormatException;
import no.hiof.set.gruppe.core.exceptions.IllegalDataAccess;
import no.hiof.set.gruppe.core.repository.IRepository;
import no.hiof.set.gruppe.core.repository.Repository;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.Group;
import no.hiof.set.gruppe.model.constantInformation.DummyUsers;
import no.hiof.set.gruppe.model.user.ProtoUser;
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
class TestingProtoUserUseCases {
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
     /**
      * Testing if adding a new arrangement is possible.
      */
    @Test
    @Order(1)
    void firstAddUserToArrangement() throws DataFormatException {
        arrangementToTest = notUserArrangements.get(0);
        userArrangements.add(arrangementToTest);

        repository.insertUserRelationToData(arrangementToTest, PROTO_USER);
        assertDataIntegrity(repository.queryAllEntityConnectedToUserData(Arrangement.class, PROTO_USER));
    }

     /**
      * Testing if removal of PROTO_USER is possible.
      */
    @Test
    @Order(2)
    void thenRemoveUserFromArrangement() throws DataFormatException {
        repository.deleteUserConnectionToData(arrangementToTest, PROTO_USER);
        userArrangements.remove(arrangementToTest);
        assertDataIntegrity(repository.queryAllEntityConnectedToUserData(Arrangement.class, PROTO_USER));
    }


    private static final Class<Group> GroupClazz = Group.class;
    private static Group group = new Group("Test");
    private static List<Group> expectedGroups = repository.queryAllDataOfGivenType(GroupClazz);
    @Test
    @Order(3)
    void firstCreateNewGroup() throws DataFormatException, IllegalDataAccess {
        expectedGroups.add(group);
        repository.insertData(group, PROTO_USER);
        assertDataIntegrity(expectedGroups, repository.queryAllDataOfGivenType(GroupClazz));
        assertEquals(group, repository.queryDataWithID(group.getID(), GroupClazz));
    }

    @Test
    @Order(4)
    void thenAddNewMembers() throws DataFormatException {
        DummyUsers[] users = DummyUsers.values();

        int number = users.length - 1;
        int min = Math.min(4, number);

        group.addMember(users[number]);
        for(int i = 0; i <= min; i++) group.addMember(users[i]);
        executeMutationAndAssertIntegrity();
    }

    @Test
    @Order(5)
    void thenRemoveAddedMembers() throws DataFormatException {
        group.removeMembers(group.getMembers().toArray(DummyUsers[]::new));
        executeMutationAndAssertIntegrity();
    }

    @Test
    @Order(6)
    void andLastRemoveCreatedGroup() throws DataFormatException, IllegalDataAccess {
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
