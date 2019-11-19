package no.hiof.set.gruppe.core.entities.constantInformation;

/*Guide
 * 1. Import Statements
 * 2. Constants
 * 3. Contracts
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * This enum represents the different sports categories
 */
public enum SportCategory {
    // --------------------------------------------------//
    //                2.Constants                        //
    // --------------------------------------------------//
    ALL("Alle"),
    OTHER("Annet"),
    BICYCLING("Sykkelritt"),
    SKIING("Skirenn"),
    MARATHON("Maraton"),
    ;
    private final String category;


    /**
     * Excludes ALL
     * @return SportCategory
     */
    public static SportCategory[] getCategories(){
        SportCategory[] categories = SportCategory.values();
        int length = categories.length;
        SportCategory[] res = new SportCategory[length - 1];
        for(int i = 0, j = 0; j < res.length; i++){
            SportCategory category = categories[i];
            if (category == ALL) continue;
            res[j] = category;
            j++;
        }
        return res;
    }
    // --------------------------------------------------//
    //                3.Contracts                        //
    // --------------------------------------------------//
    /**
     * @param category String
     */
    @Contract(pure = true)
    SportCategory(@NotNull String category){
        this.category = category;
    }

    /**
     * @return String
     */
    @Contract(pure = true)
    @Override
    public String toString(){
        return category;
    }
}
