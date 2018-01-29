package uk.co.sloshyd.mybakingapp.data;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.media.DeniedByServerException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import uk.co.sloshyd.mybakingapp.Utils;

/**
 * Created by Darren on 25/01/2018.
 */

public class DataLoader extends AsyncTaskLoader<String> {

    Context mContext;
    String mCache;
    public DataLoader(Context context){
        super(context);
        mContext = context;

    }

    @Override
    protected void onStartLoading() {
        //if we have any data stored return it else start loader
        if (mCache != null) {
            deliverResult(mCache);
        } else {
            forceLoad();
        }
    }

    @Override
    public String loadInBackground() {
        String response = null;
        try {
            URL dataUrl = Utils.getDataURL(Utils.DATA_URL);
            response = Utils.getResponseFromHttpUrl(dataUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (DeniedByServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    @Override
    public void deliverResult(String data) {
        mCache = data;//set result to cache so if run again the cached data will be used
        //this method is on the main thread so should not do heavy processing here
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        mCache = null;
    }
}
