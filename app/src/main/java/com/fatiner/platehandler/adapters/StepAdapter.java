package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.globals.Format;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.models.Step;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class StepAdapter extends PrimaryAdapter<StepAdapter.StepHolder> {

    private List<Step> steps;
    private StepListener listener;

    public StepAdapter(Context context, List<Step> steps, StepListener listener) {
        super(context);
        this.steps = steps;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StepHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StepHolder(getLayout(parent, R.layout.item_step));
    }

    @Override
    public void onBindViewHolder(@NonNull StepHolder holder, int position) {
        manageShowing(holder);
        setStep(holder, position);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    private void manageShowing(StepHolder holder) {
        if (listener == null) {
            hideView(holder.ivRemove);
            hideView(holder.ibEdit);
            showView(holder.cbDone);
        }
    }

    private void setStep(StepHolder holder, int position) {
        Step step = steps.get(position);
        setTv(holder.tvId, getStep(position));
        setTv(holder.tvContent, step.getContent());
    }

    private String getStep(int size) {
        int id = size + Globals.DF_INCREMENT;
        return String.format(Locale.ENGLISH, Format.FM_STEP, getString(R.string.ct_step), id);
    }

    private void checkStepCompletion(StepHolder holder, boolean checked) {
        if(checked) stepCompleted(holder);
        else stepRemains(holder);
    }

    private void stepCompleted(StepHolder holder) {
        setAlpha(holder.tvId, Globals.AP_SMALL);
        setAlpha(holder.tvContent, Globals.AP_SMALL);
        showView(holder.tvDone);
    }

    private void stepRemains(StepHolder holder) {
        setAlpha(holder.tvId, Globals.AP_FULL);
        setAlpha(holder.tvContent, Globals.AP_FULL);
        removeView(holder.tvDone);
    }

    class StepHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_id) TextView tvId;
        @BindView(R.id.tv_content) TextView tvContent;
        @BindView(R.id.tv_done) TextView tvDone;
        @BindView(R.id.iv_remove) ImageView ivRemove;
        @BindView(R.id.ib_edit) ImageButton ibEdit;
        @BindView(R.id.cb_done) CheckBox cbDone;

        @OnClick(R.id.ib_edit)
        void clickIbEdit() {
            listener.editStep(getAdapterPosition());
        }

        @OnClick(R.id.iv_remove)
        void clickIvRemove() {
            listener.removeStep(getAdapterPosition());
        }

        @OnCheckedChanged(R.id.cb_done)
        void checkedCbDone(boolean checked) {
            checkStepCompletion(this, checked);
        }

        private StepHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface StepListener {
        void editStep(int position);
        void removeStep(int position);
    }
}