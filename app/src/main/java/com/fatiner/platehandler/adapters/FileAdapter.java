package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.classes.ImportFile;
import com.fatiner.platehandler.globals.Format;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FileAdapter extends PrimaryAdapter<FileAdapter.FilesHolder> {

    private List<ImportFile> files;
    private FileListener listener;

    public FileAdapter(Context context, List<ImportFile> files, FileListener listener) {
        super(context);
        this.files = files;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FilesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilesHolder(getLayout(parent, R.layout.item_file));
    }

    @Override
    public void onBindViewHolder(@NonNull FilesHolder holder, int position) {
        setViews(holder, position);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    private void setViews(FilesHolder holder, int position) {
        ImportFile file = files.get(position);
        setTv(holder.tvName, file.getName());
        setTv(holder.tvRecipe, getText(R.string.nv_recipe, file.getRecipeAmount()));
        setTv(holder.tvProduct, getText(R.string.nv_product, file.getProductAmount()));
    }

    private String getText(int idText, int amount) {
        String text = getString(idText);
        return String.format(Locale.ENGLISH, Format.FM_AMOUNT, text, amount);
    }

    private String getName(FilesHolder holder) {
        return holder.tvName.getText().toString();
    }

    class FilesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.tv_recipe) TextView tvRecipe;
        @BindView(R.id.tv_product) TextView tvProduct;

        @OnClick(R.id.cl_file)
        void clickClFile() {
            listener.clickFile(getName(this));
        }

        private FilesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface FileListener {
        void clickFile(String name);
    }
}
