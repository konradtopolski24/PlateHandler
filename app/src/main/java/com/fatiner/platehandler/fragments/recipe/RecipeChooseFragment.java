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
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.recyclerview.RecipeAdapter;
import com.fatiner.platehandler.details.ShoppingListDetails;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.fragments.recipe.manage.RecipeManagePagerFragment;
import com.fatiner.platehandler.globals.Db;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.globals.Shared;
import com.fatiner.platehandler.items.ShoppingItem;
import com.fatiner.platehandler.managers.QueryManager;
import com.fatiner.platehandler.models.IngredientComplete;
import com.fatiner.platehandler.models.Recipe;
import com.fatiner.platehandler.viewmodels.recipe.RecipeChooseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecipeChooseFragment extends PrimaryFragment implements RecipeAdapter.RecipeListener {

    private RecipeChooseViewModel viewModel;
    private CompositeDisposable disposables;

    @BindView(R.id.rv_recipes)
    RecyclerView rvRecipes;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;

    @OnClick(R.id.fab_add)
    void clickFabAdd() {
        resetRecipeDetails();
        setFragment(RecipeManagePagerFragment.getInstance(false));
    }

    public static RecipeChooseFragment getInstance(boolean isShopping) {
        RecipeChooseFragment fragment = new RecipeChooseFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Globals.BN_BOOL, isShopping);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_recipe_choose, container,
                false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initOptions(getMenuId(), R.string.tb_rp_choose, true);
        initViewModelEssentials();
        initAction();
    }

    private void initViewModelEssentials() {
        viewModel = new ViewModelProvider(this).get(RecipeChooseViewModel.class);
        disposables = new CompositeDisposable();
    }

    private void initAction() {
        observeGetRecipes();
        hideFab();
    }

    private int getMenuId() {
        if (isShopping()) return R.id.it_shopping;
        else return R.id.it_recipe;
    }

    private List<ShoppingItem> getShoppingItems() {
        return ShoppingListDetails.getShoppingList().getShoppingItems();
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
        if (isShopping()) removeView(fabAdd);
    }

    private boolean isShopping() {
        return getBundle().getBoolean(Globals.BN_BOOL);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.rp_choose, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.it_settings) {
            setFragment(RecipeSettingsFragment.getInstance(isShopping()));
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void setIngredientsFromRecipe(List<IngredientComplete> ingredients) {
        if (isEmptyItemOnly()) getShoppingItems().clear();
        for (IngredientComplete ingredientComplete : ingredients) {
            ShoppingItem item = new ShoppingItem();
            item.setAmount(ingredientComplete.ingredient.getAmount());
            item.setMeasure(ingredientComplete.ingredient.getMeasure());
            item.setName(ingredientComplete.product.getName());
            getShoppingItems().add(item);
        }
    }

    private boolean isEmptyItemOnly() {
        if (getShoppingItems().size() == Globals.DF_INCREMENT) {
            ShoppingItem item = getShoppingItems().get(Globals.DF_ZERO);
            return item.getAmount() == Globals.DF_ZERO && item.getName().equals(Globals.SN_EMPTY);
        } else return false;
    }

    private void observeGetRecipes() {
        viewModel.getRecipes(getRecipesQuery())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getRecipeObserver());
    }

    private void observeGetCompleteIngredients(int id) {
        viewModel.getCompleteIngredients(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getIngredientObserver());
    }

    private SingleObserver<List<Recipe>> getRecipeObserver() {
        return new SingleObserver<List<Recipe>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

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

    private SingleObserver<List<IngredientComplete>> getIngredientObserver() {
        return new SingleObserver<List<IngredientComplete>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

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
        if (isShopping()) observeGetCompleteIngredients(id);
        else {
            resetRecipeDetails();
            setFragment(RecipeShowFragment.getInstance(id));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        disposables.clear();
    }
}
