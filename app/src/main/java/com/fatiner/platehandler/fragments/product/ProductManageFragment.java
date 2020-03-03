package com.fatiner.platehandler.fragments.product;

import android.content.DialogInterface;
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
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.Product;

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
    @BindView(R.id.sp_type) Spinner spType;

    @OnTextChanged(R.id.et_name)
    void changedEtName(CharSequence text) {
        ProductDetails.getProduct().setName(String.valueOf(text));
    }

    @OnTextChanged(R.id.et_size)
    void changedEtSize(CharSequence text) {
        ProductDetails.getProduct().setSize(getCorrectEtValue(text));
    }

    @OnTextChanged(R.id.et_carbohydrates)
    void changedEtCarbohydrates(CharSequence text) {
        ProductDetails.getProduct().setCarbohydrates(getCorrectEtValue(text));
    }

    @OnTextChanged(R.id.et_proteins)
    void changedEtProteins(CharSequence text) {
        ProductDetails.getProduct().setProteins(getCorrectEtValue(text));
    }

    @OnTextChanged(R.id.et_fats)
    void changedEtFats(CharSequence text) {
        ProductDetails.getProduct().setFats(getCorrectEtValue(text));
    }

    @OnClick(R.id.ib_add)
    void clickIbAdd() {

    }

    @OnClick(R.id.ib_remove)
    void clickIbRemove() {

    }

    @OnClick(R.id.fab_finished)
    void clickFabFinished() {
        hideKeyboard();
        endAction();
    }

    @OnItemSelected(R.id.sp_type)
    void selectedSpType(int id) {
        setIv(ivType, id, R.array.dw_product);
        ProductDetails.getProduct().setType(id);
    }

    @OnClick(R.id.iv_tt_photo)
    void clickIvTtPhoto() {
        showDialog(R.string.hd_pd_photo, R.string.tt_pd_photo);
    }

    @OnClick(R.id.iv_tt_name)
    void clickIvTtName() {
        showDialog(R.string.hd_pd_name, R.string.tt_pd_name);
    }

    @OnClick(R.id.iv_tt_nutrients)
    void clickIvTtNutrients() {
        showDialog(R.string.hd_pd_nutrient, R.string.tt_pd_nutrient);
    }

    public ProductManageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_product_manage, container, false);
        init(this, view, getMenuId(), getToolbar(), false);
        manageEtInput();
        setProductInfo();
        return view;
    }

    private int getMenuId() {
        if(isPosition()) return R.id.it_recipe;
        else return R.id.it_product;
    }

    private int getToolbar() {
        if(isEditing()) return R.string.tb_pd_edit;
        else return R.string.tb_pd_add;
    }

    private void manageEtInput() {
        setCorrectInput(etSize);
        setCorrectInput(etCarbohydrates);
        setCorrectInput(etProteins);
        setCorrectInput(etFats);
    }

    private void setProductInfo() {
        Product product = ProductDetails.getProduct();
        setEt(etName, product.getName());
        setSp(spType, product.getType());
        setEt(etSize, product.getSize());
        setEt(etCarbohydrates, product.getCarbohydrates());
        setEt(etProteins, product.getProteins());
        setEt(etFats, product.getFats());
    }

    private void chooseDialog() {
        if(isEditing()) showDialog(R.string.dg_pd_edit, getDialogListener());
        else showDialog(R.string.dg_pd_add, getDialogListener());
    }

    private void chooseDbAction() {
        if(isEditing()) updateProduct();
        else insertProduct();
    }

    private void setProductInIngredient(int id) {
        if(isPosition()) {
            int position = getIntFromBundle();
            Ingredient ingredient = RecipeDetails.getRecipe().getIngredients().get(position);
            ingredient.setProductId(id);
        }
    }

    private void endAction() {
        if(ProductDetails.isProductCorrect()) chooseDialog();
        else showShortToast(R.string.ts_product);
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

    //Insert Product
    private void insertProduct() {
        PlateHandlerDatabase db = getDb(getContext());
        Single<Long> single = db.getProductDAO().addProduct(ProductDetails.getProduct());
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getInsertProductObserver());
    }

    private DisposableSingleObserver<Long> getInsertProductObserver() {
        return new DisposableSingleObserver<Long>() {

            @Override
            public void onSuccess(Long id) {
                setProductInIngredient(TypeManager.longToInt(id));
                productSuccess(R.string.sb_pd_add);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    //Update Product
    private void updateProduct() {
        getCompletable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUpdateProductObserver());
    }

    private Completable getCompletable() {
        return Completable.fromAction(() -> {
            PlateHandlerDatabase db = getDb(getContext());
            db.getProductDAO().updateProduct(ProductDetails.getProduct());
        });
    }

    private DisposableCompletableObserver getUpdateProductObserver() {
        return new DisposableCompletableObserver() {

            @Override
            public void onComplete() {
                productSuccess(R.string.sb_pd_edit);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }




    //Image
    /*private void setImagePhoto(String encodedImage) {
        if(encodedImage == null) return;
        ivPhoto.setImageBitmap(TypeManager.base64StringToBitmap(encodedImage));
    }*/

    /*private void setEncodedImageInfo(int resultCode, Intent data) {
        String encodedImage = getEncodedImage(resultCode, data);
        //ProductDetails.getProduct().setEncodedImage(encodedImage);
        setImagePhoto(encodedImage);
    }*/

    /*private void manageImageSaving() {
        if(ProductDetails.getProduct().getEncodedImage() == null) {
            ImageManager.deleteImage(ImageManager.getImageProductName(ProductDetails.getProduct().getId()));
        } else {
            ImageManager.saveImage(
                    TypeManager.base64StringToBitmap(ProductDetails.getProduct().getEncodedImage()),
                    ImageManager.getImageProductName(ProductDetails.getProduct().getId()));
        }
    }*/

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case Globals.PH_REQUEST:
                setEncodedImageInfo(resultCode, data);
                break;
        }
    }*/
}
