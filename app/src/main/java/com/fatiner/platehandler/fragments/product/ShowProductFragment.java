package com.fatiner.platehandler.fragments.product;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.classes.Product;
import com.fatiner.platehandler.details.ProductDetails;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.BundleGlobals;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.managers.CalculateManager;
import com.fatiner.platehandler.managers.ImageManager;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.managers.database.DbOperations;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowProductFragment extends PrimaryFragment {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_carbohydrates)
    TextView tvCarbohydrates;
    @BindView(R.id.tv_protein)
    TextView tvProtein;
    @BindView(R.id.tv_fat)
    TextView tvFat;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.pc_kcal)
    PieChart pcKcal;
    @BindView(R.id.pc_kj)
    PieChart pcKj;

    public ShowProductFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_product, container, false);
        ButterKnife.bind(this, view);
        setMenuItem(MainGlobals.ID_INGREDIENTS_DRAW_MAIN);
        setProduct();
        setHasOptionsMenu(true);
        return view;
    }

    private void setProduct(){
        if(isBundleNotEmpty()){
            new AsyncShowProduct().execute(Type.READ);
        }
    }

    private int getProductId(){
        return getIntFromBundle(BundleGlobals.BUND_ID_FRAG_SHOWPROD);
    }

    private void loadPhoto(){
        Product product = ProductDetails.getProduct();
        Bitmap bitmap = ImageManager.getImageFromStorage(ImageManager.getImageProductName(product.getId()));
        if(bitmap == null) return;
        ProductDetails.getProduct().setEncodedImage(TypeManager.bitmapToBase64String(bitmap));
    }

    private void setProductInfo(){
        Product product = ProductDetails.getProduct();
        setTextName(product.getName());
        setTextType(product.getType());
        setTextCarbohydrates(product.getCarbohydrates());
        setTextProtein(product.getProtein());
        setTextFat(product.getFat());
        setImagePhoto(product.getEncodedImage());
    }

    private void setTextName(String text){
        tvName.setText(text);
    }

    private void setTextType(int type){
        String[] arrayType = getStringArray(R.array.ar_product_type);
        tvType.setText(arrayType[type]);
    }

    private void setTextCarbohydrates(float carbohydrates){
        tvCarbohydrates.setText(String.valueOf(carbohydrates));
    }

    private void setTextProtein(float protein){
        tvProtein.setText(String.valueOf(protein));
    }

    private void setTextFat(float fat){
        tvFat.setText(String.valueOf(fat));
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
                        new AsyncShowProduct().execute(Type.DELETE);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_shwprod, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.item_edit_menu_shwprod:
                Fragment fragment = new ManageProductFragment();
                setBoolInBundle(fragment, true, BundleGlobals.BUND_BOOL_FRAG_ADDPROD);
                setFragment(fragment);
                return true;
            case R.id.item_delete_menu_shwprod:
                showAlertDialog(R.string.dg_product_delete, getDialogListener());
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private class AsyncShowProduct extends AsyncTask<Type, Void, Boolean>{

        private Type type;

        @Override
        protected Boolean doInBackground(Type... types) {
            type = types[MainGlobals.INT_STARTING_VAR_INIT];
            try{
                switch(type) {
                    case READ:
                        DbOperations.readProduct(getContext(),
                                ProductDetails.getProduct(), getProductId());
                        break;
                    case DELETE:
                        DbOperations.deletedProduct(
                                getContext(), ProductDetails.getProduct().getId());
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
                        finishedReadProduct();
                        break;
                    case DELETE:
                        finishedDeleteProduct();
                        break;
                }
            }
        }
    }

    private void finishedReadProduct() {
        setToolbarTitle(ProductDetails.getProduct().getName());
        loadPhoto();
        setProductInfo();
        managePieCharts();
    }

    private void finishedDeleteProduct() {
        productSuccess(R.string.sb_product_deleted);
    }

    private void setPieChart(PieChart chart, float v1, float v2, float v3, boolean isKcal) {
        float total = v1 + v2 + v3;
        String measure = getCorrectMeasure(isKcal);
        chart.setTouchEnabled(false);
        chart.setHoleRadius(50f);
        chart.setCenterText(getString(R.string.tv_total) + MainGlobals.STR_SPACE_OBJ_INIT +
                MainGlobals.STR_ENTER_OBJ_INIT + total + MainGlobals.STR_SPACE_OBJ_INIT + measure);
        chart.getDescription().setEnabled(false);
        chart.setEntryLabelTextSize(10);
        chart.animateY(500);
        ArrayList<PieEntry> entries = getEntries(v1, v2, v3);
        PieDataSet dataSet = getDataSet(entries);
        setLegend(chart.getLegend());
        chart.setData(new PieData(dataSet));
        chart.invalidate();
    }

    private void managePieCharts() {
        Product product = ProductDetails.getProduct();
        setPcKcal(product);
        setPcKj(product);
    }

    private void setPcKcal(Product product) {
        float carbohydrates = CalculateManager.getKcal(product.getCarbohydrates(),
                CalculateManager.Organic.CARBOHYDRATES);
        float protein = CalculateManager.getKcal(product.getProtein(),
                CalculateManager.Organic.PROTEIN);
        float fat = CalculateManager.getKcal(product.getFat(),
                CalculateManager.Organic.FAT);
        setPieChart(pcKcal, carbohydrates, protein, fat, true);
    }

    private void setPcKj(Product product) {
        float carbohydrates = CalculateManager.getKj(product.getCarbohydrates(),
                CalculateManager.Organic.CARBOHYDRATES);
        float protein = CalculateManager.getKj(product.getProtein(),
                CalculateManager.Organic.PROTEIN);
        float fat = CalculateManager.getKj(product.getFat(),
                CalculateManager.Organic.FAT);
        setPieChart(pcKj, carbohydrates, protein, fat, false);
    }

    private String getCorrectMeasure(boolean isKcal) {
        if(isKcal) {
            return getString(R.string.tv_kcal);
        } else {
            return getString(R.string.tv_kj);
        }
    }

    private ArrayList<Integer> getColors() {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        return colors;
    }

    private ArrayList<PieEntry> getEntries(float v1, float v2, float v3) {
        ArrayList<PieEntry> values = new ArrayList<>();
        values.add(new PieEntry(v1, getString(R.string.tv_carbohydrates)));
        values.add(new PieEntry(v2, getString(R.string.tv_protein)));
        values.add(new PieEntry(v3, getString(R.string.tv_fat)));
        return values;
    }

    private PieDataSet getDataSet(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, MainGlobals.STR_EMPTY_OBJ_INIT);
        dataSet.setColors(getColors());
        dataSet.setSliceSpace(4);
        dataSet.setValueTextSize(10);
        return dataSet;
    }

    private void setLegend(Legend legend) {
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
    }

    private enum Type {
        READ, DELETE
    }
}
