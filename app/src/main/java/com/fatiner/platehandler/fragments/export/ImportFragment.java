package com.fatiner.platehandler.fragments.export;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.FilesAdapter;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.MainGlobals;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImportFragment extends PrimaryFragment {

    @BindView(R.id.rv_files)
    RecyclerView rvFiles;

    public ImportFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_import, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tb_import);
        setMenuItem(MainGlobals.ID_IMPORT_DRAW_MAIN);
        setRecyclerView();
        return view;
    }

    private void setRecyclerView() {
        setRecyclerView(
                rvFiles,
                getLinearLayoutManager(LinearLayoutManager.VERTICAL),
                new FilesAdapter(getContext(), getFileNames())
        );
    }

    private ArrayList<String> getFileNames() {
        ArrayList<String> names = new ArrayList<>();
        File[] files = getContext().getExternalFilesDir(null).listFiles();
        for(File file : files) {
            if(file.isFile() && file.getPath().endsWith(MainGlobals.FILE_XLS_SAVE_WORKBOOK)) {
                String full = file.toString();
                String name = full.substring(full.lastIndexOf(
                        MainGlobals.STR_SLASH_OBJ_INIT) + MainGlobals.INT_INCREMENT_VAR_INIT);
                names.add(name);
            }
        }
        return names;
    }
}
