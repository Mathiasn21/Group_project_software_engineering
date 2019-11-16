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
    /**
     * @param arrangement {@link Arrangement}
     * @param protoUser {@link ProtoUser}
     * @return boolean
     */
    @Contract(pure = true)
    public static boolean userCanModifyArrangement(@NotNull Arrangement arrangement, @NotNull ProtoUser protoUser){
        return (protoUser == ProtoUser.ORGANIZER && repository.queryAllUserRelatedArrangements(protoUser).contains(arrangement)) || protoUser == ProtoUser.ADMIN;
    }

    /**
     * @param protoUser {@link ProtoUser}
     * @return boolean
     */
    @Contract(pure = true)
    public static boolean userCanCreateArrangement(@NotNull ProtoUser protoUser){
        return protoUser == ProtoUser.ORGANIZER || protoUser == ProtoUser.ADMIN;
    }

    public static <T extends IBaseEntity> boolean ThatUserCanModifyBaseEntity(T thatBaseEntity, ProtoUser user) {
        return true;
    }
}
