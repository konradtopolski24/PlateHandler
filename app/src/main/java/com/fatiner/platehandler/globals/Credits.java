package com.fatiner.platehandler.globals;

import com.fatiner.platehandler.items.Font;
import com.fatiner.platehandler.items.Library;
import com.fatiner.platehandler.items.Pack;

import java.util.ArrayList;

public class Credits {

    public static final String DL_NAME = "Konrad \"Fatiner\" Topolski";

    private static final String LB_BK_NAME = "Butter Knife";
    private static final String LB_BK_AUTHOR = "Jake Wharton";
    private static final String LB_BK_VERSION = "10.2.1";
    private static final String LB_BK_LICENSE = "Apache License 2.0";

    private static final String LB_GS_NAME = "Gson";
    private static final String LB_GS_AUTHOR = "Google";
    private static final String LB_GS_VERSION = "2.8.6";
    private static final String LB_GS_LICENSE = "Apache License 2.0";

    private static final String LB_AP_NAME = "Apache POI";
    private static final String LB_AP_AUTHOR = "Apache Software Foundation";
    private static final String LB_AP_VERSION = "3.7";
    private static final String LB_AP_LICENSE = "Apache License 2.0";

    private static final String LB_MP_NAME = "MPAndroidChart";
    private static final String LB_MP_AUTHOR = "Philipp Jahoda";
    private static final String LB_MP_VERSION = "3.1.0";
    private static final String LB_MP_LICENSE = "Apache License 2.0";

    private static final String LB_RM_NAME = "Room";
    private static final String LB_RM_AUTHOR = "Google";
    private static final String LB_RM_VERSION = "2.2.5";
    private static final String LB_RM_LICENSE = "Apache License 2.0";

    private static final String LB_LC_NAME = "Lifecycle";
    private static final String LB_LC_AUTHOR = "Google";
    private static final String LB_LC_VERSION = "2.2.0";
    private static final String LB_LC_LICENSE = "Apache License 2.0";

    private static final String LB_RJ_NAME = "RxJava";
    private static final String LB_RJ_AUTHOR = "ReactiveX";
    private static final String LB_RJ_VERSION = "2.2.9";
    private static final String LB_RJ_LICENSE = "Apache License 2.0";

    private static final String LB_RA_NAME = "RxAndroid";
    private static final String LB_RA_AUTHOR = "ReactiveX";
    private static final String LB_RA_VERSION = "2.1.1";
    private static final String LB_RA_LICENSE = "Apache License 2.0";

    private static final String IP_FL_NAME = "International Flags Icon Pack";
    private static final String IP_FL_ATTRIBUTION = "Icons made by Freepik from www.flaticon.com";
    private static final String IP_FL_LICENSE = "Flaticon License";
    private static final int IP_FL_AMOUNT = 26;

    private static final String IP_GC_NAME = "Gastronomy Collection Icon Pack";
    private static final String IP_GC_ATTRIBUTION = "Icons made by Smashicons from " +
            "www.flaticon.com";
    private static final String IP_GC_LICENSE = "Flaticon License";
    private static final int IP_GC_AMOUNT = 22;

    private static final String IP_GN_NAME = "Gastronomy Icon Pack";
    private static final String IP_GN_ATTRIBUTION = "Icons made by Smashicons from " +
            "www.flaticon.com";
    private static final String IP_GN_LICENSE = "Flaticon License";
    private static final int IP_GN_AMOUNT = 8;

    private static final String IP_IF_NAME = "Interface Icon Pack";
    private static final String IP_IF_ATTRIBUTION = "Icons made by Those Icons from " +
            "www.flaticon.com";
    private static final String IP_IF_LICENSE = "Flaticon License";
    private static final int IP_IF_AMOUNT = 1;

    private static final String IP_SP_NAME = "Support Icon Pack";
    private static final String IP_SP_ATTRIBUTION = "Icons made by Freepik from www.flaticon.com";
    private static final String IP_SP_LICENSE = "Flaticon License";
    private static final int IP_SP_AMOUNT = 1;

    private static final String IP_DT_NAME = "Discotheque Icon Pack";
    private static final String IP_DT_ATTRIBUTION = "Icons made by Freepik from www.flaticon.com";
    private static final String IP_DT_LICENSE = "Flaticon License";
    private static final int IP_DT_AMOUNT = 1;

    private static final String IP_CS_NAME = "Cleaning Services Icon Pack";
    private static final String IP_CS_ATTRIBUTION = "Icons made by Freepik from www.flaticon.com";
    private static final String IP_CS_LICENSE = "Flaticon License";
    private static final int IP_CS_AMOUNT = 1;

