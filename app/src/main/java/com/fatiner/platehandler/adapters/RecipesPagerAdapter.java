package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fatiner.platehandler.fragments.recipe.manage.ManageIngredientsFragment;
import com.fatiner.platehandler.fragments.recipe.manage.ManageInstructionsFragment;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.R;
import com.fatiner.platehandler.fragments.recipe.manage.ManageInfoFragment;

public class RecipesPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public RecipesPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch(position){
            case MainGlobals.ID_ADDREC_PAGER_RECIPES:
                fragment = new ManageInfoFragment();
                break;
            case MainGlobals.ID_ADDINGRED_PAGER_RECIPES:
                fragment = new ManageIngredientsFragment();
                break;
            case MainGlobals.ID_ADDSTEPS_PAGER_RECIPES:
                fragment = new ManageInstructionsFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return MainGlobals.NO_TABS_PAGER_RECIPES;
    }

    @Override
    public CharSequence getPageTitle(int position){
        String pageTitle = MainGlobals.STR_EMPTY_OBJ_INIT;
        switch(position){
            case MainGlobals.ID_ADDREC_PAGER_RECIPES:
                pageTitle = context.getResources().getString(
                        R.string.item_info_pager_recipes);
                break;
            case MainGlobals.ID_ADDINGRED_PAGER_RECIPES:
                pageTitle = context.getResources().getString(
                        R.string.item_ingredients_pager_recipes);
                break;
            case MainGlobals.ID_ADDSTEPS_PAGER_RECIPES:
                pageTitle = context.getResources().getString(
                        R.string.item_instructions_pager_recipes);
                break;
        }
        return pageTitle;
    }
}
