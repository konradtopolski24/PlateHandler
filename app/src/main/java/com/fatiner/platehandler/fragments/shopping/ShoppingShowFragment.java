package com.fatiner.platehandler.fragments.shopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.recyclerview.ShoppingAdapter;
import com.fatiner.platehandler.items.ShoppingItem;
import com.fatiner.platehandler.items.ShoppingList;
import com.fatiner.platehandler.details.ShoppingListDetails;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.globals.Format;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.globals.Shared;
import com.fatiner.platehandler.managers.SharedManager;
import com.fatiner.platehandler.managers.TypeManager;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class ShoppingShowFragment extends PrimaryFragment implements
        ShoppingAdapter.ShoppingListener {

    @BindView(R.id.cv_data) CardView cvData;
    @BindView(R.id.cv_list) CardView cvList;
    @BindView(R.id.iv_hd_data) ImageView ivHdData;
    @BindView(R.id.iv_hd_list) ImageView ivHdList;
    @BindView(R.id.tv_date) TextView tvDate;
    @BindView(R.id.tv_state) TextView tvState;
    @BindView(R.id.tv_empty) TextView tvEmpty;
    @BindView(R.id.rv_shopping) RecyclerView rvShopping;

    @OnClick(R.id.bt_create)
    void clickBtCreate() {
        resetShoppingListDetails();
        setFragment(new ShoppingCreateFragment());
    }

    @OnClick(R.id.rv_shopping)
    void clickRvShopping() {
        checkState();
    }

    @OnClick(R.id.cv_hd_data)
    void clickCvHdData() {
        manageExpandCv(cvData, ivHdData);
    }

    @OnClick(R.id.cv_hd_list)
    void clickCvHdList() {
        manageExpandCv(cvList, ivHdList);
    }

    @OnClick(R.id.iv_tt_data)
    void clickIvTtData() {
        showDialog(R.string.hd_sp_sh_data, R.string.tt_sp_sh_data);
    }

    @OnClick(R.id.iv_tt_list)
    void clickIvTtList() {
        showDialog(R.string.hd_sp_sh_list, R.string.tt_sp_sh_list);
    }

    public ShoppingShowFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_shopping_show, container,
                false);
        init(this, view, R.id.it_shopping, R.string.tb_sp_show, false);
        initAction();
        return view;
    }

    private ShoppingList getShoppingList() {
        return ShoppingListDetails.getShoppingList();
    }

    private void initAction() {
        resetShoppingListDetails();
        checkShared();
        setViews();
    }

    private void checkShared() {
        if (isSharedList()) setShoppingDetails();
    }

    private boolean isSharedList() {
        return SharedManager.isValueAvailable(getContext(), Shared.SR_SHOPPING, Shared.KY_LIST);
    }

    private void setViews() {
        if (getShoppingList().getShoppingItems().isEmpty()) listEmptyAction();
        else listExistsAction();
        manageRv();
    }

    private void listExistsAction() {
        setTv(tvDate, getShoppingList().getDate());
        checkState();
    }

    private void listEmptyAction() {
        setTv(tvDate, Globals.SN_DASH);
        setTv(tvState, Globals.SN_DASH);
    }

    private void setShoppingDetails() {
        ShoppingList list = getListFromJson();
        getShoppingList().setShoppingItems(list.getShoppingItems());
        getShoppingList().setDate(list.getDate());
    }

    private ShoppingList getListFromJson() {
        String json = SharedManager.getString(getContext(), Shared.SR_SHOPPING, Shared.KY_LIST);
        return TypeManager.jsonToShoppingList(json);
    }

    private void manageRv() {
        setRv(rvShopping, getManager(Globals.GL_ONE), getShoppingAdapter());
        changeRvSize(rvShopping);
        checkIfRvEmpty(rvShopping, tvEmpty);
    }

    private ShoppingAdapter getShoppingAdapter() {
        return new ShoppingAdapter(getContext(),
                getShoppingList().getShoppingItems(), this);
    }

    private int getCheckedAmount() {
        int checked = Globals.DF_ZERO;
        ArrayList<ShoppingItem> items = getShoppingList().getShoppingItems();
        for (ShoppingItem item : items) if (item.isCrossedOut()) checked += Globals.DF_INCREMENT;
        return checked;
    }

    private void checkState() {
        int size = getShoppingList().getShoppingItems().size();
        int checked = getCheckedAmount();
        String state =  String.format(Locale.ENGLISH, Format.FM_STATE, checked, size);
        setTv(tvState, state);
    }

    private void saveShoppingList() {
        String json = TypeManager.shoppingListToJson(getShoppingList());
        SharedManager.setValue(getContext(), Shared.SR_SHOPPING, Shared.KY_LIST, json);
    }

    @Override
    public void clickItem() {
        saveShoppingList();
        checkState();
    }
}
