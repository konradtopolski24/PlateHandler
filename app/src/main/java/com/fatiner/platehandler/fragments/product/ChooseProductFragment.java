package com.fatiner.platehandler.fragments.product;

import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.ProductsAdapter;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.DbGlobals;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.classes.Product;
import com.fatiner.platehandler.globals.SharedGlobals;
import com.fatiner.platehandler.managers.database.DbOperations;
import com.fatiner.platehandler.managers.database.DbSelection;
import com.fatiner.platehandler.managers.SharedManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseProductFragment extends PrimaryFragment {

    @BindView(R.id.rv_products)
    RecyclerView rvProducts;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    @OnClick(R.id.fab_add)
    public void onClickFabAdd(){
        resetProductDetails();
        setFragment(new ManageProductFragment());
    }

    public ChooseProductFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_product, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tb_pd_choose);
        setMenuItem(MainGlobals.ID_PRODUCT);
        readProducts();
        setHasOptionsMenu(true);
        return view;
    }

    private void readProducts(){
        new AsyncChooseProduct().execute();
    }

    private String getSelection(){
        String selection = MainGlobals.SN_EMPTY;
        ArrayList<String> strings = DbSelection.getProductSelection(getContext());
        for(int i = MainGlobals.DF_ZERO; i < strings.size(); i++){
            selection += strings.get(i);
            if(i < strings.size() + MainGlobals.DF_DECREMENT){
                selection += " AND ";
            }
        }
        return selection;
    }

    private String getOrderBy(){
        String orderBy = null;
        if(SharedManager.getBoolean(getContext(), SharedGlobals.SR_PRODUCT, SharedGlobals.KY_ALPHABETICAL)){
            orderBy = DbGlobals.CL_PD_NAME + " ASC";
        }
        return orderBy;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.pd_choose, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.it_settings:
                setFragment(new SettingsProductFragment());
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private class AsyncChooseProduct extends AsyncTask<Void, Void, Boolean> {

        private ArrayList<Product> products;

        protected void onPreExecute(){
            products = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                DbOperations.readProducts (
                        getContext(), products, getSelection(), getOrderBy());
                return true;
            } catch (SQLiteException e) {
                showShortToast(R.string.ts_database);
                return false;
            }
        }

        protected void onPostExecute(Boolean success){
            if(success){
                setRecyclerView(
                        rvProducts,
                        getGridLayoutManager(MainGlobals.GL_TWO),
                        new ProductsAdapter(getContext(), products)
                );
                checkIfRvEmpty(rvProducts, tvEmpty);
            }
        }
    }
}
