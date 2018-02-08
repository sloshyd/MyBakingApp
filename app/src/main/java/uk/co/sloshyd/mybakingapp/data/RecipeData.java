package uk.co.sloshyd.mybakingapp.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Darren on 25/01/2018.
 */

public class RecipeData  implements Parcelable {

    private static final String TAG = RecipeData.class.getSimpleName();
    private String mRecipeId;
    private String mRecipeName;
    private String mServing;
    private String mImage;

    public RecipeData(Parcel in){
        mRecipeId = in.readString();
        mRecipeName = in.readString();
        mImage = in.readString();
        mServing = in.readString();
    }
    public RecipeData(String recipeId, String recipeName, String serving, String image){

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mRecipeId);
        dest.writeString(mRecipeName);
        dest.writeString(mServing);
        dest.writeString(mImage);

    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<RecipeData> CREATOR
            = new Parcelable.Creator<RecipeData>() {
        public RecipeData createFromParcel(Parcel in){
            return new RecipeData(in);
        }

        public RecipeData[] newArray(int size) {
            return new RecipeData[size];
        }
    };
}
