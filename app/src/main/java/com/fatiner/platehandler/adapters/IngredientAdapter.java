package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fatiner.platehandler.classes.Ingredient;
import com.fatiner.platehandler.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder>{

    private Context context;
    private ArrayList<Ingredient> ingredients;

    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredients){
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(
                R.layout.layout_ingredient,
                parent,
                false);
        return new IngredientHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        setTextAmount(holder, ingredient.getAmount());
        String[] arrayMeasure = getArrayMeasure();
        setTextMeasure(holder, arrayMeasure[ingredient.getMeasure()]);
        setTextProduct(holder, ingredient.getProduct().getName());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    private void setTextAmount(IngredientHolder holder, float amount){
        holder.tvAmount.setText(String.valueOf(amount));
    }

    private String[] getArrayMeasure(){
        return context.getResources().getStringArray(R.array.ar_measure);
    }

    private void setTextMeasure(IngredientHolder holder, String measure){
        holder.tvMeasure.setText(measure);
    }

    private void setTextProduct(IngredientHolder holder, String name){
        holder.tvProduct.setText(name);
    }

    public class IngredientHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_amount)
        TextView tvAmount;
        @BindView(R.id.tv_measure)
        TextView tvMeasure;
        @BindView(R.id.tv_product)
        TextView tvProduct;

        public IngredientHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
