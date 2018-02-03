package uk.co.sloshyd.mybakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
    private boolean mTwoPanes;


    public RecipeListRecyclerAdapter (Context context, boolean twoPanes){
        mContext = context;
        mTwoPanes = twoPanes;
    }

    //used for communication with Fragment
    interface OnItemClickCallback {
        void onItemSelected(String recipeId);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view;
        if(mTwoPanes = false){
            view = inflater.inflate(R.layout.recipe_list_item, parent, false);

        } else{
            view =inflater.inflate(R.layout.recipe_list_item, parent, false);
        }
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
        //load image into view with Picasso which will handle any errors
        if(!mData.get(position).getmImage().isEmpty()){
            Uri imageUri = Uri.parse(mData.get(position).getmImage());
            Picasso.with(mContext).load(imageUri)
                    .resize(100, 100).into(holder.recipeImage);

        }else {
            holder.recipeImage.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
            Picasso.with(mContext).load(R.drawable.ic_cake_white_48dp).into(holder.recipeImage);
        }


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
        ImageView recipeImage;


        public ViewHolder(View itemView) {
            super(itemView);

          recipeName = itemView.findViewById(R.id.recipe_list_item_tv_recipe_name);
          serving = itemView.findViewById(R.id.recipe_list_item_tv_number_served);
          recipeImage = itemView.findViewById(R.id.recipe_list_item_iv_picture);
        }


    }
    public void setOnClick(OnItemClickCallback onClick)
    {
        this.mOnItemClickCallback= onClick;
    }
}
