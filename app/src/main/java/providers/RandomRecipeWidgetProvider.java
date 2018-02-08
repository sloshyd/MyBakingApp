package providers;

import android.annotation.TargetApi;
import android.app.LauncherActivity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import java.util.ArrayList;

import services.ListWidgetService;
import services.MyBakingService;
import uk.co.sloshyd.mybakingapp.R;
import uk.co.sloshyd.mybakingapp.data.IngredientsData;
import uk.co.sloshyd.mybakingapp.data.RecipeData;

/**
 * Implementation of App Widget functionality.
 */
public class RandomRecipeWidgetProvider extends AppWidgetProvider {

    public static final String TAG = RandomRecipeWidgetProvider.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, RecipeData recipeData, ArrayList<IngredientsData> ingredientsData) {

        Bundle appInfo = appWidgetManager.getAppWidgetOptions(appWidgetId);
        RemoteViews remoteViews = getRandomRecipeViewWithIngredients(context, recipeData, ingredientsData);

        //important to actually do the update
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }



    private static RemoteViews getRandomRecipeViewWithIngredients
                        (Context context, RecipeData recipeData, ArrayList<IngredientsData> ingredientsData){

        RemoteViews remoteViews;
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.random_recipe_with_ingredients_widget);
        String recipeName = recipeData.getmRecipeName();
        remoteViews.setImageViewResource(R.id.widget_with_ingredients_next_iv, R.drawable.ic_navigate_next_black_24dp);
        Intent refreshIntent = new Intent(context, MyBakingService.class);
        refreshIntent.setAction(MyBakingService.ACTION_UPDATE_WIDGET);
        PendingIntent refreshPendingIntent = PendingIntent.getService(context, 0, refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_with_ingredients_next_iv, refreshPendingIntent);
        remoteViews.setTextViewText(R.id.widget_todays_recipe_tv_name, recipeName);
        Intent intent = new Intent(context, ListWidgetService.class);
        remoteViews.setRemoteAdapter(R.id.random_recipe_with_ingredients_widget_lv_ingredients, intent);

        return remoteViews;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            MyBakingService.startActionUpdateRandomRecipeWidget(context);
        }
    }

    public static void updateRandomRecipeWidgets(Context context, AppWidgetManager appWidgetManager,
                                                 int[] appWidgetIds, RecipeData data, ArrayList<IngredientsData> ingredientsData) {



        //cycle through any widgets (as can have multiple instances)
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, data, ingredientsData);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        MyBakingService.startActionUpdateRandomRecipeWidget(context);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

}

