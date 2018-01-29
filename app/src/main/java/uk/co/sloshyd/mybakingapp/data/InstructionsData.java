package uk.co.sloshyd.mybakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Darren on 25/01/2018.
 */

public class InstructionsData implements Parcelable{
    public static final String TAG = InstructionsData.class.getSimpleName();
    private String mRecipeId;
    private String mStepNumber;//order in which to do work
    private String mShortDescription;
    private String mDescription;
    private String mVideoUrl;
    private String mThumbnail;

    public InstructionsData(String recipeId, String stepNumber, String shortDescription, String description,
                            String videoUrl, String thumbnail){

        mRecipeId = recipeId;
        mStepNumber = stepNumber;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoUrl = videoUrl;
        mThumbnail = thumbnail;
    }

    private InstructionsData(Parcel in) {
        mRecipeId = in.readString();
        mStepNumber = in.readString();
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoUrl = in.readString();
        mThumbnail = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeString(mRecipeId);
        out.writeString(mStepNumber);
        out.writeString(mShortDescription);
        out.writeString(mDescription);
        out.writeString(mVideoUrl);
        out.writeString(mThumbnail);

    }

    @Override
    public String toString() {
        return "InstructionsData{" +
                "mRecipeId=" + mRecipeId +
                ", mStepNumber=" + mStepNumber +
                ", mShortDescription='" + mShortDescription + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mVideoUrl='" + mVideoUrl + '\'' +
                ", mThumbnail='" + mThumbnail + '\'' +
                '}';
    }

    public String getmRecipeId() {
        return mRecipeId;
    }

    public void setmRecipeId(String mRecipeId) {
        this.mRecipeId = mRecipeId;
    }

    public String getmStepNumber() {
        return mStepNumber;
    }

    public void setmStepNumber(String mStepNumber) {
        this.mStepNumber = mStepNumber;
    }

    public String getmShortDescription() {
        return mShortDescription;
    }

    public void setmShortDescription(String mShortDescription) {
        this.mShortDescription = mShortDescription;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmVideoUrl() {
        return mVideoUrl;
    }

    public void setmVideoUrl(String mVideoUrl) {
        this.mVideoUrl = mVideoUrl;
    }

    public String getmThumbnail() {
        return mThumbnail;
    }

    public void setmThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<InstructionsData> CREATOR
            = new Parcelable.Creator<InstructionsData>() {
        public InstructionsData createFromParcel(Parcel in){
            return new InstructionsData(in);
        }

        public InstructionsData[] newArray(int size) {
            return new InstructionsData[size];
        }
    };

}
