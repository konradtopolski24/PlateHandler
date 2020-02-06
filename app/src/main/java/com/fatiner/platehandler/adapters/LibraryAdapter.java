package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.classes.Library;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryHolder> {

    private Context context;
    private ArrayList<Library> libraries;

    public LibraryAdapter(Context context, ArrayList<Library> libraries){
        this.context = context;
        this.libraries = libraries;
    }

    @NonNull
    @Override
    public LibraryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView layout = (CardView) inflater.inflate(
                R.layout.cardview_library,
                parent,
                false);
        return new LibraryHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryHolder holder, int position) {
        holder.tvName.setText(libraries.get(position).getName());
        holder.tvAuthor.setText(libraries.get(position).getAuthor());
        holder.tvVersion.setText(libraries.get(position).getVersion());
        holder.tvLicense.setText(libraries.get(position).getLicense());
    }

    @Override
    public int getItemCount() {
        return libraries.size();
    }

    public class LibraryHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_version)
        TextView tvVersion;
        @BindView(R.id.tv_license)
        TextView tvLicense;

        public LibraryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
