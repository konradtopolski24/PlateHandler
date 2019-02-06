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

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_carbohydrates)
    TextView tvCarbohydrates;
    @BindView(R.id.tv_carbokcal)
    TextView tvCarbokcal;
    @BindView(R.id.tv_carbokj)
    TextView tvCarbokj;
    @BindView(R.id.tv_protein)
    TextView tvProtein;
    @BindView(R.id.tv_protkcal)
    TextView tvProtkcal;
    @BindView(R.id.tv_protkj)
    TextView tvProtkj;
    @BindView(R.id.tv_fat)
    TextView tvFat;
    @BindView(R.id.tv_fatkcal)
    TextView tvFatkcal;
    @BindView(R.id.tv_fatkj)
    TextView tvFatkj;
    @BindView(R.id.tv_kcal)
    TextView tvKcal;
    @BindView(R.id.tv_kj)
    TextView tvKj;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;

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
        tvName.setText(text);
    }

    private void setTextType(int type){
        String[] arrayType = getStringArray(R.array.array_type_frag_product);
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

    private void calculateAllKcal(){
        Product product = ProductDetails.getProduct();
        setText(getKcal(product.getCarbohydrates(),
                MainGlobals.KCAL_CARBOHYDRATES_OBJ_PROD), tvCarbokcal);
        setText(getKcal(product.getProtein(),
                MainGlobals.KCAL_PROTEIN_OBJ_PROD), tvProtkcal);
        setText(getKcal(product.getFat(),
                MainGlobals.KCAL_FAT_OBJ_PROD), tvFatkcal);
    }

    private void calculateAllKj(){
        Product product = ProductDetails.getProduct();
        setText(getKj(product.getCarbohydrates(),
                MainGlobals.KCAL_CARBOHYDRATES_OBJ_PROD), tvCarbokj);
        setText(getKj(product.getProtein(),
                MainGlobals.KCAL_PROTEIN_OBJ_PROD), tvProtkj);
        setText(getKj(product.getFat(),
                MainGlobals.KCAL_FAT_OBJ_PROD), tvFatkj);
    }

    private void calculateTotalKcal(){
        float carbohydrates = Float.valueOf(tvCarbokcal.getText().toString());
        float protein = Float.valueOf(tvProtkcal.getText().toString());
        float fat = Float.valueOf(tvFatkcal.getText().toString());
        float total = carbohydrates + protein + fat;
        tvKcal.setText(String.valueOf(total));
    }

    private void calculateTotalKj(){
        float carbohydrates = Float.valueOf(tvCarbokj.getText().toString());
        float protein = Float.valueOf(tvProtkj.getText().toString());
        float fat = Float.valueOf(tvFatkj.getText().toString());
        float total = carbohydrates + protein + fat;
        tvKj.setText(String.valueOf(total));
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
