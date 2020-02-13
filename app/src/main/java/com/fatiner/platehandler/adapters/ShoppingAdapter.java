package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.details.ShoppingListDetails;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.classes.ShoppingItem;
import com.fatiner.platehandler.globals.SharedGlobals;
import com.fatiner.platehandler.managers.SharedManager;
import com.fatiner.platehandler.managers.TypeManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShoppingHolder> {

    private Context context;
    private ArrayList<ShoppingItem> shoppingItems;
    private OnShoppingListener listener;

    public ShoppingAdapter(Context context, ArrayList<ShoppingItem> shoppingItems, OnShoppingListener listener){
        this.context = context;
        this.shoppingItems = shoppingItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShoppingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = (View) inflater.inflate(
                R.layout.layout_shopping_item,
                parent,
                false);
        return new ShoppingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingHolder holder, int position) {
        ShoppingItem item = shoppingItems.get(position);
        String[] arrayMeasure = getArrayMeasure();
        String wholeText = item.getName() + MainGlobals.SN_SPACE
                + MainGlobals.SN_BRACKET1 + item.getAmount() + MainGlobals.SN_SPACE
                + arrayMeasure[item.getMeasure()] + MainGlobals.SN_BRACKET2;
        setTextItem(holder, wholeText);
        setCrossedOut(holder, item.isCrossedOut());
    }

    @Override
    public int getItemCount() {
        return shoppingItems.size();
    }

    private String[] getArrayMeasure(){
        return context.getResources().getStringArray(R.array.tx_measure);
    }

    private void setTextItem(ShoppingHolder holder, String item){
        holder.tvItem.setText(item);
    }

    private void setCrossedOut(ShoppingHolder holder, boolean isCrossedOut){
        if(isCrossedOut){
            crossOutTextView(holder.tvItem);
        }
    }

    private void crossOutTextView(TextView textView){
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void removeLineTextView(TextView textView){
        textView.setPaintFlags(textView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public class ShoppingHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_item)
        TextView tvItem;

        @OnClick(R.id.tv_item)
        public void onClickTextItem(){
            crossOutPosition();
            listener.onClickShoppingItem();
        }

        public ShoppingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void crossOutPosition(){
            if(tvItem.getPaint().isStrikeThruText()){
                removeLineTextView(tvItem);
                shoppingItems.get(getAdapterPosition()).setCrossedOut(false);
                saveCrossedOutPosition();
            } else {
                crossOutTextView(tvItem);
                shoppingItems.get(getAdapterPosition()).setCrossedOut(true);
                saveCrossedOutPosition();
            }
        }

        private void saveCrossedOutPosition(){
            ShoppingListDetails.getShoppingList().setShoppingItems(shoppingItems);
            String json = TypeManager.shoppingListToJson(ShoppingListDetails.getShoppingList());
            SharedManager.setValue(context, SharedGlobals.SR_SHOPPING, SharedGlobals.KY_LIST, json);
        }
    }

    public interface OnShoppingListener {
        void onClickShoppingItem();
    }
}
