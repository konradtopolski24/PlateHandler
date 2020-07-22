package com.fatiner.platehandler.adapters.recyclerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.details.RecipeDetails;
import com.fatiner.platehandler.globals.Format;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.models.Ingredient;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class IngredientAdapter extends PrimaryAdapter<IngredientAdapter.IngredientHolder> {

    private List<Ingredient> ingredients;
    private IngredientListener listener;

    public IngredientAdapter(Context context,
                             List<Ingredient> ingredients, IngredientListener listener) {
        super(context);
        this.ingredients = ingredients;
        this.listener = listener;
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientHolder(getLayout(parent, R.layout.item_ingredient));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {
        setViews(holder, position);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    private void setViews(IngredientHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        setTv(holder.tvMeasure, getMeasure(ingredient.getAmount(), ingredient.getMeasure()));
        setTv(holder.tvProduct, ingredient.getProduct().getName());
        setIv(holder.ivIcon, ingredient.getProduct().getType(), R.array.dw_pd_type);
        manageShowing(holder, ingredient);
    }

    private void manageShowing(IngredientHolder holder, Ingredient ingredient) {
        if (listener == null) setCaloriesInfo(holder, ingredient);
        else setDoneInfo(holder, ingredient);
    }

    private void setCaloriesInfo(IngredientHolder holder, Ingredient ingredient) {
        int[] factors = getIntArray(R.array.nb_cl_factor);
        if (ingredient.getProduct().getSize() == Globals.DF_ZERO) setEmptyInfo(holder);
        else setCaloriesInfo(holder, ingredient, factors);
    }

    private void setCaloriesInfo(IngredientHolder holder, Ingredient ingredient, int[] factors) {
        setTv(holder.tvKcal, ingredient.getTotalKcal(factors), Globals.UT_KCAL);
        setTv(holder.tvKj, ingredient.getTotalKj(factors), Globals.UT_KJ);
    }

    private void setEmptyInfo(IngredientHolder holder) {
        setTv(holder.tvKcal, Globals.SN_DASH);
        setTv(holder.tvKj, Globals.SN_DASH);
    }

    private void setDoneInfo(IngredientHolder holder, Ingredient ingredient) {
        hideView(holder.tvKcal);
        hideView(holder.tvKj);
        showView(holder.cbDone);
        setCb(holder.cbDone, ingredient.isUsed());
    }

    private String getMeasure(float amount, int measure) {
        String[] arrayMeasure = getStringArray(R.array.tx_rp_measure);
        return String.format(Locale.ENGLISH, Format.FM_MEASURE, amount, arrayMeasure[measure]);
    }

    private void checkIngredientCompletion(IngredientHolder holder, boolean checked) {
        if (checked) ingredientUsed(holder);
        else ingredientRemains(holder);
    }

    private void ingredientUsed(IngredientHolder holder) {
        setAlpha(holder.ivMeasure, Globals.AP_SMALL);
        setAlpha(holder.tvMeasure, Globals.AP_SMALL);
        setAlpha(holder.tvProduct, Globals.AP_SMALL);
        showView(holder.tvUsed);
    }

    private void ingredientRemains(IngredientHolder holder) {
        setAlpha(holder.ivMeasure, Globals.AP_FULL);
        setAlpha(holder.tvMeasure, Globals.AP_FULL);
        setAlpha(holder.tvProduct, Globals.AP_FULL);
        removeView(holder.tvUsed);
    }

    class IngredientHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_measure) TextView tvMeasure;
        @BindView(R.id.tv_product) TextView tvProduct;
        @BindView(R.id.tv_kcal) TextView tvKcal;
        @BindView(R.id.tv_kj) TextView tvKj;
        @BindView(R.id.tv_used) TextView tvUsed;
        @BindView(R.id.iv_ct_measure) ImageView ivMeasure;
        @BindView(R.id.iv_icon) ImageView ivIcon;
        @BindView(R.id.cb_done) CheckBox cbDone;

        @OnCheckedChanged(R.id.cb_done)
        void checkedCbDone(boolean checked) {
            Ingredient ingredient = RecipeDetails.getRecipe().getIngredients().get(
                    getAdapterPosition());
            ingredient.setUsed(checked);
            checkIngredientCompletion(this, checked);
            listener.setUsed(ingredient.getId(), checked);
        }

        private IngredientHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface IngredientListener {
        void setUsed(int id, boolean checked);
    }
}
