package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.activities.MainActivity;
import com.fatiner.platehandler.classes.ImportFile;
import com.fatiner.platehandler.classes.Ingredient;
import com.fatiner.platehandler.classes.Product;
import com.fatiner.platehandler.classes.Recipe;
import com.fatiner.platehandler.classes.Step;
import com.fatiner.platehandler.globals.DbGlobals;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.managers.database.DbOperations;
import com.fatiner.platehandler.managers.shared.SharedMainManager;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FilesHolder> {

    private Context context;
    private ArrayList<ImportFile> files;

    public FilesAdapter(Context context, ArrayList<ImportFile> files){
        this.context = context;
        this.files = files;
    }

    @NonNull
    @Override
    public FilesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView cardView = (CardView) inflater.inflate(
                R.layout.cardview_file,
                parent,
                false);
        return new FilesHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull FilesHolder holder, int position) {
        ImportFile importFile = files.get(position);
        holder.tvName.setText(importFile.getName());
        String recipeAmount = context.getString(R.string.nv_recipe) + MainGlobals.SN_COLON + MainGlobals.SN_SPACE + importFile.getRecipeAmount();
        holder.tvRecipeAmount.setText(recipeAmount);
        String productAmount = context.getString(R.string.nv_product) + MainGlobals.SN_COLON + MainGlobals.SN_SPACE + importFile.getProductAmount();
        holder.tvProductAmount.setText(productAmount);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    private Workbook getWorkbookFromFile(String name) {
        Workbook workbook = new HSSFWorkbook();
        try {
            File file = new File(context.getExternalFilesDir(null), name);
            FileInputStream inputStream = new FileInputStream(file);
            POIFSFileSystem system = new POIFSFileSystem(inputStream);
            workbook = new HSSFWorkbook(system);
        } catch (Exception e) {

        }
        return workbook;
    }

    private ArrayList<Product> getProducts(Workbook workbook) {
        ArrayList<String> columns = DbGlobals.getProductsColumns();
        ArrayList<Product> products = new ArrayList<>();
        Sheet sheet = workbook.getSheet(DbGlobals.TB_PRODUCT);
        Iterator<Row> iterator = sheet.rowIterator();
        while (iterator.hasNext()) {
            Row row = iterator.next();
            if(row.getRowNum() != MainGlobals.DF_ZERO) {
                Product product = new Product();
                product.setId((int) row.getCell(columns.indexOf(DbGlobals.CL_PD_ID)).getNumericCellValue());
                product.setName(row.getCell(columns.indexOf(DbGlobals.CL_PD_NAME)).toString());
                product.setType((int) row.getCell(columns.indexOf(DbGlobals.CL_PD_TYPE)).getNumericCellValue());
                product.setCarbohydrates(Float.parseFloat(row.getCell(columns.indexOf(DbGlobals.CL_PD_CARBOHYDRATES)).toString()));
                product.setProtein(Float.parseFloat(row.getCell(columns.indexOf(DbGlobals.CL_PD_PROTEIN)).toString()));
                product.setFat(Float.parseFloat(row.getCell(columns.indexOf(DbGlobals.CL_PD_FAT)).toString()));
                products.add(product);
            }
        }
        return products;
    }

    private ArrayList<Recipe> getRecipes(Workbook workbook) {
        ArrayList<String> columns = DbGlobals.getRecipesColumns();
        ArrayList<Recipe> recipes = new ArrayList<>();
        Sheet sheet = workbook.getSheet(DbGlobals.TB_RECIPE);
        Iterator<Row> iterator = sheet.rowIterator();
        while (iterator.hasNext()) {
            Row row = iterator.next();
            if(row.getRowNum() != MainGlobals.DF_ZERO) {
                Recipe recipe = new Recipe();
                recipe.setId((int) row.getCell(columns.indexOf(DbGlobals.CL_RP_ID)).getNumericCellValue());
                recipe.setName(row.getCell(columns.indexOf(DbGlobals.CL_RP_NAME)).toString());
                recipe.setAuthor(row.getCell(columns.indexOf(DbGlobals.CL_RP_AUTHOR)).toString());
                recipe.setServing((int) row.getCell(columns.indexOf(DbGlobals.CL_RP_SERVING)).getNumericCellValue());
                recipe.setTime(row.getCell(columns.indexOf(DbGlobals.CL_RP_TIME)).toString());
                recipe.setDifficulty((int) row.getCell(columns.indexOf(DbGlobals.CL_RP_DIFFICULTY)).getNumericCellValue());
                recipe.setSpiciness((int) row.getCell(columns.indexOf(DbGlobals.CL_RP_SPICINESS)).getNumericCellValue());
                recipe.setCountry((int) row.getCell(columns.indexOf(DbGlobals.CL_RP_COUNTRY)).getNumericCellValue());
                recipe.setType((int) row.getCell(columns.indexOf(DbGlobals.CL_RP_TYPE)).getNumericCellValue());
                recipe.setPreference(TypeManager.integerToBoolean(
                        (int) row.getCell(columns.indexOf(DbGlobals.CL_RP_PREFERENCE)).getNumericCellValue()));
                recipe.setFavorite(TypeManager.integerToBoolean(
                        (int) row.getCell(columns.indexOf(DbGlobals.CL_RP_FAVORITE)).getNumericCellValue()));
                recipes.add(recipe);
            }
        }
        return recipes;
    }

    private ArrayList<Ingredient> getIngredients(Workbook workbook) {
        ArrayList<String> columns = DbGlobals.getIngredientsColumns();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Sheet sheet = workbook.getSheet(DbGlobals.TB_INGREDIENT);
        Iterator<Row> iterator = sheet.rowIterator();
        while (iterator.hasNext()) {
            Row row = iterator.next();
            if(row.getRowNum() != MainGlobals.DF_ZERO) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId((int) row.getCell(columns.indexOf(DbGlobals.CL_IG_ID)).getNumericCellValue());
                ingredient.setIdRec((int) row.getCell(columns.indexOf(DbGlobals.CL_IG_RECIPE)).getNumericCellValue());
                ingredient.setIdProd((int) row.getCell(columns.indexOf(DbGlobals.CL_IG_PRODUCT)).getNumericCellValue());
                ingredient.setAmount(Float.parseFloat(row.getCell(columns.indexOf(DbGlobals.CL_IG_AMOUNT)).toString()));
                ingredient.setMeasure((int) row.getCell(columns.indexOf(DbGlobals.CL_IG_MEASURE)).getNumericCellValue());
                ingredient.setCategory(row.getCell(columns.indexOf(DbGlobals.CL_IG_CATEGORY)).toString());
                ingredient.setProduct(new Product());
                ingredient.getProduct().setId((int) row.getCell(columns.indexOf(DbGlobals.CL_IG_PRODUCT)).getNumericCellValue());
                ingredients.add(ingredient);
            }
        }
        return ingredients;
    }

    private ArrayList<Step> getSteps(Workbook workbook) {
        ArrayList<String> columns = DbGlobals.getStepsColumns();
        ArrayList<Step> steps = new ArrayList<>();
        Sheet sheet = workbook.getSheet(DbGlobals.TB_STEP);
        Iterator<Row> iterator = sheet.rowIterator();
        while (iterator.hasNext()) {
            Row row = iterator.next();
            if(row.getRowNum() != MainGlobals.DF_ZERO) {
                Step step = new Step();
                step.setId((int) row.getCell(columns.indexOf(DbGlobals.CL_ST_ID)).getNumericCellValue());
                step.setIdRec((int) row.getCell(columns.indexOf(DbGlobals.CL_ST_RECIPE)).getNumericCellValue());
                step.setInstruction(row.getCell(columns.indexOf(DbGlobals.CL_ST_INSTRUCTION)).toString());
                steps.add(step);
            }
        }
        return steps;
    }

    public class FilesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_recipe_amount)
        TextView tvRecipeAmount;
        @BindView(R.id.tv_product_amount)
        TextView tvProductAmount;

        @OnClick(R.id.cl_file)
        public void onClickLlFile(){
            Workbook workbook = getWorkbookFromFile(tvName.getText().toString());
            if(workbook == null) return;
            new AsyncFilesAdapter().execute(workbook);
        }

        public FilesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class AsyncFilesAdapter extends AsyncTask<Workbook, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Workbook... workbooks) {
            try {
                Workbook workbook = workbooks[MainGlobals.DF_ZERO];
                ArrayList<Product> products = getProducts(workbook);
                ArrayList<Recipe> recipes = getRecipes(workbook);
                ArrayList<Ingredient> ingredients = getIngredients(workbook);
                ArrayList<Step> steps = getSteps(workbook);
                DbOperations.insertDatabase(context, products, recipes, ingredients, steps);
                return true;
            } catch (SQLiteException e) {

                return false;
            }
        }

        protected void onPostExecute(Boolean success){
            if(success) {
                SharedMainManager.removeSharedDish(context);
                SharedMainManager.removeSharedRecent(context);
                Toast.makeText(context, R.string.sb_im_add, Toast.LENGTH_SHORT).show();
                ((MainActivity)context).popFragment();
            }
        }
    }
}
