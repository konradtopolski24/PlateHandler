package com.fatiner.platehandler.fragments.recipe;

import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fatiner.platehandler.adapters.RecipesAdapter;
import com.fatiner.platehandler.globals.DbGlobals;
import com.fatiner.platehandler.globals.SharedGlobals;
import com.fatiner.platehandler.managers.SharedManager;
import com.fatiner.platehandler.managers.database.DbOperations;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.R;
import com.fatiner.platehandler.managers.database.DbSelection;
import com.fatiner.platehandler.classes.Recipe;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.fragments.recipe.manage.ManageRecipeFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseRecipeFragment extends PrimaryFragment {

    @BindView(R.id.rv_recipes)
    RecyclerView rvRecipes;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;

    @OnClick(R.id.fab_add)
    public void onClickFabAdd(){
        resetRecipeDetails();
        setFragment(new ManageRecipeFragment());
    }

    public ChooseRecipeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_recipe, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tb_rp_choose);
        chooseItem();
        setRecipes();
        hideFloatRecipes();
        setHasOptionsMenu(true);
        return view;
    }

    private void chooseItem(){
        if(isBundleNotEmpty()){
            setMenuItem(MainGlobals.ID_SHOPPING);
        } else {
            setMenuItem(MainGlobals.ID_RECIPE);
        }
    }

    private void setRecipes(){
        new AsyncChooseRecipe().execute();
    }

    private String getSelection(){
        String selection = MainGlobals.SN_EMPTY;
        ArrayList<String> strings = DbSelection.getRecipeSelection(getContext());
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
        if(SharedManager.getBoolean(getContext(), SharedGlobals.SR_RECIPE, SharedGlobals.KY_ALPHABETICAL)){
            orderBy = DbGlobals.CL_RP_NAME + " ASC";
        }
        return orderBy;
    }

    private void hideFloatRecipes(){
        if(isBundleNotEmpty()){
            fabAdd.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.rp_choose, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.it_settings:
                setFragment(new SettingsRecipeFragment());
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private class AsyncChooseRecipe extends AsyncTask<Void, Void, Boolean> {

        private ArrayList<Recipe> recipes;

        protected void onPreExecute(){
            recipes = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                DbOperations.readRecipes(getContext(), recipes, getSelection(), getOrderBy());
                return true;
            }catch (SQLiteException e){
                showShortToast(R.string.ts_database);
                return false;
            }
        }

        protected void onPostExecute(Boolean success){
            if(success){
                finishedReadRecipes(recipes);
            }
        }
    }

    private void finishedReadRecipes(ArrayList<Recipe> recipes) {
        setRecyclerView(
                rvRecipes,
                getGridLayoutManager(MainGlobals.GL_TWO),
                new RecipesAdapter(getContext(), recipes, isBundleNotEmpty())
        );
        checkIfRvEmpty(rvRecipes, tvEmpty);
    }
}
