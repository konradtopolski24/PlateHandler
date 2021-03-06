package com.fatiner.platehandler.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.fragments.credits.CreditsFragment;
import com.fatiner.platehandler.fragments.export.ExportFragment;
import com.fatiner.platehandler.fragments.export.ImportFragment;
import com.fatiner.platehandler.fragments.home.HomeFragment;
import com.fatiner.platehandler.fragments.product.ProductChooseFragment;
import com.fatiner.platehandler.fragments.recipe.RecipeChooseFragment;
import com.fatiner.platehandler.fragments.shopping.ShoppingShowFragment;
import com.fatiner.platehandler.globals.Globals;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tb_main)
    Toolbar tbMain;
    @BindView(R.id.dl_main)
    DrawerLayout dlMain;
    @BindView(R.id.nv_main)
    NavigationView nvMain;

    private boolean isNotReady = true;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initAction(state);
    }

    private void initAction(Bundle state) {
        setViews();
        appStartAction(state);
    }

    private void appStartAction(Bundle state) {
        if (state == null) {
            greetUser();
            setFirstFragment();
        }
    }

    private void setFirstFragment() {
        setFragment(new HomeFragment(), false);
    }

    private void greetUser() {
        Toast.makeText(this, getString(R.string.ts_welcome), Toast.LENGTH_SHORT).show();
    }

    private void setViews() {
        setSupportActionBar(tbMain);
        setDl();
        setNv();
    }

    private void setDl() {
        ActionBarDrawerToggle toggle = getToggle();
        dlMain.addDrawerListener(toggle);
        toggle.syncState();
    }

    private ActionBarDrawerToggle getToggle() {
        return new ActionBarDrawerToggle(this, dlMain, tbMain,
                R.string.dt_opened, R.string.dt_closed) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (isNotReady || nvMain.getCheckedItem() == null) return;
                Fragment fragment = getFragment(nvMain.getCheckedItem());
                manageNewFragment(fragment);
            }
        };
    }

    private void setNv() {
        nvMain.setNavigationItemSelectedListener(getItemListener());
    }

    private NavigationView.OnNavigationItemSelectedListener getItemListener() {
        return item -> {
            onItemClick(item);
            return true;
        };
    }

    private void onItemClick(MenuItem item) {
        isNotReady = false;
        closeDrawer();
        setMenuItem(item.getItemId());
    }

    private Fragment getFragment(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.it_home:
                return HomeFragment.getInstance();
            case R.id.it_recipe:
                return RecipeChooseFragment.getInstance(false);
            case R.id.it_product:
                return ProductChooseFragment.getInstance();
            case R.id.it_shopping:
                return ShoppingShowFragment.getInstance();
            case R.id.it_export:
                return ExportFragment.getInstance();
            case R.id.it_import:
                return ImportFragment.getInstance();
            case R.id.it_credits:
                return CreditsFragment.getInstance();
            default:
                return null;
        }
    }

    private void manageNewFragment(Fragment fragment) {
        isNotReady = true;
        clearBackStack();
        setFragment(fragment, isOtherFragment(fragment));
    }

    private boolean isDrawerOpen() {
        return dlMain.isDrawerOpen(GravityCompat.START);
    }

    public void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        for (int i = Globals.DF_ZERO; i < manager.getBackStackEntryCount(); i++)
            manager.popBackStack();
    }

    public void setFragment(Fragment fragment, boolean withBackStack) {
        String name = fragment.getClass().getName();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_main, fragment);
        if (withBackStack) transaction.addToBackStack(name);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    private boolean isOtherFragment(Fragment fragment) {
        return !(fragment instanceof HomeFragment);
    }

    private void closeDrawer() {
        dlMain.closeDrawers();
    }

    public void setMenuItem(int id) {
        nvMain.getMenu().findItem(id).setChecked(true);
    }

    public void setTbTitle(String title) {
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        if (isDrawerOpen()) closeDrawer();
        else super.onBackPressed();
    }
}
