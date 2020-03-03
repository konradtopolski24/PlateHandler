package com.fatiner.platehandler.fragments.recipe;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.IngredientAdapter;
import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.Product;
import com.fatiner.platehandler.dialogs.MeasureDialog;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.Globals;
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
        setTb(R.string.tb_rp_calculate);
        ArrayList<Ingredient> ingredients = getIngredients();
        //setRv(
                //rvIngredients,
                //getLinearManager(),
                //new IngredientAdapter(getContext(), ingredients, false)
        //);

        return view;
    }

    private ArrayList<Ingredient> getIngredients(){
        //ArrayList<Category> categories = RecipeDetails.getRecipe().getCategories();
        //ArrayList<Ingredient> ingredients = new ArrayList<>();
        //for(Category category : categories){
            //ingredients.addAll(category.getIngredients());
        //}
        //return ingredients;
        return new ArrayList<Ingredient>();
    }

    private int[] getArrayFactor(){
        return getIntArray(R.array.tx_factor);
    }
}
