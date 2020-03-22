package com.fatiner.platehandler.adapters.recyclerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.models.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductAdapter extends PrimaryAdapter<ProductAdapter.ProductHolder> {

    private List<Product> products;
    private ProductListener listener;

    public ProductAdapter(Context context, List<Product> products, ProductListener listener) {
        super(context);
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductHolder(getLayout(parent, R.layout.item_product));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = products.get(position);
        setTv(holder.tvName, product.getName());
        setIv(holder.ivType, product.getType(), R.array.dw_product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    private int getId(int position) {
        return products.get(position).getId();
    }

    class ProductHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.iv_type) ImageView ivType;

        @OnClick(R.id.cl_product)
        void clickClProduct() {
            listener.clickProduct(getId(getAdapterPosition()));
        }

        private ProductHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ProductListener {
        void clickProduct(int id);
    }
}
