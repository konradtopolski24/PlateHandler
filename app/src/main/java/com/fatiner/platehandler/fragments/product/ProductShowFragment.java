package com.fatiner.platehandler.fragments.product;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.fatiner.platehandler.PlateHandlerDatabase;
import com.fatiner.platehandler.R;
import com.fatiner.platehandler.details.ProductDetails;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.globals.Chart;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.models.Product;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ProductShowFragment extends PrimaryFragment {

    @BindView(R.id.cv_composition) CardView cvComposition;
    @BindView(R.id.cv_kcal) CardView cvKcal;
    @BindView(R.id.cv_kj) CardView cvKj;
    @BindView(R.id.iv_hd_composition) ImageView ivHdComposition;
    @BindView(R.id.iv_hd_kcal) ImageView ivHdKcal;
    @BindView(R.id.iv_hd_kj) ImageView ivHdKj;
    @BindView(R.id.iv_photo) ImageView ivPhoto;
    @BindView(R.id.iv_type) ImageView ivType;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_type) TextView tvType;
    @BindView(R.id.tv_carbohydrates1) TextView tvCarbohydrates1;
    @BindView(R.id.tv_proteins1) TextView tvProteins1;
    @BindView(R.id.tv_fats1) TextView tvFats1;
    @BindView(R.id.tv_other) TextView tvOther;
    @BindView(R.id.tv_carbohydrates2) TextView tvCarbohydrates2;
    @BindView(R.id.tv_proteins2) TextView tvProteins2;
    @BindView(R.id.tv_fats2) TextView tvFats2;
    @BindView(R.id.tv_total1) TextView tvTotal1;
    @BindView(R.id.tv_carbohydrates3) TextView tvCarbohydrates3;
    @BindView(R.id.tv_proteins3) TextView tvProteins3;
    @BindView(R.id.tv_fats3) TextView tvFats3;
    @BindView(R.id.tv_total2) TextView tvTotal2;
    @BindView(R.id.tv_empty) TextView tvEmpty;
    @BindView(R.id.bc_composition) BarChart bcComposition;
    @BindView(R.id.pc_composition) PieChart pcComposition;

    @OnClick(R.id.cv_hd_composition)
    void clickCvHdComposition() {
        manageExpandCv(cvComposition, ivHdComposition);
    }

    @OnClick(R.id.cv_hd_kcal)
    void clickCvHdKcal() {
        manageExpandCv(cvKcal, ivHdKcal);
    }

    @OnClick(R.id.cv_hd_kj)
    void clickCvHdKj() {
        manageExpandCv(cvKj, ivHdKj);
    }

    @OnClick(R.id.iv_tt_composition)
    void clickIvTtNutrients() {
        showDialog(R.string.hd_pd_sh_composition, R.string.tt_pd_sh_composition);
    }

    @OnClick(R.id.iv_tt_kcal)
    void clickIvTtKcal() {
        showDialog(R.string.hd_pd_sh_kcal, R.string.tt_pd_sh_kcal);
    }

    @OnClick(R.id.iv_tt_kj)
    void clickIvTtKj() {
        showDialog(R.string.hd_pd_sh_kj, R.string.tt_pd_sh_kj);
    }

    public static ProductShowFragment getInstance(int id) {
        ProductShowFragment fragment = new ProductShowFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Globals.BN_INT, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_product_show, container, false);
        init(this, view, R.id.it_product, R.string.tb_pd_show, true);
        initAction();
        return view;
    }

    private Product getProduct() {
        return ProductDetails.getProduct();
    }

    private void initAction() {
        resetProductDetails();
        readProduct();
    }

    private void setProductDetails(Product product) {
        getProduct().setId(product.getId());
        getProduct().setName(product.getName());
        getProduct().setType(product.getType());
        getProduct().setSize(product.getSize());
        getProduct().setCarbohydrates(product.getCarbohydrates());
        getProduct().setProteins(product.getProteins());
        getProduct().setFats(product.getFats());
        getProduct().setPhoto(getImage(Globals.NM_PRODUCT, product.getId()));
    }

    private void setViews() {
        if (areNutrientsEmpty()) setEmptyInfo();
        else {
            setProductInfo();
            setEnergyInfo();
        }
    }

    private boolean areNutrientsEmpty() {
        return getProduct().getSize() == Globals.DF_ZERO;
    }

    private void setProductInfo() {
        Product product = getProduct();
        setTv(tvName, product.getName());
        setTv(tvType, product.getType(), R.array.tx_product);
        setIv(ivType, product.getType(), R.array.dw_product);
        setTv(tvCarbohydrates1, product.getCarbohydrates(), getUnit());
        setTv(tvProteins1, product.getProteins(), getUnit());
        setTv(tvFats1, product.getFats(), getUnit());
        setTv(tvOther, product.getOther(), getUnit());
        setIv(ivPhoto, product.getPhoto());
    }

    private String getUnit() {
        if (isMillilitres()) return Globals.UT_MILLILITRE;
        return Globals.UT_GRAM;
    }

    private boolean isMillilitres() {
        return isType(R.string.ar_pd_liquid) || isType(R.string.ar_pd_sauce);
    }

    private boolean isType(int id) {
        String[] arrayType = getStringArray(R.array.tx_product);
        return getString(id).equals(arrayType[getProduct().getType()]);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pd_show, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.it_edit:
                setFragment(ProductManageFragment.getInstance(true, Globals.DF_DECREMENT));
                return true;
            case R.id.it_remove:
                showDialog(R.string.dg_pd_remove, getDialogListener());
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private DialogInterface.OnClickListener getDialogListener() {
        return (DialogInterface dialog, int which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    readIngredientAmount();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };
    }

    private void setEnergyInfo() {
        setBc(bcComposition);
        setPc(pcComposition);
        setKcalInfo();
        setKjInfo();
    }

    private void setBc(BarChart bc) {
        bc.setTouchEnabled(false);
        bc.getDescription().setEnabled(false);
        bc.getLegend().setEnabled(false);
        bc.animateY(Chart.AM_BC);
        bc.setExtraOffsets(Globals.DF_ZERO, Globals.DF_ZERO, Globals.DF_ZERO, Chart.OS_BC);
        bc.getAxisRight().setEnabled(false);
        setXAxis(bc.getXAxis());
        setYAxis(bc.getAxisLeft());
        bc.setData(getBarData());
        bc.invalidate();
    }

    private void setXAxis(XAxis axis) {
        axis.setValueFormatter(new IndexAxisValueFormatter(getLabels()));
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setTextColor(getColor(R.color.tv_gray));
        axis.setTextSize(getInt(R.integer.tx_axis));
    }

    private void setYAxis(YAxis axis) {
        axis.setValueFormatter(new PercentFormatter());
        axis.setTextColor(getColor(R.color.tv_gray));
        axis.setTextSize(getInt(R.integer.tx_axis));
    }

    private ArrayList<BarEntry> getBarEntries() {
        Product product = getProduct();
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(Chart.VX_CARBOHYDRATES, product.getPercentage(
                Globals.NT_CARBOHYDRATES)));
        entries.add(new BarEntry(Chart.VX_PROTEINS, product.getPercentage(Globals.NT_PROTEINS)));
        entries.add(new BarEntry(Chart.VX_FATS, product.getPercentage(Globals.NT_FATS)));
        return entries;
    }

    private ArrayList<String> getLabels() {
        ArrayList<String> labels = new ArrayList<>();
        labels.add(Globals.SN_EMPTY);
        labels.add(getString(R.string.ct_carbohydrates));
        labels.add(getString(R.string.ct_proteins));
        labels.add(getString(R.string.ct_fats));
        return labels;
    }

    private BarDataSet getBarDataSet() {
        BarDataSet dataSet = new BarDataSet(getBarEntries(), Globals.SN_EMPTY);
        dataSet.setValueTextColor(getColor(R.color.tv_gray));
        dataSet.setColors(getBcColors());
        dataSet.setValueTextSize(getInt(R.integer.tx_bar));
        return dataSet;
    }

    private BarData getBarData() {
        BarData data = new BarData(getBarDataSet());
        data.setValueFormatter(new PercentFormatter());
        data.setBarWidth(Chart.WT_BAR);
        return data;
    }

    private void setPc(PieChart pc) {
        pc.getDescription().setEnabled(false);
        pc.setDrawEntryLabels(false);
        pc.setHighlightPerTapEnabled(false);
        pc.setUsePercentValues(true);
        pc.setRotationEnabled(true);
        pc.setTransparentCircleAlpha(Globals.DF_ZERO);
        pc.setHoleRadius(Chart.RD_HOLE);
        pc.animateY(Chart.AM_PC);
        pc.setExtraOffsets(Chart.OS_PC, Globals.DF_ZERO, Chart.OS_PC, Globals.DF_ZERO);
        setLegend(pc.getLegend());
        pc.setData(new PieData(getPieDataSet(pc)));
        pc.invalidate();
    }

    private ArrayList<Integer> getBcColors() {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getColor(R.color.bc_red));
        colors.add(getColor(R.color.bc_green));
        colors.add(getColor(R.color.bc_blue));
        return colors;
    }

    private ArrayList<Integer> getPcColors() {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getColor(R.color.pr_normal));
        colors.add(getColor(R.color.pr_light));
        return colors;
    }

    private ArrayList<PieEntry> getPieEntries() {
        Product product = getProduct();
        ArrayList<PieEntry> values = new ArrayList<>();
        values.add(new PieEntry(product.getTotalNutrients(), getString(R.string.ct_nutrients)));
        values.add(new PieEntry(product.getOther(), getString(R.string.ct_other)));
        return values;
    }

    private PieDataSet getPieDataSet(PieChart pc) {
        PieDataSet dataSet = new PieDataSet(getPieEntries(), Globals.SN_EMPTY);
        dataSet.setColors(getPcColors());
        dataSet.setSliceSpace(Chart.SC_SLICE);
        dataSet.setValueTextSize(getInt(R.integer.tx_pie));
        dataSet.setValueLinePart1Length(Chart.LT_LINE);
        dataSet.setValueLinePart2Length(Chart.LT_LINE);
        dataSet.setValueTextColor(getColor(R.color.tv_gray));
        dataSet.setValueLineColor(getColor(R.color.tv_gray));
        dataSet.setValueFormatter(new PercentFormatter(pc));
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        return dataSet;
    }

    private void setLegend(Legend legend) {
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(getInt(R.integer.tx_legend));
        legend.setXEntrySpace(Chart.SC_LEGEND);
        legend.setTextColor(getColor(R.color.tv_gray));
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
    }

    private void setKcalInfo() {
        Product product = getProduct();
        setTv(tvCarbohydrates2, product.getKcal(Globals.NT_CARBOHYDRATES), Globals.UT_KCAL);
        setTv(tvProteins2, product.getKcal(Globals.NT_PROTEINS), Globals.UT_KCAL);
        setTv(tvFats2, product.getKcal(Globals.NT_FATS), Globals.UT_KCAL);
        setTv(tvTotal1, product.getTotalKcal(), Globals.UT_KCAL);
    }

    private void setKjInfo() {
        Product product = getProduct();
        setTv(tvCarbohydrates3, product.getKj(Globals.NT_CARBOHYDRATES), Globals.UT_KJ);
        setTv(tvProteins3, product.getKj(Globals.NT_PROTEINS), Globals.UT_KJ);
        setTv(tvFats3, product.getKj(Globals.NT_FATS), Globals.UT_KJ);
        setTv(tvTotal2, product.getTotalKj(), Globals.UT_KJ);
    }

    private void setEmptyInfo() {
        Product product = getProduct();
        setTv(tvName, product.getName());
        setTv(tvType, product.getType(), R.array.tx_product);
        setIv(ivType, product.getType(), R.array.dw_product);
        setIv(ivPhoto, product.getPhoto());
        setTv(tvCarbohydrates1, Globals.SN_DASH);
        setTv(tvCarbohydrates2, Globals.SN_DASH);
        setTv(tvCarbohydrates3, Globals.SN_DASH);
        setTv(tvProteins1, Globals.SN_DASH);
        setTv(tvProteins2, Globals.SN_DASH);
        setTv(tvProteins3, Globals.SN_DASH);
        setTv(tvFats1, Globals.SN_DASH);
        setTv(tvFats2, Globals.SN_DASH);
        setTv(tvFats3, Globals.SN_DASH);
        setTv(tvOther, Globals.SN_DASH);
        setTv(tvTotal1, Globals.SN_DASH);
        setTv(tvTotal2, Globals.SN_DASH);
        setTv(tvEmpty, Globals.SN_DASH);
        removeView(bcComposition);
        removeView(pcComposition);
    }

    private void readProduct() {
        PlateHandlerDatabase db = getDb(getContext());
        int id = getIntFromBundle();
        Single<Product> single = db.getProductDAO().getProduct(id);
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getReadObserver());
    }

    private DisposableSingleObserver<Product> getReadObserver() {
        return new DisposableSingleObserver<Product>() {

            @Override
            public void onSuccess(Product product) {
                setProductDetails(product);
                setViews();
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    private void removeProduct() {
        getRemoveCompletable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getRemoveObserver());
    }

    private Completable getRemoveCompletable() {
        return Completable.fromAction(() -> {
            PlateHandlerDatabase db = getDb(getContext());
            db.getProductDAO().deleteProduct(getProduct());
        });
    }

    private DisposableCompletableObserver getRemoveObserver() {
        return new DisposableCompletableObserver() {

            @Override
            public void onComplete() {
                removeImage(Globals.NM_PRODUCT, getProduct().getId());
                productSuccess(R.string.sb_pd_remove);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    private void readIngredientAmount() {
        PlateHandlerDatabase db = getDb(getContext());
        Single<Integer> single = db.getIngredientDAO().getRowCount(getProduct().getId());
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getAmountObserver());
    }

    private DisposableSingleObserver<Integer> getAmountObserver() {
        return new DisposableSingleObserver<Integer>() {

            @Override
            public void onSuccess(Integer amount) {
                if (amount == Globals.DF_ZERO) removeProduct();
                else showShortToast(R.string.ts_used);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }
}
