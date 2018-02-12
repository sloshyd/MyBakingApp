package services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;

import providers.RandomRecipeWidgetProvider;
import uk.co.sloshyd.mybakingapp.R;
import uk.co.sloshyd.mybakingapp.Utils;
import uk.co.sloshyd.mybakingapp.data.IngredientsData;
import uk.co.sloshyd.mybakingapp.data.RecipeData;

/**
 * Created by Darren on 03/02/2018.
 */

public class MyBakingService extends IntentService {

    public static final String TAG = MyBakingService.class.getSimpleName();
    public static final String ACTION_GET_APP_DATA = "getAppData";
    public static final String ACTION_UPDATE_WIDGET = "uk.co.sloshyd.mybakingapp.action.update_widget";


    public MyBakingService() {
        super("MyBakingService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String action = intent.getAction();
        if (intent != null) {
            if (ACTION_GET_APP_DATA.equals(action)) {
                handleActionGetAppData();
            } else if (ACTION_UPDATE_WIDGET.equals(action)) {
                handleActionUpdateWidget();
            }
        }
    }

    //returns a string of data
    private void handleActionGetAppData() {

        String response = Utils.getDataFromServer();
        Intent dataResponse = new Intent("dataResponse");
        dataResponse.putExtra("dataResponse", response);
        //send data back to activity via broadcast
        LocalBroadcastManager.getInstance(getApplicationContext())
                .sendBroadcast(dataResponse);

    }

    private void handleActionUpdateWidget() {

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
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RandomRecipeWidgetProvider.class));
        //this line needs to be added to update the ListViewService something has changed
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.random_recipe_with_ingredients_widget_lv_ingredients);
        //Now update all widgets and pass data object
        RandomRecipeWidgetProvider
                .updateRandomRecipeWidgets(this, appWidgetManager,
                        appWidgetIds, selectedRecipe, selectedIngredients);
    }



    public static void startActionGetApplicationData
            (Context context) {

        Intent i = new Intent(context, MyBakingService.class);
        i.setAction(ACTION_GET_APP_DATA);
        context.startService(i);
    }


    public static void startActionUpdateRandomRecipeWidget(Context context) {
        Intent i = new Intent(context, MyBakingService.class);
        i.setAction(ACTION_UPDATE_WIDGET);
        context.startService(i);
    }


}
