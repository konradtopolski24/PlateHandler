package com.fatiner.platehandler.fragments.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.RecipesAdapter;
import com.fatiner.platehandler.classes.Recipe;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.managers.database.DbSuccessManager;
import com.fatiner.platehandler.managers.shared.SharedMainManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends PrimaryFragment {

    @BindView(R.id.recyc_dish_frag_main)
    RecyclerView recycDish;
    @BindView(R.id.recyc_recent_frag_main)
    RecyclerView recycRecent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tool_main_frag_main);
        setMenuItem(MainGlobals.ID_MAIN_DRAW_MAIN);
        setDate();
        setRecent();
        return view;
    }

    private void setDate(){
        if(SharedMainManager.isSharedDateAvailable(getContext())){
            checkCurrentDate();
        } else {
            SharedMainManager.setSharedDate(getContext(),
                    TypeManager.dateToString(Calendar.getInstance().getTime()));
        }
    }

    private void checkCurrentDate(){
        String currentTime = TypeManager.dateToString(Calendar.getInstance().getTime());
        if(SharedMainManager.getSharedDate(getContext()).equals(currentTime)){
            setDish();
        } else {
            SharedMainManager.setSharedDate(getContext(),
                    TypeManager.dateToString(Calendar.getInstance().getTime()));
            readIds();
        }
    }

    private void setDish(){
        if(SharedMainManager.isSharedDishAvailable(getContext())){
            readDish();
        } else {
            readIds();
        }
    }

    private void readDish(){
        new AsyncReadDish().execute(SharedMainManager.getSharedDish(getContext()));
    }

    private void readIds(){
        new AsyncReadIds().execute();
    }

    private void chooseNewDishId(ArrayList<Integer> ids){
        if(ids.isEmpty()) return;
        int currentId = getCurrentDishId();
        int newId = ids.get(new Random().nextInt(ids.size()));
        while(currentId == newId){
            newId = ids.get(new Random().nextInt(ids.size()));
        }
        SharedMainManager.setSharedDish(getContext(), newId);
        readDish();
    }

    private int getCurrentDishId(){
        if(SharedMainManager.isSharedDishAvailable(getContext())){
            return SharedMainManager.getSharedDish(getContext());
        } else {
            return MainGlobals.INT_STARTING_VAR_INIT;
        }
    }

    private void setRecent(){
        if(SharedMainManager.isSharedRecentAvailable(getContext())){
            new AsyncReadRecent().execute();
        } else {
            SharedMainManager.setSharedRecent(getContext(),
                    TypeManager.recentToJson(new ArrayList<Integer>()));
        }
    }

    private class AsyncReadIds extends AsyncTask<Void, Void, Boolean>{

        private ArrayList<Integer> ids;

        protected void onPreExecute(){
            ids = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DbSuccessManager.readIds(getContext(), ids);
        }

        protected void onPostExecute(Boolean success){
            if(success){
                chooseNewDishId(ids);
            } else {
                showShortToast(R.string.toast_fail_async_readids);
            }
        }
    }

    private class AsyncReadDish extends AsyncTask<Integer, Void, Boolean>{

        private ArrayList<Recipe> dish;

        protected void onPreExecute(){
            dish = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Integer... id) {
            return DbSuccessManager.readDish(getContext(), dish,
                    id[MainGlobals.INT_STARTING_VAR_INIT]);
        }

        protected void onPostExecute(Boolean success){
            if(success){
                setRecyclerView(
                        recycDish,
                        getLinearLayoutManager(LinearLayoutManager.HORIZONTAL),
                        new RecipesAdapter(getContext(), dish, false)
                );
            } else {
                showShortToast(R.string.toast_fail_async_readdish);
            }
        }
    }

    private class AsyncReadRecent extends AsyncTask<Void, Void, Boolean> {

        private ArrayList<Recipe> recent;

        protected void onPreExecute(){
            recent = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DbSuccessManager.readRecent(getContext(), recent,
                    TypeManager.jsonToRecent(SharedMainManager.getSharedRecent(getContext())));
        }

        protected void onPostExecute(Boolean success){
            if(success){
                setRecyclerView(
                        recycRecent,
                        getLinearLayoutManager(LinearLayoutManager.HORIZONTAL),
                        new RecipesAdapter(getContext(), recent, false));
            } else {
                showShortToast(R.string.toast_fail_async_readrecn);
            }
        }
    }
}
