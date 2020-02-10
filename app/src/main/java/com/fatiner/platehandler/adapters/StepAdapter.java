package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.activities.MainActivity;
import com.fatiner.platehandler.classes.Step;
import com.fatiner.platehandler.fragments.recipe.manage.ManageStepFragment;
import com.fatiner.platehandler.globals.BundleGlobals;
import com.fatiner.platehandler.globals.MainGlobals;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {

    private Context context;
    private ArrayList<Step> steps;
    private boolean isShowing;
    private OnStepListener listener;

    public StepAdapter(Context context, ArrayList<Step> steps, boolean isShowing, OnStepListener listener){
        this.context = context;
        this.steps = steps;
        this.isShowing = isShowing;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StepHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView layout = (CardView) inflater.inflate(
                R.layout.layout_step,
                parent,
                false);
        return new StepHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull StepHolder holder, int position) {
        if(isShowing){
            hideImageRemove(holder);
            hideImgbuttEdit(holder);
            showCbDone(holder);
        }
        Step step = steps.get(position);
        setTextId(holder, position);
        setTextInstruction(holder, step.getInstruction());
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    private void hideImageRemove(StepHolder holder){
        holder.ivRemove.setVisibility(View.INVISIBLE);
    }

    private void hideImgbuttEdit(StepHolder holder){
        holder.ibEdit.setVisibility(View.INVISIBLE);
    }

    private void showCbDone(StepHolder holder){
        holder.cbDone.setVisibility(View.VISIBLE);
    }

    private void setTextId(StepHolder holder, int id){
        int actualId = id + MainGlobals.INT_INCREMENT_VAR_INIT;
        String text = context.getResources().getString(
                R.string.ct_step) + MainGlobals.STR_SPACE_OBJ_INIT + actualId;
        holder.tvId.setText(text);
    }

    private void setTextInstruction(StepHolder holder, String instruction){
        holder.tvInstruction.setText(instruction);
    }

    public class StepHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_id)
        TextView tvId;
        @BindView(R.id.tv_instruction)
        TextView tvInstruction;
        @BindView(R.id.iv_remove)
        ImageView ivRemove;
        @BindView(R.id.ib_edit)
        ImageButton ibEdit;
        @BindView(R.id.cb_done)
        CheckBox cbDone;
        @BindView(R.id.tv_done)
        TextView tvDone;

        @OnClick(R.id.ib_edit)
        public void onClickImgbuttEdit(){
            ManageStepFragment fragment = new ManageStepFragment();
            setBundle(fragment);
            ((MainActivity) context).setFragment(fragment, true);
        }

        @OnClick(R.id.iv_remove)
        public void onClickImageRemove(){
            steps.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            if (listener == null) return;
            listener.onClickRemove();
        }

        @OnCheckedChanged(R.id.cb_done)
        public void onCheckedChangedSwFavorite(boolean checked){
            if(checked) {
                tvId.setAlpha(0.1f);
                tvInstruction.setAlpha(0.1f);
                tvDone.setVisibility(View.VISIBLE);
            } else {
                tvId.setAlpha(1.0f);
                tvInstruction.setAlpha(1.0f);
                tvDone.setVisibility(View.GONE);
            }
        }

        public StepHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setBundle(Fragment fragment){
            Bundle bundle = new Bundle();
            bundle.putInt(BundleGlobals.BUND_ID_FRAG_ADDSTEP, getAdapterPosition());
            fragment.setArguments(bundle);
        }
    }

    public interface OnStepListener {
        void onClickRemove();
    }
}