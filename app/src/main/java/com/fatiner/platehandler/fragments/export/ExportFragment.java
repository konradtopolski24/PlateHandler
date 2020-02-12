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
        setMenuItem(MainGlobals.ID_EXPORT);
        String saved = getString(R.string.nv_recipe) + MainGlobals.SN_ENTER + getString(R.string.nv_product);
        setTv(tvSaved, saved);
        String unsaved = getString(R.string.nv_shopping) + MainGlobals.SN_ENTER + getString(R.string.hd_hm_day) + MainGlobals.SN_ENTER + getString(R.string.hd_hm_recent);
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
        HSSFRow headerRow = sheet.createRow(MainGlobals.DF_ZERO);
        for(int i = MainGlobals.DF_ZERO; i < columns.size(); i++){
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(columns.get(i));
        }
        return sheet;
    }

    private void createRecipeSheet(HSSFWorkbook workbook, ArrayList<Recipe> recipes) {
        ArrayList<String> columns = DbGlobals.getRecipesColumns();
        HSSFSheet sheet = getEmptySheet(workbook,
                DbGlobals.TB_RECIPE, columns);

        int id = MainGlobals.DF_INCREMENT;
        for(Recipe recipe : recipes){
            HSSFRow row = sheet.createRow(id);
            row.createCell(columns.indexOf(DbGlobals.CL_RP_ID)).setCellValue(recipe.getId());
            row.createCell(columns.indexOf(DbGlobals.CL_RP_NAME)).setCellValue(recipe.getName());
            row.createCell(columns.indexOf(DbGlobals.CL_RP_AUTHOR)).setCellValue(recipe.getAuthor());
            row.createCell(columns.indexOf(DbGlobals.CL_RP_SERVING)).setCellValue(recipe.getServing());
            row.createCell(columns.indexOf(DbGlobals.CL_RP_TIME)).setCellValue(recipe.getTime());
            row.createCell(columns.indexOf(DbGlobals.CL_RP_DIFFICULTY)).setCellValue(recipe.getDifficulty());
            row.createCell(columns.indexOf(DbGlobals.CL_RP_SPICINESS)).setCellValue(recipe.getSpiciness());
            row.createCell(columns.indexOf(DbGlobals.CL_RP_COUNTRY)).setCellValue(recipe.getCountry());
            row.createCell(columns.indexOf(DbGlobals.CL_RP_TYPE)).setCellValue(recipe.getType());
            row.createCell(columns.indexOf(DbGlobals.CL_RP_PREFERENCE)).setCellValue(TypeManager.booleanToInteger(recipe.getPreference()));
            row.createCell(columns.indexOf(DbGlobals.CL_RP_FAVORITE)).setCellValue(TypeManager.booleanToInteger(recipe.getFavorite()));
            id++;
        }
    }

    private void createIngredientSheet(HSSFWorkbook workbook, ArrayList<Ingredient> ingredients) {
        ArrayList<String> columns = DbGlobals.getIngredientsColumns();
        HSSFSheet sheet = getEmptySheet(workbook,
                DbGlobals.TB_INGREDIENT, columns);

        int id = MainGlobals.DF_INCREMENT;
        for(Ingredient ingredient : ingredients){
            HSSFRow row = sheet.createRow(id);
            row.createCell(columns.indexOf(DbGlobals.CL_IG_ID)).setCellValue(ingredient.getId());
            row.createCell(columns.indexOf(DbGlobals.CL_IG_RECIPE)).setCellValue(ingredient.getIdRec());
            row.createCell(columns.indexOf(DbGlobals.CL_IG_PRODUCT)).setCellValue(ingredient.getIdProd());
            row.createCell(columns.indexOf(DbGlobals.CL_IG_AMOUNT)).setCellValue(ingredient.getAmount());
            row.createCell(columns.indexOf(DbGlobals.CL_IG_MEASURE)).setCellValue(ingredient.getMeasure());
            row.createCell(columns.indexOf(DbGlobals.CL_IG_CATEGORY)).setCellValue(ingredient.getCategory());
            id++;
        }
    }

    private void createStepSheet(HSSFWorkbook workbook, ArrayList<Step> steps) {
        ArrayList<String> columns = DbGlobals.getStepsColumns();
        HSSFSheet sheet = getEmptySheet(workbook,
                DbGlobals.TB_STEP, columns);

        int id = MainGlobals.DF_INCREMENT;
        for(Step step : steps){
            HSSFRow row = sheet.createRow(id);
            row.createCell(columns.indexOf(DbGlobals.CL_ST_ID)).setCellValue(step.getId());
            row.createCell(columns.indexOf(DbGlobals.CL_ST_RECIPE)).setCellValue(step.getIdRec());
            row.createCell(columns.indexOf(DbGlobals.CL_ST_INSTRUCTION)).setCellValue(step.getInstruction());
            id++;
        }
    }

    private void createProductSheet(HSSFWorkbook workbook, ArrayList<Product> products) {
        ArrayList<String> columns = DbGlobals.getProductsColumns();
        HSSFSheet sheet = getEmptySheet(workbook,
                DbGlobals.TB_PRODUCT, DbGlobals.getProductsColumns());

        int id = MainGlobals.DF_INCREMENT;
        for(Product product : products){
            HSSFRow row = sheet.createRow(id);
            row.createCell(columns.indexOf(DbGlobals.CL_PD_ID)).setCellValue(product.getId());
            row.createCell(columns.indexOf(DbGlobals.CL_PD_NAME)).setCellValue(product.getName());
            row.createCell(columns.indexOf(DbGlobals.CL_PD_TYPE)).setCellValue(product.getType());
            row.createCell(columns.indexOf(DbGlobals.CL_PD_CARBOHYDRATES)).setCellValue(product.getCarbohydrates());
            row.createCell(columns.indexOf(DbGlobals.CL_PD_PROTEIN)).setCellValue(product.getProtein());
            row.createCell(columns.indexOf(DbGlobals.CL_PD_FAT)).setCellValue(product.getFat());
            id++;
        }
    }

    private void saveWorkbookFile(HSSFWorkbook workbook) {
        File file = new File(getContext().getExternalFilesDir(null),
                etExport.getText().toString() + MainGlobals.FL_XLS);
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
