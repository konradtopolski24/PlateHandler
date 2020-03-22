package com.fatiner.platehandler.adapters.pager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fatiner.platehandler.fragments.recipe.manage.RecipeManageIngredientFragment;
import com.fatiner.platehandler.fragments.recipe.manage.RecipeManageStepFragment;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.R;
import com.fatiner.platehandler.fragments.recipe.manage.RecipeManageInfoFragment;

public class RecipePagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public RecipePagerAdapter(FragmentManager manager, Context context) {
        super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case Globals.PG_RECIPE:
                return new RecipeManageInfoFragment();
            case Globals.PG_INGREDIENT:
                return new RecipeManageIngredientFragment();
            case Globals.PG_STEP:
                return new RecipeManageStepFragment();
            default:
                return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return Globals.PG_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case Globals.PG_RECIPE:
                return getString(R.string.pg_info);
            case Globals.PG_INGREDIENT:
                return getString(R.string.pg_ingredient);
            case Globals.PG_STEP:
                return getString(R.string.pg_step);
            default:
                return Globals.SN_EMPTY;
        }
    }

    private String getString(int id) {
        return context.getResources().getString(id);
    }
}
