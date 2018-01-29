package uk.co.sloshyd.mybakingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

import uk.co.sloshyd.mybakingapp.R;
import uk.co.sloshyd.mybakingapp.data.IngredientsData;
import uk.co.sloshyd.mybakingapp.data.InstructionsData;

public class IngredientsActivity extends AppCompatActivity implements IngredientsFragment.FragmentDataCallback {

    private ArrayList<InstructionsData> mInstructions;
    private IngredientsFragment mIngredientsFragment;
    private ArrayList<IngredientsData> mIngredients;

    private static final String TAG = IngredientsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        //get information from Calling Activity
        mIngredientsFragment = new IngredientsFragment();
        mInstructions =  getIntent().getParcelableArrayListExtra("instructions");
        mIngredients  = getIntent().getParcelableArrayListExtra("ingredients");
        FragmentManager fm = getSupportFragmentManager();


        fm.beginTransaction()
                .add(R.id.ingredients_fragment_container,mIngredientsFragment,null)
                .commit();

        //pass information to fragment
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("instructions", mInstructions);
        bundle.putParcelableArrayList("ingredients", mIngredients);
        mIngredientsFragment.setArguments(bundle);

    }







    //callback from IngredientsFragment
    @Override
    public void fragmentData(InstructionsData data) {

    }


}
