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
import uk.co.sloshyd.mybakingapp.data.InstructionsData;

/**
 * Created by Darren on 27/01/2018.
 */

public class InstructionsRecyclerAdapter extends RecyclerView.Adapter<InstructionsRecyclerAdapter.ViewHolder> {

    public static final String TAG = InstructionsRecyclerAdapter.class.getSimpleName();

    private ArrayList<InstructionsData>mData;
    private OnItemClickCallback onItemClickCallback;
    private Context mContext;
    private int mSelectedPosition;
    private static final int SELECTED_ITEM = 1;
    private static final int UNSELECTED_ITEM =0;

    public InstructionsRecyclerAdapter (Context context){
        mContext = context;
    }

    //used for communication with Fragment
    interface OnItemClickCallback {
        void onItemSelected(int position);
    }

    @Override
    public int getItemViewType(int position) {
        if(mSelectedPosition == position){
            return SELECTED_ITEM;
        }else {
            return UNSELECTED_ITEM;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view;
        if(viewType == SELECTED_ITEM){
            view = inflater.inflate(R.layout.instructions_list_item, parent, false);
        } else{
            view = inflater.inflate(R.layout.instructions_list_item, parent, false);
            view.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        }


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String recipeId = mData.get(position).getmRecipeId();
        final String stepNumber = mData.get(position).getmStepNumber();
        holder.stepNumber.setText(stepNumber);
        String description = mData.get(position).getmShortDescription();
        holder.shortInstruction.setText(description);


        //set onClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mSelectedPosition = holder.getAdapterPosition();//select the new position
                notifyDataSetChanged();// notify that data has changed and redraw
                onItemClickCallback.onItemSelected(position);//return the position in arraylist

            }
        });

    }


    @Override
    public int getItemCount() {
        if(mData != null){
            return mData.size();
        }else {
            return 0;
        }
    }

    public void swapData(ArrayList<InstructionsData> data){
        mData = data;
        if (mData != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        };
    }

    //used to get the selected position so it can be stored in fragment
    public int getSelectedPosition(){
        return mSelectedPosition;
    }

    //needed to return the last selected position before state change
    public void setSelectedPosition(int position){
        mSelectedPosition = position;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView stepNumber;
        private TextView shortInstruction;


        public ViewHolder(View itemView) {
            super(itemView);
            stepNumber = itemView.findViewById(R.id.instructions_list_item_tv_step_number);
            shortInstruction = itemView.findViewById(R.id.instructions_list_item_tv_short_description);
        }
    }

    public void setOnItemClickCallback(OnItemClickCallback callback){
        onItemClickCallback = callback;
    }
}


