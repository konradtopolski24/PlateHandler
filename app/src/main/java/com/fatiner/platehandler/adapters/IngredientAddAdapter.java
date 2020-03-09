package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.models.Ingredient;
import com.fatiner.platehandler.models.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class IngredientAddAdapter extends PrimaryAdapter<IngredientAddAdapter.IngredientAddHolder> {

    private List<Ingredient> ingredients;
    private List<Product> products;
    private IngredientAddListener listener;

    public IngredientAddAdapter(Context context, List<Ingredient> ingredients,
                                List<Product> products, IngredientAddListener listener) {
        super(context);
        this.ingredients = ingredients;
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public IngredientAddHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientAddHolder(getLayout(parent, R.layout.item_ingredient_add));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAddHolder holder, int position) {
        setViews(holder, position);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    private void setViews(IngredientAddHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        setCorrectInput(holder.etAmount);
        setEt(holder.etAmount, ingredient.getAmount());
        setSp(holder.spMeasure, ingredient.getMeasure());
        setSp(holder.spProduct, getChosenProduct(ingredient.getProductId()), getAdapter());
        manageSpVisibility(holder);
    }

    private ArrayAdapter<Product> getAdapter() {
        ArrayAdapter<Product> adapter = new ArrayAdapter<>(context,
                R.layout.support_simple_spinner_dropdown_item, products);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        return adapter;
    }

    private void manageSpVisibility(IngredientAddHolder holder) {
        if (holder.spProduct.getCount() == Globals.DF_ZERO) {
            hideView(holder.spProduct);
            showView(holder.tvEmpty);
        }
    }

    private int getChosenProduct(int idDatabase) {
        int idSpin = Globals.DF_ZERO;
        for(int i = Globals.DF_ZERO; i < products.size(); i++) {
            Product product = products.get(i);
            if(product.getId() == idDatabase) return i;
        }
        return idSpin;
    }

    private boolean isAmountZero(int id) {
        return ingredients.get(id).getAmount() == Globals.DF_ZERO;
    }

    class IngredientAddHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.et_amount) EditText etAmount;
        @BindView(R.id.sp_measure) Spinner spMeasure;
        @BindView(R.id.sp_product) Spinner spProduct;
        @BindView(R.id.tv_empty) TextView tvEmpty;

        @OnClick(R.id.ib_add)
        void clickIbAdd() {
            listener.clickIb(getAdapterPosition());
        }

        @OnClick(R.id.iv_remove)
        void clickIvRemove() {
            listener.removeIngredient(getAdapterPosition());
        }

        @OnTextChanged(R.id.et_amount)
        void changedEtAmount(CharSequence text) {
            ingredients.get(getAdapterPosition()).setAmount(getCorrectEtValue(text));
            setError(etAmount, R.string.er_ig_amount, isAmountZero(getAdapterPosition()));
        }

        @OnItemSelected(R.id.sp_product)
        void selectedSpProduct(int id) {
            Product product = products.get(id);
            ingredients.get(getAdapterPosition()).setProductId(product.getId());
        }

        @OnItemSelected(R.id.sp_measure)
        void selectedSpMeasure(int id) {
            ingredients.get(getAdapterPosition()).setMeasure(id);
        }

        private IngredientAddHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface IngredientAddListener {
        void clickIb(int position);
        void removeIngredient(int position);
    }
}
