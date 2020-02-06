package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.activities.MainActivity;
import com.fatiner.platehandler.classes.Ingredient;
import com.fatiner.platehandler.classes.Product;
import com.fatiner.platehandler.details.ProductDetails;
import com.fatiner.platehandler.fragments.product.ManageProductFragment;
import com.fatiner.platehandler.globals.BundleGlobals;
import com.fatiner.platehandler.globals.MainGlobals;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class AddIngredientAdapter extends RecyclerView.Adapter<AddIngredientAdapter.AddIngredientHolder> {

    private Context context;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Product> products;

    public AddIngredientAdapter(Context context, ArrayList<Ingredient> ingredients, ArrayList<Product> products){
        this.context = context;
        this.ingredients = ingredients;
        this.products = products;
    }

    @NonNull
    @Override
    public AddIngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(
                R.layout.layout_add_ingredient,
                parent,
                false);
        return new AddIngredientHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull AddIngredientHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        setSpinProductAdapter(holder.spProduct);
        setSpinProduct(holder, ingredient.getProduct().getId());
        setEditAmount(holder, ingredient.getAmount());
        setSpinMeasure(holder, ingredient.getMeasure());
        hideSpProduct(holder);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    private void setSpinProductAdapter(Spinner spinProduct){
        ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(context,
                R.layout.support_simple_spinner_dropdown_item, products);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinProduct.setAdapter(adapter);
    }

    private void setSpinProduct(AddIngredientHolder holder, int idDatabase){
        holder.spProduct.setSelection(getChosenProduct(idDatabase));
    }

    private void setEditAmount(AddIngredientHolder holder, float amount){
        holder.etAmount.setText(String.valueOf(amount));
    }

    private void setSpinMeasure(AddIngredientHolder holder, int measure){
        holder.spMeasure.setSelection(measure);
    }

    private int getChosenProduct(int idDatabase){
        int idSpin = MainGlobals.INT_STARTING_VAR_INIT;
        for(int i = MainGlobals.INT_STARTING_VAR_INIT; i < products.size(); i++){
            Product product = products.get(i);
            if(product.getId() == idDatabase){
                return i;
            }
        }
        return idSpin;
    }

    private void hideSpProduct(AddIngredientHolder holder) {
        if (holder.spProduct.getCount() == 0) {
            holder.spProduct.setVisibility(View.INVISIBLE);
            holder.tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    public class AddIngredientHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.et_amount)
        EditText etAmount;
        @BindView(R.id.sp_measure)
        Spinner spMeasure;
        @BindView(R.id.sp_product)
        Spinner spProduct;
        @BindView(R.id.tv_empty)
        TextView tvEmpty;

        @OnClick(R.id.ib_add)
        public void onClickImgbuttAdd(){
            ProductDetails.resetProductDetails();
            ManageProductFragment fragment = new ManageProductFragment();
            setBundle(fragment, getAdapterPosition());
            ((MainActivity)context).setFragment(fragment, true);
        }

        @OnClick(R.id.iv_remove)
        public void onClickImageRemove(){
            if(ingredients.size() == MainGlobals.INT_INCREMENT_VAR_INIT){

            } else {
                int position = getAdapterPosition();
                ingredients.remove(position);
                notifyItemRemoved(position);
            }
        }

        @OnTextChanged(R.id.et_amount)
        public void onTextChangeEditAmount(CharSequence text){
            float amount;
            if(String.valueOf(text).isEmpty()){
                amount =  MainGlobals.INT_STARTING_VAR_INIT;
            } else {
                amount  = Float.valueOf(String.valueOf(text));
            }
            setIngredientsAmount(amount);
        }

        @OnItemSelected(R.id.sp_product)
        public void onItemSelectedSpinProduct(int id){
            Product product = new Product();
            product.setId(products.get(id).getId());
            product.setName(products.get(id).getName());
            product.setType(products.get(id).getType());
            product.setProtein(products.get(id).getProtein());
            product.setCarbohydrates(products.get(id).getProtein());
            product.setProtein(products.get(id).getProtein());
            product.setEncodedImage(products.get(id).getEncodedImage());
            setIngredientsProduct(product);
        }

        @OnItemSelected(R.id.sp_measure)
        public void onItemSelectedSpinMeasure(int id){
            setIngredientsMeasure(id);
        }

        public AddIngredientHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setIngredientsAmount(float amount){
            ingredients.get(getAdapterPosition()).setAmount(amount);
        }

        private void setIngredientsProduct(Product product){
            ingredients.get(getAdapterPosition()).setProduct(product);
        }

        private void setIngredientsMeasure(int measure){
            ingredients.get(getAdapterPosition()).setMeasure(measure);
        }

        private void setBundle(Fragment fragment, int position){
            Bundle bundle = new Bundle();
            bundle.putInt(BundleGlobals.BUND_ID_FRAG_ADDPROD, position);
            fragment.setArguments(bundle);
        }
    }
}
