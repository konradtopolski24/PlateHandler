package com.fatiner.platehandler.fragments.main;

import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.RecipesAdapter;
import com.fatiner.platehandler.classes.Recipe;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.managers.database.DbOperations;
import com.fatiner.platehandler.managers.shared.SharedMainManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends PrimaryFragment {

    @BindView(R.id.rv_day)
    RecyclerView rvDay;
    @BindView(R.id.rv_recent)
    RecyclerView rvRecent;
    @BindView(R.id.cv_day)
    CardView cvDay;
    @BindView(R.id.cv_recent)
    CardView cvRecent;
    @BindView(R.id.iv_day)
    ImageView ivDay;
    @BindView(R.id.iv_recent)
    ImageView ivRecent;

    @OnClick(R.id.cv_day_hd)
    public void onClickFaAdd(){
        manageExpandCardView(cvDay, ivDay);
    }

    @OnClick(R.id.cv_recent_hd)
    public void onClickFbAdd(){
        manageExpandCardView(cvRecent, ivRecent);
    }

    public MainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tb_hm_welcome);
        setMenuItem(MainGlobals.ID_MAIN_DRAW_MAIN);
        checkDay();
        return view;
    }

    private void checkDay() {
        if(SharedMainManager.isSharedDishAvailable(getContext())){
            asyncReadDay();
        }
    }

    private void asyncReadDay(){
        new AsyncMain().execute(Type.DAY);
    }

    private void setRecent(){
        if(SharedMainManager.isSharedRecentAvailable(getContext())){
            asyncReadRecent();
        } else {
            SharedMainManager.setSharedRecent(getContext(),
                    TypeManager.recentToJson(new ArrayList<Integer>()));
        }
    }

    private void asyncReadRecent() {
        new AsyncMain().execute(Type.RECENT);
    }

    private class AsyncMain extends AsyncTask<Type, Void, Boolean>{

        private ArrayList<Recipe> dish;
        private ArrayList<Recipe> recent;
        private Type type;

        protected void onPreExecute(){
            dish = new ArrayList<>();
            recent = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Type... types) {
            type = types[MainGlobals.INT_STARTING_VAR_INIT];
            try{
                switch(type) {
                    case DAY:
                        DbOperations.readDay(getContext(), dish,
                                SharedMainManager.getSharedDish(getContext()));
                        break;
                    case RECENT:
                        DbOperations.readRecent(getContext(), recent,
                                TypeManager.jsonToRecent(SharedMainManager.getSharedRecent(getContext())));
                        break;
                }
                return true;
            }catch (SQLiteException e){
                return false;
            }
        }

        protected void onPostExecute(Boolean success){
            if(success){
                switch(type) {
                    case DAY:
                        readDayFinished(dish);
                        break;
                    case RECENT:
                        readRecentFinished(recent);
                        break;
                }
            }
        }
    }

    private void readDayFinished(ArrayList<Recipe> dish) {
        setRecyclerView(
                rvDay,
                getLinearLayoutManager(LinearLayoutManager.HORIZONTAL),
                new RecipesAdapter(getContext(), dish, false)
        );
        setRecent();
    }

    private void readRecentFinished(ArrayList<Recipe> recent) {
        setRecyclerView(
                rvRecent,
                getLinearLayoutManager(LinearLayoutManager.HORIZONTAL),
                new RecipesAdapter(getContext(), recent, false));
    }

    private enum Type {
        DAY, RECENT
    }
}
