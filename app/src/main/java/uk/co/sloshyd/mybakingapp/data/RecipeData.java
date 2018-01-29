package uk.co.sloshyd.mybakingapp.data;

/**
 * Created by Darren on 25/01/2018.
 */

public class RecipeData {

    private static final String TAG = RecipeData.class.getSimpleName();
    private String mRecipeId;
    private String mRecipeName;
    private String mServing;
    private String mImage;

    public RecipeData (String recipeId, String recipeName, String serving, String image){

        mRecipeId = recipeId;
        mRecipeName = recipeName;
        mServing = serving;
        mImage = image;

    }
    public String getmServing() {
        return mServing;
    }

    public void setmServing(String mServing) {
        this.mServing = mServing;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }


    @Override
    public String toString() {
        return "RecipeData{" +
                "mRecipeId='" + mRecipeId + '\'' +
                ", mRecipeName='" + mRecipeName + '\'' +
                ", mServing='" + mServing + '\'' +
                ", mImage='" + mImage + '\'' +
                '}';
    }

    public String getmRecipeId() {
        return mRecipeId;
    }

    public void setmRecipeId(String mRecipeId) {
        this.mRecipeId = mRecipeId;
    }

    public String getmRecipeName() {
        return mRecipeName;
    }

    public void setmRecipeName(String mRecipeName) {
        this.mRecipeName = mRecipeName;
    }


}