    private static final String IP_MD_NAME = "Material Design Icons";
    private static final String IP_MD_ATTRIBUTION = "Icons made by Google";
    private static final String IP_MD_LICENSE = "Apache License 2.0";
    private static final int IP_MD_AMOUNT = 26;

    private static final String FT_MP_NAME = "Mops";
    private static final String FT_MP_AUTHOR = "Uwe Borchert";
    private static final String FT_MP_VERSION = "1.3";
    private static final String FT_MP_LICENSE = "SIL Open Font License";

    private static final String FT_GT_NAME = "Gentium";
    private static final String FT_GT_AUTHOR = "SIL International";
    private static final String FT_GT_VERSION = "1.02";
    private static final String FT_GT_LICENSE = "SIL Open Font License";

    public static ArrayList<Library> getLibraries() {
        ArrayList<Library> libraries = new ArrayList<>();
        libraries.add(getLibrary(LB_BK_NAME, LB_BK_AUTHOR, LB_BK_VERSION, LB_BK_LICENSE));
        libraries.add(getLibrary(LB_GS_NAME, LB_GS_AUTHOR, LB_GS_VERSION, LB_GS_LICENSE));
        libraries.add(getLibrary(LB_AP_NAME, LB_AP_AUTHOR, LB_AP_VERSION, LB_AP_LICENSE));
        libraries.add(getLibrary(LB_MP_NAME, LB_MP_AUTHOR, LB_MP_VERSION, LB_MP_LICENSE));
        libraries.add(getLibrary(LB_RM_NAME, LB_RM_AUTHOR, LB_RM_VERSION, LB_RM_LICENSE));
        libraries.add(getLibrary(LB_LC_NAME, LB_LC_AUTHOR, LB_LC_VERSION, LB_LC_LICENSE));
        libraries.add(getLibrary(LB_RJ_NAME, LB_RJ_AUTHOR, LB_RJ_VERSION, LB_RJ_LICENSE));
        libraries.add(getLibrary(LB_RA_NAME, LB_RA_AUTHOR, LB_RA_VERSION, LB_RA_LICENSE));
        return libraries;
    }

    public static ArrayList<Pack> getPacks() {
        ArrayList<Pack> packs = new ArrayList<>();
        packs.add(getPack(IP_FL_NAME, IP_FL_ATTRIBUTION, IP_FL_LICENSE, IP_FL_AMOUNT));
        packs.add(getPack(IP_GC_NAME, IP_GC_ATTRIBUTION, IP_GC_LICENSE, IP_GC_AMOUNT));
        packs.add(getPack(IP_GN_NAME, IP_GN_ATTRIBUTION, IP_GN_LICENSE, IP_GN_AMOUNT));
        packs.add(getPack(IP_IF_NAME, IP_IF_ATTRIBUTION, IP_IF_LICENSE, IP_IF_AMOUNT));
        packs.add(getPack(IP_SP_NAME, IP_SP_ATTRIBUTION, IP_SP_LICENSE, IP_SP_AMOUNT));
        packs.add(getPack(IP_DT_NAME, IP_DT_ATTRIBUTION, IP_DT_LICENSE, IP_DT_AMOUNT));
        packs.add(getPack(IP_CS_NAME, IP_CS_ATTRIBUTION, IP_CS_LICENSE, IP_CS_AMOUNT));
        packs.add(getPack(IP_MD_NAME, IP_MD_ATTRIBUTION, IP_MD_LICENSE, IP_MD_AMOUNT));
        return packs;
    }

    public static ArrayList<Font> getFonts() {
        ArrayList<Font> fonts = new ArrayList<>();
        fonts.add(getFont(FT_MP_NAME, FT_MP_AUTHOR, FT_MP_VERSION, FT_MP_LICENSE));
        fonts.add(getFont(FT_GT_NAME, FT_GT_AUTHOR, FT_GT_VERSION, FT_GT_LICENSE));
        return fonts;
    }

    private static Library getLibrary(String name, String author, String version, String license) {
        Library library = new Library();
        library.setName(name);
        library.setAuthor(author);
        library.setVersion(version);
        library.setLicense(license);
        return library;
    }

    private static Pack getPack(String name, String attribution, String license, int amount) {
        Pack pack = new Pack();
        pack.setName(name);
        pack.setAttribution(attribution);
        pack.setLicense(license);
        pack.setAmount(amount);
        return pack;
    }

    private static Font getFont(String name, String author, String version, String license) {
        Font font = new Font();
        font.setName(name);
        font.setAuthor(author);
        font.setVersion(version);
        font.setLicense(license);
        return font;
    }
}
