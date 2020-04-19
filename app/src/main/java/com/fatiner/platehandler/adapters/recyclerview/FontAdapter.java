package com.fatiner.platehandler.adapters.recyclerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.classes.Font;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FontAdapter extends PrimaryAdapter<FontAdapter.FontHolder> {

    private ArrayList<Font> fonts;

    public FontAdapter(Context context, ArrayList<Font> fonts) {
        super(context);
        this.fonts = fonts;
    }

    @NonNull
    @Override
    public FontHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FontHolder(getLayout(parent, R.layout.item_font));
    }

    @Override
    public void onBindViewHolder(@NonNull FontHolder holder, int position) {
        setViews(holder, position);
    }

    @Override
    public int getItemCount() {
        return fonts.size();
    }

    private void setViews(FontHolder holder, int position) {
        Font font = fonts.get(position);
        setTv(holder.tvName, font.getName());
        setTv(holder.tvAuthor, font.getAuthor());
        setTv(holder.tvVersion, font.getVersion());
        setTv(holder.tvLicense, font.getLicense());
    }

    static class FontHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_version)
        TextView tvVersion;
        @BindView(R.id.tv_license)
        TextView tvLicense;

        private FontHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}