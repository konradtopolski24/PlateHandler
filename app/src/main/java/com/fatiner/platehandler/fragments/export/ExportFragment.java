package com.fatiner.platehandler.fragments.export;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.fatiner.platehandler.PlateHandlerDatabase;
import com.fatiner.platehandler.R;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.globals.Db;
import com.fatiner.platehandler.globals.Format;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.Product;
import com.fatiner.platehandler.models.Recipe;
import com.fatiner.platehandler.models.Step;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ExportFragment extends PrimaryFragment {

    @BindView(R.id.et_export) EditText etExport;
    @BindView(R.id.tv_saved) TextView tvSaved;
    @BindView(R.id.tv_unsaved) TextView tvUnsaved;
    @BindView(R.id.cv_info) CardView cvInfo;
    @BindView(R.id.iv_hd_info) ImageView ivHdInfo;

    @OnTextChanged(R.id.et_export)
    void changedEtExport() {
        setError(etExport, R.string.er_ex_name, isNameEmpty());
    }

    @OnClick(R.id.cv_hd_info)
    void clickCvHdInfo() {
        manageExpandCv(cvInfo, ivHdInfo);
    }

    @OnClick(R.id.fab_export)
    void clickFabExport() {
        hideKeyboard();
        chooseEndAction();
    }

    @OnClick(R.id.iv_tt_name)
    void clickIvTtName() {
        showDialog(R.string.hd_ex_name, R.string.tt_ex_name);
    }

    @OnClick(R.id.iv_tt_info)
    void clickIvTtInfo() {
        showDialog(R.string.hd_ex_info, R.string.tt_ex_info);
    }

    public ExportFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_export, container, false);
        init(this, view, R.id.it_export, R.string.tb_ex_database, false);
        setViews();
        return view;
    }

    private void setViews() {
        setTv(tvSaved, getSavedFeatures());
        setTv(tvUnsaved, getUnsavedFeatures());
        setError(etExport, R.string.er_ex_name, isNameEmpty());
    }

    private String getSavedFeatures() {
        return String.format(Locale.ENGLISH, Format.FM_SAVED,
                getString(R.string.nv_recipe),
                getString(R.string.nv_product));
    }

    private String getUnsavedFeatures() {
        return String.format(Locale.ENGLISH, Format.FM_UNSAVED,
                getString(R.string.nv_shopping),
                getString(R.string.hd_hm_recent),
                getString(R.string.hd_hm_recent));
    }

    private void chooseEndAction() {
        if(isNameEmpty()) showShortToast(R.string.ts_export);
        else showDialog(R.string.dg_ex_add, getDialogListener());
    }

    private boolean isNameEmpty() {
        return etExport.getText().toString().isEmpty();
    }

    private DialogInterface.OnClickListener getDialogListener() {
        return (dialog, which) -> {
            switch(which) {
                case DialogInterface.BUTTON_POSITIVE:
                    exportDb();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };
    }

    private HSSFSheet getSheet(HSSFWorkbook workbook, String name, List<String> columns) {
        HSSFSheet sheet = workbook.createSheet(name);
        HSSFRow headerRow = sheet.createRow(Globals.DF_ZERO);
        for(int i = Globals.DF_ZERO; i < columns.size(); i++) {
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(columns.get(i));
        }
        return sheet;
    }

    private void createCell(HSSFRow row, int id, int value) {
        row.createCell(id).setCellValue(value);
    }

    private void createCell(HSSFRow row, int id, float value) {
        row.createCell(id).setCellValue(value);
    }

    private void createCell(HSSFRow row, int id, String value) {
        row.createCell(id).setCellValue(value);
    }

    private void createCell(HSSFRow row, int id, boolean value) {
        row.createCell(id).setCellValue(TypeManager.boolToInt(value));
    }

    private void createRecipeSheet(HSSFWorkbook workbook, List<Recipe> recipes) {
        List<String> columns = Db.getRecipeColumns();
        HSSFSheet sheet = getSheet(workbook, Db.TB_RECIPE, columns);
        int id = Globals.DF_INCREMENT;
        for(Recipe recipe : recipes) {
            HSSFRow row = sheet.createRow(id);
            createCell(row, columns.indexOf(Db.CL_RP_ID), recipe.getId());
            createCell(row, columns.indexOf(Db.CL_RP_NAME), recipe.getName());
            createCell(row, columns.indexOf(Db.CL_RP_AUTHOR), recipe.getAuthor());
            createCell(row, columns.indexOf(Db.CL_RP_SERVING), recipe.getServing());
            createCell(row, columns.indexOf(Db.CL_RP_TIME), recipe.getTime());
            createCell(row, columns.indexOf(Db.CL_RP_DIFFICULTY), recipe.getDifficulty());
            createCell(row, columns.indexOf(Db.CL_RP_SPICINESS), recipe.getSpiciness());
            createCell(row, columns.indexOf(Db.CL_RP_COUNTRY), recipe.getCountry());
            createCell(row, columns.indexOf(Db.CL_RP_TYPE), recipe.getType());
            createCell(row, columns.indexOf(Db.CL_RP_PREFERENCE), recipe.getPreference());
            createCell(row, columns.indexOf(Db.CL_RP_FAVORITE), recipe.getFavorite());
            id++;
        }
    }

    private void createIngredientSheet(HSSFWorkbook workbook, List<Ingredient> ingredients) {
        List<String> columns = Db.getIngredientColumns();
        HSSFSheet sheet = getSheet(workbook, Db.TB_INGREDIENT, columns);
        int id = Globals.DF_INCREMENT;
        for(Ingredient ingredient : ingredients) {
            HSSFRow row = sheet.createRow(id);
            createCell(row, columns.indexOf(Db.CL_IG_ID), ingredient.getId());
            createCell(row, columns.indexOf(Db.CL_IG_RECIPE_ID), ingredient.getRecipeId());
            createCell(row, columns.indexOf(Db.CL_IG_PRODUCT_ID), ingredient.getProductId());
            createCell(row, columns.indexOf(Db.CL_IG_AMOUNT), ingredient.getAmount());
            createCell(row, columns.indexOf(Db.CL_IG_MEASURE), ingredient.getMeasure());
            createCell(row, columns.indexOf(Db.CL_IG_USED), ingredient.isUsed());
            id++;
        }
    }

    private void createStepSheet(HSSFWorkbook workbook, List<Step> steps) {
        List<String> columns = Db.getStepColumns();
        HSSFSheet sheet = getSheet(workbook, Db.TB_STEP, columns);
        int id = Globals.DF_INCREMENT;
        for(Step step : steps) {
            HSSFRow row = sheet.createRow(id);
            createCell(row, columns.indexOf(Db.CL_ST_ID), step.getId());
            createCell(row, columns.indexOf(Db.CL_ST_RECIPE_ID), step.getRecipeId());
            createCell(row, columns.indexOf(Db.CL_ST_CONTENT), step.getContent());
            createCell(row, columns.indexOf(Db.CL_ST_DONE), step.isDone());
            id++;
        }
    }

    private void createProductSheet(HSSFWorkbook workbook, List<Product> products) {
        List<String> columns = Db.getProductColumns();
        HSSFSheet sheet = getSheet(workbook, Db.TB_PRODUCT, Db.getProductColumns());
        int id = Globals.DF_INCREMENT;
        for(Product product : products) {
            HSSFRow row = sheet.createRow(id);
            createCell(row, columns.indexOf(Db.CL_PD_ID), product.getId());
            createCell(row, columns.indexOf(Db.CL_PD_NAME), product.getName());
            createCell(row, columns.indexOf(Db.CL_PD_TYPE), product.getType());
            createCell(row, columns.indexOf(Db.CL_PD_SIZE), product.getSize());
            createCell(row, columns.indexOf(Db.CL_PD_CARBOHYDRATES), product.getCarbohydrates());
            createCell(row, columns.indexOf(Db.CL_PD_PROTEINS), product.getProteins());
            createCell(row, columns.indexOf(Db.CL_PD_FATS), product.getFats());
            id++;
        }
    }

    private void saveWorkbookFile(HSSFWorkbook workbook) {
        File file = getFile(getXlsName(etExport.getText().toString()), Globals.DR_EXPORT);
        try {
            FileOutputStream output = new FileOutputStream(file);
            workbook.write(output);
            output.close();
        } catch (IOException e) {
            showShortToast(R.string.ts_file);
        }
    }

    private void exportDb() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        readAllRecipes(workbook);
    }

    //Read All Recipes
    private void readAllRecipes(HSSFWorkbook workbook) {
        PlateHandlerDatabase db = getDb(getContext());
        Single<List<Recipe>> single = db.getRecipeDAO().getAllRecipes();
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getReadAllRecipesObserver(workbook));
    }

    private DisposableSingleObserver<List<Recipe>> getReadAllRecipesObserver(HSSFWorkbook workbook) {
        return new DisposableSingleObserver<List<Recipe>>() {

            @Override
            public void onSuccess(List<Recipe> recipes) {
                createRecipeSheet(workbook, recipes);
                readAllProducts(workbook);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    //Read All Products
    private void readAllProducts(HSSFWorkbook workbook) {
        PlateHandlerDatabase db = getDb(getContext());
        Single<List<Product>> single = db.getProductDAO().getAllProducts();
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getReadAllProductsObserver(workbook));
    }

    private DisposableSingleObserver<List<Product>> getReadAllProductsObserver(HSSFWorkbook workbook) {
        return new DisposableSingleObserver<List<Product>>() {

            @Override
            public void onSuccess(List<Product> products) {
                createProductSheet(workbook, products);
                readAllIngredients(workbook);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    //Read All Ingredients
    private void readAllIngredients(HSSFWorkbook workbook) {
        PlateHandlerDatabase db = getDb(getContext());
        Single<List<Ingredient>> single = db.getIngredientDAO().getAllIngredients();
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getReadAllIngredientsObserver(workbook));
    }

    private DisposableSingleObserver<List<Ingredient>> getReadAllIngredientsObserver(HSSFWorkbook workbook) {
        return new DisposableSingleObserver<List<Ingredient>>() {

            @Override
            public void onSuccess(List<Ingredient> ingredients) {
                createIngredientSheet(workbook, ingredients);
                readAllSteps(workbook);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    //Read All Steps
    private void readAllSteps(HSSFWorkbook workbook) {
        PlateHandlerDatabase db = getDb(getContext());
        Single<List<Step>> single = db.getStepDAO().getAllSteps();
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getReadAllStepsObserver(workbook));
    }

    private DisposableSingleObserver<List<Step>> getReadAllStepsObserver(HSSFWorkbook workbook) {
        return new DisposableSingleObserver<List<Step>>() {

            @Override
            public void onSuccess(List<Step> steps) {
                createStepSheet(workbook, steps);
                createDirectory(Globals.DR_EXPORT);
                saveWorkbookFile(workbook);
                showShortSnack(R.string.sb_ex_add);
                popFragment();
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }
}
