package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.classes.Library;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LibraryAdapter extends PrimaryAdapter<LibraryAdapter.LibraryHolder> {
    
    private ArrayList<Library> libraries;

    public LibraryAdapter(Context context, ArrayList<Library> libraries) {
        super(context);
        this.libraries = libraries;
    }

    @NonNull
    @Override
    public LibraryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LibraryHolder(getLayout(parent, R.layout.item_library));
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryHolder holder, int position) {
        setViews(holder, position);
    }

    @Override
    public int getItemCount() {
        return libraries.size();
    }

    private void setViews(LibraryHolder holder, int position) {
        Library library = libraries.get(position);
        setTv(holder.tvName, library.getName());
        setTv(holder.tvAuthor, library.getAuthor());
        setTv(holder.tvVersion, library.getVersion());
        setTv(holder.tvLicense, library.getLicense());
    }

    static class LibraryHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.tv_author) TextView tvAuthor;
        @BindView(R.id.tv_version) TextView tvVersion;
        @BindView(R.id.tv_license) TextView tvLicense;

        private LibraryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
