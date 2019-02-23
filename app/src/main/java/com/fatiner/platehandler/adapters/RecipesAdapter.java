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
import com.fatiner.platehandler.managers.database.DbSuccessManager;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.activities.MainActivity;
import com.fatiner.platehandler.fragments.recipe.ShowRecipeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
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
        setTextAuthor(holder, recipe.getAuthor());
        setTextDifficulty(holder, recipe.getDifficulty());
        String [] arrayCountries = getArrayCountries();
        setTextCountry(holder, arrayCountries[recipe.getCountry()]);
        setImageType(holder, recipe.getType());
        setImagePreference(holder, recipe.getPreference());
    }

    private String[] getArrayCountries(){
        return context.getResources().getStringArray(R.array.ar_country);
    }

    private TypedArray getArrayTypes(){
        return context.getResources().obtainTypedArray(R.array.ar_drawable_type);
    }

    private TypedArray getArrayPreferences(){
        return context.getResources().obtainTypedArray(R.array.ar_drawable_preference);
    }

    private void setTextName(RecipesHolder holder, String name){
        holder.tvName.setText(name);
    }

    private void setTextAuthor(RecipesHolder holder, String author){
        holder.tvAuthor.setText(author);
    }

    private void setTextDifficulty(RecipesHolder holder, int difficulty){
        holder.tvDifficulty.setText(String.valueOf(difficulty));
    }

    private void setTextCountry(RecipesHolder holder, String country){
        holder.tvCountry.setText(country);
    }

    private void setImageType(RecipesHolder holder, int type){
        TypedArray arrayTypes = getArrayTypes();
        holder.ivType.setImageResource(arrayTypes.getResourceId(type,
                MainGlobals.INT_DECREMENT_VAR_INIT));
    }

    private void setImagePreference(RecipesHolder holder, boolean preference){
        TypedArray arrayPreferences = getArrayPreferences();
        holder.ivPreference.setImageResource(arrayPreferences.getResourceId(
                TypeManager.booleanToInteger(preference), MainGlobals.INT_DECREMENT_VAR_INIT));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipesHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_difficulty)
        TextView tvDifficulty;
        @BindViews({
                R.id.iv_spiciness0,
                R.id.iv_spiciness1,
                R.id.iv_spiciness2})
        List<ImageView> arIvSpiciness;
        @BindView(R.id.tv_country)
        TextView tvCountry;
        @BindView(R.id.iv_type)
        ImageView ivType;
        @BindView(R.id.iv_preference)
        ImageView ivPreference;

        @OnClick(R.id.ll_recipe)
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
                DbSuccessManager.readShoppingItems(context,
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
