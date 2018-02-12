package services;

import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import uk.co.sloshyd.mybakingapp.R;
import uk.co.sloshyd.mybakingapp.Utils;
import uk.co.sloshyd.mybakingapp.data.IngredientsData;
import uk.co.sloshyd.mybakingapp.data.RecipeData;

/**
 * Created by Darren on 05/02/2018.
 */

public class ListWidgetService extends RemoteViewsService {

    private static final String TAG = ListWidgetService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    private class ListRemoteViewsFactory implements RemoteViewsFactory {
        private Context mContext;
        private ArrayList<IngredientsData> mData;

        private int mAppWidgetId;


        public final String TAG = ListRemoteViewsFactory.class.getSimpleName();

        public ListRemoteViewsFactory(Context applicationContext, Intent intent) {
            mContext = applicationContext;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            String response = Utils.getDataFromServer();
            ArrayList<RecipeData> recipeData = Utils.getRecipes(response);
            int select = Utils.getRandomNumber(recipeData.size());
            RecipeData selectedRecipe = recipeData.get(select);

            ArrayList<IngredientsData> ingredientsData = Utils.getIngredients(response);

            ArrayList<IngredientsData> selectedIngredients = new ArrayList<>();
            for (IngredientsData ingredient : ingredientsData) {
                if (ingredient.getmRecipeId().equals(selectedRecipe.getmRecipeId())) {
                    selectedIngredients.add(ingredient);
                }
                mData = selectedIngredients;
            }

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (mData != null) {
                return mData.size();
            } else {
                return 0;
            }

        }

        @Override
        public RemoteViews getViewAt(int position) {

            if (mData == null || mData.size() == 0) {
                return null;
            }

            String quantity = mData.get(position).getmQuantity();
            String measure = mData.get(position).getmMeasure().toLowerCase();
            String name = mData.get(position).getmIngredientName();
            RemoteViews remoteViews
                    = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredients_list_item);
            String quantityAndMeasure = quantity + " " + measure;
            remoteViews.setTextViewText(R.id.ingredient_list_item_tv_quantity, quantityAndMeasure);
            remoteViews.setTextViewText(R.id.ingredient_list_item_tv_ingredient_name, name);

            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

    }




}
