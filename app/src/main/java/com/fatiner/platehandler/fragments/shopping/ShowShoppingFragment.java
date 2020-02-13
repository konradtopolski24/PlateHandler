package com.fatiner.platehandler.fragments.shopping;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.ShoppingAdapter;
import com.fatiner.platehandler.classes.ShoppingItem;
import com.fatiner.platehandler.details.ShoppingListDetails;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.classes.ShoppingList;
import com.fatiner.platehandler.globals.SharedGlobals;
import com.fatiner.platehandler.managers.SharedManager;
import com.fatiner.platehandler.managers.TypeManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowShoppingFragment extends PrimaryFragment implements ShoppingAdapter.OnShoppingListener {

    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.rv_shopping)
    RecyclerView rvShopping;
    @BindView(R.id.cv_info)
    CardView cvInfo;
    @BindView(R.id.cv_list)
    CardView cvList;
    @BindView(R.id.iv_info)
    ImageView ivInfo;
    @BindView(R.id.iv_list)
    ImageView ivList;

    @OnClick(R.id.bt_create)
    public void onClickBtCreate(){
        resetShoppingListDetails();
        setFragment(new CreateShoppingFragment());
    }

    @OnClick(R.id.rv_shopping)
    public void onClickRvShopping(){
        checkState();
    }

    @OnClick(R.id.cv_info_hd)
    public void onClickFbAdd(){
        manageExpandCardView(cvInfo, ivInfo);
    }

    @OnClick(R.id.cv_list_hd)
    public void onClickFaAdd(){
        manageExpandCardView(cvList, ivList);
    }

    public ShowShoppingFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_shopping, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tb_sh_list);
        setMenuItem(MainGlobals.ID_SHOPPING);
        setShoppingListWithJson();
        setRecyclerView(
                rvShopping,
                getLayoutManagerNoScroll(LinearLayoutManager.VERTICAL),
                new ShoppingAdapter(getContext(),
                ShoppingListDetails.getShoppingList().getShoppingItems(), this));
        checkIfRvEmpty(rvShopping, tvEmpty);
        return view;
    }

    private void setShoppingListWithJson(){
        resetShoppingListDetails();
        if(SharedManager.isValueAvailable(getContext(), SharedGlobals.SR_SHOPPING, SharedGlobals.KY_LIST)){
            String json = SharedManager.getString(getContext(), SharedGlobals.SR_SHOPPING, SharedGlobals.KY_LIST);
            ShoppingList shoppingList = TypeManager.jsonToShoppingList(json);
            ShoppingListDetails.getShoppingList().setShoppingItems(
                    shoppingList.getShoppingItems());
            ShoppingListDetails.getShoppingList().setDate(shoppingList.getDate());
            setTv(tvDate, ShoppingListDetails.getShoppingList().getDate());
            checkState();
        } else {
            setTv(tvDate, MainGlobals.SN_DASH);
            setTv(tvState, MainGlobals.SN_DASH);
        }
    }

    private void checkState() {
        int size = ShoppingListDetails.getShoppingList().getShoppingItems().size();
        int checked = getCheckedShopping();
        String state =  checked + MainGlobals.SN_SLASH + size;
        setTv(tvState, state);
    }

    private int getCheckedShopping() {
        int checked = 0;
        ArrayList<ShoppingItem> items = ShoppingListDetails.getShoppingList().getShoppingItems();
        for (ShoppingItem item : items) {
            if (item.isCrossedOut())
                checked++;
        }
        return checked;
    }

    @Override
    public void onClickShoppingItem() {
        checkState();
    }
}
