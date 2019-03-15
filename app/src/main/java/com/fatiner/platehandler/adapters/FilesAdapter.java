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
    private ArrayList<String> files;

    public FilesAdapter(Context context, ArrayList<String> files){
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
        String name = files.get(position);
        holder.tvName.setText(name);
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
        Sheet sheet = workbook.getSheet(DbGlobals.TAB_PRODUCTS_DB_MAIN);
        Iterator<Row> iterator = sheet.rowIterator();
        while (iterator.hasNext()) {
            Row row = iterator.next();
            if(row.getRowNum() != MainGlobals.INT_STARTING_VAR_INIT) {
                Product product = new Product();
                product.setId((int) row.getCell(columns.indexOf(DbGlobals.COL_ID_TAB_PRODUCTS)).getNumericCellValue());
                product.setName(row.getCell(columns.indexOf(DbGlobals.COL_NAME_TAB_PRODUCTS)).toString());
                product.setType((int) row.getCell(columns.indexOf(DbGlobals.COL_TYPE_TAB_PRODUCTS)).getNumericCellValue());
                product.setCarbohydrates(Float.parseFloat(row.getCell(columns.indexOf(DbGlobals.COL_CARBOHYDRATES_TAB_PRODUCTS)).toString()));
                product.setProtein(Float.parseFloat(row.getCell(columns.indexOf(DbGlobals.COL_PROTEIN_TAB_PRODUCTS)).toString()));
                product.setFat(Float.parseFloat(row.getCell(columns.indexOf(DbGlobals.COL_FAT_TAB_PRODUCTS)).toString()));
                products.add(product);
            }
        }
        return products;
    }

    private ArrayList<Recipe> getRecipes(Workbook workbook) {
        ArrayList<String> columns = DbGlobals.getRecipesColumns();
        ArrayList<Recipe> recipes = new ArrayList<>();
        Sheet sheet = workbook.getSheet(DbGlobals.TAB_RECIPES_DB_MAIN);
        Iterator<Row> iterator = sheet.rowIterator();
        while (iterator.hasNext()) {
            Row row = iterator.next();
            if(row.getRowNum() != MainGlobals.INT_STARTING_VAR_INIT) {
                Recipe recipe = new Recipe();
                recipe.setId((int) row.getCell(columns.indexOf(DbGlobals.COL_ID_TAB_RECIPES)).getNumericCellValue());
                recipe.setName(row.getCell(columns.indexOf(DbGlobals.COL_NAME_TAB_RECIPES)).toString());
                recipe.setAuthor(row.getCell(columns.indexOf(DbGlobals.COL_AUTHOR_TAB_RECIPES)).toString());
                recipe.setServing((int) row.getCell(columns.indexOf(DbGlobals.COL_SERVING_TAB_RECIPES)).getNumericCellValue());
                recipe.setTime(row.getCell(columns.indexOf(DbGlobals.COL_TIME_TAB_RECIPES)).toString());
                recipe.setDifficulty((int) row.getCell(columns.indexOf(DbGlobals.COL_DIFFICULTY_TAB_RECIPES)).getNumericCellValue());
                recipe.setSpiciness((int) row.getCell(columns.indexOf(DbGlobals.COL_SPICINESS_TAB_RECIPES)).getNumericCellValue());
                recipe.setCountry((int) row.getCell(columns.indexOf(DbGlobals.COL_COUNTRY_TAB_RECIPES)).getNumericCellValue());
                recipe.setType((int) row.getCell(columns.indexOf(DbGlobals.COL_TYPE_TAB_RECIPES)).getNumericCellValue());
                recipe.setPreference(TypeManager.integerToBoolean(
                        (int) row.getCell(columns.indexOf(DbGlobals.COL_PREFERENCES_TAB_RECIPES)).getNumericCellValue()));
                recipe.setFavorite(TypeManager.integerToBoolean(
                        (int) row.getCell(columns.indexOf(DbGlobals.COL_FAVORITE_TAB_RECIPES)).getNumericCellValue()));
                recipes.add(recipe);
            }
        }
        return recipes;
    }

    private ArrayList<Ingredient> getIngredients(Workbook workbook) {
        ArrayList<String> columns = DbGlobals.getIngredientsColumns();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Sheet sheet = workbook.getSheet(DbGlobals.TAB_INGREDIENTS_DB_MAIN);
        Iterator<Row> iterator = sheet.rowIterator();
        while (iterator.hasNext()) {
            Row row = iterator.next();
            if(row.getRowNum() != MainGlobals.INT_STARTING_VAR_INIT) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId((int) row.getCell(columns.indexOf(DbGlobals.COL_ID_TAB_INGREDIENTS)).getNumericCellValue());
                ingredient.setIdRec((int) row.getCell(columns.indexOf(DbGlobals.COL_IDREC_TAB_INGREDIENTS)).getNumericCellValue());
                ingredient.setIdProd((int) row.getCell(columns.indexOf(DbGlobals.COL_IDPROD_TAB_INGREDIENTS)).getNumericCellValue());
                ingredient.setAmount(Float.parseFloat(row.getCell(columns.indexOf(DbGlobals.COL_AMOUNT_TAB_INGREDIENTS)).toString()));
                ingredient.setMeasure((int) row.getCell(columns.indexOf(DbGlobals.COL_MEASURE_TAB_INGREDIENTS)).getNumericCellValue());
                ingredient.setCategory(row.getCell(columns.indexOf(DbGlobals.COL_CATEGORY_TAB_INGREDIENTS)).toString());
                ingredient.setProduct(new Product());
                ingredient.getProduct().setId((int) row.getCell(columns.indexOf(DbGlobals.COL_IDPROD_TAB_INGREDIENTS)).getNumericCellValue());
                ingredients.add(ingredient);
            }
        }
        return ingredients;
    }

    private ArrayList<Step> getSteps(Workbook workbook) {
        ArrayList<String> columns = DbGlobals.getStepsColumns();
        ArrayList<Step> steps = new ArrayList<>();
        Sheet sheet = workbook.getSheet(DbGlobals.TAB_STEPS_DB_MAIN);
        Iterator<Row> iterator = sheet.rowIterator();
        while (iterator.hasNext()) {
            Row row = iterator.next();
            if(row.getRowNum() != MainGlobals.INT_STARTING_VAR_INIT) {
                Step step = new Step();
                step.setId((int) row.getCell(columns.indexOf(DbGlobals.COL_ID_TAB_STEPS)).getNumericCellValue());
                step.setIdRec((int) row.getCell(columns.indexOf(DbGlobals.COL_IDREC_TAB_STEPS)).getNumericCellValue());
                step.setInstruction(row.getCell(columns.indexOf(DbGlobals.COL_INSTRUCTION_TAB_STEPS)).toString());
                steps.add(step);
            }
        }
        return steps;
    }

    public class FilesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;

        @OnClick(R.id.ll_file)
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
                Workbook workbook = workbooks[MainGlobals.INT_STARTING_VAR_INIT];
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
                Toast.makeText(context, R.string.sb_import, Toast.LENGTH_SHORT).show();
                ((MainActivity)context).popFragment();
            }
        }
    }
}
