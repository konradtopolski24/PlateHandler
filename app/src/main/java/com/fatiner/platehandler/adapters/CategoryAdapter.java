package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fatiner.platehandler.classes.Product;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.globals.BundleGlobals;
import com.fatiner.platehandler.classes.Category;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.R;
import com.fatiner.platehandler.activities.MainActivity;
import com.fatiner.platehandler.fragments.recipe.manage.ManageCategoryFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private Context context;
    private ArrayList<Category> categories;
    private boolean isShowing;

    public CategoryAdapter(Context context, ArrayList<Category> categories, boolean isShowing){
        this.context = context;
        this.categories = categories;
        this.isShowing = isShowing;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView layout = (CardView) inflater.inflate(
                R.layout.layout_category,
                parent,
                false);
        return new CategoryHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        if(isShowing){
            hideImageRemove(holder);
            hideImgbuttEdit(holder);
        }
        setTextName(holder, categories.get(position).getName());
        setLayoutManager(getLayoutManager(), holder.rvIngredients);
        setAdapter(getAdapter(position), holder.rvIngredients);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    private void hideImageRemove(CategoryHolder holder){
        holder.ivRemove.setVisibility(View.GONE);
    }

    private void hideImgbuttEdit(CategoryHolder holder){
        holder.ibEdit.setVisibility(View.GONE);
    }

    private void setTextName(CategoryHolder holder, String name){
        holder.tvName.setText(name);
    }

    private void setLayoutManager(RecyclerView.LayoutManager manager, RecyclerView recyclerView){
        recyclerView.setLayoutManager(manager);
    }

    private GridLayoutManager getLayoutManager(){
        return new GridLayoutManager(context, MainGlobals.RECYC_SPAN_ADAP_CATEGORY);
    }

    private void setAdapter(RecyclerView.Adapter adapter, RecyclerView recyclerView){
        recyclerView.setAdapter(adapter);
    }

    private IngredientAdapter getAdapter(int position){
        return new IngredientAdapter(context, categories.get(position).getIngredients());
    }

    public class CategoryHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.ll_ingredients)
        LinearLayout llIngredients;
        @BindView(R.id.rv_ingredients)
        RecyclerView rvIngredients;
        @BindView(R.id.iv_remove)
        ImageView ivRemove;
        @BindView(R.id.ib_edit)
        ImageButton ibEdit;

        @OnClick(R.id.ib_edit)
        public void onClickImgbuttEdit(){
            RecipeDetails.resetTempIngredients();
            ManageCategoryFragment fragment = new ManageCategoryFragment();
            setBundle(fragment);
            setFragment(fragment);
        }

        @OnClick(R.id.iv_remove)
        public void onClickImageRemove(){
            int position = getAdapterPosition();
            categories.remove(position);
            notifyItemRemoved(position);
        }

        @OnClick(R.id.ll_header)
        public void onClickLinearHeader(){
            if(rvIngredients.getVisibility() == View.VISIBLE){
                hideRecycIngredients();
            } else {
                showRecycIngredients();
            }
        }

        public CategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setFragment(Fragment fragment){
            ((MainActivity) context).setFragment(fragment, true);
        }

        private void setBundle(Fragment fragment){
            Bundle bundle = new Bundle();
            bundle.putInt(BundleGlobals.BUND_ID_FRAG_SHOWREC, getAdapterPosition());
            fragment.setArguments(bundle);
        }

        private void showRecycIngredients(){
            rvIngredients.setVisibility(View.VISIBLE);
        }

        private void hideRecycIngredients(){
            rvIngredients.setVisibility(View.GONE);
        }
    }
}
