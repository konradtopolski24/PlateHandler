package com.fatiner.platehandler.adapters.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fatiner.platehandler.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpinnerAdapter extends BaseAdapter {

    private Context context;
    private List<?> items;

    public SpinnerAdapter(@NonNull Context context, List<?> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        return getSpView(position, view, parent, R.layout.item_spinner);
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup parent) {
        return getSpView(position, view, parent, R.layout.item_dropdown);
    }

    private View getSpView(int position, View view, ViewGroup parent, int layoutId) {
        SpinnerHolder holder;
        if (view == null) {
            view = getLayout(layoutId, parent);
            holder = new SpinnerHolder(view);
            view.setTag(holder);
        } else holder = (SpinnerHolder) view.getTag();
        setView(holder, position);
        return view;
    }

    private void setView(SpinnerHolder holder, int position) {
        holder.tvItem.setText(items.get(position).toString());
        holder.tvItem.setSelected(true);
    }

    private View getLayout(int layoutId, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(layoutId, parent, false);
    }

    static class SpinnerHolder {

        @BindView(R.id.tv_item)
        TextView tvItem;

        private SpinnerHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
