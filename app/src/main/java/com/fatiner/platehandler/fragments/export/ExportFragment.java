package com.fatiner.platehandler.fragments.export;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.globals.Db;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.Product;
import com.fatiner.platehandler.models.Recipe;
import com.fatiner.platehandler.models.Step;
import com.fatiner.platehandler.viewmodels.export.ExportViewModel;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExportFragment extends PrimaryFragment {

    private ExportViewModel viewModel;
    private CompositeDisposable disposables;

    @BindView(R.id.cv_guideline) CardView cvGuideline;
    @BindView(R.id.iv_hd_guideline) ImageView ivHdGuideline;
    @BindView(R.id.til_name) TextInputLayout tilName;
    @BindView(R.id.et_name) EditText etName;

    @OnTextChanged(R.id.et_name)
    void changedEtName() {
        setError(tilName, R.string.er_ex_name, isNameEmpty());
    }

    @OnClick(R.id.cv_hd_guideline)
    void clickCvHdGuideline() {
        manageExpandCv(cvGuideline, ivHdGuideline);
    }

    @OnClick(R.id.fab_export)
    void clickFabExport() {
        hideKeyboard();
        chooseEndAction();
    }

    @OnClick(R.id.iv_tt_data)
    void clickIvTtData() {
        showDialog(R.string.hd_ex_data, R.string.tt_ex_data);
    }

    @OnClick(R.id.iv_tt_guideline)
    void clickIvTtGuideline() {
        showDialog(R.string.hd_ex_guideline, R.string.tt_ex_guideline);
    }

    public static ExportFragment getInstance() {
        return new ExportFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_export, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initOptions(R.id.it_export, R.string.tb_ex_database, false);
        initViewModelEssentials();
        setViews();
    }

    private void initViewModelEssentials() {
        viewModel = new ViewModelProvider(this).get(ExportViewModel.class);
        disposables = new CompositeDisposable();
    }

    private void setViews() {
        setError(tilName, R.string.er_ex_name, isNameEmpty());
    }

    private void chooseEndAction() {
        if (isNameEmpty()) showShortToast(R.string.ts_export);
        else showDialog(R.string.dg_ex_add, getDialogListener());
    }

    private boolean isNameEmpty() {
        return etName.getText().toString().isEmpty();
    }

    private DialogInterface.OnClickListener getDialogListener() {
        return (dialog, which) -> {
            switch (which) {
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
        for (int i = Globals.DF_ZERO; i < columns.size(); i++) {
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
        for (Recipe recipe : recipes) {
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
        for (Ingredient ingredient : ingredients) {
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
        for (Step step : steps) {
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
        for (Product product : products) {
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
        File file = getFile(getXlsName(etName.getText().toString()), Globals.DR_EXPORT);
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
        observeGetAllRecipes(workbook);
    }

    private void observeGetAllRecipes(HSSFWorkbook workbook) {
        viewModel.getAllRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getRecipeObserver(workbook));
    }

    private void observeGetAllProducts(HSSFWorkbook workbook) {
        viewModel.getAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getProductObserver(workbook));
    }

    private void observeGetAllIngredients(HSSFWorkbook workbook) {
        viewModel.getAllIngredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getIngredientObserver(workbook));
    }

    private void observeGetAllSteps(HSSFWorkbook workbook) {
        viewModel.getAllSteps()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStepObserver(workbook));
    }

    private SingleObserver<List<Recipe>> getRecipeObserver(HSSFWorkbook workbook) {
        return new SingleObserver<List<Recipe>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onSuccess(List<Recipe> recipes) {
                createRecipeSheet(workbook, recipes);
                observeGetAllProducts(workbook);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    private SingleObserver<List<Product>> getProductObserver(HSSFWorkbook workbook) {
        return new SingleObserver<List<Product>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onSuccess(List<Product> products) {
                createProductSheet(workbook, products);
                observeGetAllIngredients(workbook);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    private SingleObserver<List<Ingredient>> getIngredientObserver(HSSFWorkbook workbook) {
        return new SingleObserver<List<Ingredient>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onSuccess(List<Ingredient> ingredients) {
                createIngredientSheet(workbook, ingredients);
                observeGetAllSteps(workbook);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    private SingleObserver<List<Step>> getStepObserver(HSSFWorkbook workbook) {
        return new SingleObserver<List<Step>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

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

    @Override
    public void onStop() {
        super.onStop();
        disposables.clear();
    }
}
