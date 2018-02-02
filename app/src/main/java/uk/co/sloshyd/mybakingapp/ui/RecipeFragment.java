package uk.co.sloshyd.mybakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import uk.co.sloshyd.mybakingapp.R;
import uk.co.sloshyd.mybakingapp.data.RecipeData;

/**
 * Created by Darren on 26/01/2018.
 */

public class RecipeFragment extends android.support.v4.app.Fragment implements RecipeListRecyclerAdapter.OnItemClickCallback {

    private ArrayList<RecipeData> mRecipes;//data source
    private RecipeListRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private FragmentDataCallback mFragmentDataCallback;
    private boolean mTwoPanes;
    private static final int NUMBER_COLUMNS = 2;
    public static final String TAG = RecipeFragment.class.getSimpleName();


    interface FragmentDataCallback{
        void fragmentData(String data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.recipe_fragment_layout, container, false);

       //check which configuration to use
        if (rootView.findViewById(R.id.recipe_fragment_recipe_list_recycler_view) != null) {
            mTwoPanes = false;
            mRecyclerView = rootView.findViewById(R.id.recipe_fragment_recipe_list_recycler_view);
            mAdapter = new RecipeListRecyclerAdapter(getContext(), mTwoPanes);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        } else{
            mTwoPanes = true;
            mRecyclerView = rootView.findViewById(R.id.recipe_fragment_recipe_grid_recycler_view);
            mAdapter = new RecipeListRecyclerAdapter(getContext(), mTwoPanes);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), NUMBER_COLUMNS));
        }

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnClick(this);


        return rootView;
    }

    public void setFragmentData(ArrayList<RecipeData> fragementData){
        if(mRecipes != null){
            return;
        }
        mRecipes = fragementData;
        if(fragementData != null){
            mAdapter.swapData(mRecipes);
            mAdapter.notifyDataSetChanged();
        }

    }

    /*method called when attached to Activity - check to see if the activity implements our
    communication interface OnItemClickCallback
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFragmentDataCallback = (FragmentDataCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentDataCallback");
        }
    }


    @Override
    public void onItemSelected(String recipeId) {
        mFragmentDataCallback.fragmentData(recipeId);
    }

    public void setFragmentDataCallback(FragmentDataCallback callback){
        mFragmentDataCallback = callback;
    }
}
