package com.fatiner.platehandler.fragments.recipe;

import android.content.DialogInterface;
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

    public ShowRecipeFragment(){}

    @BindView(R.id.text_name_frag_shwrecp)
    TextView textName;
    @BindView(R.id.text_author_frag_shwrecp)
    TextView textAuthor;
    @BindView(R.id.text_serving_frag_shwrecp)
    TextView textServing;
    @BindView(R.id.text_time_frag_shwrecp)
    TextView textTime;
    @BindView(R.id.text_difficulty_frag_shwrecp)
    TextView textDifficulty;
    @BindView(R.id.text_country_frag_shwrecp)
    TextView textCountry;
    @BindView(R.id.text_type_frag_shwrecp)
    TextView textType;
    @BindView(R.id.text_preferences_frag_shwrecp)
    TextView textPreferences;
    @BindViews({
            R.id.image_spiciness0_frag_shwrecp,
            R.id.image_spiciness1_frag_shwrecp,
            R.id.image_spiciness2_frag_shwrecp})
    List<ImageView> arrayImageSpiciness;
    @BindView(R.id.switch_favorite_frag_shwrecp)
    Switch switchFavorite;
    @BindView(R.id.recyc_categories_frag_shwrecp)
    RecyclerView recycCategories;
    @BindView(R.id.recyc_steps_frag_shwrecp)
    RecyclerView recycSteps;
    @BindView(R.id.image_photo_frag_shwrecp)
    ImageView imagePhoto;

    @OnCheckedChanged(R.id.switch_favorite_frag_shwrecp)
    public void onCheckedChangedSwitchFavorite(boolean checked){
        RecipeDetails.getRecipe().setFavorite(checked);
        new AsyncUpdateRecipeFavorite().execute();
    }

    @OnClick(R.id.butt_calculate_frag_shwrecp)
    public void onClickButtCalculate(){
        setFragment(new CalculateRecipeFragment());
    }

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
            new AsyncReadRecipe().execute(getRecipeId());
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
        textName.setText(text);
    }

    private void setTextAuthor(String text){
        textAuthor.setText(text);
    }

    private void setTextServing(int serving){
        String text = String.valueOf(serving);
        textServing.setText(text);
    }

    private void setTextTime(String time){
        textTime.setText(time);
    }

    private void setTextDifficulty(int difficulty){
        String[] arrayDifficulty = getStringArray(R.array.array_difficulty_frag_recipe);
        textDifficulty.setText(arrayDifficulty[difficulty]);
    }

    private void setImageSpiciness(int spiciness){
        for(int i = MainGlobals.INT_STARTING_VAR_INIT; i < spiciness; i++){
            arrayImageSpiciness.get(i).setVisibility(View.VISIBLE);
        }
    }

    private void setTextCountry(int country){
        String[] arrayCountry = getStringArray(R.array.array_country_frag_recipe);
        textCountry.setText(arrayCountry[country]);
    }

    private void setTextType(int type){
        String[] arrayType = getStringArray(R.array.array_type_frag_recipe);
        textType.setText(arrayType[type]);
    }

    private void setTextPreferences(boolean isMeat){
        if(isMeat){
            textPreferences.setText(getString(R.string.radio_meat_frag_recipe));
        } else {
            textPreferences.setText(getString(R.string.radio_vegetarian_frag_recipe));
        }
    }

    private void setSwitchFavorite(boolean isFavorite){
        switchFavorite.setChecked(isFavorite);
    }

    private void setImagePhoto(String encodedImage){
        if(encodedImage == null) return;
        imagePhoto.setImageBitmap(TypeManager.base64StringToBitmap(encodedImage));
    }

    private DialogInterface.OnClickListener getDialogListener(){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        new AsyncDeleteRecipe().execute();
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
                showAlertDialog(R.string.dial_delete_frag_recipe, getDialogListener());
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private class AsyncReadRecipe extends AsyncTask<Integer, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Integer... id) {
            return DbSuccessManager.readRecipe(getContext(), RecipeDetails.getRecipe(),
                    id[MainGlobals.INT_STARTING_VAR_INIT]);
        }

        protected void onPostExecute(Boolean success){
            if(success){
                loadPhoto();
                setRecipeInfo();
                setRecyclerView(
                        recycCategories,
                        getGridLayoutManager(MainGlobals.RECYC_SPAN_FRAG_ADDINGRED),
                        new CategoryAdapter(getContext(),
                                RecipeDetails.getRecipe().getCategories(), true)
                );
                setRecyclerView(
                        recycSteps,
                        getLinearLayoutManager(LinearLayoutManager.HORIZONTAL),
                        new StepAdapter(getContext(),
                                RecipeDetails.getRecipe().getSteps(), true)
                );
                setRecipeInRecent();
            } else {
                showShortToast(R.string.toast_fail_async_readrec);
            }
        }
    }

    private class AsyncUpdateRecipeFavorite extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DbSuccessManager.updatedRecipeFavorite(getContext());
        }
    }

    private class AsyncDeleteRecipe extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DbSuccessManager.deletedRecipe(getContext());
        }

        protected void onPostExecute(Boolean success){
            if(success){
                new AsyncReadAuthors().execute();
            } else {
                showShortToast(R.string.toast_fail_async_delrec);
            }
        }
    }

    private class AsyncReadAuthors extends AsyncTask<Void, Void, Boolean>{

        ArrayList<String> authors;

        protected void onPreExecute(){
            authors = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DbSuccessManager.readAuthors(getContext(), authors);
        }

        protected void onPostExecute(Boolean success){
            if(success){
                removeUnavailableAuthor(authors);
                deleteRecentId();
                recipeSuccess(R.string.snack_deleted_frag_recipe);
            } else {
                showShortToast(R.string.toast_fail_async_insrec);
            }
        }
    }
}
