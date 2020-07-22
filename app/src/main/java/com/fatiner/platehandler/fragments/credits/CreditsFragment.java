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
import com.fatiner.platehandler.items.Font;
import com.fatiner.platehandler.items.Library;
import com.fatiner.platehandler.items.Pack;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreditsFragment extends PrimaryFragment {

    @BindView(R.id.cv_author) CardView cvAuthor;
    @BindView(R.id.cv_library) CardView cvLibrary;
    @BindView(R.id.cv_icon) CardView cvIcon;
    @BindView(R.id.cv_font) CardView cvFont;
    @BindView(R.id.iv_hd_author) ImageView ivHdAuthor;
    @BindView(R.id.iv_hd_library) ImageView ivHdLibrary;
    @BindView(R.id.iv_hd_icon) ImageView ivHdIcon;
    @BindView(R.id.iv_hd_font) ImageView ivHdFont;
    @BindView(R.id.tv_developer) TextView tvDeveloper;
    @BindView(R.id.rv_library) RecyclerView rvLibrary;
    @BindView(R.id.rv_icon) RecyclerView rvIcon;
    @BindView(R.id.rv_font) RecyclerView rvFont;

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
        return new LibraryAdapter(getContext(), getLibraries());
    }

    private PackAdapter getPackAdapter() {
        return new PackAdapter(getContext(), getPacks());
    }

    private FontAdapter getFontAdapter() {
        return new FontAdapter(getContext(), getFonts());
    }

    private ArrayList<Library> getLibraries() {
        ArrayList<Library> libraries = new ArrayList<>();
        libraries.add(getLibrary(
                Credits.LB_BK_NAME,
                Credits.LB_BK_AUTHOR,
                Credits.LB_BK_VERSION,
                Credits.LB_BK_LICENSE));
        libraries.add(getLibrary(
                Credits.LB_GS_NAME,
                Credits.LB_GS_AUTHOR,
                Credits.LB_GS_VERSION,
                Credits.LB_GS_LICENSE));
        libraries.add(getLibrary(
                Credits.LB_AP_NAME,
                Credits.LB_AP_AUTHOR,
                Credits.LB_AP_VERSION,
                Credits.LB_AP_LICENSE));
        libraries.add(getLibrary(
                Credits.LB_MP_NAME,
                Credits.LB_MP_AUTHOR,
                Credits.LB_MP_VERSION,
                Credits.LB_MP_LICENSE));
        libraries.add(getLibrary(
                Credits.LB_RM_NAME,
                Credits.LB_RM_AUTHOR,
                Credits.LB_RM_VERSION,
                Credits.LB_RM_LICENSE));
        libraries.add(getLibrary(
                Credits.LB_LC_NAME,
                Credits.LB_LC_AUTHOR,
                Credits.LB_LC_VERSION,
                Credits.LB_LC_LICENSE));
        libraries.add(getLibrary(
                Credits.LB_RJ_NAME,
                Credits.LB_RJ_AUTHOR,
                Credits.LB_RJ_VERSION,
                Credits.LB_RJ_LICENSE));
        libraries.add(getLibrary(
                Credits.LB_RA_NAME,
                Credits.LB_RA_AUTHOR,
                Credits.LB_RA_VERSION,
                Credits.LB_RA_LICENSE));
        return libraries;
    }

    private ArrayList<Pack> getPacks() {
        ArrayList<Pack> packs = new ArrayList<>();
        packs.add(getPack(
                Credits.IP_FL_NAME,
                Credits.IP_FL_ATTRIBUTION,
                Credits.IP_FL_LICENSE,
                Credits.IP_FL_AMOUNT));
        packs.add(getPack(
                Credits.IP_GC_NAME,
                Credits.IP_GC_ATTRIBUTION,
                Credits.IP_GC_LICENSE,
                Credits.IP_GC_AMOUNT));
        packs.add(getPack(
                Credits.IP_GN_NAME,
                Credits.IP_GN_ATTRIBUTION,
                Credits.IP_GN_LICENSE,
                Credits.IP_GN_AMOUNT));
        packs.add(getPack(
                Credits.IP_IF_NAME,
                Credits.IP_IF_ATTRIBUTION,
                Credits.IP_IF_LICENSE,
                Credits.IP_IF_AMOUNT));
        packs.add(getPack(
                Credits.IP_SP_NAME,
                Credits.IP_SP_ATTRIBUTION,
                Credits.IP_SP_LICENSE,
                Credits.IP_SP_AMOUNT));
        packs.add(getPack(
                Credits.IP_DT_NAME,
                Credits.IP_DT_ATTRIBUTION,
                Credits.IP_DT_LICENSE,
                Credits.IP_DT_AMOUNT));
        packs.add(getPack(
                Credits.IP_CS_NAME,
                Credits.IP_CS_ATTRIBUTION,
                Credits.IP_CS_LICENSE,
                Credits.IP_CS_AMOUNT));
        packs.add(getPack(
                Credits.IP_MD_NAME,
                Credits.IP_MD_ATTRIBUTION,
                Credits.IP_MD_LICENSE,
                Credits.IP_MD_AMOUNT));
        return packs;
    }

    private ArrayList<Font> getFonts() {
        ArrayList<Font> fonts = new ArrayList<>();
        fonts.add(getFont(
                Credits.FT_MP_NAME,
                Credits.FT_MP_AUTHOR,
                Credits.FT_MP_VERSION,
                Credits.FT_MP_LICENSE));
        fonts.add(getFont(
                Credits.FT_GT_NAME,
                Credits.FT_GT_AUTHOR,
                Credits.FT_GT_VERSION,
                Credits.FT_GT_LICENSE));
        return fonts;
    }

    private Library getLibrary(String name, String author, String version, String license) {
        Library library = new Library();
        library.setName(name);
        library.setAuthor(author);
        library.setVersion(version);
        library.setLicense(license);
        return library;
    }

    private Pack getPack(String name, String attribution, String license, int amount) {
        Pack pack = new Pack();
        pack.setName(name);
        pack.setAttribution(attribution);
        pack.setLicense(license);
        pack.setAmount(amount);
        return pack;
    }

    private Font getFont(String name, String author, String version, String license) {
        Font font = new Font();
        font.setName(name);
        font.setAuthor(author);
        font.setVersion(version);
        font.setLicense(license);
        return font;
    }
}
