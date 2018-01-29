package uk.co.sloshyd.mybakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import uk.co.sloshyd.mybakingapp.ui.IngredientsFragment;

/**
 * Created by Darren on 25/01/2018.
 */

public class IngredientsData implements Parcelable{
    public static final String TAG = IngredientsData.class.getSimpleName();
    private String mRecipeId;
    private String mQuantity;
    private String mMeasure;
    private String mIngredientName;

    public IngredientsData(String recipeId, String quantity, String measure, String name){

        mRecipeId = recipeId;
        mQuantity = quantity;
        mMeasure = measure;
        mIngredientName = name;

    }

    private IngredientsData(Parcel in){
       mRecipeId =  in.readString();
       mQuantity = in.readString();
       mMeasure = in.readString();
       mIngredientName = in.readString();
    }



    public String getmRecipeId() {
        return mRecipeId;
    }

    public void setmRecipeId(String mRecipeId) {
        this.mRecipeId = mRecipeId;
    }

    public String getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(String mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getmMeasure() {
        return mMeasure;
    }

    public void setmMeasure(String mMeasure) {
        this.mMeasure = mMeasure;
    }

    public String getmIngredientName() {
        return mIngredientName;
    }

    public void setmIngredientName(String mIngredientName) {
        this.mIngredientName = mIngredientName;
    }

    @Override
    public String toString() {
        return "IngredientsData{" +
                "mRecipeId=" + mRecipeId +
                ", mQuantity='" + mQuantity + '\'' +
                ", mMeasure='" + mMeasure + '\'' +
                ", mIngredientName='" + mIngredientName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mRecipeId);
        dest.writeString(mQuantity);
        dest.writeString(mMeasure);
        dest.writeString(mIngredientName);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<IngredientsData> CREATOR
            = new Parcelable.Creator<IngredientsData>() {
        public IngredientsData createFromParcel(Parcel in){
            return new IngredientsData(in);
        }

        public IngredientsData[] newArray(int size) {
            return new IngredientsData[size];
        }
    };

}
