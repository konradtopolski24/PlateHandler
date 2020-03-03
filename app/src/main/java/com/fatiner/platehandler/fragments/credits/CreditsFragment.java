package com.fatiner.platehandler.fragments.credits;


import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.LibraryAdapter;
import com.fatiner.platehandler.adapters.PackAdapter;
import com.fatiner.platehandler.classes.Library;
import com.fatiner.platehandler.classes.Pack;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.Credits;
import com.fatiner.platehandler.globals.Globals;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class CreditsFragment extends PrimaryFragment {

    @BindView(R.id.rv_libraries) RecyclerView rvLibraries;
    @BindView(R.id.rv_icons) RecyclerView rvIcons;
    @BindView(R.id.cv_libraries) CardView cvLibraries;
    @BindView(R.id.cv_icons) CardView cvIcons;
    @BindView(R.id.iv_libraries) ImageView ivLibraries;
    @BindView(R.id.iv_icons) ImageView ivIcons;

    @OnClick(R.id.cv_hd_libraries)
    void clickCvHdLibraries() {
        manageExpandCv(cvLibraries, ivLibraries);
    }

    @OnClick(R.id.cv_hd_icons)
    void clickCvHdIcons() {
        manageExpandCv(cvIcons, ivIcons);
    }

    @OnClick(R.id.iv_tt_libraries)
    void clickIvTtLibraries() {
        showDialog(R.string.hd_cd_library, R.string.tt_cd_library);
    }

    @OnClick(R.id.iv_tt_icons)
    void clickIvTtIcons() {
        showDialog(R.string.hd_cd_icon, R.string.tt_cd_icon);
    }

    public CreditsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_credits, container, false);
        init(this, view, R.id.it_credits, R.string.tb_cd_show, false);
        setCreditsInfo();
        return view;
    }

    private void setCreditsInfo() {
        setRv(rvLibraries, getManager(getColumnAmount()), getLibraryAdapter());
        setRv(rvIcons, getManager(getColumnAmount()), getPackAdapter());
        changeRvSize(rvLibraries);
        changeRvSize(rvIcons);
    }

    private int getColumnAmount() {
        if(getOrientation() == Configuration.ORIENTATION_PORTRAIT) return Globals.GL_ONE;
        else return Globals.GL_TWO;
    }

    private LibraryAdapter getLibraryAdapter() {
        return new LibraryAdapter(getContext(), getLibraries());
    }

    private PackAdapter getPackAdapter() {
        return new PackAdapter(getContext(), getPacks());
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
                Credits.IP_MD_NAME,
                Credits.IP_MD_ATTRIBUTION,
                Credits.IP_MD_LICENSE,
                Credits.IP_MD_AMOUNT));
        return packs;
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
}
