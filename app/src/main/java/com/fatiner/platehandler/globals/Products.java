package com.fatiner.platehandler.globals;

import android.content.Context;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.models.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Products {

    private static final float PD_TM_SIZE = 175;
    private static final float PD_TM_CARBOHYDRATES = 6.5f;
    private static final float PD_TM_PROTEINS = 1.7f;
    private static final float PD_TM_FATS = 0.25f;

    private static final float PD_CT_SIZE = 50;
    private static final float PD_CT_CARBOHYDRATES = 4.35f;
    private static final float PD_CT_PROTEINS = 0.3f;
    private static final float PD_CT_FATS = 0.25f;

    private static final float PD_ON_SIZE = 110;
    private static final float PD_ON_CARBOHYDRATES = 8.25f;
    private static final float PD_ON_PROTEINS = 1.25f;
    private static final float PD_ON_FATS = 0.15f;

    private static final float PD_BD_SIZE = 1000;
    private static final float PD_BD_CARBOHYDRATES = 425.5f;
    private static final float PD_BD_PROTEINS = 53.15f;
    private static final float PD_BD_FATS = 10.25f;

    private static final float PD_GB_SIZE = 1000;
    private static final float PD_GB_CARBOHYDRATES = 493.35f;
    private static final float PD_GB_PROTEINS = 63.45f;
    private static final float PD_GB_FATS = 15.35f;

    private static final float PD_CS_SIZE = 200;
    private static final float PD_CS_CARBOHYDRATES = 2.25f;
    private static final float PD_CS_PROTEINS = 52.15f;
    private static final float PD_CS_FATS = 55.15f;

    private static final float PD_CM_SIZE = 1000;
    private static final float PD_CM_CARBOHYDRATES = 45.25f;
    private static final float PD_CM_PROTEINS = 32.35f;
    private static final float PD_CM_FATS = 20.5f;

    private static final float PD_CB_SIZE = 500;
    private static final float PD_CB_CARBOHYDRATES = 0;
    private static final float PD_CB_PROTEINS = 110.25f;
    private static final float PD_CB_FATS = 12.95f;

    private static final float PD_CE_SIZE = 62;
    private static final float PD_CE_CARBOHYDRATES = 0;
    private static final float PD_CE_PROTEINS = 7.25f;
    private static final float PD_CE_FATS = 5.55f;

    private static final float PD_LM_SIZE = 125;
    private static final float PD_LM_CARBOHYDRATES = 12.15f;
    private static final float PD_LM_PROTEINS = 1.4f;
    private static final float PD_LM_FATS = 0.65f;

    private static final float PD_SL_SIZE = 5;
    private static final float PD_SL_CARBOHYDRATES = 0;
    private static final float PD_SL_PROTEINS = 0;
    private static final float PD_SL_FATS = 0;

    private static final float PD_PP_SIZE = 5;
    private static final float PD_PP_CARBOHYDRATES = 2.45f;
    private static final float PD_PP_PROTEINS = 0.65f;
    private static final float PD_PP_FATS = 0.1f;

    private static final float PD_KT_SIZE = 900;
    private static final float PD_KT_CARBOHYDRATES = 150.5f;
    private static final float PD_KT_PROTEINS = 14.35f;
    private static final float PD_KT_FATS = 2.85f;

    private static final float PD_MN_SIZE = 450;
    private static final float PD_MN_CARBOHYDRATES = 15.25f;
    private static final float PD_MN_PROTEINS = 4.65f;
    private static final float PD_MN_FATS = 309.5f;

    private static final float PD_BT_SIZE = 200;
    private static final float PD_BT_CARBOHYDRATES = 1.05f;
    private static final float PD_BT_PROTEINS = 1.65f;
    private static final float PD_BT_FATS = 160.55f;

    private static final float PD_SG_SIZE = 5;
    private static final float PD_SG_CARBOHYDRATES = 5;
    private static final float PD_SG_PROTEINS = 0;
    private static final float PD_SG_FATS = 0;

    private static final float PD_CG_SIZE = 5;
    private static final float PD_CG_CARBOHYDRATES = 1.65f;
    private static final float PD_CG_PROTEINS = 0.2f;
    private static final float PD_CG_FATS = 0.05f;

    private static final float PD_RP_SIZE = 260;
    private static final float PD_RP_CARBOHYDRATES = 14.35f;
    private static final float PD_RP_PROTEINS = 3.4f;
    private static final float PD_RP_FATS = 1.25f;

    private static final float PD_OJ_SIZE = 1000;
    private static final float PD_OJ_CARBOHYDRATES = 94.7f;
    private static final float PD_OJ_PROTEINS = 6.75f;
    private static final float PD_OJ_FATS = 1.05f;

    private static final float PD_TL_SIZE = 500;
    private static final float PD_TL_CARBOHYDRATES = 0;
    private static final float PD_TL_PROTEINS = 97.65f;
    private static final float PD_TL_FATS = 15.25f;

    private static final float PD_MP_SIZE = 500;
    private static final float PD_MP_CARBOHYDRATES = 0;
    private static final float PD_MP_PROTEINS = 103.25f;
    private static final float PD_MP_FATS = 76.85f;

    private static final float PD_CD_SIZE = 500;
    private static final float PD_CD_CARBOHYDRATES = 2.5f;
    private static final float PD_CD_PROTEINS = 82.15f;
    private static final float PD_CD_FATS = 5.55f;

    private static final float PD_TT_SIZE = 500;
    private static final float PD_TT_CARBOHYDRATES = 0;
    private static final float PD_TT_PROTEINS = 95.35f;
    private static final float PD_TT_FATS = 42.5f;

    private static final float PD_PL_SIZE = 75;
    private static final float PD_PL_CARBOHYDRATES = 9.75f;
    private static final float PD_PL_PROTEINS = 2.5f;
    private static final float PD_PL_FATS = 0.45f;

    private static final float PD_CL_SIZE = 285;
    private static final float PD_CL_CARBOHYDRATES = 20.45f;
    private static final float PD_CL_PROTEINS = 4.95f;
    private static final float PD_CL_FATS = 0.7f;

    private static final float PD_WF_SIZE = 1000;
    private static final float PD_WF_CARBOHYDRATES = 660.35f;
    private static final float PD_WF_PROTEINS = 107.5f;
    private static final float PD_WF_FATS = 15.4f;

    private static final float PD_RF_SIZE = 1000;
    private static final float PD_RF_CARBOHYDRATES = 726.75f;
    private static final float PD_RF_PROTEINS = 79.3f;
    private static final float PD_RF_FATS = 13.65f;

    private static final float PD_18_SIZE = 200;
    private static final float PD_18_CARBOHYDRATES = 8.25f;
    private static final float PD_18_PROTEINS = 4.45f;
    private static final float PD_18_FATS = 34.9f;

    private static final float PD_NY_SIZE = 200;
    private static final float PD_NY_CARBOHYDRATES = 11.2f;
    private static final float PD_NY_PROTEINS = 8.95f;
    private static final float PD_NY_FATS = 4.45f;

    private static final float PD_BW_SIZE = 100;
    private static final float PD_BW_CARBOHYDRATES = 70.95f;
    private static final float PD_BW_PROTEINS = 11.35f;
    private static final float PD_BW_FATS = 2.6f;

    private static final float PD_WR_SIZE = 100;
    private static final float PD_WR_CARBOHYDRATES = 72.35f;
    private static final float PD_WR_PROTEINS = 7.65f;
    private static final float PD_WR_FATS = 1.3f;

    private static final float PD_PN_SIZE = 500;
    private static final float PD_PN_CARBOHYDRATES = 345.5f;
    private static final float PD_PN_PROTEINS = 69.25f;
    private static final float PD_PN_FATS = 10.8f;

    private static final float PD_RO_SIZE = 1000;
    private static final float PD_RO_CARBOHYDRATES = 0;
    private static final float PD_RO_PROTEINS = 0;
    private static final float PD_RO_FATS = 965.75f;

    private static final float PD_OG_SIZE = 220;
    private static final float PD_OG_CARBOHYDRATES = 28.45f;
    private static final float PD_OG_PROTEINS = 1.25f;
    private static final float PD_OG_FATS = 0.6f;

    private static final float PD_AL_SIZE = 210;
    private static final float PD_AL_CARBOHYDRATES = 26.5f;
    private static final float PD_AL_PROTEINS = 0.85f;
    private static final float PD_AL_FATS = 0.45f;

    private static final float PD_BC_SIZE = 500;
    private static final float PD_BC_CARBOHYDRATES = 22.15f;
    private static final float PD_BC_PROTEINS = 15.35f;
    private static final float PD_BC_FATS = 1.7f;

    private static final float PD_TB_SIZE = 500;
    private static final float PD_TB_CARBOHYDRATES = 218.5f;
    private static final float PD_TB_PROTEINS = 46.85f;
    private static final float PD_TB_FATS = 16.3f;

    private static final float PD_SN_SIZE = 500;
    private static final float PD_SN_CARBOHYDRATES = 313.25f;
    private static final float PD_SN_PROTEINS = 63.45f;
    private static final float PD_SN_FATS = 12.4f;

    private static final float PD_TG_SIZE = 500;
    private static final float PD_TG_CARBOHYDRATES = 255.3f;
    private static final float PD_TG_PROTEINS = 53.85f;
    private static final float PD_TG_FATS = 14.15f;

    private static final float PD_LR_SIZE = 200;
    private static final float PD_LR_CARBOHYDRATES = 0;
    private static final float PD_LR_PROTEINS = 0;
    private static final float PD_LR_FATS = 197.95f;

    private static final float PD_CN_SIZE = 350;
    private static final float PD_CN_CARBOHYDRATES = 34.6f;
    private static final float PD_CN_PROTEINS = 6.35f;
    private static final float PD_CN_FATS = 2.95f;

    private static final float PD_PS_SIZE = 350;
    private static final float PD_PS_CARBOHYDRATES = 34.5f;
    private static final float PD_PS_PROTEINS = 18.25f;
    private static final float PD_PS_FATS = 2.45f;

    private static final float PD_CC_SIZE = 230;
    private static final float PD_CC_CARBOHYDRATES = 7.75f;
    private static final float PD_CC_PROTEINS = 1.8f;
    private static final float PD_CC_FATS = 0.4f;

    private static final float PD_SP_SIZE = 5;
    private static final float PD_SP_CARBOHYDRATES = 3.15f;
    private static final float PD_SP_PROTEINS = 0.85f;
    private static final float PD_SP_FATS = 0.6f;

    private static final float PD_HP_SIZE = 5;
    private static final float PD_HP_CARBOHYDRATES = 2.85f;
    private static final float PD_HP_PROTEINS = 1.15f;
    private static final float PD_HP_FATS = 0.55f;

    private static final float PD_GR_SIZE = 5;
    private static final float PD_GR_CARBOHYDRATES = 0.65f;
    private static final float PD_GR_PROTEINS = 0.85f;
    private static final float PD_GR_FATS = 0;

    private static final float PD_CR_SIZE = 5;
    private static final float PD_CR_CARBOHYDRATES = 0;
    private static final float PD_CR_PROTEINS = 0.7f;
    private static final float PD_CR_FATS = 0.55f;

    private static final float PD_MT_SIZE = 170;
    private static final float PD_MT_CARBOHYDRATES = 17.25f;
    private static final float PD_MT_PROTEINS = 8.5f;
    private static final float PD_MT_FATS = 5.85f;

    private static final float PD_SR_SIZE = 14;
    private static final float PD_SR_CARBOHYDRATES = 0.65f;
    private static final float PD_SR_PROTEINS = 3.35f;
    private static final float PD_SR_FATS = 0;

    private static final float PD_SQ_SIZE = 200;
    private static final float PD_SQ_CARBOHYDRATES = 42.25f;
    private static final float PD_SQ_PROTEINS = 12.3f;
    private static final float PD_SQ_FATS = 12.2f;

    private static Product getProduct(Context context, int idName, int idType, float size,
                                      float carbohydrates, float proteins, float fats) {
        String name = context.getString(idName);
        String type = context.getString(idType);
        List<String> types = Arrays.asList(
                context.getResources().getStringArray(R.array.tx_pd_type));
        Product product = new Product();
        product.setName(name);
        product.setType(types.indexOf(type));
        product.setSize(size);
        product.setCarbohydrates(carbohydrates);
        product.setProteins(proteins);
        product.setFats(fats);
        return product;
    }

    public static ArrayList<Product> getProducts(Context context) {
        ArrayList<Product> products = new ArrayList<>();
        products.add(getProduct(context,
                R.string.df_tomato, R.string.ar_pd_fruit, PD_TM_SIZE,
                PD_TM_CARBOHYDRATES, PD_TM_PROTEINS, PD_TM_FATS));
        products.add(getProduct(context,
                R.string.df_carrot, R.string.ar_pd_vegetable, PD_CT_SIZE,
                PD_CT_CARBOHYDRATES, PD_CT_PROTEINS, PD_CT_FATS));
        products.add(getProduct(context,
                R.string.df_onion, R.string.ar_pd_vegetable, PD_ON_SIZE,
                PD_ON_CARBOHYDRATES, PD_ON_PROTEINS, PD_ON_FATS));
        products.add(getProduct(context,
                R.string.df_bread, R.string.ar_pd_bread, PD_BD_SIZE,
                PD_BD_CARBOHYDRATES, PD_BD_PROTEINS, PD_BD_FATS));
        products.add(getProduct(context,
                R.string.df_grain, R.string.ar_pd_bread, PD_GB_SIZE,
                PD_GB_CARBOHYDRATES, PD_GB_PROTEINS, PD_GB_FATS));
        products.add(getProduct(context,
                R.string.df_gouda, R.string.ar_pd_dairy, PD_CS_SIZE,
                PD_CS_CARBOHYDRATES, PD_CS_PROTEINS, PD_CS_FATS));
        products.add(getProduct(context,
                R.string.df_cow, R.string.ar_pd_dairy, PD_CM_SIZE,
                PD_CM_CARBOHYDRATES, PD_CM_PROTEINS, PD_CM_FATS));
        products.add(getProduct(context,
                R.string.df_breast, R.string.ar_pd_meat, PD_CB_SIZE,
                PD_CB_CARBOHYDRATES, PD_CB_PROTEINS, PD_CB_FATS));
        products.add(getProduct(context,
                R.string.df_egg, R.string.ar_pd_egg, PD_CE_SIZE,
                PD_CE_CARBOHYDRATES, PD_CE_PROTEINS, PD_CE_FATS));
        products.add(getProduct(context,
                R.string.df_lemon, R.string.ar_pd_fruit, PD_LM_SIZE,
                PD_LM_CARBOHYDRATES, PD_LM_PROTEINS, PD_LM_FATS));
        products.add(getProduct(context,
                R.string.df_salt, R.string.ar_pd_spice, PD_SL_SIZE,
                PD_SL_CARBOHYDRATES, PD_SL_PROTEINS, PD_SL_FATS));
        products.add(getProduct(context,
                R.string.df_pepper, R.string.ar_pd_spice, PD_PP_SIZE,
                PD_PP_CARBOHYDRATES, PD_PP_PROTEINS, PD_PP_FATS));
        products.add(getProduct(context,
                R.string.df_ketchup, R.string.ar_pd_sauce, PD_KT_SIZE,
                PD_KT_CARBOHYDRATES, PD_KT_PROTEINS, PD_KT_FATS));
        products.add(getProduct(context,
                R.string.df_mayonnaise, R.string.ar_pd_sauce, PD_MN_SIZE,
                PD_MN_CARBOHYDRATES, PD_MN_PROTEINS, PD_MN_FATS));
        products.add(getProduct(context,
                R.string.df_butter, R.string.ar_pd_fat, PD_BT_SIZE,
                PD_BT_CARBOHYDRATES, PD_BT_PROTEINS, PD_BT_FATS));
        products.add(getProduct(context,
                R.string.df_sugar, R.string.ar_pd_spice, PD_SG_SIZE,
                PD_SG_CARBOHYDRATES, PD_SG_PROTEINS, PD_SG_FATS));
        products.add(getProduct(context,
                R.string.df_garlic, R.string.ar_pd_vegetable, PD_CG_SIZE,
                PD_CG_CARBOHYDRATES, PD_CG_PROTEINS, PD_CG_FATS));
        products.add(getProduct(context,
                R.string.df_red, R.string.ar_pd_vegetable, PD_RP_SIZE,
                PD_RP_CARBOHYDRATES, PD_RP_PROTEINS, PD_RP_FATS));
        products.add(getProduct(context,
                R.string.df_juice, R.string.ar_pd_liquid, PD_OJ_SIZE,
                PD_OJ_CARBOHYDRATES, PD_OJ_PROTEINS, PD_OJ_FATS));
        products.add(getProduct(context,
                R.string.df_tenderloin, R.string.ar_pd_meat, PD_TL_SIZE,
                PD_TL_CARBOHYDRATES, PD_TL_PROTEINS, PD_TL_FATS));
        products.add(getProduct(context,
                R.string.df_pork, R.string.ar_pd_meat, PD_MP_SIZE,
                PD_MP_CARBOHYDRATES, PD_MP_PROTEINS, PD_MP_FATS));
        products.add(getProduct(context,
                R.string.df_cod, R.string.ar_pd_fish, PD_CD_SIZE,
                PD_CD_CARBOHYDRATES, PD_CD_PROTEINS, PD_CD_FATS));
        products.add(getProduct(context,
                R.string.df_trout, R.string.ar_pd_fish, PD_TT_SIZE,
                PD_TT_CARBOHYDRATES, PD_TT_PROTEINS, PD_TT_FATS));
        products.add(getProduct(context,
                R.string.df_parsley, R.string.ar_pd_vegetable, PD_PL_SIZE,
                PD_PL_CARBOHYDRATES, PD_PL_PROTEINS, PD_PL_FATS));
        products.add(getProduct(context,
                R.string.df_celery, R.string.ar_pd_vegetable, PD_CL_SIZE,
                PD_CL_CARBOHYDRATES, PD_CL_PROTEINS, PD_CL_FATS));
        products.add(getProduct(context,
                R.string.df_wheat, R.string.ar_pd_grain, PD_WF_SIZE,
                PD_WF_CARBOHYDRATES, PD_WF_PROTEINS, PD_WF_FATS));
        products.add(getProduct(context,
                R.string.df_rye, R.string.ar_pd_grain, PD_RF_SIZE,
                PD_RF_CARBOHYDRATES, PD_RF_PROTEINS, PD_RF_FATS));
        products.add(getProduct(context,
                R.string.df_cream, R.string.ar_pd_dairy, PD_18_SIZE,
                PD_18_CARBOHYDRATES, PD_18_PROTEINS, PD_18_FATS));
        products.add(getProduct(context,
                R.string.df_yogurt, R.string.ar_pd_dairy, PD_NY_SIZE,
                PD_NY_CARBOHYDRATES, PD_NY_PROTEINS, PD_NY_FATS));
        products.add(getProduct(context,
                R.string.df_buckwheat, R.string.ar_pd_grain, PD_BW_SIZE,
                PD_BW_CARBOHYDRATES, PD_BW_PROTEINS, PD_BW_FATS));
        products.add(getProduct(context,
                R.string.df_rice, R.string.ar_pd_grain, PD_WR_SIZE,
                PD_WR_CARBOHYDRATES, PD_WR_PROTEINS, PD_WR_FATS));
        products.add(getProduct(context,
                R.string.df_pasta, R.string.ar_pd_pasta, PD_PN_SIZE,
                PD_PN_CARBOHYDRATES, PD_PN_PROTEINS, PD_PN_FATS));
        products.add(getProduct(context,
                R.string.df_oil, R.string.ar_pd_fat, PD_RO_SIZE,
                PD_RO_CARBOHYDRATES, PD_RO_PROTEINS, PD_RO_FATS));
        products.add(getProduct(context,
                R.string.df_orange, R.string.ar_pd_fruit, PD_OG_SIZE,
                PD_OG_CARBOHYDRATES, PD_OG_PROTEINS, PD_OG_FATS));
        products.add(getProduct(context,
                R.string.df_apple, R.string.ar_pd_fruit, PD_AL_SIZE,
                PD_AL_CARBOHYDRATES, PD_AL_PROTEINS, PD_AL_FATS));
        products.add(getProduct(context,
                R.string.df_broccoli, R.string.ar_pd_vegetable, PD_BC_SIZE,
                PD_BC_CARBOHYDRATES, PD_BC_PROTEINS, PD_BC_FATS));
        products.add(getProduct(context,
                R.string.df_toast, R.string.ar_pd_bread, PD_TB_SIZE,
                PD_TB_CARBOHYDRATES, PD_TB_PROTEINS, PD_TB_FATS));
        products.add(getProduct(context,
                R.string.df_spaghetti, R.string.ar_pd_pasta, PD_SN_SIZE,
                PD_SN_CARBOHYDRATES, PD_SN_PROTEINS, PD_SN_FATS));
        products.add(getProduct(context,
                R.string.df_tagliatelle, R.string.ar_pd_pasta, PD_TG_SIZE,
                PD_TG_CARBOHYDRATES, PD_TG_PROTEINS, PD_TG_FATS));
        products.add(getProduct(context,
                R.string.df_lard, R.string.ar_pd_fat, PD_LR_SIZE,
                PD_LR_CARBOHYDRATES, PD_LR_PROTEINS, PD_LR_FATS));
        products.add(getProduct(context,
                R.string.df_corn, R.string.ar_pd_vegetable, PD_CN_SIZE,
                PD_CN_CARBOHYDRATES, PD_CN_PROTEINS, PD_CN_FATS));
        products.add(getProduct(context,
                R.string.df_peas, R.string.ar_pd_vegetable, PD_PS_SIZE,
                PD_PS_CARBOHYDRATES, PD_PS_PROTEINS, PD_PS_FATS));
        products.add(getProduct(context,
                R.string.df_cucumber, R.string.ar_pd_vegetable, PD_CC_SIZE,
                PD_CC_CARBOHYDRATES, PD_CC_PROTEINS, PD_CC_FATS));
        products.add(getProduct(context,
                R.string.df_sweet, R.string.ar_pd_spice, PD_SP_SIZE,
                PD_SP_CARBOHYDRATES, PD_SP_PROTEINS, PD_SP_FATS));
        products.add(getProduct(context,
                R.string.df_hot, R.string.ar_pd_spice, PD_HP_SIZE,
                PD_HP_CARBOHYDRATES, PD_HP_PROTEINS, PD_HP_FATS));
        products.add(getProduct(context,
                R.string.df_gyros, R.string.ar_pd_spice, PD_GR_SIZE,
                PD_GR_CARBOHYDRATES, PD_GR_PROTEINS, PD_GR_FATS));
        products.add(getProduct(context,
                R.string.df_curry, R.string.ar_pd_spice, PD_CR_SIZE,
                PD_CR_CARBOHYDRATES, PD_CR_PROTEINS, PD_CR_FATS));
        products.add(getProduct(context,
                R.string.df_mustard, R.string.ar_pd_sauce, PD_MT_SIZE,
                PD_MT_CARBOHYDRATES, PD_MT_PROTEINS, PD_MT_FATS));
        products.add(getProduct(context,
                R.string.df_shrimp, R.string.ar_pd_seafood, PD_SR_SIZE,
                PD_SR_CARBOHYDRATES, PD_SR_PROTEINS, PD_SR_FATS));
        products.add(getProduct(context,
                R.string.df_squid, R.string.ar_pd_seafood, PD_SQ_SIZE,
                PD_SQ_CARBOHYDRATES, PD_SQ_PROTEINS, PD_SQ_FATS));
        return products;
    }
}
