package com.fatiner.platehandler.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.globals.Globals;

public abstract class PrimaryAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected Context context;

    PrimaryAdapter(Context context) {
        this.context = context;
    }

    View getLayout(ViewGroup parent, int id) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(id, parent, false);
    }

    void showView(View vw) {
        vw.setVisibility(View.VISIBLE);
    }

    void hideView(View vw) {
        vw.setVisibility(View.INVISIBLE);
    }

    void removeView(View vw) {
        vw.setVisibility(View.GONE);
    }

    void setTv(TextView tv, String text) {
        tv.setText(text);
    }

    void setTv(TextView tv, int value) {
        String text = String.valueOf(value);
        tv.setText(text);
    }

    void setEt(EditText et, String text) {
        et.setText(text);
    }

    void setEt(EditText et, float value) {
        String text = String.valueOf(value);
        et.setText(text);
    }

    void setSp(Spinner sp, int position) {
        sp.setSelection(position);
    }

    void setSp(Spinner sp, int position, ArrayAdapter<?> adapter) {
        sp.setAdapter(adapter);
        sp.setSelection(position);
    }

    private Resources getResources() {
        return context.getResources();
    }

    void setAlpha(View vw, float value) {
        vw.setAlpha(value);
    }

    String getString(int id) {
        return getResources().getString(id);
    }

    String [] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    private TypedArray getTypedArray(int id) {
        return getResources().obtainTypedArray(id);
    }

    void setIv(ImageView iv, int value, int arrayId) {
        TypedArray array = getTypedArray(arrayId);
        int resource = array.getResourceId(value, Globals.DF_DECREMENT);
        iv.setImageResource(resource);
        array.recycle();
    }

    float getCorrectEtValue(CharSequence text) {
        try {
            return Float.parseFloat(String.valueOf(text));
        } catch(Exception e) {
            return Globals.DF_ZERO;
        }
    }

    void setCorrectInput(EditText et) {
        et.setKeyListener(DigitsKeyListener.getInstance(false, true));
    }
}
