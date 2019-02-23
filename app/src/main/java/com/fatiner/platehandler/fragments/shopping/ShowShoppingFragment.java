package com.fatiner.platehandler.fragments.shopping;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.ShoppingAdapter;
import com.fatiner.platehandler.details.ShoppingListDetails;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.classes.ShoppingList;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.managers.shared.SharedShoppingManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowShoppingFragment extends PrimaryFragment {

    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.rv_shopping)
    RecyclerView rvShopping;

    @OnClick(R.id.bt_create)
    public void onClickBtCreate(){
        resetShoppingListDetails();
        setFragment(new CreateShoppingFragment());
    }

    public ShowShoppingFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_shopping, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tb_shopping_list);
        setMenuItem(MainGlobals.ID_SHOPPING_DRAW_MAIN);
        setShoppingListWithJson();
        setRecyclerView(
                rvShopping,
                getGridLayoutManager(MainGlobals.RECYC_SPAN_FRAG_SHOPPING),
                new ShoppingAdapter(getContext(),
                ShoppingListDetails.getShoppingList().getShoppingItems()));
        return view;
    }

    private void setShoppingListWithJson(){
        resetShoppingListDetails();
        if(SharedShoppingManager.isSharedListAvailable(getContext())){
            String json = SharedShoppingManager.getSharedList(getContext());
            ShoppingList shoppingList = TypeManager.jsonToShoppingList(json);
            ShoppingListDetails.getShoppingList().setShoppingItems(
                    shoppingList.getShoppingItems());
            ShoppingListDetails.getShoppingList().setDate(shoppingList.getDate());
            setTvDate(ShoppingListDetails.getShoppingList().getDate());
        }
    }

    private void setTvDate(String date){
        tvDate.setText(date);
    }
}
