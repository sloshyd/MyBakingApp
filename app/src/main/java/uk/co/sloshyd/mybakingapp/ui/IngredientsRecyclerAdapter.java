package uk.co.sloshyd.mybakingapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import uk.co.sloshyd.mybakingapp.R;
import uk.co.sloshyd.mybakingapp.data.IngredientsData;

/**
 * Created by Darren on 29/01/2018.
 */

public class IngredientsRecyclerAdapter extends RecyclerView.Adapter<IngredientsRecyclerAdapter.ViewHolder> {

    public static final String TAG = IngredientsRecyclerAdapter.class.getSimpleName();
    private ArrayList<IngredientsData> mData;
    private Context mContext;


    public IngredientsRecyclerAdapter(Context context) {
        mContext = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.ingredients_list_item, parent, false);
        return new IngredientsRecyclerAdapter.ViewHolder(view);  }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String quantity = mData.get(position).getmQuantity();
        String measure = mData.get(position).getmMeasure();
        String ingredient = mData.get(position).getmIngredientName();

        holder.amountOfIngredients.setText(quantity);
        holder.measurementType.setText(measure);
        holder.nameOfIngredient.setText(ingredient);

    }


    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView amountOfIngredients;
        private TextView measurementType;
        private TextView nameOfIngredient;

        public ViewHolder(View itemView) {
            super(itemView);

            amountOfIngredients = itemView.findViewById(R.id.ingredient_list_item_tv_quantity);
            measurementType = itemView.findViewById(R.id.ingredient_list_item_tv_measure);
            nameOfIngredient = itemView.findViewById(R.id.ingredient_list_item_tv_ingredient_name);
        }
    }

    public void swapArrayList(ArrayList<IngredientsData> data) {
        mData = data;
        if (mData != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
        ;

    }
}
