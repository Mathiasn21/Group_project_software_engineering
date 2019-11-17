package no.hiof.set.gruppe.core.validations;

/*Guide
 * 1. Import Statements
 * 2. Validations Methods
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import no.hiof.set.gruppe.core.interfaces.IBaseEntity;
import no.hiof.set.gruppe.core.interfaces.IRepository;
import no.hiof.set.gruppe.core.repository.Repository;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.Group;
import no.hiof.set.gruppe.core.interfaces.IUser;
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
        boolean res = false;
        if(user == ProtoUser.ORGANIZER && repository.queryAllEntityConnectedToUserData(baseEntity.getClass(), user).contains(baseEntity))res = true;
        else if(user == ProtoUser.USER && baseEntity.getClass() == Group.class)res = true;
        else if (user == ProtoUser.ADMIN)res = true;
        return res;
    }

    public static <T extends IBaseEntity, E extends IUser> boolean ThatUserCanCreateNewBaseEntity(E user, Class<T> tClass) {
        boolean res = false;
        if(user == ProtoUser.ORGANIZER && tClass == Arrangement.class)res = true;
        else if(user == ProtoUser.USER && tClass == Group.class)res = true;
        else if (user == ProtoUser.ADMIN)res = true;
        return res;
    }
}
