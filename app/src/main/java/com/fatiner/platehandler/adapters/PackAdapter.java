package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.classes.Pack;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PackAdapter extends RecyclerView.Adapter<PackAdapter.PackHolder> {

    private Context context;
    private ArrayList<Pack> packs;

    public PackAdapter(Context context, ArrayList<Pack> packs){
        this.context = context;
        this.packs = packs;
    }

    @NonNull
    @Override
    public PackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView layout = (CardView) inflater.inflate(
                R.layout.cardview_pack,
                parent,
                false);
        return new PackHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull PackHolder holder, int position) {
        holder.tvName.setText(packs.get(position).getName());
        holder.tvAttribution.setText(packs.get(position).getAttribution());
        holder.tvLicense.setText(packs.get(position).getLicense());
        holder.tvAmount.setText(String.valueOf(packs.get(position).getAmount()));
    }

    @Override
    public int getItemCount() {
        return packs.size();
    }

    public class PackHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_attribution)
        TextView tvAttribution;
        @BindView(R.id.tv_license)
        TextView tvLicense;
        @BindView(R.id.tv_amount)
        TextView tvAmount;

        public PackHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}