package com.fatiner.platehandler.fragments.recipe;

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

import com.fatiner.platehandler.adapters.RecipesAdapter;
import com.fatiner.platehandler.globals.DatabaseGlobals;
import com.fatiner.platehandler.managers.database.DbSuccessManager;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.managers.shared.SharedRecipeManager;
import com.fatiner.platehandler.R;
import com.fatiner.platehandler.managers.database.DbSelectionManager;
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
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;

    @OnClick(R.id.fab_add)
    public void onClickFloatRecipes(){
        resetRecipeDetails();
        setFragment(new ManageRecipeFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_recipe, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tool_choose_frag_chsrecp);
        chooseItem();
        setRecipes();
        hideFloatRecipes();
        setHasOptionsMenu(true);
        return view;
    }

    private void chooseItem(){
        if(isBundleNotEmpty()){
            setMenuItem(MainGlobals.ID_SHOPPING_DRAW_MAIN);
        } else {
            setMenuItem(MainGlobals.ID_RECIPES_DRAW_MAIN);
        }
    }

    private void setRecipes(){
        new AsyncReadRecipes().execute();
    }

    private String getSelection(){
        String selection = MainGlobals.STR_EMPTY_OBJ_INIT;
        ArrayList<String> strings = DbSelectionManager.getArraySelection(getContext());
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
        if(SharedRecipeManager.getSharedAlphabetical(getContext())){
            orderBy = DatabaseGlobals.COL_NAME_TAB_RECIPES + " ASC";
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
        inflater.inflate(R.menu.menu_chsrecp, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.item_settings_menu_chsrecp:
                setFragment(new SettingsRecipeFragment());
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private class AsyncReadRecipes extends AsyncTask<Void, Void, Boolean> {

        ArrayList<Recipe> recipes;

        protected void onPreExecute(){
            recipes = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DbSuccessManager.readRecipes(getContext(), recipes, getSelection(), getOrderBy());
        }

        protected void onPostExecute(Boolean success){
            if(success){
                setRecyclerView(
                        rvRecipes,
                        getGridLayoutManager(MainGlobals.RECYC_SPAN_FRAG_RECIPES),
                        new RecipesAdapter(getContext(), recipes, isBundleNotEmpty())
                );
            } else {
                showShortToast(R.string.toast_fail_async_readrec);
            }
        }
    }
}
