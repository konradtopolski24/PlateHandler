package com.fatiner.platehandler.globals;

import android.content.Context;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.models.Product;

import java.util.ArrayList;
import java.util.Arrays;

public class Products {

    private static Product getProduct(Context context, int idName, int idType, float size,
                                      float carbohydrates, float proteins, float fats) {
        String name = context.getString(idName);
        String type = context.getString(idType);
        Product product = new Product();
        product.setName(name);
        product.setType(Arrays.asList(context.getResources().getStringArray(R.array.tx_pd_type)).indexOf(type));
        product.setSize(size);
        product.setCarbohydrates(carbohydrates);
        product.setProteins(proteins);
        product.setFats(fats);
        return product;
    }

    public static ArrayList<Product> getProducts(Context context) {
        ArrayList<Product> products = new ArrayList<>();
        products.add(getProduct(context,
                R.string.df_tomato, R.string.ar_pd_fruit, Defaults.PD_TM_SIZE,
                Defaults.PD_TM_CARBOHYDRATES, Defaults.PD_TM_PROTEINS, Defaults.PD_TM_FATS));
        products.add(getProduct(context,
                R.string.df_carrot, R.string.ar_pd_vegetable, Defaults.PD_CT_SIZE,
                Defaults.PD_CT_CARBOHYDRATES, Defaults.PD_CT_PROTEINS, Defaults.PD_CT_FATS));
        products.add(getProduct(context,
                R.string.df_onion, R.string.ar_pd_vegetable, Defaults.PD_ON_SIZE,
                Defaults.PD_ON_CARBOHYDRATES, Defaults.PD_ON_PROTEINS, Defaults.PD_ON_FATS));
        products.add(getProduct(context,
                R.string.df_bread, R.string.ar_pd_bread, Defaults.PD_BD_SIZE,
                Defaults.PD_BD_CARBOHYDRATES, Defaults.PD_BD_PROTEINS, Defaults.PD_BD_FATS));
        products.add(getProduct(context,
                R.string.df_grain, R.string.ar_pd_bread, Defaults.PD_GB_SIZE,
                Defaults.PD_GB_CARBOHYDRATES, Defaults.PD_GB_PROTEINS, Defaults.PD_GB_FATS));
        products.add(getProduct(context,
                R.string.df_gouda, R.string.ar_pd_dairy, Defaults.PD_CS_SIZE,
                Defaults.PD_CS_CARBOHYDRATES, Defaults.PD_CS_PROTEINS, Defaults.PD_CS_FATS));
        products.add(getProduct(context,
                R.string.df_cow, R.string.ar_pd_dairy, Defaults.PD_CM_SIZE,
                Defaults.PD_CM_CARBOHYDRATES, Defaults.PD_CM_PROTEINS, Defaults.PD_CM_FATS));
        products.add(getProduct(context,
                R.string.df_breast, R.string.ar_pd_meat, Defaults.PD_CB_SIZE,
                Defaults.PD_CB_CARBOHYDRATES, Defaults.PD_CB_PROTEINS, Defaults.PD_CB_FATS));
        products.add(getProduct(context,
                R.string.df_egg, R.string.ar_pd_egg, Defaults.PD_CE_SIZE,
                Defaults.PD_CE_CARBOHYDRATES, Defaults.PD_CE_PROTEINS, Defaults.PD_CE_FATS));
        products.add(getProduct(context,
                R.string.df_lemon, R.string.ar_pd_fruit, Defaults.PD_LM_SIZE,
                Defaults.PD_LM_CARBOHYDRATES, Defaults.PD_LM_PROTEINS, Defaults.PD_LM_FATS));
        products.add(getProduct(context,
                R.string.df_salt, R.string.ar_pd_spice, Defaults.PD_SL_SIZE,
                Defaults.PD_SL_CARBOHYDRATES, Defaults.PD_SL_PROTEINS, Defaults.PD_SL_FATS));
        products.add(getProduct(context,
                R.string.df_pepper, R.string.ar_pd_spice, Defaults.PD_PP_SIZE,
                Defaults.PD_PP_CARBOHYDRATES, Defaults.PD_PP_PROTEINS, Defaults.PD_PP_FATS));
        products.add(getProduct(context,
                R.string.df_ketchup, R.string.ar_pd_sauce, Defaults.PD_KT_SIZE,
                Defaults.PD_KT_CARBOHYDRATES, Defaults.PD_KT_PROTEINS, Defaults.PD_KT_FATS));
        products.add(getProduct(context,
                R.string.df_mayonnaise, R.string.ar_pd_sauce, Defaults.PD_MN_SIZE,
                Defaults.PD_MN_CARBOHYDRATES, Defaults.PD_MN_PROTEINS, Defaults.PD_MN_FATS));
        products.add(getProduct(context,
                R.string.df_butter, R.string.ar_pd_fat, Defaults.PD_BT_SIZE,
                Defaults.PD_BT_CARBOHYDRATES, Defaults.PD_BT_PROTEINS, Defaults.PD_BT_FATS));
        products.add(getProduct(context,
                R.string.df_sugar, R.string.ar_pd_spice, Defaults.PD_SG_SIZE,
                Defaults.PD_SG_CARBOHYDRATES, Defaults.PD_SG_PROTEINS, Defaults.PD_SG_FATS));
        products.add(getProduct(context,
                R.string.df_garlic, R.string.ar_pd_vegetable, Defaults.PD_CG_SIZE,
                Defaults.PD_CG_CARBOHYDRATES, Defaults.PD_CG_PROTEINS, Defaults.PD_CG_FATS));
        products.add(getProduct(context,
                R.string.df_red, R.string.ar_pd_vegetable, Defaults.PD_RP_SIZE,
                Defaults.PD_RP_CARBOHYDRATES, Defaults.PD_RP_PROTEINS, Defaults.PD_RP_FATS));
        products.add(getProduct(context,
                R.string.df_juice, R.string.ar_pd_liquid, Defaults.PD_OJ_SIZE,
                Defaults.PD_OJ_CARBOHYDRATES, Defaults.PD_OJ_PROTEINS, Defaults.PD_OJ_FATS));
        products.add(getProduct(context,
                R.string.df_tenderloin, R.string.ar_pd_meat, Defaults.PD_TL_SIZE,
                Defaults.PD_TL_CARBOHYDRATES, Defaults.PD_TL_PROTEINS, Defaults.PD_TL_FATS));
        products.add(getProduct(context,
                R.string.df_pork, R.string.ar_pd_meat, Defaults.PD_MP_SIZE,
                Defaults.PD_MP_CARBOHYDRATES, Defaults.PD_MP_PROTEINS, Defaults.PD_MP_FATS));
        products.add(getProduct(context,
                R.string.df_cod, R.string.ar_pd_fish, Defaults.PD_CD_SIZE,
                Defaults.PD_CD_CARBOHYDRATES, Defaults.PD_CD_PROTEINS, Defaults.PD_CD_FATS));
        products.add(getProduct(context,
                R.string.df_trout, R.string.ar_pd_fish, Defaults.PD_TT_SIZE,
                Defaults.PD_TT_CARBOHYDRATES, Defaults.PD_TT_PROTEINS, Defaults.PD_TT_FATS));
        products.add(getProduct(context,
                R.string.df_parsley, R.string.ar_pd_vegetable, Defaults.PD_PL_SIZE,
                Defaults.PD_PL_CARBOHYDRATES, Defaults.PD_PL_PROTEINS, Defaults.PD_PL_FATS));
        products.add(getProduct(context,
                R.string.df_celery, R.string.ar_pd_vegetable, Defaults.PD_CL_SIZE,
                Defaults.PD_CL_CARBOHYDRATES, Defaults.PD_CL_PROTEINS, Defaults.PD_CL_FATS));
        products.add(getProduct(context,
                R.string.df_wheat, R.string.ar_pd_grain, Defaults.PD_WF_SIZE,
                Defaults.PD_WF_CARBOHYDRATES, Defaults.PD_WF_PROTEINS, Defaults.PD_WF_FATS));
        products.add(getProduct(context,
                R.string.df_rye, R.string.ar_pd_grain, Defaults.PD_RF_SIZE,
                Defaults.PD_RF_CARBOHYDRATES, Defaults.PD_RF_PROTEINS, Defaults.PD_RF_FATS));
        products.add(getProduct(context,
                R.string.df_cream, R.string.ar_pd_dairy, Defaults.PD_18_SIZE,
                Defaults.PD_18_CARBOHYDRATES, Defaults.PD_18_PROTEINS, Defaults.PD_18_FATS));
        products.add(getProduct(context,
                R.string.df_yogurt, R.string.ar_pd_dairy, Defaults.PD_NY_SIZE,
                Defaults.PD_NY_CARBOHYDRATES, Defaults.PD_NY_PROTEINS, Defaults.PD_NY_FATS));
        products.add(getProduct(context,
                R.string.df_buckwheat, R.string.ar_pd_grain, Defaults.PD_BW_SIZE,
                Defaults.PD_BW_CARBOHYDRATES, Defaults.PD_BW_PROTEINS, Defaults.PD_BW_FATS));
        products.add(getProduct(context,
                R.string.df_rice, R.string.ar_pd_grain, Defaults.PD_WR_SIZE,
                Defaults.PD_WR_CARBOHYDRATES, Defaults.PD_WR_PROTEINS, Defaults.PD_WR_FATS));
        products.add(getProduct(context,
                R.string.df_pasta, R.string.ar_pd_pasta, Defaults.PD_PN_SIZE,
                Defaults.PD_PN_CARBOHYDRATES, Defaults.PD_PN_PROTEINS, Defaults.PD_PN_FATS));
        products.add(getProduct(context,
                R.string.df_oil, R.string.ar_pd_fat, Defaults.PD_RO_SIZE,
                Defaults.PD_RO_CARBOHYDRATES, Defaults.PD_RO_PROTEINS, Defaults.PD_RO_FATS));
        products.add(getProduct(context,
                R.string.df_orange, R.string.ar_pd_fruit, Defaults.PD_OG_SIZE,
                Defaults.PD_OG_CARBOHYDRATES, Defaults.PD_OG_PROTEINS, Defaults.PD_OG_FATS));
        products.add(getProduct(context,
                R.string.df_apple, R.string.ar_pd_fruit, Defaults.PD_AL_SIZE,
                Defaults.PD_AL_CARBOHYDRATES, Defaults.PD_AL_PROTEINS, Defaults.PD_AL_FATS));
        products.add(getProduct(context,
                R.string.df_broccoli, R.string.ar_pd_vegetable, Defaults.PD_BC_SIZE,
                Defaults.PD_BC_CARBOHYDRATES, Defaults.PD_BC_PROTEINS, Defaults.PD_BC_FATS));
        products.add(getProduct(context,
                R.string.df_toast, R.string.ar_pd_bread, Defaults.PD_TB_SIZE,
                Defaults.PD_TB_CARBOHYDRATES, Defaults.PD_TB_PROTEINS, Defaults.PD_TB_FATS));
        products.add(getProduct(context,
                R.string.df_spaghetti, R.string.ar_pd_pasta, Defaults.PD_SN_SIZE,
                Defaults.PD_SN_CARBOHYDRATES, Defaults.PD_SN_PROTEINS, Defaults.PD_SN_FATS));
        products.add(getProduct(context,
                R.string.df_tagliatelle, R.string.ar_pd_pasta, Defaults.PD_TG_SIZE,
                Defaults.PD_TG_CARBOHYDRATES, Defaults.PD_TG_PROTEINS, Defaults.PD_TG_FATS));
        products.add(getProduct(context,
                R.string.df_lard, R.string.ar_pd_fat, Defaults.PD_LR_SIZE,
                Defaults.PD_LR_CARBOHYDRATES, Defaults.PD_LR_PROTEINS, Defaults.PD_LR_FATS));
        products.add(getProduct(context,
                R.string.df_corn, R.string.ar_pd_vegetable, Defaults.PD_CN_SIZE,
                Defaults.PD_CN_CARBOHYDRATES, Defaults.PD_CN_PROTEINS, Defaults.PD_CN_FATS));
        products.add(getProduct(context,
                R.string.df_peas, R.string.ar_pd_vegetable, Defaults.PD_PS_SIZE,
                Defaults.PD_PS_CARBOHYDRATES, Defaults.PD_PS_PROTEINS, Defaults.PD_PS_FATS));
        products.add(getProduct(context,
                R.string.df_cucumber, R.string.ar_pd_vegetable, Defaults.PD_CC_SIZE,
                Defaults.PD_CC_CARBOHYDRATES, Defaults.PD_CC_PROTEINS, Defaults.PD_CC_FATS));
        products.add(getProduct(context,
                R.string.df_sweet, R.string.ar_pd_spice, Defaults.PD_SP_SIZE,
                Defaults.PD_SP_CARBOHYDRATES, Defaults.PD_SP_PROTEINS, Defaults.PD_SP_FATS));
        products.add(getProduct(context,
                R.string.df_hot, R.string.ar_pd_spice, Defaults.PD_HP_SIZE,
                Defaults.PD_HP_CARBOHYDRATES, Defaults.PD_HP_PROTEINS, Defaults.PD_HP_FATS));
        products.add(getProduct(context,
                R.string.df_gyros, R.string.ar_pd_spice, Defaults.PD_GR_SIZE,
                Defaults.PD_GR_CARBOHYDRATES, Defaults.PD_GR_PROTEINS, Defaults.PD_GR_FATS));
        products.add(getProduct(context,
                R.string.df_curry, R.string.ar_pd_spice, Defaults.PD_CR_SIZE,
                Defaults.PD_CR_CARBOHYDRATES, Defaults.PD_CR_PROTEINS, Defaults.PD_CR_FATS));
        products.add(getProduct(context,
                R.string.df_mustard, R.string.ar_pd_sauce, Defaults.PD_MT_SIZE,
                Defaults.PD_MT_CARBOHYDRATES, Defaults.PD_MT_PROTEINS, Defaults.PD_MT_FATS));
        products.add(getProduct(context,
                R.string.df_shrimp, R.string.ar_pd_seafood, Defaults.PD_SR_SIZE,
                Defaults.PD_SR_CARBOHYDRATES, Defaults.PD_SR_PROTEINS, Defaults.PD_SR_FATS));
        products.add(getProduct(context,
                R.string.df_squid, R.string.ar_pd_seafood, Defaults.PD_SQ_SIZE,
                Defaults.PD_SQ_CARBOHYDRATES, Defaults.PD_SQ_PROTEINS, Defaults.PD_SQ_FATS));
        return products;
    }
}
