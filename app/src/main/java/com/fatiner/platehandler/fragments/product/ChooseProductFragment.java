package com.fatiner.platehandler.fragments.product;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.ProductsAdapter;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.DatabaseGlobals;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.classes.Product;
import com.fatiner.platehandler.managers.database.DbSuccessManager;
import com.fatiner.platehandler.managers.database.DbSelectionManager;
import com.fatiner.platehandler.managers.shared.SharedProductManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseProductFragment extends PrimaryFragment {

    @BindView(R.id.recyc_products_frag_chsprod)
    RecyclerView recycProducts;

    @OnClick(R.id.float_products_frag_chsprod)
    public void onClickFloatProducts(){
        resetProductDetails();
        setFragment(new ManageProductFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_product, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tool_choose_frag_chsprod);
        setMenuItem(MainGlobals.ID_INGREDIENTS_DRAW_MAIN);
        readProducts();
        setHasOptionsMenu(true);
        return view;
    }

    private void readProducts(){
        new AsyncReadProducts().execute();
    }

    private String getSelection(){
        String selection = MainGlobals.STR_EMPTY_OBJ_INIT;
        ArrayList<String> strings = DbSelectionManager.getArraySelectionProduct(getContext());
        for(int i = MainGlobals.INT_STARTING_VAR_INIT; i < strings.size(); i++){
            selection += strings.get(i);
            if(i < strings.size() + MainGlobals.INT_DECREMENT_VAR_INIT){
                selection += " AND ";
            }
        }
        return selection;
    }

    private String getOrderBy(){
        String orderBy = null;
        if(SharedProductManager.getSharedProductAlphabetical(getContext())){
            orderBy = DatabaseGlobals.COL_NAME_TAB_PRODUCTS + " ASC";
        }
        return orderBy;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_chsprod, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.item_settings_menu_chsprod:
                setFragment(new SettingsProductFragment());
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private class AsyncReadProducts extends AsyncTask<Void, Void, Boolean> {

        ArrayList<Product> products;

        protected void onPreExecute(){
            products = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DbSuccessManager.readProducts(
                    getContext(), products, getSelection(), getOrderBy());
        }

        protected void onPostExecute(Boolean success){
            if(success){
                setRecyclerView(
                        recycProducts,
                        getGridLayoutManager(MainGlobals.RECYC_SPAN_FRAG_PRODUCTS),
                        new ProductsAdapter(getContext(), products)
                );
            } else {
                showShortToast(R.string.toast_fail_async_readprod);
            }
        }
    }
}
