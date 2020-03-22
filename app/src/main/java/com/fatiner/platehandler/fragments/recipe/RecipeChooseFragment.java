package com.fatiner.platehandler.fragments.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.fatiner.platehandler.PlateHandlerDatabase;
import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.recyclerview.RecipeAdapter;
import com.fatiner.platehandler.classes.ShoppingItem;
import com.fatiner.platehandler.details.ShoppingListDetails;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.fragments.recipe.manage.RecipeManagePagerFragment;
import com.fatiner.platehandler.globals.Db;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.globals.Shared;
import com.fatiner.platehandler.managers.QueryManager;
import com.fatiner.platehandler.models.IngredientComplete;
import com.fatiner.platehandler.models.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RecipeChooseFragment extends PrimaryFragment implements RecipeAdapter.RecipeListener {

    @BindView(R.id.rv_recipes) RecyclerView rvRecipes;
    @BindView(R.id.tv_empty) TextView tvEmpty;
    @BindView(R.id.fab_add) FloatingActionButton fabAdd;

    @OnClick(R.id.fab_add)
    void clickFabAdd() {
        resetRecipeDetails();
        setFragment(new RecipeManagePagerFragment());
    }

    public RecipeChooseFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_recipe_choose, container, false);
        init(this, view, getMenuId(), R.string.tb_rp_choose, true);
        initAction();
        return view;
    }

    private void initAction() {
        readRecipes();
        hideFab();
    }

    private int getMenuId() {
        if(isShopping()) return R.id.it_shopping;
        else return R.id.it_recipe;
    }

    private SimpleSQLiteQuery getRecipesQuery() {
        String where = QueryManager.getWhere(QueryManager.getRpConditions(getContext()));
        String orderBy = QueryManager.getOrderBy(getContext(), Shared.SR_RECIPE, Db.CL_RP_NAME);
        return QueryManager.getRowsQuery(Db.TB_RECIPE, where, orderBy);
    }

    private RecipeAdapter getRecipeAdapter(List<Recipe> recipes) {
        return new RecipeAdapter(getContext(), recipes, this);
    }

    private void hideFab() {
        if(isShopping()) removeView(fabAdd);
    }

    private boolean isShopping() {
        return isValueInBundle(Globals.BN_BOOL);
    }

    private void chooseSetFragment() {
        if(isShopping()) setFragment(new RecipeSettingsFragment(), true, Globals.BN_BOOL);
        else setFragment(new RecipeSettingsFragment());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.rp_choose, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.it_settings) {
            chooseSetFragment();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void setIngredientsFromRecipe(List<IngredientComplete> ingredients) {
        for(IngredientComplete ingredientComplete : ingredients) {
            ShoppingItem item = new ShoppingItem();
            item.setAmount(ingredientComplete.ingredient.getAmount());
            item.setMeasure(ingredientComplete.ingredient.getMeasure());
            item.setName(ingredientComplete.product.getName());
            ShoppingListDetails.getShoppingList().getShoppingItems().add(item);
        }
    }

    //Read Recipes
    private void readRecipes() {
        PlateHandlerDatabase db = getDb(getContext());
        Single<List<Recipe>> single = db.getRecipeDAO().getRecipes(getRecipesQuery());
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getReadRecipesObserver());
    }

    private DisposableSingleObserver<List<Recipe>> getReadRecipesObserver() {
        return new DisposableSingleObserver<List<Recipe>>() {

            @Override
            public void onSuccess(List<Recipe> recipes) {
                setRv(rvRecipes, getManager(getColumnAmountChoose()), getRecipeAdapter(recipes));
                checkIfRvEmpty(rvRecipes, tvEmpty);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    //Read Ingredients
    private void readIngredients(int id) {
        PlateHandlerDatabase db = getDb(getContext());
        Single<List<IngredientComplete>> single = db.getIngredientDAO().getCompleteIngredients(id);
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getReadIngredientsObserver());
    }

    private DisposableSingleObserver<List<IngredientComplete>> getReadIngredientsObserver() {
        return new DisposableSingleObserver<List<IngredientComplete>>() {

            @Override
            public void onSuccess(List<IngredientComplete> ingredients) {
                setIngredientsFromRecipe(ingredients);
                popFragment();
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    @Override
    public void clickRecipe(int id) {
        if(isShopping()) readIngredients(id);
        else {
            resetRecipeDetails();
            setFragment(new RecipeShowFragment(), id, Globals.BN_INT);
        }
    }
}
