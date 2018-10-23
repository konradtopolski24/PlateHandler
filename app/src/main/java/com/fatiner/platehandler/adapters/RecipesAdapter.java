package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.content.res.TypedArray;
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
        return context.getResources().getStringArray(R.array.array_country_frag_recipe);
    }

    private TypedArray getArrayTypes(){
        return context.getResources().obtainTypedArray(R.array.array_drawable_image_type);
    }

    private TypedArray getArrayPreferences(){
        return context.getResources().obtainTypedArray(R.array.array_drawable_image_preference);
    }

    private void setTextName(RecipesHolder holder, String name){
        holder.textName.setText(name);
    }

    private void setTextAuthor(RecipesHolder holder, String author){
        holder.textAuthor.setText(author);
    }

    private void setTextDifficulty(RecipesHolder holder, int difficulty){
        holder.textDifficulty.setText(String.valueOf(difficulty));
    }

    private void setTextCountry(RecipesHolder holder, String country){
        holder.textCountry.setText(country);
    }

    private void setImageType(RecipesHolder holder, int type){
        TypedArray arrayTypes = getArrayTypes();
        holder.imageType.setImageResource(arrayTypes.getResourceId(type,
                MainGlobals.INT_DECREMENT_VAR_INIT));
    }

    private void setImagePreference(RecipesHolder holder, boolean preference){
        TypedArray arrayPreferences = getArrayPreferences();
        holder.imagePreference.setImageResource(arrayPreferences.getResourceId(
                TypeManager.booleanToInteger(preference), MainGlobals.INT_DECREMENT_VAR_INIT));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipesHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text_name_card_recipe)
        TextView textName;
        @BindView(R.id.text_author_card_recipe)
        TextView textAuthor;
        @BindView(R.id.text_difficulty_card_recipe)
        TextView textDifficulty;
        @BindViews({
                R.id.image_spiciness0_card_recipe,
                R.id.image_spiciness1_card_recipe,
                R.id.image_spiciness2_card_recipe})
        List<ImageView> arrayImageSpiciness;
        @BindView(R.id.text_country_card_recipe)
        TextView textCountry;
        @BindView(R.id.image_type_card_recipe)
        ImageView imageType;
        @BindView(R.id.image_preference_card_recipe)
        ImageView imagePreference;

        @OnClick(R.id.lin_recipe_card_recipe)
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
            return DbSuccessManager.readShoppingItems(context,
                    ShoppingListDetails.getShoppingList().getShoppingItems(),
                    id[MainGlobals.INT_STARTING_VAR_INIT]);
        }

        protected void onPostExecute(Boolean success){
            if(success){
                popFragment();
            } else {

            }
        }
    }
}
