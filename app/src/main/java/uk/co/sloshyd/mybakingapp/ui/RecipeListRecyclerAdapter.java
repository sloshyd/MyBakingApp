package uk.co.sloshyd.mybakingapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import uk.co.sloshyd.mybakingapp.R;
import uk.co.sloshyd.mybakingapp.data.RecipeData;

/**
 * Created by Darren on 25/01/2018.
 */

public class RecipeListRecyclerAdapter extends RecyclerView.Adapter<RecipeListRecyclerAdapter.ViewHolder> {

    private ArrayList<RecipeData> mData;
    private Context mContext;
    public static final String TAG = RecipeListRecyclerAdapter.class.getSimpleName();
    public OnItemClickCallback mOnItemClickCallback;

    public RecipeListRecyclerAdapter (Context context){
        mContext = context;

    }

    //used for communication with Fragment
    interface OnItemClickCallback {
        void onItemSelected(String recipeId);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recipe_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String recipeId = mData.get(position).getmRecipeId();
        String name = mData.get(position).getmRecipeName();
        holder.recipeName.setText(name);
        String serve = mData.get(position).getmServing();
        holder.serving.setText(serve);
        holder.recipeName.setTag(recipeId);//used to pass recipeId which is needed for ingredients and steps

        //set onClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickCallback.onItemSelected(recipeId);

            }
        });

    }



    @Override
    public int getItemCount() {
        if(mData != null){
            return mData.size();
        } else {
            return 0;
        }
    }

    public void swapData(ArrayList<RecipeData> data){

        mData = data;
        if (mData != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView recipeName;
        TextView serving;


        public ViewHolder(View itemView) {
            super(itemView);

          recipeName = itemView.findViewById(R.id.recipe_list_item_tv_recipe_name);
          serving = itemView.findViewById(R.id.recipe_list_item_tv_number_served);
        }


    }
    public void setOnClick(OnItemClickCallback onClick)
    {
        this.mOnItemClickCallback= onClick;
    }
}
