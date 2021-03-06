package com.fatiner.platehandler.fragments.product;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.details.ProductDetails;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.globals.Format;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.Product;
import com.fatiner.platehandler.viewmodels.product.ProductManageViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProductManageFragment extends PrimaryFragment {

    private ProductManageViewModel viewModel;
    private CompositeDisposable disposables;

    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.iv_type)
    ImageView ivType;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_size)
    EditText etSize;
    @BindView(R.id.et_carbohydrates)
    EditText etCarbohydrates;
    @BindView(R.id.et_proteins)
    EditText etProteins;
    @BindView(R.id.et_fats)
    EditText etFats;
    @BindView(R.id.til_name)
    TextInputLayout tilName;
    @BindView(R.id.til_size)
    TextInputLayout tilSize;
    @BindView(R.id.til_carbohydrates)
    TextInputLayout tilCarbohydrates;
    @BindView(R.id.til_proteins)
    TextInputLayout tilProteins;
    @BindView(R.id.til_fats)
    TextInputLayout tilFats;
    @BindView(R.id.sp_type)
    Spinner spType;

    @OnTextChanged(R.id.et_name)
    void changedEtName(CharSequence text) {
        getProduct().setName(String.valueOf(text));
        setError(tilName, R.string.er_pd_name, isNameEmpty());
    }

    @OnTextChanged(R.id.et_size)
    void changedEtSize(CharSequence text) {
        getProduct().setSize(getCorrectEtValue(text));
        setNutrientsErrors();
    }

    @OnTextChanged(R.id.et_carbohydrates)
    void changedEtCarbohydrates(CharSequence text) {
        getProduct().setCarbohydrates(getCorrectEtValue(text));
        setNutrientsErrors();
    }

    @OnTextChanged(R.id.et_proteins)
    void changedEtProteins(CharSequence text) {
        getProduct().setProteins(getCorrectEtValue(text));
        setNutrientsErrors();
    }

    @OnTextChanged(R.id.et_fats)
    void changedEtFats(CharSequence text) {
        getProduct().setFats(getCorrectEtValue(text));
        setNutrientsErrors();
    }

    @OnClick(R.id.ib_add)
    void clickIbAdd() {
        selectImage();
        getProduct().setPhotoChanged(true);
    }

    @OnClick(R.id.ib_remove)
    void clickIbRemove() {
        getProduct().setPhoto(null);
        setIv(ivPhoto, getProduct().getPhoto());
        getProduct().setPhotoChanged(true);
    }

    @OnClick(R.id.fab_finished)
    void clickFabFinished() {
        hideKeyboard();
        endAction();
    }

    @OnItemSelected(R.id.sp_type)
    void selectedSpType(int id) {
        setIv(ivType, id, R.array.dw_pd_type);
        getProduct().setType(id);
        manageHints();
    }

    @OnClick(R.id.iv_tt_photo)
    void clickIvTtPhoto() {
        showDialog(R.string.hd_pd_mg_photo, R.string.tt_pd_mg_photo);
    }

    @OnClick(R.id.iv_tt_essential)
    void clickIvTtEssential() {
        showDialog(R.string.hd_pd_mg_essential, R.string.tt_pd_mg_essential);
    }

    @OnClick(R.id.iv_tt_composition)
    void clickIvTtComposition() {
        showDialog(R.string.hd_pd_mg_composition, R.string.tt_pd_mg_composition);
    }

    public static ProductManageFragment getInstance(boolean isEditing, int position) {
        ProductManageFragment fragment = new ProductManageFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Globals.BN_BOOL, isEditing);
        bundle.putInt(Globals.BN_INT, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_product_manage, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initOptions(getMenuId(), getToolbar(), false);
        initViewModelEssentials();
        setEtInput();
        setViews();
    }

    private void initViewModelEssentials() {
        viewModel = new ViewModelProvider(this).get(ProductManageViewModel.class);
        disposables = new CompositeDisposable();
    }

    private Product getProduct() {
        return ProductDetails.getProduct();
    }

    private int getMenuId() {
        if (isPosition()) return R.id.it_recipe;
        else return R.id.it_product;
    }

    private int getToolbar() {
        if (isEditing()) return R.string.tb_pd_edit;
        else return R.string.tb_pd_add;
    }

    private void setEtInput() {
        setCorrectInput(etSize);
        setCorrectInput(etCarbohydrates);
        setCorrectInput(etProteins);
        setCorrectInput(etFats);
    }

    private void setViews() {
        Product product = getProduct();
        setEt(etName, product.getName());
        setSp(spType, product.getType(), getEntries(R.array.tx_pd_type), getContext());
        setEt(etSize, product.getSize());
        setEt(etCarbohydrates, product.getCarbohydrates());
        setEt(etProteins, product.getProteins());
        setEt(etFats, product.getFats());
        manageHints();
        setIv(ivPhoto, getProduct().getPhoto());
    }

    private void chooseDialog() {
        if (isEditing()) showDialog(R.string.dg_pd_edit, getDialogListener());
        else showDialog(R.string.dg_pd_add, getDialogListener());
    }

    private void chooseDbAction() {
        if (isEditing()) observeUpdateProduct();
        else observeAddProduct();
    }

    private void setProductInIngredient(int id) {
        if (isPosition()) {
            int position = getIntFromBundle();
            Ingredient ingredient = RecipeDetails.getRecipe().getIngredients().get(position);
            ingredient.setProductId(id);
        }
    }

    private void endAction() {
        if (isIncomplete()) showShortToast(R.string.ts_product);
        else chooseDialog();
    }

    private void setNutrientsErrors() {
        setError(tilCarbohydrates, R.string.er_pd_nutrients, isSizeSmaller());
        setError(tilProteins, R.string.er_pd_nutrients, isSizeSmaller());
        setError(tilFats, R.string.er_pd_nutrients, isSizeSmaller());
    }

    private boolean isIncomplete() {
        return isNameEmpty() || isSizeSmaller();
    }

    private boolean isNameEmpty() {
        return getProduct().getName().equals(Globals.SN_EMPTY);
    }

    private boolean isSizeSmaller() {
        return getProduct().getSize() < getNutrientsSum();
    }

    private float getNutrientsSum() {
        Product product = getProduct();
        return product.getCarbohydrates() + product.getProteins() + product.getFats();
    }

    private DialogInterface.OnClickListener getDialogListener() {
        return (DialogInterface dialog, int which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    chooseDbAction();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };
    }

    private boolean isEditing() {
        return getBundle().getBoolean(Globals.BN_BOOL);
    }

    private boolean isPosition() {
        int position = getBundle().getInt(Globals.BN_INT);
        return position != Globals.DF_DECREMENT;
    }

    private void manageHints() {
        if (isMillilitres()) setHints(Globals.UT_MILLILITRE);
        else setHints(Globals.UT_GRAM);
    }

    private void setHints(String unit) {
        setHint(tilSize, getHint(R.string.ct_size, unit));
        setHint(tilCarbohydrates, getHint(R.string.ct_carbohydrates, unit));
        setHint(tilProteins, getHint(R.string.ct_proteins, unit));
        setHint(tilFats, getHint(R.string.ct_fats, unit));
    }

    private String getHint(int id, String unit) {
        return String.format(Locale.ENGLISH, Format.FM_HINT, getString(id), unit);
    }

    private boolean isMillilitres() {
        return isType(R.string.ar_pd_liquid) || isType(R.string.ar_pd_sauce);
    }

    private boolean isType(int id) {
        String[] arrayType = getStringArray(R.array.tx_pd_type);
        return getString(id).equals(arrayType[getProduct().getType()]);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        Bitmap photo = getImageFromGallery(resultCode, data);
        getProduct().setPhoto(photo);
        setIv(ivPhoto, getProduct().getPhoto());
    }

    private void observeAddProduct() {
        viewModel.addProduct(getProduct())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getInsertObserver());
    }

    private void observeUpdateProduct() {
        viewModel.updateProduct(getProduct())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUpdateObserver());
    }

    private SingleObserver<Long> getInsertObserver() {
        return new SingleObserver<Long>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onSuccess(Long id) {
                setProductInIngredient(TypeManager.longToInt(id));
                if (getProduct().isPhotoChanged()) manageImageSaving(getProduct().getPhoto(),
                        Globals.NM_PRODUCT, TypeManager.longToInt(id));
                productSuccess(R.string.sb_pd_add);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    private CompletableObserver getUpdateObserver() {
        return new CompletableObserver() {

            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onComplete() {
                if (getProduct().isPhotoChanged()) manageImageSaving(getProduct().getPhoto(),
                        Globals.NM_PRODUCT, getProduct().getId());
                productSuccess(R.string.sb_pd_edit);
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
