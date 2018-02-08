package uk.co.sloshyd.mybakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import uk.co.sloshyd.mybakingapp.R;
import uk.co.sloshyd.mybakingapp.data.IngredientsData;
import uk.co.sloshyd.mybakingapp.data.InstructionsData;

/**
 * Created by Darren on 27/01/2018.
 */

public class InstructionsFragment extends Fragment implements InstructionsRecyclerAdapter.OnItemClickCallback {

    public static final String TAG = InstructionsFragment.class.getSimpleName();
    private InstructionsRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<IngredientsData> mIngredients;
    private FragmentDataCallback mFragmentDataCallback;
    private ArrayList<InstructionsData> mInstructions;
    private boolean mTwoPanes;

    interface FragmentDataCallback {
        void fragmentData(int position, ArrayList<InstructionsData> recipeInstructions);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstructions = getArguments().getParcelableArrayList("instructions");
        mTwoPanes = getArguments().getBoolean("twopanes");

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.instructions_fragment_layout, container, false);
        mRecyclerView = rootView.findViewById(R.id.ingredients_fragment_recycler_view);
        mAdapter = new InstructionsRecyclerAdapter(getContext(), mTwoPanes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickCallback(this);
        mAdapter.swapData(mInstructions);
        if (savedInstanceState != null) {
            mRecyclerView.scrollToPosition(savedInstanceState.getInt("position"));
            mAdapter.setSelectedPosition(savedInstanceState.getInt("position"));
        }

        return rootView;
    }


    //callback from InstructionsRecyclerAdapter
    @Override
    public void onItemSelected(int position) {
        mFragmentDataCallback.fragmentData(position, mInstructions);//pass to DetailActivity

    }

    public void setFragmentDataCallback(FragmentDataCallback callback) {
        mFragmentDataCallback = callback;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFragmentDataCallback = (InstructionsFragment.FragmentDataCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentDataCallback");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", mAdapter.getSelectedPosition());
    }
}
