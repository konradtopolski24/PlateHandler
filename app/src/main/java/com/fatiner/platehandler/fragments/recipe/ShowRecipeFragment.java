package com.fatiner.platehandler.fragments.recipe;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.CategoryAdapter;
import com.fatiner.platehandler.adapters.StepAdapter;
import com.fatiner.platehandler.classes.Recipe;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.fragments.recipe.manage.ManageRecipeFragment;
import com.fatiner.platehandler.globals.BundleGlobals;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.managers.ImageManager;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.managers.database.DbSuccessManager;
import com.fatiner.platehandler.managers.shared.SharedMainManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class ShowRecipeFragment extends PrimaryFragment {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_serving)
    TextView tvServing;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_difficulty)
    TextView tvDifficulty;
    @BindView(R.id.tv_country)
    TextView tvCountry;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_preferences)
    TextView tvPreferences;
    @BindViews({
            R.id.iv_spiciness0,
            R.id.iv_spiciness1,
            R.id.iv_spiciness2})
    List<ImageView> arrayIvSpiciness;
    @BindView(R.id.sw_favorite)
    Switch swFavorite;
    @BindView(R.id.rv_categories)
    RecyclerView rvCategories;
    @BindView(R.id.rv_steps)
    RecyclerView rvSteps;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;

    @OnCheckedChanged(R.id.sw_favorite)
    public void onCheckedChangedSwFavorite(boolean checked){
        RecipeDetails.getRecipe().setFavorite(checked);
        new AsyncShowRecipe().execute(Type.FAVORITE);
    }

    @OnClick(R.id.bt_calculate)
    public void onClickBtCalculate(){
        setFragment(new CalculateRecipeFragment());
    }

    public ShowRecipeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_recipe, container, false);
        ButterKnife.bind(this, view);
        resetRecipeDetails();
        setRecipe();
        setToolbarTitle(RecipeDetails.getRecipe().getName());
        setMenuItem(MainGlobals.ID_RECIPES_DRAW_MAIN);
        setHasOptionsMenu(true);
        return view;
    }

    private void setRecipe(){
        if(isBundleNotEmpty()){
            new AsyncShowRecipe().execute(Type.READ);
        }
    }

    private int getRecipeId(){
        return getIntFromBundle(BundleGlobals.BUND_ID_FRAG_SHOWREC);
    }

    private void loadPhoto(){
        Recipe recipe = RecipeDetails.getRecipe();
        Bitmap bitmap = ImageManager.getImageFromStorage(ImageManager.getImageRecipeName(recipe.getId()));
        if(bitmap == null) return;
        recipe.setEncodedImage(TypeManager.bitmapToBase64String(bitmap));
    }

    private void setRecipeInfo(){
        Recipe recipe = RecipeDetails.getRecipe();
        setTextName(recipe.getName());
        setTextAuthor(recipe.getAuthor());
        setTextServing(recipe.getServing());
        setTextTime(recipe.getTime());
        setTextDifficulty(recipe.getDifficulty());
        setImageSpiciness(recipe.getSpiciness());
        setTextCountry(recipe.getCountry());
        setTextType(recipe.getType());
        setTextPreferences(recipe.getPreference());
        setSwitchFavorite(recipe.getFavorite());
        setImagePhoto(recipe.getEncodedImage());
    }

    private void setTextName(String text){
        tvName.setText(text);
    }

    private void setTextAuthor(String text){
        tvAuthor.setText(text);
    }

    private void setTextServing(int serving){
        String text = String.valueOf(serving);
        tvServing.setText(text);
    }

    private void setTextTime(String time){
        tvTime.setText(time);
    }

    private void setTextDifficulty(int difficulty){
        String[] arrayDifficulty = getStringArray(R.array.ar_difficulty);
        tvDifficulty.setText(arrayDifficulty[difficulty]);
    }

    private void setImageSpiciness(int spiciness){
        for(int i = MainGlobals.INT_STARTING_VAR_INIT; i < spiciness; i++){
            arrayIvSpiciness.get(i).setVisibility(View.VISIBLE);
        }
    }

    private void setTextCountry(int country){
        String[] arrayCountry = getStringArray(R.array.ar_country);
        tvCountry.setText(arrayCountry[country]);
    }

    private void setTextType(int type){
        String[] arrayType = getStringArray(R.array.ar_recipe_type);
        tvType.setText(arrayType[type]);
    }

    private void setTextPreferences(boolean isMeat){
        if(isMeat){
            tvPreferences.setText(getString(R.string.rb_meat));
        } else {
            tvPreferences.setText(getString(R.string.rb_vegetarian));
        }
    }

    private void setSwitchFavorite(boolean isFavorite){
        swFavorite.setChecked(isFavorite);
    }

    private void setImagePhoto(String encodedImage){
        if(encodedImage == null) return;
        ivPhoto.setImageBitmap(TypeManager.base64StringToBitmap(encodedImage));
    }

    private DialogInterface.OnClickListener getDialogListener(){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        new AsyncShowRecipe().execute(Type.DELETE);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
    }

    private void setRecipeInRecent(){
        if(SharedMainManager.isSharedRecentAvailable(getContext())){
            ArrayList<Integer> recent = TypeManager.jsonToRecent(
                    SharedMainManager.getSharedRecent(getContext()));
            checkIfContains(recent);
            checkSize(recent);
            recent.add(MainGlobals.INT_STARTING_VAR_INIT, getRecipeId());
            SharedMainManager.setSharedRecent(getContext(), TypeManager.recentToJson(recent));
        }
    }

    private void checkIfContains(ArrayList<Integer> recent){
        if(recent.contains(getRecipeId())){
            Integer id = getRecipeId();
            recent.remove(id);
        }
    }

    private void checkSize(ArrayList<Integer> recent){
        if(recent.size() == MainGlobals.MAX_RECENT_FRAG_MAIN){
            recent.remove(MainGlobals.LAST_RECENT_FRAG_MAIN);
        }
    }

    private void deleteRecentId(){
        if(SharedMainManager.isSharedRecentAvailable(getContext())){
            ArrayList<Integer> recent = TypeManager.jsonToRecent(SharedMainManager.getSharedRecent(getContext()));
            Integer id = RecipeDetails.getRecipe().getId();
            recent.remove(id);
            SharedMainManager.setSharedRecent(getContext(), TypeManager.recentToJson(recent));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_shwrecp, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.item_edit_menu_shwrecp:
                Fragment fragment = new ManageRecipeFragment();
                setBoolInBundle(fragment, true, BundleGlobals.BUND_BOOL_FRAG_ADDREC);
                setFragment(fragment);
                return true;
            case R.id.item_delete_menu_shwrecp:
                showAlertDialog(R.string.dg_recipe_delete, getDialogListener());
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private class AsyncShowRecipe extends AsyncTask<Type, Void, Boolean>{

        private Type type;
        private ArrayList<String> authors;

        protected void onPreExecute(){
            authors = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Type... types) {
            try{
                type = types[MainGlobals.INT_STARTING_VAR_INIT];
                switch(type) {
                    case READ:
                        DbSuccessManager.readRecipe(getContext(),
                                RecipeDetails.getRecipe(), getRecipeId());
                        break;
                    case DELETE:
                        DbSuccessManager.deletedRecipe(getContext());
                        DbSuccessManager.readAuthors(getContext(), authors);
                        break;
                    case FAVORITE:
                        DbSuccessManager.updatedRecipeFavorite(getContext());
                        break;
                }
                return true;
            }catch (SQLiteException e){
                showShortToast(R.string.ts_db_error);
                return false;
            }
        }

        protected void onPostExecute(Boolean success){
            if(success){
                switch(type) {
                    case READ:
                        finishedReadRecipe();
                        break;
                    case DELETE:
                        finishedDeleteRecipe(authors);
                        break;
                }
            }
        }
    }

    private void finishedReadRecipe() {
        loadPhoto();
        setRecipeInfo();
        setRecyclerView(
                rvCategories,
                getGridLayoutManager(MainGlobals.RECYC_SPAN_FRAG_ADDINGRED),
                new CategoryAdapter(getContext(),
                        RecipeDetails.getRecipe().getCategories(), true)
        );
        setRecyclerView(
                rvSteps,
                getLinearLayoutManager(LinearLayoutManager.HORIZONTAL),
                new StepAdapter(getContext(),
                        RecipeDetails.getRecipe().getSteps(), true)
        );
        setRecipeInRecent();
    }

    private void finishedDeleteRecipe(ArrayList<String> authors) {
        removeUnavailableAuthor(authors);
        deleteRecentId();
        recipeSuccess(R.string.sb_recipe_deleted);
    }

    private enum Type {
        READ, DELETE, FAVORITE
    }
}
