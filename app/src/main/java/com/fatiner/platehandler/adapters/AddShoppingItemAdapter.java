package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.classes.ShoppingItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class AddShoppingItemAdapter extends RecyclerView.Adapter<AddShoppingItemAdapter.AddShoppingItemHolder> {

    private Context context;
    private ArrayList<ShoppingItem> shoppingItems;

    public AddShoppingItemAdapter(Context context, ArrayList<ShoppingItem> shoppingItems){
        this.context = context;
        this.shoppingItems = shoppingItems;
    }

    @NonNull
    @Override
    public AddShoppingItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(
                R.layout.layout_add_shopping_item,
                parent,
                false);
        return new AddShoppingItemHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull AddShoppingItemHolder holder, int position) {
        ShoppingItem shoppingItem = shoppingItems.get(position);
        setEditAmount(holder, shoppingItem.getAmount());
        setSpinMeasure(holder, shoppingItem.getMeasure());
        setEditName(holder, shoppingItem.getName());
    }

    @Override
    public int getItemCount() {
        return shoppingItems.size();
    }

    private void setEditAmount(AddShoppingItemHolder holder, float amount){
        holder.etAmount.setText(String.valueOf(amount));
    }

    private void setSpinMeasure(AddShoppingItemHolder holder, int measure){
        holder.spMeasure.setSelection(measure);
    }

    private void setEditName(AddShoppingItemHolder holder, String name){
        holder.etName.setText(name);
    }

    public class AddShoppingItemHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.et_amount)
        EditText etAmount;
        @BindView(R.id.sp_measure)
        Spinner spMeasure;
        @BindView(R.id.et_name)
        EditText etName;

        @OnClick(R.id.iv_remove)
        public void onClickImageRemove(){
            if(shoppingItems.size() == MainGlobals.INT_INCREMENT_VAR_INIT){

            } else {
                int position = getAdapterPosition();
                shoppingItems.remove(position);
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
            setShoppingItemsAmount(amount);
        }

        @OnTextChanged(R.id.et_name)
        public void onTextChangeEditName(CharSequence text){
            setShoppingItemsName(String.valueOf(text));
        }

        @OnItemSelected(R.id.sp_measure)
        public void onItemSelectedSpinMeasure(int id){
            setShoppingItemsMeasure(id);
        }

        public AddShoppingItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setShoppingItemsAmount(float amount){
            shoppingItems.get(getAdapterPosition()).setAmount(amount);
        }

        private void setShoppingItemsName(String name){
            shoppingItems.get(getAdapterPosition()).setName(name);
        }

        private void setShoppingItemsMeasure(int measure){
            shoppingItems.get(getAdapterPosition()).setMeasure(measure);
        }
    }
}
