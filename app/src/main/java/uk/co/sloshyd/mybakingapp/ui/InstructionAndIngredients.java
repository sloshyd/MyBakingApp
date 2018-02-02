package uk.co.sloshyd.mybakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import uk.co.sloshyd.mybakingapp.R;
import uk.co.sloshyd.mybakingapp.data.IngredientsData;
import uk.co.sloshyd.mybakingapp.data.InstructionsData;

/**
 * Created by Darren on 29/01/2018.
 */

public class InstructionAndIngredients extends Fragment {
    private FragmentTabHost mTabHost;

    private ArrayList<InstructionsData> mInstructions;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ArrayList<InstructionsData> instructions = getArguments().getParcelableArrayList("instructions");
        ArrayList<IngredientsData> ingredients = getArguments().getParcelableArrayList("ingredients");
        Bundle instructionsBundle = new Bundle();
        instructionsBundle.putParcelableArrayList("instructions",instructions);

        Bundle ingredientsBundle = new Bundle();
        ingredientsBundle.putParcelableArrayList("ingredients",ingredients);

        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tab_instructions_and_indregients);


        mTabHost.addTab(mTabHost.newTabSpec("instructions").setIndicator(getResources().getString(R.string.tab_label_instruction)),
                InstructionsFragment.class, instructionsBundle);
        mTabHost.addTab(mTabHost.newTabSpec("ingredients").setIndicator(getResources().getString(R.string.tab_label_ingredients)),
                IngredientsFragment.class, ingredientsBundle);
        return mTabHost;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;//remember to destroy
    }
}
