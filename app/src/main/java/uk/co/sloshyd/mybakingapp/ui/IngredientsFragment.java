package uk.co.sloshyd.mybakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import uk.co.sloshyd.mybakingapp.R;
import uk.co.sloshyd.mybakingapp.data.IngredientsData;

/**
 * Fragment that manages the Recipe Ingredients
 */

public class IngredientsFragment extends Fragment {

    public static final String TAG = InstructionsFragment.class.getSimpleName();
    private IngredientsRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<IngredientsData> mIngredients;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIngredients = getArguments().getParcelableArrayList("ingredients");


    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.instructions_fragment_layout, container, false);
        mRecyclerView = rootView.findViewById(R.id.ingredients_fragment_recycler_view);
        mAdapter = new IngredientsRecyclerAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.swapArrayList(mIngredients);

        return rootView;
    }


}
