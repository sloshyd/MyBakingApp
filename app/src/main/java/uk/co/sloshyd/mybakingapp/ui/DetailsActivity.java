package uk.co.sloshyd.mybakingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.ArrayList;

import uk.co.sloshyd.mybakingapp.R;
import uk.co.sloshyd.mybakingapp.data.IngredientsData;
import uk.co.sloshyd.mybakingapp.data.InstructionsData;

public class DetailsActivity extends AppCompatActivity implements InstructionsFragment.FragmentDataCallback {

    private ArrayList<InstructionsData> mInstructions;
    private InstructionAndIngredients mInstructionAndIngredients;
    private ArrayList<IngredientsData> mIngredients;
    private FragmentManager mFragmentManager;
    private int START_AT_BEGINING = 0;
    private boolean mTwoPane;
    private int mPosition;

    private static final String TAG = DetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if(savedInstanceState == null){
            mPosition = START_AT_BEGINING;
        } else{
            mPosition = savedInstanceState.getInt("position");
            Log.i(TAG, "POSITION  **************  " + mPosition);
        }

        mInstructions = getIntent().getParcelableArrayListExtra("instructions");
        mIngredients = getIntent().getParcelableArrayListExtra("ingredients");
        mFragmentManager = getSupportFragmentManager();

        //get information from Calling Activity
        if (findViewById(R.id.detail_instruction_fragment_container) != null) {
            mTwoPane = true;
            setUpInstructionsDetailFragment(mPosition, mInstructions);
            setUpIngredientsAndInstructionsFragment();
        } else {
            mTwoPane = false;
            setUpIngredientsAndInstructionsFragment();

        }
    }

    private void setUpIngredientsAndInstructionsFragment() {
        mInstructionAndIngredients = new InstructionAndIngredients();
        mFragmentManager.beginTransaction()
                .add(R.id.ingredients_fragment_container, mInstructionAndIngredients, null)
                .commit();

        //pass information to fragment
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("instructions", mInstructions);
        bundle.putParcelableArrayList("ingredients", mIngredients);
        mInstructionAndIngredients.setArguments(bundle);
    }

    private void setUpInstructionsDetailFragment(int position, ArrayList<InstructionsData> data) {
        DetailInstructionFragment detailInstructionFragment = new DetailInstructionFragment();
        mFragmentManager.beginTransaction()
                .add(R.id.detail_instruction_fragment_container, detailInstructionFragment)
                .commit();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("instruction", data);
        bundle.putInt("position", position);
        bundle.putBoolean("twopanes", mTwoPane);
        detailInstructionFragment.setArguments(bundle);
    }

    //callback from InstructionsFragment when data returned open new fragment with data object
    @Override
    public void fragmentData(int position, ArrayList<InstructionsData> recipeInstructions) {
        DetailInstructionFragment detailInstructionFragment = new DetailInstructionFragment();

        if (mTwoPane) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.ingredients_fragment_container, mInstructionAndIngredients, null)
                    .commit();
            mPosition = position;
            //pass information to fragment
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("instructions", mInstructions);
            bundle.putParcelableArrayList("ingredients", mIngredients);
            mInstructionAndIngredients.setArguments(bundle);
            mFragmentManager.beginTransaction()
                    .replace(R.id.detail_instruction_fragment_container, detailInstructionFragment)
                    .commit();
            Bundle bundleInstruction = new Bundle();
            bundleInstruction.putParcelableArrayList("instruction", recipeInstructions);
            bundleInstruction.putInt("position", position);
            bundleInstruction.putBoolean("twopanes", mTwoPane);
            detailInstructionFragment.setArguments(bundleInstruction);

        } else {

            mFragmentManager.beginTransaction()
                    .replace(R.id.ingredients_fragment_container, detailInstructionFragment)
                    .addToBackStack(null)//if added then replaced fragment is not destroyed just paused allowing back navigation
                    .commit();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("instruction", recipeInstructions);
            bundle.putInt("position", position);
            bundle.putBoolean("twopanes", mTwoPane);
            detailInstructionFragment.setArguments(bundle);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", mPosition);
    }
}
