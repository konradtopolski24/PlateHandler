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

import com.fatiner.platehandler.PlateHandlerDatabase;
import com.fatiner.platehandler.R;
import com.fatiner.platehandler.details.ProductDetails;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.globals.Format;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.Product;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ProductManageFragment extends PrimaryFragment {

    @BindView(R.id.iv_photo) ImageView ivPhoto;
    @BindView(R.id.iv_type) ImageView ivType;
    @BindView(R.id.et_name) EditText etName;
    @BindView(R.id.et_size) EditText etSize;
    @BindView(R.id.et_carbohydrates) EditText etCarbohydrates;
    @BindView(R.id.et_proteins) EditText etProteins;
    @BindView(R.id.et_fats) EditText etFats;
    @BindView(R.id.til_size) TextInputLayout tilSize;
    @BindView(R.id.til_carbohydrates) TextInputLayout tilCarbohydrates;
    @BindView(R.id.til_proteins) TextInputLayout tilProteins;
    @BindView(R.id.til_fats) TextInputLayout tilFats;
    @BindView(R.id.sp_type) Spinner spType;

    @OnTextChanged(R.id.et_name)
    void changedEtName(CharSequence text) {
        getProduct().setName(String.valueOf(text));
        setError(etName, R.string.er_pd_name, isNameEmpty());
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
        setIv(ivType, id, R.array.dw_product);
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

    public ProductManageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_product_manage, container,
                false);
        init(this, view, getMenuId(), getToolbar(), false);
        initAction();
        return view;
    }

    private Product getProduct() {
        return ProductDetails.getProduct();
    }

    private void initAction() {
        setEtInput();
        setViews();
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
        setSp(spType, product.getType(), getEntries(R.array.tx_product), getContext());
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
        if (isEditing()) updateProduct();
        else insertProduct();
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
        setError(etCarbohydrates, R.string.er_pd_nutrients, isSizeSmaller());
        setError(etProteins, R.string.er_pd_nutrients, isSizeSmaller());
        setError(etFats, R.string.er_pd_nutrients, isSizeSmaller());
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
        return isValueInBundle(Globals.BN_BOOL);
    }

    private boolean isPosition() {
        return isValueInBundle(Globals.BN_INT);
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
        String[] arrayType = getStringArray(R.array.tx_product);
        return getString(id).equals(arrayType[getProduct().getType()]);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        Bitmap photo = getImageFromGallery(resultCode, data);
        getProduct().setPhoto(photo);
        setIv(ivPhoto, getProduct().getPhoto());
    }

    private void insertProduct() {
        PlateHandlerDatabase db = getDb(getContext());
        Single<Long> single = db.getProductDAO().addProduct(getProduct());
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getInsertObserver());
    }

    private DisposableSingleObserver<Long> getInsertObserver() {
        return new DisposableSingleObserver<Long>() {

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

    private void updateProduct() {
        getUpdateCompletable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUpdateObserver());
    }

    private Completable getUpdateCompletable() {
        return Completable.fromAction(() -> {
            PlateHandlerDatabase db = getDb(getContext());
            db.getProductDAO().updateProduct(getProduct());
        });
    }

    private DisposableCompletableObserver getUpdateObserver() {
        return new DisposableCompletableObserver() {

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
}
