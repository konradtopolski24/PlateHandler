package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.classes.ShoppingItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class ShoppingAddAdapter extends PrimaryAdapter<ShoppingAddAdapter.ShoppingAddHolder> {

    private ArrayList<ShoppingItem> shoppingItems;
    private ShoppingAddListener listener;

    public ShoppingAddAdapter(Context context, ArrayList<ShoppingItem> shoppingItems, ShoppingAddListener listener) {
        super(context);
        this.shoppingItems = shoppingItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShoppingAddHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShoppingAddHolder(getLayout(parent, R.layout.item_shopping_add));
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingAddHolder holder, int position) {
        ShoppingItem item = shoppingItems.get(position);
        setCorrectInput(holder.etAmount);
        setEt(holder.etAmount, item.getAmount());
        setSp(holder.spMeasure, item.getMeasure());
        setEt(holder.etName, item.getName());
    }

    @Override
    public int getItemCount() {
        return shoppingItems.size();
    }

    private ShoppingItem getShoppingItem(int position) {
        return shoppingItems.get(position);
    }

    class ShoppingAddHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.et_amount) EditText etAmount;
        @BindView(R.id.et_name) EditText etName;
        @BindView(R.id.sp_measure) Spinner spMeasure;

        @OnClick(R.id.iv_remove)
        void clickIvRemove() {
            listener.removeItem(getAdapterPosition());
        }

        @OnTextChanged(R.id.et_amount)
        void changedEtAmount(CharSequence text) {
            getShoppingItem(getAdapterPosition()).setAmount(getCorrectEtValue(text));
        }

        @OnTextChanged(R.id.et_name)
        void changedEtName(CharSequence text) {
            String name = String.valueOf(text);
            getShoppingItem(getAdapterPosition()).setName(name);
        }

        @OnItemSelected(R.id.sp_measure)
        void selectedSpMeasure(int id) {
            getShoppingItem(getAdapterPosition()).setMeasure(id);
        }

        private ShoppingAddHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ShoppingAddListener {
        void removeItem(int position);
    }
}
