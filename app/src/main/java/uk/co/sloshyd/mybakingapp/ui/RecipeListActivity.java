package uk.co.sloshyd.mybakingapp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import services.MyBakingService;
import uk.co.sloshyd.mybakingapp.R;
import uk.co.sloshyd.mybakingapp.Utils;
import uk.co.sloshyd.mybakingapp.data.DataLoader;
import uk.co.sloshyd.mybakingapp.data.IngredientsData;
import uk.co.sloshyd.mybakingapp.data.InstructionsData;
import uk.co.sloshyd.mybakingapp.data.RecipeData;

public class RecipeListActivity extends AppCompatActivity implements RecipeFragment.FragmentDataCallback {

    private static final String TAG = RecipeListActivity.class.getSimpleName();

    private ArrayList<RecipeData> mRecipes;
    private ArrayList<IngredientsData> mIngredients;
    private ArrayList<InstructionsData> mInstructions;
    private RecipeFragment mRecipeFragment;
    private BroadcastReceiver mBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        //starts the service to get the data
        MyBakingService.startActionGetApplicationData(this);

        mRecipeFragment = new RecipeFragment();
        mRecipeFragment.setFragmentDataCallback(this);


        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //do here with response
                String response = intent.getStringExtra("dataResponse");
                mRecipes = Utils.getRecipes(response);
                mIngredients = Utils.getIngredients(response);
                mInstructions = Utils.getInstructions(response);

                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .add(R.id.master_list_fragment_container, mRecipeFragment)
                        .commit();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("recipes",mRecipes);
                mRecipeFragment.setArguments(bundle);

            }

        };

    }

    @Override
    public void fragmentData(String data) {

        Intent intent = new Intent (this, DetailsActivity.class);
        ArrayList<InstructionsData> instructionToSend = getInstructionsToSend(data);
        intent.putExtra("instructions",instructionToSend);
        intent.putExtra("ingredients", getIngredientsToSend(data));
        startActivity(intent);
    }

    //returns only instructions that are from the selected recipe
    private ArrayList<InstructionsData> getInstructionsToSend(String data) {
        ArrayList<InstructionsData> tempInstructions = new ArrayList<>();
        for (InstructionsData instructionsData : mInstructions){
            if(data.equalsIgnoreCase(instructionsData.getmRecipeId())){
                tempInstructions.add(instructionsData);
            }
        }
        return tempInstructions;
    }
    private ArrayList<IngredientsData> getIngredientsToSend(String data) {
        ArrayList<IngredientsData> tempIngredients = new ArrayList<>();
        for (IngredientsData ingredientsData : mIngredients){
            if(data.equalsIgnoreCase(ingredientsData.getmRecipeId())){
                tempIngredients.add(ingredientsData);
            }
        }
        return tempIngredients;

    }
    //register the Listener
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mBroadcastReceiver, new IntentFilter("dataResponse"));
    }

    //in on pause remove the broacastListener
    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }
}



