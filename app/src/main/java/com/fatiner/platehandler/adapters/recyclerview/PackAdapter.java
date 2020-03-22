package com.fatiner.platehandler.adapters.recyclerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.classes.Pack;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PackAdapter extends PrimaryAdapter<PackAdapter.PackHolder> {
    
    private ArrayList<Pack> packs;

    public PackAdapter(Context context, ArrayList<Pack> packs) {
        super(context);
        this.packs = packs;
    }

    @NonNull
    @Override
    public PackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PackHolder(getLayout(parent, R.layout.item_pack));
    }

    @Override
    public void onBindViewHolder(@NonNull PackHolder holder, int position) {
        setViews(holder, position);
    }

    @Override
    public int getItemCount() {
        return packs.size();
    }

    private void setViews(PackHolder holder, int position) {
        Pack pack = packs.get(position);
        setTv(holder.tvName, pack.getName());
        setTv(holder.tvAttribution, pack.getAttribution());
        setTv(holder.tvLicense, pack.getLicense());
        setTv(holder.tvAmount, pack.getAmount());
    }

    static class PackHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.tv_attribution) TextView tvAttribution;
        @BindView(R.id.tv_license) TextView tvLicense;
        @BindView(R.id.tv_amount) TextView tvAmount;

        private PackHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}