package com.fatiner.platehandler.fragments.recipe.manage;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.CategoryAdapter;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.MainGlobals;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageIngredientsFragment extends PrimaryFragment {

    @BindView(R.id.rv_categories)
    RecyclerView rvCategories;

    @OnClick(R.id.bt_add)
    public void onClickButtAdd(){
        RecipeDetails.resetTempIngredients();
        setFragment(new ManageCategoryFragment());
    }

    public ManageIngredientsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_ingredients, container, false);
        ButterKnife.bind(this, view);
        hideKeyboard();
        setMenuItem(MainGlobals.ID_RECIPES_DRAW_MAIN);
        manageRecyclerView();
        return view;
    }

    private void manageRecyclerView(){
        GridLayoutManager manager = getGridLayoutManager(MainGlobals.RECYC_SPAN_FRAG_ADDINGRED);
        CategoryAdapter adapter = new CategoryAdapter(getContext(),
                RecipeDetails.getRecipe().getCategories(), false);
        setRecyclerView(rvCategories, manager, adapter);
    }
}
