package com.fatiner.platehandler.fragments.recipe.manage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.fatiner.platehandler.PlateHandlerDatabase;
import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.recyclerview.IngredientAddAdapter;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.fragments.product.ProductManageFragment;
import com.fatiner.platehandler.globals.Db;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.globals.Shared;
import com.fatiner.platehandler.managers.QueryManager;
import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RecipeManageIngredientFragment extends PrimaryFragment implements
        IngredientAddAdapter.IngredientAddListener {

    @BindView(R.id.rv_ingredient) RecyclerView rvIngredients;

    @OnClick(R.id.bt_add)
    void clickBtAdd() {
        addNewItem();
    }

    @OnClick(R.id.iv_tt_add)
    void clickIvTtAdd() {
        showDialog(R.string.hd_ig_mg_add, R.string.tt_ig_mg_add);
    }

    @OnClick(R.id.iv_tt_data)
    void clickIvTtData() {
        showDialog(R.string.hd_ig_mg_data, R.string.tt_ig_mg_data);
    }

    public RecipeManageIngredientFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_recipe_manage_ingredient, container,
                false);
        ButterKnife.bind(this, view);
        readProducts();
        return view;
    }

    private List<Ingredient> getIngredients() {
        return RecipeDetails.getRecipe().getIngredients();
    }

    private void startAction(List<Product> products) {
        manageRv(products);
        addFirstItem();
    }

    private void manageRv(List<Product> products) {
        setRv(rvIngredients, getManager(Globals.GL_ONE), getIngredientAddAdapter(products));
        changeRvSize(rvIngredients);
    }

    private IngredientAddAdapter getIngredientAddAdapter(List<Product> products) {
        return new IngredientAddAdapter(getContext(), getIngredients(), products, this);
    }

    private void addFirstItem() {
        if (areIngredientsEmpty()) addNewItem();
    }

    private boolean areIngredientsEmpty() {
        return getIngredients().isEmpty();
    }

    private void addNewItem() {
        List<Ingredient> ingredients = getIngredients();
        Ingredient ingredient = new Ingredient();
        ingredients.add(ingredient);
        getAdapter().notifyItemInserted(ingredients.size() + Globals.DF_DECREMENT);
        changeRvSize(rvIngredients);
    }

    private RecyclerView.Adapter getAdapter() {
        return rvIngredients.getAdapter();
    }

    private SimpleSQLiteQuery getProductsQuery() {
        String orderBy = QueryManager.getOrderBy(getContext(), Shared.SR_PRODUCT, Db.CL_PD_NAME);
        return QueryManager.getRowsQuery(Db.TB_PRODUCT, Globals.SN_EMPTY, orderBy);
    }

    private void readProducts() {
        PlateHandlerDatabase db = getDb(getContext());
        Single<List<Product>> single = db.getProductDAO().getProducts(getProductsQuery());
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getProductObserver());
    }

    private DisposableSingleObserver<List<Product>> getProductObserver() {
        return new DisposableSingleObserver<List<Product>>() {

            @Override
            public void onSuccess(List<Product> products) {
                startAction(products);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    @Override
    public void clickIb(int position) {
        resetProductDetails();
        setFragment(new ProductManageFragment(), position, Globals.BN_INT);
    }

    @Override
    public void removeIngredient(int position) {
        List<Ingredient> ingredients = getIngredients();
        if (ingredients.size() == Globals.DF_INCREMENT) {
            showShortToast(R.string.ts_item);
        } else {
            ingredients.remove(position);
            getAdapter().notifyItemRemoved(position);
            changeRvSize(rvIngredients);
        }
    }
}
