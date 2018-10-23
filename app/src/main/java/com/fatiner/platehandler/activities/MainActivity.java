package com.fatiner.platehandler.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.fragments.product.ChooseProductFragment;
import com.fatiner.platehandler.fragments.main.MainFragment;
import com.fatiner.platehandler.fragments.recipe.ChooseRecipeFragment;
import com.fatiner.platehandler.fragments.shopping.ShowShoppingFragment;
import com.fatiner.platehandler.globals.MainGlobals;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tool_main_act_main)
    Toolbar toolMain;
    @BindView(R.id.draw_main_act_main)
    DrawerLayout drawMain;
    @BindView(R.id.navig_main_act_main)
    NavigationView navigMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        manageDrawer();
        setFragment(new MainFragment(), false);
        setBackStackListener();
    }

    private void manageDrawer(){
        setSupportActionBar(toolMain);
        setDrawerToggle();
        setOnItemClickListener();
    }

    private void setDrawerToggle(){
        ActionBarDrawerToggle drawerToggle = getDrawerToggle();
        drawMain.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private ActionBarDrawerToggle getDrawerToggle(){
        return new ActionBarDrawerToggle(
                this,
                drawMain,
                toolMain,
                R.string.toggle_open_draw_main,
                R.string.toggle_close_draw_main
        );
    }

    private void setOnItemClickListener(){
        navigMain.setNavigationItemSelectedListener(getOnItemClickListener());
    }

    private NavigationView.OnNavigationItemSelectedListener getOnItemClickListener(){
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                onItemClick(item);
                return true;
            }
        };
    }

    private void onItemClick(MenuItem item){
        Fragment fragment = selectFragment(item);
        manageFragmentReplacement(fragment);
        closeDrawer();
    }

    private Fragment selectFragment(MenuItem item){
        Fragment fragment = new Fragment();
        switch(item.getItemId()){
            case R.id.item_main_draw_main:
                fragment = new MainFragment();
                break;
            case R.id.item_recipes_draw_main:
                fragment = new ChooseRecipeFragment();
                break;
            case R.id.item_shopping_draw_main:
                fragment = new ShowShoppingFragment();
                break;
            case R.id.item_ingredients_draw_main:
                fragment = new ChooseProductFragment();
                break;
        }
        return fragment;
    }

    private void manageFragmentReplacement(Fragment fragment){
        if(isPreviousFragment(fragment)) return;
        clearBackStack();
        setFragment(fragment, isNotMainFragment(fragment));
    }

    private boolean isPreviousFragment(Fragment fragment){
        Fragment currentFragment = getCurrentFragment();
        return currentFragment.getClass().equals(fragment.getClass());
    }

    private Fragment getCurrentFragment(){
        return getSupportFragmentManager().findFragmentById(R.id.frame_main_act_main);
    }

    private void clearBackStack(){
        FragmentManager manager = getSupportFragmentManager();
        for(int i = MainGlobals.INT_STARTING_VAR_INIT; i < manager.getBackStackEntryCount(); i++){
            manager.popBackStack();
        }
    }

    public void setFragment(Fragment fragment, boolean addToBackStack){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_main_act_main, fragment);
        if(addToBackStack){
            transaction.addToBackStack(null);
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    private boolean isNotMainFragment(Fragment fragment){
        if(fragment instanceof MainFragment)
            return false;
        else
            return true;
    }

    private void closeDrawer(){
        drawMain.closeDrawer(navigMain);
    }

    public void setMenuItem(int id){
        navigMain.getMenu().getItem(id).setChecked(true);
    }

    private void setBackStackListener(){
        getSupportFragmentManager().addOnBackStackChangedListener(getBackStackListener());
    }

    private FragmentManager.OnBackStackChangedListener getBackStackListener(){
        return new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                //selectMenuItem(getCurrentFragment());
            }
        };
    }

    @Override
    public void onBackPressed(){
        if(drawMain.isDrawerOpen(GravityCompat.START)){
            closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    public void setToolbarTitle(String title){
        toolMain.setTitle(title);
    }

    public void popFragment(){
        getSupportFragmentManager().popBackStackImmediate();
    }
}
