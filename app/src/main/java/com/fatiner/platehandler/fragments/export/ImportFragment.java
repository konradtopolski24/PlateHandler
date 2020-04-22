package com.fatiner.platehandler.fragments.export;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.PlateHandlerDatabase;
import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.recyclerview.FileAdapter;
import com.fatiner.platehandler.items.ImportFile;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.globals.Db;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.globals.Shared;
import com.fatiner.platehandler.managers.SharedManager;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.Product;
import com.fatiner.platehandler.models.Recipe;
import com.fatiner.platehandler.models.Step;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class ImportFragment extends PrimaryFragment implements FileAdapter.FileListener {

    @BindView(R.id.tv_empty) TextView tvEmpty;
    @BindView(R.id.rv_file) RecyclerView rvFile;

    @OnClick(R.id.iv_tt_file)
    void clickIvTtFile() {
        showDialog(R.string.hd_im_file, R.string.tt_im_file);
    }

    public ImportFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_import, container, false);
        init(this, view, R.id.it_import, R.string.tb_im_database, false);
        setViews();
        return view;
    }

    private void setViews() {
        setRv(rvFile, getManager(getColumnAmountList()), getFilesAdapter());
        checkIfRvEmpty(rvFile, tvEmpty);
    }

    private FileAdapter getFilesAdapter() {
        return new FileAdapter(getContext(), getImportFiles(), this);
    }

    private File getDir(String name) {
        return new File(getExternalDir(), name);
    }

    private List<ImportFile> getImportFiles() {
        List<ImportFile> importFiles = new ArrayList<>();
        File directory = getDir(Globals.DR_EXPORT);
        File[] files = directory.listFiles();
        if (files == null) return importFiles;
        for (File file : files) addNewImportFile(importFiles, file);
        return importFiles;
    }

    private void addNewImportFile(List<ImportFile> importFiles, File file) {
        if (isFile(file)) {
            String name = getShortName(file.toString());
            Workbook workbook = getWorkbook(name);
            int recipeAmount = getAmount(workbook, Db.TB_RECIPE);
            int productAmount = getAmount(workbook, Db.TB_PRODUCT);
            importFiles.add(getImportFile(name, recipeAmount, productAmount));
        }
    }

    private boolean isFile(File file) {
        return file.isFile() && file.getPath().endsWith(Globals.FL_XLS);
    }

    private String getShortName(String full) {
        return full.substring(full.lastIndexOf(Globals.SN_SLASH) + Globals.DF_INCREMENT);
    }

    private Workbook getWorkbook(String fileName) {
        try {
            File file = new File(getDir(Globals.DR_EXPORT), fileName);
            FileInputStream stream = new FileInputStream(file);
            POIFSFileSystem system = new POIFSFileSystem(stream);
            return new HSSFWorkbook(system);
        } catch (Exception e) {
            showShortToast(R.string.ts_file);
            return new HSSFWorkbook();
        }
    }

    private ImportFile getImportFile(String name, int recipeAmount, int productAmount) {
        ImportFile importFile = new ImportFile();
        importFile.setName(name);
        importFile.setRecipeAmount(recipeAmount);
        importFile.setProductAmount(productAmount);
        return importFile;
    }

    private int getAmount(Workbook workbook, String tabName) {
        Sheet sheet = workbook.getSheet(tabName);
        return sheet.getPhysicalNumberOfRows() + Globals.DF_DECREMENT;
    }

    private void clearShared() {
        SharedManager.removeAll(getContext(), Shared.SR_RECIPE);
        SharedManager.removeAll(getContext(), Shared.SR_PRODUCT);
        SharedManager.removeAll(getContext(), Shared.SR_HOME);
        SharedManager.removeAll(getContext(), Shared.SR_SHOPPING);
    }

    private List<Product> getProducts(Workbook workbook) {
        List<Product> products = new ArrayList<>();
        Iterator<Row> iterator = getIterator(workbook, Db.TB_PRODUCT);
        while (iterator.hasNext()) {
            Row row = iterator.next();
            if (isRowAvailable(row)) products.add(getProduct(row));
        }
        return products;
    }

    private List<Recipe> getRecipes(Workbook workbook) {
        List<Recipe> recipes = new ArrayList<>();
        Iterator<Row> iterator = getIterator(workbook, Db.TB_RECIPE);
        while (iterator.hasNext()) {
            Row row = iterator.next();
            if (isRowAvailable(row)) recipes.add(getRecipe(row));
        }
        return recipes;
    }

    private List<Ingredient> getIngredients(Workbook workbook) {
        List<Ingredient> ingredients = new ArrayList<>();
        Iterator<Row> iterator = getIterator(workbook, Db.TB_INGREDIENT);
        while (iterator.hasNext()) {
            Row row = iterator.next();
            if (isRowAvailable(row)) ingredients.add(getIngredient(row));
        }
        return ingredients;
    }

    private List<Step> getSteps(Workbook workbook) {
        List<Step> steps = new ArrayList<>();
        Iterator<Row> iterator = getIterator(workbook, Db.TB_STEP);
        while (iterator.hasNext()) {
            Row row = iterator.next();
            if (isRowAvailable(row)) steps.add(getStep(row));
        }
        return steps;
    }

    private Product getProduct(Row row) {
        List<String> columns = Db.getProductColumns();
        Product product = new Product();
        product.setId(getInt(row, columns.indexOf(Db.CL_PD_ID)));
        product.setName(getString(row, columns.indexOf(Db.CL_PD_NAME)));
        product.setType(getInt(row, columns.indexOf(Db.CL_PD_TYPE)));
        product.setSize(getFloat(row, columns.indexOf(Db.CL_PD_SIZE)));
        product.setCarbohydrates(getFloat(row, columns.indexOf(Db.CL_PD_CARBOHYDRATES)));
        product.setProteins(getFloat(row, columns.indexOf(Db.CL_PD_PROTEINS)));
        product.setFats(getFloat(row, columns.indexOf(Db.CL_PD_FATS)));
        return product;
    }

    private Recipe getRecipe(Row row) {
        List<String> columns = Db.getRecipeColumns();
        Recipe recipe = new Recipe();
        recipe.setId(getInt(row, columns.indexOf(Db.CL_RP_ID)));
        recipe.setName(getString(row, columns.indexOf(Db.CL_RP_NAME)));
        recipe.setAuthor(getString(row, columns.indexOf(Db.CL_RP_AUTHOR)));
        recipe.setServing(getInt(row, columns.indexOf(Db.CL_RP_SERVING)));
        recipe.setTime(getString(row, columns.indexOf(Db.CL_RP_TIME)));
        recipe.setDifficulty(getInt(row, columns.indexOf(Db.CL_RP_DIFFICULTY)));
        recipe.setSpiciness(getInt(row, columns.indexOf(Db.CL_RP_SPICINESS)));
        recipe.setCountry(getInt(row, columns.indexOf(Db.CL_RP_COUNTRY)));
        recipe.setType(getInt(row, columns.indexOf(Db.CL_RP_TYPE)));
        recipe.setPreference(getBoolean(row, columns.indexOf(Db.CL_RP_PREFERENCE)));
        recipe.setFavorite(getBoolean(row, columns.indexOf(Db.CL_RP_FAVORITE)));
        return recipe;
    }

    private Ingredient getIngredient(Row row) {
        List<String> columns = Db.getIngredientColumns();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(getInt(row, columns.indexOf(Db.CL_IG_ID)));
        ingredient.setRecipeId(getInt(row, columns.indexOf(Db.CL_IG_RECIPE_ID)));
        ingredient.setProductId(getInt(row, columns.indexOf(Db.CL_IG_PRODUCT_ID)));
        ingredient.setAmount(getFloat(row, columns.indexOf(Db.CL_IG_AMOUNT)));
        ingredient.setMeasure(getInt(row, columns.indexOf(Db.CL_IG_MEASURE)));
        ingredient.setUsed(getBoolean(row, columns.indexOf(Db.CL_IG_USED)));
        return ingredient;
    }

    private Step getStep(Row row) {
        List<String> columns = Db.getStepColumns();
        Step step = new Step();
        step.setId(getInt(row, columns.indexOf(Db.CL_ST_ID)));
        step.setRecipeId(getInt(row, columns.indexOf(Db.CL_ST_RECIPE_ID)));
        step.setContent(getString(row, columns.indexOf(Db.CL_ST_CONTENT)));
        step.setDone(getBoolean(row, columns.indexOf(Db.CL_ST_DONE)));
        return step;
    }

    private Iterator<Row> getIterator(Workbook workbook, String table) {
        Sheet sheet = workbook.getSheet(table);
        return sheet.rowIterator();
    }

    private boolean isRowAvailable(Row row) {
        return row.getRowNum() != Globals.DF_ZERO;
    }

    private int getInt(Row row, int id) {
        return (int) row.getCell(id).getNumericCellValue();
    }

    private float getFloat(Row row, int id) {
        return Float.parseFloat(row.getCell(id).toString());
    }

    private String getString(Row row, int id) {
        return row.getCell(id).toString();
    }

    private boolean getBoolean(Row row, int id) {
        return TypeManager.intToBool((int) row.getCell(id).getNumericCellValue());
    }

    private void removeAllImages() {
        File directory = getDir(Globals.DR_IMAGES);
        File[] files = directory.listFiles();
        if (files == null) return;
        for (File file : files) removeFile(file);
    }

    private void endAction() {
        clearShared();
        removeAllImages();
        showLongToast(R.string.sb_im_add);
        popFragment();
    }

    private DialogInterface.OnClickListener getDialogListener(String name) {
        return (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    importData(getWorkbook(name));
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };
    }

    private void importData(Workbook workbook) {
        getImportCompletable(workbook).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getImportObserver());
    }

    private Completable getImportCompletable(Workbook workbook) {
        return Completable.fromAction(() -> {
            PlateHandlerDatabase db = getDb(getContext());
            db.clearAllTables();
            db.getRecipeDAO().addRecipes(getRecipes(workbook));
            db.getProductDAO().addProducts(getProducts(workbook));
            db.getIngredientDAO().addIngredients(getIngredients(workbook));
            db.getStepDAO().addSteps(getSteps(workbook));
        });
    }

    private DisposableCompletableObserver getImportObserver() {
        return new DisposableCompletableObserver() {

            @Override
            public void onComplete() {
                endAction();
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    @Override
    public void clickFile(String name) {
        showDialog(R.string.dg_im_choose, getDialogListener(name));
    }
}
