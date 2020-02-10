package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.classes.Ingredient;
import com.fatiner.platehandler.globals.MainGlobals;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder>{

    private Context context;
    private ArrayList<Ingredient> ingredients;
    private boolean isShowing;

    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredients, boolean isShowing){
        this.context = context;
        this.ingredients = ingredients;
        this.isShowing = isShowing;
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView layout = (CardView) inflater.inflate(
                R.layout.layout_ingredient,
                parent,
                false);
        return new IngredientHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {
        if(isShowing)
            showCbDone(holder);
        Ingredient ingredient = ingredients.get(position);
        String[] arrayMeasure = getArrayMeasure();
        setTextMeasure(holder, ingredient.getAmount() + MainGlobals.STR_SPACE_OBJ_INIT
                + MainGlobals.STR_TIMES + MainGlobals.STR_SPACE_OBJ_INIT + arrayMeasure[ingredient.getMeasure()]);
        setTextProduct(holder, ingredient.getProduct().getName());
        TypedArray arrayType = context.getResources().obtainTypedArray(R.array.dw_product);
        int resource = arrayType.getResourceId(ingredient.getProduct().getType(), -1);
        holder.ivIcon.setImageResource(resource);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    private String[] getArrayMeasure(){
        return context.getResources().getStringArray(R.array.tx_measure);
    }

    private void setTextMeasure(IngredientHolder holder, String measure){
        holder.tvMeasure.setText(measure);
    }

    private void setTextProduct(IngredientHolder holder, String name){
        holder.tvProduct.setText(name);
    }

    private void showCbDone(IngredientHolder holder){
        holder.cbDone.setVisibility(View.VISIBLE);
    }

    public class IngredientHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_measure)
        TextView tvMeasure;
        @BindView(R.id.tv_product)
        TextView tvProduct;
        @BindView(R.id.tv_used)
        TextView tvUsed;
        @BindView(R.id.iv_measure)
        ImageView ivMeasure;
        @BindView(R.id.cb_done)
        CheckBox cbDone;
        @BindView(R.id.iv_icon)
        ImageView ivIcon;

        public IngredientHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnCheckedChanged(R.id.cb_done)
        public void onCheckedChangedSwFavorite(boolean checked){
            if(checked) {
                ivMeasure.setAlpha(0.1f);
                tvMeasure.setAlpha(0.1f);
                tvProduct.setAlpha(0.1f);
                tvUsed.setVisibility(View.VISIBLE);
            } else {
                ivMeasure.setAlpha(1.0f);
                tvMeasure.setAlpha(1.0f);
                tvProduct.setAlpha(1.0f);
                tvUsed.setVisibility(View.GONE);
            }
        }
    }
}
