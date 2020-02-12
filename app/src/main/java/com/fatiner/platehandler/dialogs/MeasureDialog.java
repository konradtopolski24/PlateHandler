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
                .setTitle(getString(R.string.ct_measure))
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
        String text = MainGlobals.SN_EMPTY;
        String[] measures = getContext().getResources().getStringArray(R.array.tx_measure);
        int[] factors = getContext().getResources().getIntArray(R.array.tx_factor);
        for(int i = MainGlobals.DF_ZERO; i < measures.length; i++) {
            text += measures[i] + MainGlobals.SN_SPACE + MainGlobals.SN_EQUALS
                    + MainGlobals.SN_SPACE + factors[i] + MainGlobals.SN_ENTER;
        }
        tvMeasures.setText(text);
    }
}
