package com.fatiner.platehandler.fragments.credits;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.recyclerview.FontAdapter;
import com.fatiner.platehandler.adapters.recyclerview.LibraryAdapter;
import com.fatiner.platehandler.adapters.recyclerview.PackAdapter;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.globals.Credits;
import com.fatiner.platehandler.globals.Globals;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreditsFragment extends PrimaryFragment {

    @BindView(R.id.cv_author)
    CardView cvAuthor;
    @BindView(R.id.cv_library)
    CardView cvLibrary;
    @BindView(R.id.cv_icon)
    CardView cvIcon;
    @BindView(R.id.cv_font)
    CardView cvFont;
    @BindView(R.id.iv_hd_author)
    ImageView ivHdAuthor;
    @BindView(R.id.iv_hd_library)
    ImageView ivHdLibrary;
    @BindView(R.id.iv_hd_icon)
    ImageView ivHdIcon;
    @BindView(R.id.iv_hd_font)
    ImageView ivHdFont;
    @BindView(R.id.tv_developer)
    TextView tvDeveloper;
    @BindView(R.id.rv_library)
    RecyclerView rvLibrary;
    @BindView(R.id.rv_icon)
    RecyclerView rvIcon;
    @BindView(R.id.rv_font)
    RecyclerView rvFont;

    @OnClick(R.id.cv_hd_author)
    void clickCvHdAuthor() {
        manageExpandCv(cvAuthor, ivHdAuthor);
    }

    @OnClick(R.id.cv_hd_library)
    void clickCvHdLibrary() {
        manageExpandCv(cvLibrary, ivHdLibrary);
    }

    @OnClick(R.id.cv_hd_icon)
    void clickCvHdIcon() {
        manageExpandCv(cvIcon, ivHdIcon);
    }

    @OnClick(R.id.cv_hd_font)
    void clickCvHdFont() {
        manageExpandCv(cvFont, ivHdFont);
    }

    @OnClick(R.id.iv_tt_author)
    void clickIvTtAuthor() {
        showDialog(R.string.hd_cd_author, R.string.tt_cd_author);
    }

    @OnClick(R.id.iv_tt_library)
    void clickIvTtLibrary() {
        showDialog(R.string.hd_cd_library, R.string.tt_cd_library);
    }

    @OnClick(R.id.iv_tt_icon)
    void clickIvTtIcon() {
        showDialog(R.string.hd_cd_icon, R.string.tt_cd_icon);
    }

    @OnClick(R.id.iv_tt_font)
    void clickIvTtFont() {
        showDialog(R.string.hd_cd_font, R.string.tt_cd_font);
    }

    public static CreditsFragment getInstance() {
        return new CreditsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_credits, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initOptions(R.id.it_credits, R.string.tb_cd_show, false);
        setViews();
    }

    private void setViews() {
        setTv(tvDeveloper, Credits.DL_NAME);
        setRv(rvLibrary, getManager(Globals.DF_INCREMENT), getLibraryAdapter());
        setRv(rvIcon, getManager(Globals.DF_INCREMENT), getPackAdapter());
        setRv(rvFont, getManager(Globals.DF_INCREMENT), getFontAdapter());
        changeRvSize(rvLibrary);
        changeRvSize(rvIcon);
        changeRvSize(rvFont);
    }

    private LibraryAdapter getLibraryAdapter() {
        return new LibraryAdapter(getContext(), Credits.getLibraries());
    }

    private PackAdapter getPackAdapter() {
        return new PackAdapter(getContext(), Credits.getPacks());
    }

    private FontAdapter getFontAdapter() {
        return new FontAdapter(getContext(), Credits.getFonts());
    }
}
