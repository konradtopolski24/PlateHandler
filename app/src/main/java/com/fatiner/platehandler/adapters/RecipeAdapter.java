package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.models.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeAdapter extends PrimaryAdapter<RecipeAdapter.RecipeHolder> {

    private List<Recipe> recipes;
    private RecipeListener listener;

    public RecipeAdapter(Context context, List<Recipe> recipes, RecipeListener listener) {
        super(context);
        this.recipes = recipes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeHolder(getLayout(parent, R.layout.item_recipe));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        setTv(holder.tvName, recipe.getName());
        setIv(holder.ivCountry, recipe.getCountry(), R.array.dw_country);
        setIv(holder.ivType, recipe.getType(), R.array.dw_recipe);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    private int getId(int position) {
        return recipes.get(position).getId();
    }

    class RecipeHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.iv_country) ImageView ivCountry;
        @BindView(R.id.iv_type) ImageView ivType;

        @OnClick(R.id.cl_recipe)
        void clickClRecipe() {
            listener.clickRecipe(getId(getAdapterPosition()));
        }

        private RecipeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface RecipeListener {
        void clickRecipe(int id);
    }
}
