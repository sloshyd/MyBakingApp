package uk.co.sloshyd.mybakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import uk.co.sloshyd.mybakingapp.R;
import uk.co.sloshyd.mybakingapp.data.IngredientsData;
import uk.co.sloshyd.mybakingapp.data.InstructionsData;

/**
 * Created by Darren on 27/01/2018.
 */

public class IngredientsFragment extends Fragment implements InstructionsRecyclerAdapter.OnItemClickCallback
{

    public static final String TAG = IngredientsFragment.class.getSimpleName();
    private InstructionsRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<IngredientsData> mIngredients;
    private FragmentDataCallback mFragmentDataCallback;
    private ArrayList<InstructionsData> mInstructions;

    interface FragmentDataCallback{
        void fragmentData(InstructionsData data);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstructions = getArguments().getParcelableArrayList("instructions");
        mIngredients = getArguments().getParcelableArrayList("ingredients");

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.ingredients_fragment_layout, container, false);
        mRecyclerView = rootView.findViewById(R.id.ingredients_fragment_recycler_view);
        mAdapter = new InstructionsRecyclerAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickCallback(this);
        mAdapter.swapData(mInstructions);

        //set up ingredients list
        TextView ingredientsList_tv = rootView.findViewById(R.id.ingredients_fragment_tv_ingredients_list);
        ingredientsList_tv.setText(generateIngredients());

        return rootView;
    }

    private String generateIngredients() {
        StringBuilder builder = new StringBuilder();
        for(IngredientsData ingredientsData : mIngredients){
            builder.append(ingredientsData.getmQuantity() + " ")
                    .append(ingredientsData.getmMeasure() + " ")
                    .append(ingredientsData.getmIngredientName() + "\n");
        }
        return builder.toString();
    }

    //callback from InstructionsRecyclerAdapter
    @Override
    public void onItemSelected(InstructionsData instructionsData) {
       //pass back to Activity

    }

    public void setFragmentDataCallback(FragmentDataCallback callback){
        mFragmentDataCallback = callback;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFragmentDataCallback = (IngredientsFragment.FragmentDataCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentDataCallback");
        }
    }
}
