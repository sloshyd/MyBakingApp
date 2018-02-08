package uk.co.sloshyd.mybakingapp.ui;

import android.os.Bundle;;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import uk.co.sloshyd.mybakingapp.R;
import uk.co.sloshyd.mybakingapp.data.IngredientsData;
import uk.co.sloshyd.mybakingapp.data.InstructionsData;

public class DetailsActivity extends AppCompatActivity implements InstructionsFragment.FragmentDataCallback {

    private ArrayList<InstructionsData> mInstructions;

    private ArrayList<IngredientsData> mIngredients;
    private FragmentManager mFragmentManager;
    private int START_AT_BEGINNING = 0;
    private boolean mTwoPane;
    private int mPosition;
    private FragmentTabHost mTabHost;



    private static final String TAG = DetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //find out if running on a tablet
        mFragmentManager = getSupportFragmentManager();
        mInstructions = getIntent().getParcelableArrayListExtra("instructions");
        mIngredients = getIntent().getParcelableArrayListExtra("ingredients");


        //define what layout to use
        if (findViewById(R.id.detail_instruction_fragment_container) != null) {
            mTwoPane = true;
            } else {
            mTwoPane = false;
        }

        if (savedInstanceState == null) {
            mPosition = START_AT_BEGINNING;
            if (mTwoPane) {
                setUpInstructionsDetailFragment(mPosition, mInstructions);
                setUpIngredientsAndInstructionsFragment();
            } else {
                setUpIngredientsAndInstructionsFragment();
            }

        }else{
            mPosition = savedInstanceState.getInt("position");

            }

    }

    private void setUpIngredientsAndInstructionsFragment() {

        Bundle instructionsBundle = new Bundle();
        instructionsBundle.putParcelableArrayList("instructions", mInstructions);
        instructionsBundle.putBoolean("twopanes", mTwoPane);

        Bundle ingredientsBundle = new Bundle();
        ingredientsBundle.putParcelableArrayList("ingredients", mIngredients);
        ingredientsBundle.putBoolean("twopanes", mTwoPane);

        mTabHost = findViewById(R.id.tab_instructions_and_ingredients);
        mTabHost.setup(this, mFragmentManager, R.id.tab_instructions_and_ingredients);
        mTabHost.addTab(mTabHost.newTabSpec("instructions")
                        .setIndicator(getResources()
                                .getString(R.string.tab_label_instruction)),
                InstructionsFragment.class, instructionsBundle);
        mTabHost.addTab(mTabHost.newTabSpec("ingredients")
                        .setIndicator(getResources()
                                .getString(R.string.tab_label_ingredients)),
                IngredientsFragment.class, ingredientsBundle);

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
                    .replace(R.id.detail_instruction_fragment_container, detailInstructionFragment)
                    .commit();
            Bundle bundleInstruction = new Bundle();
            bundleInstruction.putParcelableArrayList("instruction", recipeInstructions);
            bundleInstruction.putInt("position", position);
            bundleInstruction.putBoolean("twopanes", mTwoPane);
            detailInstructionFragment.setArguments(bundleInstruction);

        } else {

            //mTabHost.removeAllViews();//remove labels which can be seen

            mFragmentManager.beginTransaction()
                    .replace(R.id.tab_instructions_and_ingredients, detailInstructionFragment)
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTabHost = null;//remember to destroy

    }
}
