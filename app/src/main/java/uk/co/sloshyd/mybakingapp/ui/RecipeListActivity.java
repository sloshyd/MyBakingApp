package uk.co.sloshyd.mybakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import uk.co.sloshyd.mybakingapp.R;
import uk.co.sloshyd.mybakingapp.Utils;
import uk.co.sloshyd.mybakingapp.data.DataLoader;
import uk.co.sloshyd.mybakingapp.data.IngredientsData;
import uk.co.sloshyd.mybakingapp.data.InstructionsData;
import uk.co.sloshyd.mybakingapp.data.RecipeData;

public class RecipeListActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<String>, MasterFragment.FragmentDataCallback {

    private static final String TAG = RecipeListActivity.class.getSimpleName();
    private static final int DATA_LOADER_ID = 100;
    private ArrayList<RecipeData> mRecipes;
    private ArrayList<IngredientsData> mIngredients;
    private ArrayList<InstructionsData> mInstructions;
    private MasterFragment mMasterFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        getLoaderManager().initLoader(DATA_LOADER_ID, null, this);

         mMasterFragment= new MasterFragment();
         mMasterFragment.setFragmentDataCallback(this);


        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.master_list_fragment_container,mMasterFragment)
                .commit();

    }


    @Override
    public android.content.Loader<String> onCreateLoader(int id, Bundle args) {
        return new DataLoader(this);
    }

    @Override
    public void onLoadFinished(android.content.Loader<String> loader, String data) {
        mRecipes = Utils.getRecipes(data);

        //TODO - REMOVE THIS
        for (RecipeData dataum : mRecipes) {
            Log.i(TAG, "RECIPES " + dataum.toString());
        }
        mIngredients = Utils.getIngredients(data);
        for (IngredientsData datum1 : mIngredients) {
            Log.i(TAG, "INGREDIENTS " + datum1.toString());
        }
        mInstructions = Utils.getInstructions(data);
        Log.i(TAG, "SIZE "+ mInstructions.size());

        for (InstructionsData datum2 : mInstructions) {
            Log.i(TAG, "INSTRUCTIONS " + datum2.toString());
        }

        mMasterFragment.setFragmentData(mRecipes);

    }

    @Override
    public void onLoaderReset(android.content.Loader<String> loader) {

    }


    @Override
    public void fragmentData(String data) {

        Intent intent = new Intent (this, IngredientsActivity.class);
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
}
