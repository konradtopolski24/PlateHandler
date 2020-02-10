package com.fatiner.platehandler.fragments.recipe;

import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.fatiner.platehandler.managers.database.DbOperations;
import com.fatiner.platehandler.managers.shared.SharedMainManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class ShowRecipeFragment extends PrimaryFragment {

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.cv_header)
    CardView cvHeader;
    @BindView(R.id.cv_info)
    CardView cvInfo;
    @BindView(R.id.cv_categories)
    CardView cvCategories;
    @BindView(R.id.cv_steps)
    CardView cvSteps;
    @BindView(R.id.cv_info_hd)
    CardView cvInfoHd;
    @BindView(R.id.cv_categories_hd)
    CardView cvCategoriesHd;
    @BindView(R.id.cv_steps_hd)
    CardView cvStepsHd;
    @BindView(R.id.iv_info)
    ImageView ivInfo;
    @BindView(R.id.iv_categories)
    ImageView ivCategories;
    @BindView(R.id.iv_steps)
    ImageView ivSteps;
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
    @BindView(R.id.tv_preference)
    TextView tvPreference;
    @BindViews({
            R.id.iv_spiciness0,
            R.id.iv_spiciness1,
            R.id.iv_spiciness2,
            R.id.iv_spiciness3})
    List<ImageView> arrayIvSpiciness;
    @BindView(R.id.cb_favorite)
    CheckBox cbFavorite;
    @BindView(R.id.rv_categories)
    RecyclerView rvCategories;
    @BindView(R.id.rv_steps)
    RecyclerView rvSteps;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.iv_country_dw)
    ImageView ivCountryDw;
    @BindView(R.id.iv_type_dw)
    ImageView ivTypeDw;
    @BindView(R.id.iv_preference_dw)
    ImageView ivPreferencesDw;

    @OnClick(R.id.cv_info_hd)
    public void onClickFabAdd(){
        manageExpandCardView(cvInfo, ivInfo);
    }

    @OnClick(R.id.cv_categories_hd)
    public void onClickFaAdd(){
        manageExpandCardView(cvCategories, ivCategories);
    }

    @OnClick(R.id.cv_steps_hd)
    public void onClickFbAdd(){
        manageExpandCardView(cvSteps, ivSteps);
    }

    @OnCheckedChanged(R.id.cb_favorite)
    public void onCheckedChangedSwFavorite(boolean checked){
        RecipeDetails.getRecipe().setFavorite(checked);
        new AsyncShowRecipe().execute(Type.FAVORITE);
    }

    public ShowRecipeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_recipe, container, false);
        ButterKnife.bind(this, view);
        showView(pbLoading);
        hideCardViews();
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
        setImageCountry(recipe.getCountry());
        setTextType(recipe.getType());
        setImageType(recipe.getType());
        setTextPreferences(recipe.getPreference());
        setImagePreferences(recipe.getPreference());
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
        String[] arrayDifficulty = getStringArray(R.array.tx_difficulty);
        tvDifficulty.setText(arrayDifficulty[difficulty]);
    }

    private void setImageSpiciness(int spiciness){
        for(int i = MainGlobals.INT_STARTING_VAR_INIT; i < spiciness + 1; i++){
            arrayIvSpiciness.get(i).setVisibility(View.VISIBLE);
        }
    }

    private void setTextCountry(int country){
        String[] arrayCountry = getStringArray(R.array.tx_country);
        tvCountry.setText(arrayCountry[country]);
    }

    private void setImageCountry(int country){
        TypedArray arrayType = getResources().obtainTypedArray(R.array.dw_country);
        int resource = arrayType.getResourceId(country, -1);
        ivCountryDw.setImageResource(resource);
    }

    private void setTextType(int type){
        String[] arrayType = getStringArray(R.array.tx_recipe);
        tvType.setText(arrayType[type]);
    }

    private void setImageType(int type){
        TypedArray arrayType = getResources().obtainTypedArray(R.array.dw_recipe);
        int resource = arrayType.getResourceId(type, -1);
        ivTypeDw.setImageResource(resource);
    }

    private void setTextPreferences(boolean isMeat){
        if(isMeat){
            tvPreference.setText(getString(R.string.ct_meat));
        } else {
            tvPreference.setText(getString(R.string.ct_vegetarian));
        }
    }

    private void setImagePreferences(boolean isMeat){
        TypedArray arrayType = getResources().obtainTypedArray(R.array.dw_preference);
        int resource = arrayType.getResourceId(TypeManager.booleanToInteger(isMeat), -1);
        ivPreferencesDw.setImageResource(resource);
    }

    private void setSwitchFavorite(boolean isFavorite){
        cbFavorite.setChecked(isFavorite);
    }

    private void setImagePhoto(String encodedImage){
        if(encodedImage == null) return;
        ivPhoto.setVisibility(View.VISIBLE);
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
        inflater.inflate(R.menu.rp_show, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.it_calculate:
                setFragment(new CalculateRecipeFragment());
                return true;
            case R.id.it_edit:
                Fragment fragment = new ManageRecipeFragment();
                setBoolInBundle(fragment, true, BundleGlobals.BUND_BOOL_FRAG_ADDREC);
                setFragment(fragment);
                return true;
            case R.id.it_remove:
                showAlertDialog(R.string.dg_rp_remove, getDialogListener());
                return true;
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
                Recipe recipe = RecipeDetails.getRecipe();
                type = types[MainGlobals.INT_STARTING_VAR_INIT];
                switch(type) {
                    case READ:
                        DbOperations.readRecipe(getContext(),
                                recipe, getRecipeId());
                        break;
                    case DELETE:
                        DbOperations.deletedRecipe(getContext(), recipe.getId());
                        DbOperations.readAuthors(getContext(), authors);
                        break;
                    case FAVORITE:
                        DbOperations.updatedRecipeFavorite(getContext(), recipe.getFavorite(), recipe.getId());
                        break;
                }
                return true;
            }catch (SQLiteException e){
                showShortToast(R.string.ts_database);
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
                getLayoutManagerNoScroll(LinearLayoutManager.VERTICAL),
                new CategoryAdapter(getContext(),
                        RecipeDetails.getRecipe().getCategories(), true, null)
        );
        setRecyclerView(
                rvSteps,
                getLayoutManagerNoScroll(LinearLayoutManager.VERTICAL),
                new StepAdapter(getContext(),
                        RecipeDetails.getRecipe().getSteps(), true, null)
        );
        setRecipeInRecent();
        hideView(pbLoading);
        showCardViews();
    }

    private void finishedDeleteRecipe(ArrayList<String> authors) {
        removeUnavailableAuthor(authors);
        deleteRecentId();
        recipeSuccess(R.string.sb_rp_remove);
    }

    private void hideCardViews() {
        removeView(cvHeader);
        removeView(cvInfoHd);
        removeView(cvInfo);
        removeView(cvCategoriesHd);
        removeView(cvCategories);
        removeView(cvStepsHd);
        removeView(cvSteps);
    }

    private void showCardViews() {
        showView(cvHeader);
        showView(cvInfoHd);
        showView(cvInfo);
        showView(cvCategoriesHd);
        showView(cvCategories);
        showView(cvStepsHd);
        showView(cvSteps);
    }

    private enum Type {
        READ, DELETE, FAVORITE
    }
}
