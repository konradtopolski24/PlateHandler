package com.fatiner.platehandler.fragments.product;

import android.content.DialogInterface;
import android.graphics.Bitmap;
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
import com.fatiner.platehandler.managers.ImageManager;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.managers.database.DbSuccessManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowProductFragment extends PrimaryFragment {

    @BindView(R.id.text_name_frag_shwprod)
    TextView textName;
    @BindView(R.id.text_type_frag_shwprod)
    TextView textType;
    @BindView(R.id.text_carbohydrates_frag_shwprod)
    TextView textCarbohydrates;
    @BindView(R.id.text_carbokcal_frag_shwprod)
    TextView textCarbokcal;
    @BindView(R.id.text_carbokj_frag_shwprod)
    TextView textCarbokj;
    @BindView(R.id.text_protein_frag_shwprod)
    TextView textProtein;
    @BindView(R.id.text_protkcal_frag_shwprod)
    TextView textProtkcal;
    @BindView(R.id.text_protkj_frag_shwprod)
    TextView textProtkj;
    @BindView(R.id.text_fat_frag_shwprod)
    TextView textFat;
    @BindView(R.id.text_fatkcal_frag_shwprod)
    TextView textFatkcal;
    @BindView(R.id.text_fatkj_frag_shwprod)
    TextView textFatkj;
    @BindView(R.id.text_kcal_frag_shwprod)
    TextView textKcal;
    @BindView(R.id.text_kj_frag_shwprod)
    TextView textKj;
    @BindView(R.id.image_photo_frag_shwprod)
    ImageView imagePhoto;

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
            new AsyncReadProduct().execute(getProductId());
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
        textName.setText(text);
    }

    private void setTextType(int type){
        String[] arrayType = getStringArray(R.array.array_type_frag_product);
        textType.setText(arrayType[type]);
    }

    private void setTextCarbohydrates(float carbohydrates){
        textCarbohydrates.setText(String.valueOf(carbohydrates));
    }

    private void setTextProtein(float protein){
        textProtein.setText(String.valueOf(protein));
    }

    private void setTextFat(float fat){
        textFat.setText(String.valueOf(fat));
    }

    private void setImagePhoto(String encodedImage){
        if(encodedImage == null) return;
        imagePhoto.setImageBitmap(TypeManager.base64StringToBitmap(encodedImage));
    }

    private void calculateAllKcal(){
        Product product = ProductDetails.getProduct();
        setText(getKcal(product.getCarbohydrates(),
                MainGlobals.KCAL_CARBOHYDRATES_OBJ_PROD), textCarbokcal);
        setText(getKcal(product.getProtein(),
                MainGlobals.KCAL_PROTEIN_OBJ_PROD), textProtkcal);
        setText(getKcal(product.getFat(),
                MainGlobals.KCAL_FAT_OBJ_PROD), textFatkcal);
    }

    private void calculateAllKj(){
        Product product = ProductDetails.getProduct();
        setText(getKj(product.getCarbohydrates(),
                MainGlobals.KCAL_CARBOHYDRATES_OBJ_PROD), textCarbokj);
        setText(getKj(product.getProtein(),
                MainGlobals.KCAL_PROTEIN_OBJ_PROD), textProtkj);
        setText(getKj(product.getFat(),
                MainGlobals.KCAL_FAT_OBJ_PROD), textFatkj);
    }

    private void calculateTotalKcal(){
        float carbohydrates = Float.valueOf(textCarbokcal.getText().toString());
        float protein = Float.valueOf(textProtkcal.getText().toString());
        float fat = Float.valueOf(textFatkcal.getText().toString());
        float total = carbohydrates + protein + fat;
        textKcal.setText(String.valueOf(total));
    }

    private void calculateTotalKj(){
        float carbohydrates = Float.valueOf(textCarbokj.getText().toString());
        float protein = Float.valueOf(textProtkj.getText().toString());
        float fat = Float.valueOf(textFatkj.getText().toString());
        float total = carbohydrates + protein + fat;
        textKj.setText(String.valueOf(total));
    }

    private DialogInterface.OnClickListener getDialogListener(){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        new AsyncDeleteProduct().execute();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
    }

    private void setText(float value, TextView textView){
        textView.setText(String.valueOf(value));
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
                showAlertDialog(R.string.dial_delete_frag_product, getDialogListener());
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private class AsyncReadProduct extends AsyncTask<Integer, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Integer... id) {
            return DbSuccessManager.readProduct(getContext(),
                    ProductDetails.getProduct(), id[MainGlobals.INT_STARTING_VAR_INIT]);
        }

        protected void onPostExecute(Boolean success){
            if(success){
                setToolbarTitle(ProductDetails.getProduct().getName());
                loadPhoto();
                setProductInfo();
                calculateAllKcal();
                calculateAllKj();
                calculateTotalKcal();
                calculateTotalKj();
            } else {
                showShortToast(R.string.toast_fail_async_readprod);
            }
        }
    }

    private class AsyncDeleteProduct extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DbSuccessManager.deletedProduct(
                    getContext(), ProductDetails.getProduct().getId());
        }

        protected void onPostExecute(Boolean success){
            if(success){
                productSuccess(R.string.snack_deleted_frag_product);
            } else {
                showShortToast(R.string.toast_fail_async_delprod);
            }
        }
    }
}
