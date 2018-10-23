package com.fatiner.platehandler.fragments.recipe.manage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fatiner.platehandler.adapters.AddIngredientAdapter;
import com.fatiner.platehandler.globals.BundleGlobals;
import com.fatiner.platehandler.classes.Category;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.classes.Ingredient;
import com.fatiner.platehandler.R;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.classes.Product;
import com.fatiner.platehandler.managers.database.DbSuccessManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageCategoryFragment extends PrimaryFragment {

    @BindView(R.id.edit_name_frag_mngcatg)
    EditText editName;
    @BindView(R.id.recyc_ingredients_frag_mngcatg)
    RecyclerView recycIngredients;

    @OnClick(R.id.butt_add_frag_mngcatg)
    public void onClickButtAdd(){
        addNewItem();
    }

    @OnClick(R.id.float_add_frag_mngcatg)
    public void onClickFloatAdd(){
        hideKeyboard();
        if(isCategoryCorrect()){
            Category category = new Category();
            category.setName(editName.getText().toString());
            category.setIngredients(getTempIngredients());
            setCategoryInRecipeDetails(category);
            resetTempIngredients();
            showShortSnack(R.string.snack_category_frag_recipe);
            popFragment();
        } else {
            showShortToast(R.string.toast_category_frag_recipe);
        }
    }

    public ManageCategoryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_category, container, false);
        ButterKnife.bind(this, view);
        chooseToolbar();
        setMenuItem(MainGlobals.ID_RECIPES_DRAW_MAIN);
        setProducts();
        return view;
    }

    private void setProducts(){
        new AsyncReadProducts().execute();
    }

    private void chooseStartingAction(){
        if(isBundleNotEmpty()){
            setCategoryInfo();
        } else {
            addNewItemFirstTime();
        }
    }

    private void addNewItemFirstTime(){
        if(isTempIngredientEmpty()){
            addNewItem();
        }
    }

    private void setCategoryInfo(){
        int id = getIntFromBundle(BundleGlobals.BUND_ID_FRAG_ADDCATEG);
        Category category = RecipeDetails.getRecipe().getCategories().get(id);
        setEditName(category.getName());
        setIngredients(category.getIngredients());
    }

    private void setIngredients(ArrayList<Ingredient> ingredients){
        if(isTempIngredientEmpty()){
            ArrayList<Ingredient> tempIngredients = getTempIngredients();
            for(Ingredient ingredient : ingredients){
                Ingredient tempIngredient = new Ingredient();
                tempIngredient.setAmount(ingredient.getAmount());
                tempIngredient.setMeasure(ingredient.getMeasure());
                tempIngredient.setProduct(ingredient.getProduct());
                tempIngredients.add(tempIngredient);
            }
            recycIngredients.getAdapter().notifyDataSetChanged();
        }
    }

    private void chooseToolbar(){
        if(isBundleNotEmpty()){
            setToolbarTitle(R.string.tool_edit_frag_mngcatg);
        } else {
            setToolbarTitle(R.string.tool_add_frag_mngcatg);
        }
    }

    private void addNewItem(){
        ArrayList<Ingredient> ingredients = getTempIngredients();
        Ingredient ingredient = new Ingredient();
        ingredient.setProduct(new Product());
        ingredients.add(ingredient);
        recycIngredients.getAdapter().notifyItemInserted(
                ingredients.size() + MainGlobals.INT_DECREMENT_VAR_INIT);
    }

    private void setCategoryInRecipeDetails(Category category){
        if(isBundleNotEmpty()){
            RecipeDetails.getRecipe().getCategories().remove(
                    getIntFromBundle(BundleGlobals.BUND_ID_FRAG_ADDCATEG));
            RecipeDetails.getRecipe().getCategories().add(
                    getIntFromBundle(BundleGlobals.BUND_ID_FRAG_ADDCATEG), category);
        } else {
            RecipeDetails.getRecipe().getCategories().add(category);
        }
    }

    private boolean isCategoryCorrect(){
        return isCategoryNameNotEmpty() && doIngredientsExist();
    }

    private boolean isCategoryNameNotEmpty(){
        return !editName.getText().toString().isEmpty();
    }

    private boolean doIngredientsExist(){
        return !getTempIngredients().isEmpty();
    }

    private void setEditName(String text){
        editName.setText(text);
    }

    private ArrayList<Ingredient> getTempIngredients(){
        return RecipeDetails.getTempIngredients();
    }

    private void resetTempIngredients(){
        RecipeDetails.resetTempIngredients();
    }

    private boolean isTempIngredientEmpty(){
        return getTempIngredients().isEmpty();
    }

    private class AsyncReadProducts extends AsyncTask<Void, Void, Boolean> {

        ArrayList<Product> products;

        protected void onPreExecute(){
            products = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DbSuccessManager.readProducts(
                    getContext(), products, null, getOrderByProducts());
        }

        protected void onPostExecute(Boolean success){
            if(success){
                setRecyclerView(
                        recycIngredients,
                        getGridLayoutManager(MainGlobals.RECYC_SPAN_FRAG_ADDCATEG),
                        new AddIngredientAdapter(getContext(), getTempIngredients(), products)
                );
                chooseStartingAction();
            } else {
                showShortToast(R.string.toast_fail_async_readprod);
            }
        }
    }
}
