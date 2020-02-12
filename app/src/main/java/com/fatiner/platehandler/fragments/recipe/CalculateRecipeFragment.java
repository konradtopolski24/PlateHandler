package com.fatiner.platehandler.fragments.recipe;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.fatiner.platehandler.dialogs.MeasureDialog;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.managers.CalculateManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalculateRecipeFragment extends PrimaryFragment {

    @BindView(R.id.rv_ingredients)
    RecyclerView rvIngredients;
    @BindView(R.id.tv_totalkcal)
    TextView tvTotalkcal;

    @OnClick(R.id.bt_dialog)
    public void onClickBtDialog() {
        MeasureDialog dialog = new MeasureDialog();
        dialog.show(getChildFragmentManager(), null);
    }

    public CalculateRecipeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculate_recipe, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tb_rp_calculate);
        ArrayList<Ingredient> ingredients = getIngredients();
        setRecyclerView(
                rvIngredients,
                getLayoutManagerNoScroll(LinearLayoutManager.VERTICAL),
                new IngredientAdapter(getContext(), ingredients, false)
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
        float total = MainGlobals.DF_ZERO;
        for(Ingredient ingredient : ingredients) {
            Product product = ingredient.getProduct();
            float carbohydrates = CalculateManager.getKcal(product.getCarbohydrates(),
                    CalculateManager.Organic.CARBOHYDRATES);
            float protein = CalculateManager.getKcal(product.getCarbohydrates(),
                    CalculateManager.Organic.CARBOHYDRATES);
            float fat = CalculateManager.getKcal(product.getCarbohydrates(),
                    CalculateManager.Organic.CARBOHYDRATES);
            float sum = carbohydrates + protein + fat;
            float amount = CalculateManager.getAmountWithMeasure(
                    getContext(), ingredient.getAmount(), ingredient.getMeasure());
            float calorific = CalculateManager.getCalorific(amount, sum);
            total += calorific;
        }
        tvTotalkcal.setText(String.valueOf(total));
    }

    private int[] getArrayFactor(){
        return getIntegerArray(R.array.tx_factor);
    }
}
