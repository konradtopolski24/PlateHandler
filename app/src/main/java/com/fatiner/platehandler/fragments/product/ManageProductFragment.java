package com.fatiner.platehandler.fragments.product;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.classes.Ingredient;
import com.fatiner.platehandler.classes.Product;
import com.fatiner.platehandler.details.ProductDetails;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.BundleGlobals;
import com.fatiner.platehandler.globals.ImageGlobals;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.managers.ImageManager;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.managers.database.DbSuccessManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class ManageProductFragment extends PrimaryFragment {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_carbohydrates)
    EditText etCarbohydrates;
    @BindView(R.id.et_protein)
    EditText etProtein;
    @BindView(R.id.et_fat)
    EditText etFat;
    @BindView(R.id.sp_type)
    Spinner spType;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;

    @OnTextChanged(R.id.et_name)
    public void onTextChangedEtName(CharSequence text){
        ProductDetails.getProduct().setName(String.valueOf(text));
    }

    @OnTextChanged(R.id.et_carbohydrates)
    public void onTextChangedEtCarbohydrates(CharSequence text){
        float carbohydrates = checkEditTextValue(String.valueOf(text));
        setProductCarbohydrates(carbohydrates);
    }

    @OnTextChanged(R.id.et_protein)
    public void onTextChangedEtProtein(CharSequence text){
        float protein = checkEditTextValue(String.valueOf(text));
        setProductProtein(protein);
    }

    @OnTextChanged(R.id.et_fat)
    public void onTextChangedEtFat(CharSequence text){
        float fat = checkEditTextValue(String.valueOf(text));
        setProductFat(fat);
    }

    @OnClick(R.id.ib_add)
    public void onClickIbAdd(){
        selectPhoto();
    }

    @OnClick(R.id.ib_remove)
    public void onClickIbRemove(){
        ProductDetails.getProduct().setEncodedImage(null);
        ivPhoto.setImageResource(android.R.color.transparent);
    }

    @OnClick(R.id.fab_done)
    public void onClickFabDone(){
        hideKeyboard();
        if(ProductDetails.isProductCorrect()){
            chooseDialog();
        } else {
            showShortToast(R.string.ts_product_empty);
        }
    }

    @OnItemSelected(R.id.sp_type)
    public void onItemSelectedSpType(int id){
        ProductDetails.getProduct().setType(id);
    }

    public ManageProductFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_product, container, false);
        ButterKnife.bind(this, view);
        chooseToolbar();
        setMenuItem(MainGlobals.ID_INGREDIENTS_DRAW_MAIN);
        setProductInfo();
        return view;
    }

    private void chooseToolbar(){
        if(isEditing()){
            setToolbarTitle(R.string.tb_product_edit);
        } else {
            setToolbarTitle(R.string.tb_product_add);
        }
    }

    private void setProductInfo(){
        Product product = ProductDetails.getProduct();
        setEditName(product.getName());
        setSpinType(product.getType());
        setEditCarbohydrates(product.getCarbohydrates());
        setEditProtein(product.getProtein());
        setEditFat(product.getFat());
        setImagePhoto(product.getEncodedImage());
    }

    private void setEditName(String text){
        etName.setText(text);
    }

    private void setSpinType(int type){
        spType.setSelection(type);
    }

    private void setEditCarbohydrates(float carbohydrates){
        etCarbohydrates.setText(String.valueOf(carbohydrates));
    }

    private void setEditProtein(float protein){
        etProtein.setText(String.valueOf(protein));
    }

    private void setEditFat(float fat){
        etFat.setText(String.valueOf(fat));
    }

    private void chooseDialog(){
        if(isEditing()){
            showAlertDialog(R.string.dg_product_edit, getDialogListener());
        } else {
            showAlertDialog(R.string.dg_product_add, getDialogListener());
        }
    }

    private void chooseDatabaseAction(){
        if(isEditing()){
            new AsyncUpdateProduct().execute();
        } else {
            new AsyncInsertProduct().execute();
        }
    }

    private boolean isEditing(){
        if(isBundleNotEmpty()){
            return getBoolFromBundle(BundleGlobals.BUND_BOOL_FRAG_ADDPROD);
        } else {
            return false;
        }
    }

    private void setIdProductInIngredient(int id){
        if(isBundleNotEmpty()){
            Ingredient ingredient = RecipeDetails.getTempIngredients().get(getIngredientPosition());
            ingredient.getProduct().setId(id);
        }
    }

    private int getIngredientPosition(){
        return getIntFromBundle(BundleGlobals.BUND_ID_FRAG_ADDPROD);
    }

    private void setProductCarbohydrates(float carbohydrates){
        ProductDetails.getProduct().setCarbohydrates(carbohydrates);
    }

    private void setProductProtein(float protein){
        ProductDetails.getProduct().setProtein(protein);
    }

    private void setProductFat(float fat){
        ProductDetails.getProduct().setFat(fat);
    }

    private void setImagePhoto(String encodedImage){
        if(encodedImage == null) return;
        ivPhoto.setImageBitmap(TypeManager.base64StringToBitmap(encodedImage));
    }

    private void setEncodedImageInfo(int resultCode, Intent data){
        String encodedImage = getEncodedImage(resultCode, data);
        ProductDetails.getProduct().setEncodedImage(encodedImage);
        setImagePhoto(encodedImage);
    }

    private void manageImageSaving(){
        if(ProductDetails.getProduct().getEncodedImage() == null){
            ImageManager.deleteImage(ImageManager.getImageProductName(ProductDetails.getProduct().getId()));
        } else {
            ImageManager.saveImage(
                    TypeManager.base64StringToBitmap(ProductDetails.getProduct().getEncodedImage()),
                    ImageManager.getImageProductName(ProductDetails.getProduct().getId()));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case ImageGlobals.REQ_PHOTO_FRAG_PRIMARY:
                setEncodedImageInfo(resultCode, data);
                break;
        }
    }

    private DialogInterface.OnClickListener getDialogListener(){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        chooseDatabaseAction();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
    }

    private class AsyncInsertProduct extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DbSuccessManager.insertedProduct(getContext());
        }

        protected void onPostExecute(Boolean success){
            if(success){
                new AsyncReadProductId().execute();
            } else {
                showShortToast(R.string.ts_product_insert);
            }
        }
    }

    private class AsyncReadProductId extends AsyncTask<Void, Void, Boolean> {

        private int[] idProduct;

        protected void onPreExecute(){
            idProduct = new int[MainGlobals.INT_INCREMENT_VAR_INIT];
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DbSuccessManager.readProductId(getContext(), idProduct);
        }

        protected void onPostExecute(Boolean success){
            if(success){
                setIdProductInIngredient(idProduct[MainGlobals.INT_STARTING_VAR_INIT]);
                ImageManager.saveImage(
                        TypeManager.base64StringToBitmap(ProductDetails.getProduct().getEncodedImage()),
                        ImageManager.getImageProductName(idProduct[MainGlobals.INT_STARTING_VAR_INIT]));
                productSuccess(R.string.sb_product_added);
            } else {
                showShortToast(R.string.ts_id_read);
            }
        }
    }

    private class AsyncUpdateProduct extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DbSuccessManager.updatedProduct(getContext());
        }

        protected void onPostExecute(Boolean success){
            if(success){
                manageImageSaving();
                productSuccess(R.string.sb_product_updated);
            } else {
                showShortToast(R.string.ts_product_update);
            }
        }
    }
}
