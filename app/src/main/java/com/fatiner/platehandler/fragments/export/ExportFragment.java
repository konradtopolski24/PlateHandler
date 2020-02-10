package com.fatiner.platehandler.fragments.export;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.classes.Ingredient;
import com.fatiner.platehandler.classes.Product;
import com.fatiner.platehandler.classes.Recipe;
import com.fatiner.platehandler.classes.Step;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.DbGlobals;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.managers.database.DbOperations;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExportFragment extends PrimaryFragment {

    @BindView(R.id.et_export)
    EditText etExport;
    @BindView(R.id.tv_saved)
    TextView tvSaved;
    @BindView(R.id.tv_unsaved)
    TextView tvUnsaved;
    @BindView(R.id.cv_info)
    CardView cvInfo;
    @BindView(R.id.iv_info)
    ImageView ivInfo;

    @OnClick(R.id.cv_info_hd)
    public void onClickFbAdd(){
        manageExpandCardView(cvInfo, ivInfo);
    }

    @OnClick(R.id.fab_export)
    public void onClickBtExport(){
        hideKeyboard();
        if(isEtEmpty(etExport)){
            showShortToast(R.string.ts_export);
        } else {
            showAlertDialog(R.string.dg_ex_add, getDialogListener());
        }
    }

    public ExportFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_export, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tb_ex_database);
        setMenuItem(MainGlobals.ID_EXPORT_DRAW_MAIN);
        String saved = getString(R.string.nv_recipe) + MainGlobals.STR_ENTER_OBJ_INIT + getString(R.string.nv_product);
        setTv(tvSaved, saved);
        String unsaved = getString(R.string.nv_shopping) + MainGlobals.STR_ENTER_OBJ_INIT + getString(R.string.hd_hm_day) + MainGlobals.STR_ENTER_OBJ_INIT + getString(R.string.hd_hm_recent);
        setTv(tvUnsaved, unsaved);
        return view;
    }

    private class AsyncExport extends AsyncTask<Void, Void, Boolean> {

        ArrayList<Recipe> recipes;
        ArrayList<Ingredient> ingredients;
        ArrayList<Step> steps;
        ArrayList<Product> products;

        protected void onPreExecute(){
            recipes = new ArrayList<>();
            ingredients = new ArrayList<>();
            steps = new ArrayList<>();
            products = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                DbOperations.readRecipes(getContext(), recipes, null, null);
                DbOperations.readIngredients(getContext(), ingredients, null, null);
                DbOperations.readSteps(getContext(), steps, null, null);
                DbOperations.readProducts(getContext(), products, null, null);
                return true;
            }catch (SQLiteException e){
                showShortToast(R.string.ts_database);
                return false;
            }
        }

        protected void onPostExecute(Boolean success){
            if(success){
                readDatabaseFinished(recipes, ingredients, steps, products);
            }
        }
    }

    private void readDatabaseFinished(ArrayList<Recipe> recipes, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, ArrayList<Product> products) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        createRecipeSheet(workbook, recipes);
        createIngredientSheet(workbook, ingredients);
        createStepSheet(workbook, steps);
        createProductSheet(workbook, products);
        saveWorkbookFile(workbook);
        showShortSnack(R.string.sb_ex_add);
        popFragment();
    }

    private HSSFSheet getEmptySheet(HSSFWorkbook workbook, String name, ArrayList<String> columns) {
        HSSFSheet sheet = workbook.createSheet(name);
        HSSFRow headerRow = sheet.createRow(MainGlobals.INT_STARTING_VAR_INIT);
        for(int i = MainGlobals.INT_STARTING_VAR_INIT; i < columns.size(); i++){
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(columns.get(i));
        }
        return sheet;
    }

    private void createRecipeSheet(HSSFWorkbook workbook, ArrayList<Recipe> recipes) {
        ArrayList<String> columns = DbGlobals.getRecipesColumns();
        HSSFSheet sheet = getEmptySheet(workbook,
                DbGlobals.TAB_RECIPES_DB_MAIN, columns);

        int id = MainGlobals.INT_INCREMENT_VAR_INIT;
        for(Recipe recipe : recipes){
            HSSFRow row = sheet.createRow(id);
            row.createCell(columns.indexOf(DbGlobals.COL_ID_TAB_RECIPES)).setCellValue(recipe.getId());
            row.createCell(columns.indexOf(DbGlobals.COL_NAME_TAB_RECIPES)).setCellValue(recipe.getName());
            row.createCell(columns.indexOf(DbGlobals.COL_AUTHOR_TAB_RECIPES)).setCellValue(recipe.getAuthor());
            row.createCell(columns.indexOf(DbGlobals.COL_SERVING_TAB_RECIPES)).setCellValue(recipe.getServing());
            row.createCell(columns.indexOf(DbGlobals.COL_TIME_TAB_RECIPES)).setCellValue(recipe.getTime());
            row.createCell(columns.indexOf(DbGlobals.COL_DIFFICULTY_TAB_RECIPES)).setCellValue(recipe.getDifficulty());
            row.createCell(columns.indexOf(DbGlobals.COL_SPICINESS_TAB_RECIPES)).setCellValue(recipe.getSpiciness());
            row.createCell(columns.indexOf(DbGlobals.COL_COUNTRY_TAB_RECIPES)).setCellValue(recipe.getCountry());
            row.createCell(columns.indexOf(DbGlobals.COL_TYPE_TAB_RECIPES)).setCellValue(recipe.getType());
            row.createCell(columns.indexOf(DbGlobals.COL_PREFERENCES_TAB_RECIPES)).setCellValue(TypeManager.booleanToInteger(recipe.getPreference()));
            row.createCell(columns.indexOf(DbGlobals.COL_FAVORITE_TAB_RECIPES)).setCellValue(TypeManager.booleanToInteger(recipe.getFavorite()));
            id++;
        }
    }

    private void createIngredientSheet(HSSFWorkbook workbook, ArrayList<Ingredient> ingredients) {
        ArrayList<String> columns = DbGlobals.getIngredientsColumns();
        HSSFSheet sheet = getEmptySheet(workbook,
                DbGlobals.TAB_INGREDIENTS_DB_MAIN, columns);

        int id = MainGlobals.INT_INCREMENT_VAR_INIT;
        for(Ingredient ingredient : ingredients){
            HSSFRow row = sheet.createRow(id);
            row.createCell(columns.indexOf(DbGlobals.COL_ID_TAB_INGREDIENTS)).setCellValue(ingredient.getId());
            row.createCell(columns.indexOf(DbGlobals.COL_IDREC_TAB_INGREDIENTS)).setCellValue(ingredient.getIdRec());
            row.createCell(columns.indexOf(DbGlobals.COL_IDPROD_TAB_INGREDIENTS)).setCellValue(ingredient.getIdProd());
            row.createCell(columns.indexOf(DbGlobals.COL_AMOUNT_TAB_INGREDIENTS)).setCellValue(ingredient.getAmount());
            row.createCell(columns.indexOf(DbGlobals.COL_MEASURE_TAB_INGREDIENTS)).setCellValue(ingredient.getMeasure());
            row.createCell(columns.indexOf(DbGlobals.COL_CATEGORY_TAB_INGREDIENTS)).setCellValue(ingredient.getCategory());
            id++;
        }
    }

    private void createStepSheet(HSSFWorkbook workbook, ArrayList<Step> steps) {
        ArrayList<String> columns = DbGlobals.getStepsColumns();
        HSSFSheet sheet = getEmptySheet(workbook,
                DbGlobals.TAB_STEPS_DB_MAIN, columns);

        int id = MainGlobals.INT_INCREMENT_VAR_INIT;
        for(Step step : steps){
            HSSFRow row = sheet.createRow(id);
            row.createCell(columns.indexOf(DbGlobals.COL_ID_TAB_STEPS)).setCellValue(step.getId());
            row.createCell(columns.indexOf(DbGlobals.COL_IDREC_TAB_STEPS)).setCellValue(step.getIdRec());
            row.createCell(columns.indexOf(DbGlobals.COL_INSTRUCTION_TAB_STEPS)).setCellValue(step.getInstruction());
            id++;
        }
    }

    private void createProductSheet(HSSFWorkbook workbook, ArrayList<Product> products) {
        ArrayList<String> columns = DbGlobals.getProductsColumns();
        HSSFSheet sheet = getEmptySheet(workbook,
                DbGlobals.TAB_PRODUCTS_DB_MAIN, DbGlobals.getProductsColumns());

        int id = MainGlobals.INT_INCREMENT_VAR_INIT;
        for(Product product : products){
            HSSFRow row = sheet.createRow(id);
            row.createCell(columns.indexOf(DbGlobals.COL_ID_TAB_PRODUCTS)).setCellValue(product.getId());
            row.createCell(columns.indexOf(DbGlobals.COL_NAME_TAB_PRODUCTS)).setCellValue(product.getName());
            row.createCell(columns.indexOf(DbGlobals.COL_TYPE_TAB_PRODUCTS)).setCellValue(product.getType());
            row.createCell(columns.indexOf(DbGlobals.COL_CARBOHYDRATES_TAB_PRODUCTS)).setCellValue(product.getCarbohydrates());
            row.createCell(columns.indexOf(DbGlobals.COL_PROTEIN_TAB_PRODUCTS)).setCellValue(product.getProtein());
            row.createCell(columns.indexOf(DbGlobals.COL_FAT_TAB_PRODUCTS)).setCellValue(product.getFat());
            id++;
        }
    }

    private void saveWorkbookFile(HSSFWorkbook workbook) {
        File file = new File(getContext().getExternalFilesDir(null),
                etExport.getText().toString() + MainGlobals.FILE_XLS_SAVE_WORKBOOK);
        try {
            FileOutputStream output = new FileOutputStream(file);
            workbook.write(output);
            output.close();
        } catch (IOException e) {
            //
        }
    }

    private DialogInterface.OnClickListener getDialogListener(){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        new AsyncExport().execute();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
    }

    private boolean isEtEmpty(EditText et) {
        return et.getText().toString().isEmpty();
    }
}
