package no.hiof.set.gruppe.core.validations;

/*Guide
 * 1. Import Statements
 * 2. Validations Methods
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import no.hiof.set.gruppe.core.repository.IBaseEntity;
import no.hiof.set.gruppe.core.repository.IRepository;
import no.hiof.set.gruppe.core.repository.Repository;
import no.hiof.set.gruppe.model.user.IUser;
import no.hiof.set.gruppe.model.user.ProtoUser;

/**
 * A class containing methods for testing user
 * access rights on given object.
 */
public final class AccessValidate {

    private static final IRepository repository = new Repository();
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
