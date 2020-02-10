package com.fatiner.platehandler.fragments.product;

import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowProductFragment extends PrimaryFragment {

    @BindView(R.id.cv_nutrients)
    CardView cvNutrients;
    @BindView(R.id.iv_nutrients)
    ImageView ivNutrients;
    @BindView(R.id.cv_kcal)
    CardView cvKcal;
    @BindView(R.id.iv_kcal)
    ImageView ivKcal;
    @BindView(R.id.cv_kj)
    CardView cvKj;
    @BindView(R.id.iv_kj)
    ImageView ivKj;
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
    @BindView(R.id.tv_other)
    TextView tvOther;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.tv_carbohydrates_kcal)
    TextView tvCarbohydratesKcal;
    @BindView(R.id.tv_protein_kcal)
    TextView tvProteinKcal;
    @BindView(R.id.tv_fat_kcal)
    TextView tvFatKcal;
    @BindView(R.id.tv_total_kcal)
    TextView tvTotalKcal;
    @BindView(R.id.tv_carbohydrates_kj)
    TextView tvCarbohydratesKj;
    @BindView(R.id.tv_protein_kj)
    TextView tvProteinKj;
    @BindView(R.id.tv_fat_kj)
    TextView tvFatKj;
    @BindView(R.id.tv_total_kj)
    TextView tvTotalKj;
    @BindView(R.id.pc_nutrients)
    PieChart pcNutrients;
    @BindView(R.id.pc_kcal)
    PieChart pcKcal;
    @BindView(R.id.pc_kj)
    PieChart pcKj;
    @BindView(R.id.bc_test)
    BarChart bcTest;
    @BindView(R.id.iv_type)
    ImageView ivType;

    @OnClick(R.id.cv_nutrients_hd)
    public void onClickFaAdd(){
        manageExpandCardView(cvNutrients, ivNutrients);
    }

    @OnClick(R.id.cv_kcal_hd)
    public void onClickFaAdd2(){
        manageExpandCardView(cvKcal, ivKcal);
    }

    @OnClick(R.id.cv_kj_hd)
    public void onClickFaAdd3(){
        manageExpandCardView(cvKj, ivKj);
    }

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

    private void setProductNutrients(){
        Product product = ProductDetails.getProduct();
        setImagePhoto(product.getEncodedImage());
        setTv(tvName, product.getName());
        setTv(tvType, product.getType(), R.array.tx_product);
        setTv(tvCarbohydrates, product.getCarbohydrates(), MainGlobals.STR_GRAM);
        setTv(tvProtein, product.getProtein(), MainGlobals.STR_GRAM);
        setTv(tvFat, product.getFat(), MainGlobals.STR_GRAM);
        setTv(tvOther, getOther(product), MainGlobals.STR_GRAM);

        ArrayList<PieEntry> entries = getEntries(product, false);
        setPieChart(pcNutrients, entries);
        setBarChart(bcTest, product);
    }

    private void setProductEnergy() {
        Product product = ProductDetails.getProduct();
        setPcKcal(product);
        setPcKj(product);
    }

    private float getOther(Product product) {
        return 100 - product.getCarbohydrates() - product.getProtein() - product.getFat();
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
        inflater.inflate(R.menu.pd_show, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.it_edit:
                Fragment fragment = new ManageProductFragment();
                setBoolInBundle(fragment, true, BundleGlobals.BUND_BOOL_FRAG_ADDPROD);
                setFragment(fragment);
                return true;
            case R.id.it_remove:
                showAlertDialog(R.string.dg_pd_remove, getDialogListener());
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
                showShortToast(R.string.ts_database);
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
        setProductNutrients();
        setProductEnergy();
        setImageType(ProductDetails.getProduct().getType());
    }

    private void finishedDeleteProduct() {
        productSuccess(R.string.sb_pd_remove);
    }

    private void setPieChart(PieChart chart, ArrayList<PieEntry> entries) {
        chart.setTouchEnabled(false);
        chart.setHoleRadius(25f);
        chart.setTransparentCircleAlpha(0);
        chart.setCenterTextColor(getResources().getColor(android.R.color.tab_indicator_text));
        chart.getDescription().setEnabled(false);
        chart.setUsePercentValues(true);
        chart.animateY(1000);
        chart.setDrawEntryLabels(false);
        PieDataSet dataSet = getDataSet(entries, chart);
        setLegend(chart.getLegend());
        chart.setData(new PieData(dataSet));
        chart.invalidate();
    }

    private void setBarChart(BarChart chart, Product product) {
        ArrayList<BarEntry> entries = getBarEntries(product, false);
        chart.setDrawValueAboveBar(true);
        BarDataSet dataSet = getDataSet(entries);
        chart.animateY(5000);
        chart.setData(new BarData(dataSet));

        chart.setTouchEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.animateY(1000);
        setLegend(chart.getLegend());
        chart.invalidate();
    }

    private void setPcKcal(Product product) {
        float carbohydrates = CalculateManager.getKcal(product.getCarbohydrates(),
                CalculateManager.Organic.CARBOHYDRATES);
        float protein = CalculateManager.getKcal(product.getProtein(),
                CalculateManager.Organic.PROTEIN);
        float fat = CalculateManager.getKcal(product.getFat(),
                CalculateManager.Organic.FAT);
        float total = getTotal(carbohydrates, protein, fat);
        setTv(tvCarbohydratesKcal, carbohydrates, MainGlobals.STR_KCAL);
        setTv(tvProteinKcal, protein, MainGlobals.STR_KCAL);
        setTv(tvFatKcal, fat, MainGlobals.STR_KCAL);
        setTv(tvTotalKcal, total, MainGlobals.STR_KCAL);

        ArrayList<PieEntry> entries = getEntries(product, false);
        setPieChart(pcKcal, entries);
    }

    private void setPcKj(Product product) {
        float carbohydrates = CalculateManager.getKj(product.getCarbohydrates(),
                CalculateManager.Organic.CARBOHYDRATES);
        float protein = CalculateManager.getKj(product.getProtein(),
                CalculateManager.Organic.PROTEIN);
        float fat = CalculateManager.getKj(product.getFat(),
                CalculateManager.Organic.FAT);
        float total = getTotal(carbohydrates, protein, fat);
        setTv(tvCarbohydratesKj, carbohydrates, MainGlobals.STR_KJ);
        setTv(tvProteinKj, protein, MainGlobals.STR_KJ);
        setTv(tvFatKj, fat, MainGlobals.STR_KJ);
        setTv(tvTotalKj, total, MainGlobals.STR_KJ);

        ArrayList<PieEntry> entries = getEntries(product, false);
        setPieChart(pcKj, entries);
    }

    private float getTotal(float carbohydrates, float protein, float fat) {
        return carbohydrates + protein + fat;
    }

    private ArrayList<Integer> getColors() {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.pr_normal));
        colors.add(getResources().getColor(R.color.pr_dark));
        colors.add(getResources().getColor(R.color.pr_light));
        colors.add(getResources().getColor(R.color.ac_normal));
        return colors;
    }

    private ArrayList<PieEntry> getEntries(Product product, boolean hasFour) {
        ArrayList<PieEntry> values = new ArrayList<>();
        values.add(new PieEntry(product.getCarbohydrates(), getString(R.string.ct_carbohydrates)));
        values.add(new PieEntry(product.getProtein(), getString(R.string.ct_protein)));
        values.add(new PieEntry(product.getFat(), getString(R.string.ct_fat)));
        if(hasFour) values.add(new PieEntry(getOther(product), getString(R.string.ct_other)));
        return values;
    }

    private ArrayList<BarEntry> getBarEntries(Product product, boolean hasFour) {
        ArrayList<BarEntry> values = new ArrayList<>();
        values.add(new BarEntry(0f, product.getCarbohydrates()));
        values.add(new BarEntry(1f, product.getProtein()));
        values.add(new BarEntry(2f, product.getFat()));
        if(hasFour) values.add(new BarEntry(3f, getOther(product)));
        return values;
    }

    private PieDataSet getDataSet(ArrayList<PieEntry> entries, PieChart chart) {
        PieDataSet dataSet = new PieDataSet(entries, MainGlobals.STR_EMPTY_OBJ_INIT);
        dataSet.setColors(getColors());
        dataSet.setSliceSpace(6);
        dataSet.setValueTextSize(12);
        dataSet.setValueTextColor(getResources().getColor(android.R.color.white));
        dataSet.setValueFormatter(new PercentFormatter(chart));
        return dataSet;
    }

    private BarDataSet getDataSet(ArrayList<BarEntry> entries) {
        BarDataSet dataSet = new BarDataSet(entries, MainGlobals.STR_EMPTY_OBJ_INIT);
        dataSet.setColors(getColors());
        dataSet.setValueTextSize(12);
        dataSet.setValueTextColor(getResources().getColor(android.R.color.white));
        //dataSet.setValueFormatter(new PercentFormatter(chart));
        return dataSet;
    }

    private void setLegend(Legend legend) {
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(12);
        legend.setTextColor(getResources().getColor(android.R.color.tab_indicator_text));
    }

    private void setImageType(int type){
        TypedArray arrayType = getResources().obtainTypedArray(R.array.dw_product);
        int resource = arrayType.getResourceId(type, -1);
        ivType.setImageResource(resource);
    }

    private enum Type {
        READ, DELETE
    }
}
