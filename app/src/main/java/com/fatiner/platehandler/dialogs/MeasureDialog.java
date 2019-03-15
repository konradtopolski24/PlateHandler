package com.fatiner.platehandler.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.globals.MainGlobals;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeasureDialog extends DialogFragment {

    @BindView(R.id.tv_measures)
    TextView tvMeasures;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_measure, null);
        ButterKnife.bind(this, view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        setBuilder(builder, view);
        setTvMeasures();
        return builder.create();
    }

    private void setBuilder(AlertDialog.Builder builder, View view){
        builder.setView(view)
                .setTitle(getString(R.string.tv_measure))
                .setPositiveButton(getString(R.string.dg_ok), getOnClickListener());
    }

    private DialogInterface.OnClickListener getOnClickListener(){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        };
    }

    private void setTvMeasures() {
        String text = MainGlobals.STR_EMPTY_OBJ_INIT;
        String[] measures = getContext().getResources().getStringArray(R.array.ar_measure);
        int[] factors = getContext().getResources().getIntArray(R.array.ar_factor);
        for(int i = MainGlobals.INT_STARTING_VAR_INIT; i < measures.length; i++) {
            text += measures[i] + MainGlobals.STR_SPACE_OBJ_INIT + MainGlobals.STR_EQUALS_OBJ_INIT
                    + MainGlobals.STR_SPACE_OBJ_INIT + factors[i] + MainGlobals.STR_ENTER_OBJ_INIT;
        }
        tvMeasures.setText(text);
    }
}
