package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.activities.MainActivity;
import com.fatiner.platehandler.details.ProductDetails;
import com.fatiner.platehandler.fragments.product.ShowProductFragment;
import com.fatiner.platehandler.globals.BundleGlobals;
import com.fatiner.platehandler.classes.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsHolder>{

    Context context;
    ArrayList<Product> products;

    public ProductsAdapter(Context context, ArrayList<Product> products){
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView cardView = (CardView) inflater.inflate(
                R.layout.cardview_product,
                parent,
                false);
        return new ProductsHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsHolder holder, int position) {
        Product product = products.get(position);
        setTextName(holder, product.getName());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    private void setTextName(ProductsHolder holder, String name){
        holder.tvName.setText(name);
    }

    private void setBundle(Fragment fragment, int position){
        int id = products.get(position).getId();
        Bundle bundle = new Bundle();
        bundle.putInt(BundleGlobals.BUND_ID_FRAG_SHOWPROD, id);
        fragment.setArguments(bundle);
    }

    public class ProductsHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_name)
        TextView tvName;

        @OnClick(R.id.cl_product)
        public void onClickLinearProduct(){
            ProductDetails.resetProductDetails();
            Fragment fragment = new ShowProductFragment();
            setBundle(fragment, getAdapterPosition());
            setFragment(fragment);
        }

        public ProductsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setFragment(Fragment fragment){
            ((MainActivity) context).setFragment(fragment, true);
        }
    }
}
