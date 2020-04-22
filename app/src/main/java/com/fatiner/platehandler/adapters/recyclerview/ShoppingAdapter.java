package com.fatiner.platehandler.adapters.recyclerview;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.items.ShoppingItem;
import com.fatiner.platehandler.globals.Format;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingAdapter extends PrimaryAdapter<ShoppingAdapter.ShoppingHolder> {

    private ArrayList<ShoppingItem> shoppingItems;
    private ShoppingListener listener;

    public ShoppingAdapter(Context context,
                           ArrayList<ShoppingItem> shoppingItems, ShoppingListener listener) {
        super(context);
        this.shoppingItems = shoppingItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShoppingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShoppingHolder(getLayout(parent, R.layout.item_shopping));
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingHolder holder, int position) {
        setViews(holder, position);
    }

    @Override
    public int getItemCount() {
        return shoppingItems.size();
    }

    private void setViews(ShoppingHolder holder, int position) {
        ShoppingItem item = shoppingItems.get(position);
        setTv(holder.tvItem, getItemText(item));
        if (item.isCrossedOut()) crossOutLine(holder.tvItem);
    }

    private String getItemText(ShoppingItem item) {
        String[] measures = getStringArray(R.array.tx_measure);
        return String.format(Locale.ENGLISH, Format.FM_SHOPPING,
                item.getName(), item.getAmount(), measures[item.getMeasure()]);
    }

    private boolean isCrossedOut(TextView tv) {
        return tv.getPaint().isStrikeThruText();
    }

    private void crossOutLine(TextView tv) {
        tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void removeLine(TextView tv) {
        tv.setPaintFlags(tv.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void setCrossOut(int position, boolean isCrossedOut) {
        shoppingItems.get(position).setCrossedOut(isCrossedOut);
    }

    private void removeAction(ShoppingHolder holder) {
        removeLine(holder.tvItem);
        setCrossOut(holder.getAdapterPosition(), false);
    }

    private void crossOutAction(ShoppingHolder holder) {
        crossOutLine(holder.tvItem);
        setCrossOut(holder.getAdapterPosition(), true);
    }

    private void manageCrossOut(ShoppingHolder holder) {
        if (isCrossedOut(holder.tvItem)) removeAction(holder);
        else crossOutAction(holder);
    }

    class ShoppingHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item) TextView tvItem;

        @OnClick(R.id.tv_item)
        void clickTvItem() {
            manageCrossOut(this);
            listener.clickItem();
        }

        private ShoppingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ShoppingListener {
        void clickItem();
    }
}
