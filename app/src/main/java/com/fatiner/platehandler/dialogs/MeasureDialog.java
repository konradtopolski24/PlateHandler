package com.fatiner.platehandler.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.globals.Globals;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeasureDialog extends DialogFragment {

    @BindView(R.id.tv_measures) TextView tvMeasures;

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
        return (dialogInterface, i) -> dialogInterface.dismiss();
    }

    private void setTvMeasures() {
        String text = Globals.SN_EMPTY;
        String[] measures = getContext().getResources().getStringArray(R.array.tx_measure);
        int[] factors = getContext().getResources().getIntArray(R.array.tx_factor);
        for(int i = Globals.DF_ZERO; i < measures.length; i++) {
            text += measures[i] + Globals.SN_SPACE + Globals.SN_EQUALS
                    + Globals.SN_SPACE + factors[i] + Globals.SN_ENTER;
        }
        tvMeasures.setText(text);
    }
}
