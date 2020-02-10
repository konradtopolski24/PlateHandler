package com.fatiner.platehandler.fragments.credits;


import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.LibraryAdapter;
import com.fatiner.platehandler.adapters.PackAdapter;
import com.fatiner.platehandler.classes.Library;
import com.fatiner.platehandler.classes.Pack;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.CreditsGlobals;
import com.fatiner.platehandler.globals.MainGlobals;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreditsFragment extends PrimaryFragment {

    @BindView(R.id.rv_libraries)
    RecyclerView rvLibraries;
    @BindView(R.id.rv_icons)
    RecyclerView rvIcons;
    @BindView(R.id.cv_libraries)
    CardView cvLibraries;
    @BindView(R.id.cv_icons)
    CardView cvIcons;
    @BindView(R.id.iv_libraries)
    ImageView ivLibraries;
    @BindView(R.id.iv_icons)
    ImageView ivIcons;

    @OnClick(R.id.cv_libraries_hd)
    public void onClickFaAdd(){
        manageExpandCardView(cvLibraries, ivLibraries);
    }

    @OnClick(R.id.cv_icons_hd)
    public void onClickFbAdd(){
        manageExpandCardView(cvIcons, ivIcons);
    }

    public CreditsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credits, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tb_cd_show);
        setMenuItem(MainGlobals.ID_CREDITS_DRAW_MAIN);
        setRecyclerView(
                rvLibraries,
                getLayoutManagerNoScroll(LinearLayoutManager.VERTICAL),
                new LibraryAdapter(getContext(), getLibraries())
        );
        setRecyclerView(
                rvIcons,
                getLayoutManagerNoScroll(LinearLayoutManager.VERTICAL),
                new PackAdapter(getContext(), getPacks())
        );
        return view;
    }

    private ArrayList<Library> getLibraries() {
        ArrayList<Library> libraries = new ArrayList<>();
        libraries.add(getLibrary(
                CreditsGlobals.LB_BK_NAME,
                CreditsGlobals.LB_BK_AUTHOR,
                CreditsGlobals.LB_BK_VERSION,
                CreditsGlobals.LB_BK_LICENSE));
        libraries.add(getLibrary(
                CreditsGlobals.LB_GS_NAME,
                CreditsGlobals.LB_GS_AUTHOR,
                CreditsGlobals.LB_GS_VERSION,
                CreditsGlobals.LB_GS_LICENSE));
        libraries.add(getLibrary(
                CreditsGlobals.LB_AP_NAME,
                CreditsGlobals.LB_AP_AUTHOR,
                CreditsGlobals.LB_AP_VERSION,
                CreditsGlobals.LB_AP_LICENSE));
        libraries.add(getLibrary(
                CreditsGlobals.LB_MP_NAME,
                CreditsGlobals.LB_MP_AUTHOR,
                CreditsGlobals.LB_MP_VERSION,
                CreditsGlobals.LB_MP_LICENSE));
        return libraries;
    }

    private ArrayList<Pack> getPacks() {
        ArrayList<Pack> packs = new ArrayList<>();
        packs.add(getPack(
                CreditsGlobals.IP_FL_NAME,
                CreditsGlobals.IP_FL_ATTRIBUTION,
                CreditsGlobals.IP_FL_LICENSE,
                CreditsGlobals.IP_FL_AMOUNT));
        packs.add(getPack(
                CreditsGlobals.IP_GC_NAME,
                CreditsGlobals.IP_GC_ATTRIBUTION,
                CreditsGlobals.IP_GC_LICENSE,
                CreditsGlobals.IP_GC_AMOUNT));
        packs.add(getPack(
                CreditsGlobals.IP_GN_NAME,
                CreditsGlobals.IP_GN_ATTRIBUTION,
                CreditsGlobals.IP_GN_LICENSE,
                CreditsGlobals.IP_GN_AMOUNT));
        packs.add(getPack(
                CreditsGlobals.IP_MD_NAME,
                CreditsGlobals.IP_MD_ATTRIBUTION,
                CreditsGlobals.IP_MD_LICENSE,
                CreditsGlobals.IP_MD_AMOUNT));
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
