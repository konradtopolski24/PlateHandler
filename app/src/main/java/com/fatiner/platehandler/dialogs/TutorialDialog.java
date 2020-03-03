package com.fatiner.platehandler.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.globals.Globals;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TutorialDialog extends DialogFragment {

    @BindView(R.id.tv_content) TextView tvContent;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_tutorial, null);
        ButterKnife.bind(this, view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        setBuilder(builder, view);
        setTv(tvContent, getArguments().getInt(Globals.BN_CONTENT));
        return builder.create();
    }

    private void setBuilder(AlertDialog.Builder builder, View view){
        builder.setView(view)
                .setTitle(getArguments().getInt(Globals.BN_HEADER))
                .setPositiveButton(getString(R.string.dg_ok), getOnClickListener());
    }

    private DialogInterface.OnClickListener getOnClickListener(){
        return (dialogInterface, i) -> dialogInterface.dismiss();
    }

    private void setTv(TextView tv, int id) {
        String text = getString(id);
        tv.setText(text);
    }
}
