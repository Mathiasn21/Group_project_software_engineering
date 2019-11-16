package no.hiof.set.gruppe.core.validations;

/*Guide
 * 1. Import Statements
 * 2. Validations Methods
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import no.hiof.set.gruppe.core.IBaseEntity;
import no.hiof.set.gruppe.core.Repository;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.user.IUser;
import no.hiof.set.gruppe.model.user.ProtoUser;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A class containing methods for testing user
 * access rights on given object.
 */
public final class AccessValidate {

    private static final Repository repository = new Repository();
    // --------------------------------------------------//
    //                2.Validations Methods              //
    // --------------------------------------------------//
    public static <T extends IBaseEntity, E extends IUser> boolean ThatUserCanModifyBaseEntity(T baseEntity, E user) {
        return (user == ProtoUser.ORGANIZER && repository.queryAllEntityConnectedToUserData(baseEntity.getClass(), user).contains(baseEntity))
                || user == ProtoUser.ADMIN;
    }

    public static <E extends IUser> boolean ThatUserCanCreateNewBaseEntity(E user) {
        return user == ProtoUser.ORGANIZER || user == ProtoUser.ADMIN;
    }
}
