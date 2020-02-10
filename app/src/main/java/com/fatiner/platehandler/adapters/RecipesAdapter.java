package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fatiner.platehandler.details.ShoppingListDetails;
import com.fatiner.platehandler.globals.BundleGlobals;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.R;
import com.fatiner.platehandler.classes.Recipe;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.managers.database.DbOperations;
import com.fatiner.platehandler.activities.MainActivity;
import com.fatiner.platehandler.fragments.recipe.ShowRecipeFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesHolder> {

    private Context context;
    private ArrayList<Recipe> recipes;
    private boolean isSelecting;

    public RecipesAdapter(Context context, ArrayList<Recipe> recipes, boolean isSelecting){
        this.context = context;
        this.recipes = recipes;
        this.isSelecting = isSelecting;
    }

    @NonNull
    @Override
    public RecipesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView cardView = (CardView) inflater.inflate(
                R.layout.cardview_recipe,
                parent,
                false);
        return new RecipesHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        setTextName(holder, recipe.getName());
        setImageCountry(holder, recipe.getCountry());
        setImageType(holder, recipe.getType());
    }

    private TypedArray getArrayCountries(){
        return context.getResources().obtainTypedArray(R.array.dw_country);
    }

    private TypedArray getArrayTypes(){
        return context.getResources().obtainTypedArray(R.array.dw_recipe);
    }

    private void setTextName(RecipesHolder holder, String name){
        holder.tvName.setText(name);
    }

    private void setImageCountry(RecipesHolder holder, int type){
        TypedArray arrayTypes = getArrayCountries();
        holder.ivCountry.setImageResource(arrayTypes.getResourceId(type,
                MainGlobals.INT_DECREMENT_VAR_INIT));
    }

    private void setImageType(RecipesHolder holder, int type){
        TypedArray arrayTypes = getArrayTypes();
        holder.ivType.setImageResource(arrayTypes.getResourceId(type,
                MainGlobals.INT_DECREMENT_VAR_INIT));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipesHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_country)
        ImageView ivCountry;
        @BindView(R.id.iv_type)
        ImageView ivType;

        @OnClick(R.id.cl_recipe)
        public void onClickLinearLayoutRecipe(){
            if(isSelecting){
                addIngredientsToShoppingList(getAdapterPosition());
            } else {
                openRecipe(getAdapterPosition());
            }
        }

        public RecipesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void addIngredientsToShoppingList(int position){
            int id = recipes.get(position).getId();
            new AsyncReadShoppingItems().execute(id);
        }

        private void openRecipe(int id){
            RecipeDetails.resetRecipeDetails();
            Fragment fragment = new ShowRecipeFragment();
            setBundle(fragment, id);
            setFragment(fragment);
        }

        private void setBundle(Fragment fragment, int position){
            int id = recipes.get(position).getId();
            Bundle bundle = new Bundle();
            bundle.putInt(BundleGlobals.BUND_ID_FRAG_SHOWREC, id);
            fragment.setArguments(bundle);
        }

        private void setFragment(Fragment fragment){
            ((MainActivity) context).setFragment(fragment, true);
        }
    }

    private void popFragment(){
        ((MainActivity)context).popFragment();
    }

    private class AsyncReadShoppingItems extends AsyncTask<Integer, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... id) {
            try{
                DbOperations.readShoppingItems(context,
                        ShoppingListDetails.getShoppingList().getShoppingItems(),
                        id[MainGlobals.INT_STARTING_VAR_INIT]);
                return true;
            }catch (SQLiteException e){
                return false;
            }
        }

        protected void onPostExecute(Boolean success){
            if(success){
                popFragment();
            } else {

            }
        }
    }
}
