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
            case MainGlobals.PG_RECIPE:
                fragment = new ManageInfoFragment();
                break;
            case MainGlobals.PG_INGREDIENT:
                fragment = new ManageIngredientsFragment();
                break;
            case MainGlobals.PG_STEP:
                fragment = new ManageInstructionsFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return MainGlobals.PG_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position){
        String pageTitle = MainGlobals.SN_EMPTY;
        switch(position){
            case MainGlobals.PG_RECIPE:
                pageTitle = context.getResources().getString(
                        R.string.pg_info);
                break;
            case MainGlobals.PG_INGREDIENT:
                pageTitle = context.getResources().getString(
                        R.string.pg_ingredient);
                break;
            case MainGlobals.PG_STEP:
                pageTitle = context.getResources().getString(
                        R.string.pg_step);
                break;
        }
        return pageTitle;
    }
}
