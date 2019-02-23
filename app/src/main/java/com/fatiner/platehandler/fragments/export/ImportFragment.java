package com.fatiner.platehandler.fragments.export;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.MainGlobals;

import butterknife.ButterKnife;

public class ImportFragment extends PrimaryFragment {

    public ImportFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_import, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tb_import);
        setMenuItem(MainGlobals.ID_IMPORT_DRAW_MAIN);
        return view;
    }
}
