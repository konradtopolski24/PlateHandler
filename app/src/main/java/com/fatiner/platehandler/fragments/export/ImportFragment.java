package com.fatiner.platehandler.fragments.export;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.FilesAdapter;
import com.fatiner.platehandler.classes.ImportFile;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.DbGlobals;
import com.fatiner.platehandler.globals.MainGlobals;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImportFragment extends PrimaryFragment {

    @BindView(R.id.rv_files)
    RecyclerView rvFiles;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    public ImportFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_import, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tb_import);
        setMenuItem(MainGlobals.ID_IMPORT_DRAW_MAIN);
        setRecyclerView();
        checkIfRvEmpty(rvFiles, tvEmpty);
        return view;
    }

    private void setRecyclerView() {
        setRecyclerView(
                rvFiles,
                getLayoutManagerNoScroll(LinearLayoutManager.VERTICAL),
                new FilesAdapter(getContext(), getImportFiles())
        );
    }

    private ArrayList<ImportFile> getImportFiles() {
        ArrayList<ImportFile> importFiles = new ArrayList<>();
        File[] files = getContext().getExternalFilesDir(null).listFiles();
        for(File file : files) {
            if(file.isFile() && file.getPath().endsWith(MainGlobals.FILE_XLS_SAVE_WORKBOOK)) {
                String full = file.toString();
                String name = full.substring(full.lastIndexOf(
                        MainGlobals.STR_SLASH_OBJ_INIT) + MainGlobals.INT_INCREMENT_VAR_INIT);
                Workbook workbook = getWorkbookFromFile(name);
                int recipeAmount = getAmount(workbook, DbGlobals.TAB_RECIPES_DB_MAIN);
                int producteAmount = getAmount(workbook, DbGlobals.TAB_PRODUCTS_DB_MAIN);
                ImportFile importFile = new ImportFile();
                importFile.setName(name);
                importFile.setRecipeAmount(recipeAmount);
                importFile.setProductAmount(producteAmount);
                importFiles.add(importFile);
            }
        }
        return importFiles;
    }

    private Workbook getWorkbookFromFile(String name) {
        Workbook workbook = new HSSFWorkbook();
        try {
            File file = new File(getContext().getExternalFilesDir(null), name);
            FileInputStream inputStream = new FileInputStream(file);
            POIFSFileSystem system = new POIFSFileSystem(inputStream);
            workbook = new HSSFWorkbook(system);
        } catch (Exception e) {

        }
        return workbook;
    }

    private int getAmount(Workbook workbook, String tabName) {
        Sheet sheet = workbook.getSheet(tabName);
        return sheet.getPhysicalNumberOfRows() - 1;
    }
}
