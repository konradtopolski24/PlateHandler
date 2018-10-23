package com.fatiner.platehandler.fragments.recipe;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.IngredientAdapter;
import com.fatiner.platehandler.classes.Category;
import com.fatiner.platehandler.classes.Ingredient;
import com.fatiner.platehandler.classes.Product;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.MainGlobals;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalculateRecipeFragment extends PrimaryFragment {

    @BindView(R.id.recyc_ingredients_frag_calcrecp)
    public RecyclerView recycIngredients;
    @BindView(R.id.text_totalkcal_frag_calcrecp)
    public TextView textTotalkcal;

    public CalculateRecipeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculate_recipe, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tool_calculate_frag_clcrecp);
        ArrayList<Ingredient> ingredients = getIngredients();
        setRecyclerView(
                recycIngredients,
                getGridLayoutManager(MainGlobals.RECYC_SPAN_FRAG_ADDCATEG),
                new IngredientAdapter(getContext(), ingredients)
        );
        calculateTotalKcal(ingredients);
        return view;
    }

    private ArrayList<Ingredient> getIngredients(){
        ArrayList<Category> categories = RecipeDetails.getRecipe().getCategories();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for(Category category : categories){
            ingredients.addAll(category.getIngredients());
        }
        return ingredients;
    }

    private void calculateTotalKcal(ArrayList<Ingredient> ingredients){
        float total = MainGlobals.INT_STARTING_VAR_INIT;
        int[] arrayFactor = getArrayFactor();
        for(Ingredient ingredient : ingredients){
            Product product = ingredient.getProduct();
            float amount = ingredient.getAmount() * arrayFactor[ingredient.getMeasure()];
            float newCarbo = (amount * product.getCarbohydrates()) / MainGlobals.GRAM_PRIMARY_OBJ_PROD;
            float newProt = (amount * product.getProtein()) / MainGlobals.GRAM_PRIMARY_OBJ_PROD;
            float newFat = (amount * product.getFat()) / MainGlobals.GRAM_PRIMARY_OBJ_PROD;
            float totalOne =
                    getKcal(newCarbo, MainGlobals.KCAL_CARBOHYDRATES_OBJ_PROD)
                            + getKcal(newProt, MainGlobals.KCAL_PROTEIN_OBJ_PROD)
                            + getKcal(newFat, MainGlobals.KCAL_FAT_OBJ_PROD);
            total += totalOne;
        }
        textTotalkcal.setText(String.valueOf(total));
    }

    private int[] getArrayFactor(){
        return getIntegerArray(R.array.array_factor_frag_ingredient);
    }
}
