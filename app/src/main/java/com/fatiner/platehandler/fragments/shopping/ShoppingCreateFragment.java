package com.fatiner.platehandler.fragments.shopping;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.recyclerview.ShoppingAddAdapter;
import com.fatiner.platehandler.fragments.recipe.manage.RecipeManageIngredientFragment;
import com.fatiner.platehandler.items.ShoppingItem;
import com.fatiner.platehandler.items.ShoppingList;
import com.fatiner.platehandler.details.ShoppingListDetails;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.fragments.recipe.RecipeChooseFragment;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.globals.Shared;
import com.fatiner.platehandler.managers.SharedManager;
import com.fatiner.platehandler.managers.TypeManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingCreateFragment extends PrimaryFragment implements
        ShoppingAddAdapter.ShoppingAddListener {

    @BindView(R.id.rv_shopping) RecyclerView rvShopping;

    @OnClick(R.id.bt_add)
    void clickBtAdd() {
        addNewItem();
    }

    @OnClick(R.id.bt_select)
    void clickBtSelect() {
        setFragment(RecipeChooseFragment.getInstance(true));
    }

    @OnClick(R.id.fab_finished)
    void clickFabFinished() {
        hideKeyboard();
        endAction();
    }

    @OnClick(R.id.iv_tt_add)
    void clickIvTtAdd() {
        showDialog(R.string.hd_sp_mg_add, R.string.tt_sp_mg_add);
    }

    @OnClick(R.id.iv_tt_data)
    void clickIvTtData() {
        showDialog(R.string.hd_sp_mg_data, R.string.tt_sp_mg_data);
    }

    public static ShoppingCreateFragment getInstance() {
        return new ShoppingCreateFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_shopping_create, container,
                false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(R.id.it_shopping, R.string.tb_sp_add, false);
        initAction();
    }

    private ShoppingList getShoppingList() {
        return ShoppingListDetails.getShoppingList();
    }

    private void initAction() {
        manageRv();
        addFirstItem();
    }

    private void manageRv() {
        setRv(rvShopping, getManager(Globals.GL_ONE), getAddShoppingItemAdapter());
        changeRvSize(rvShopping);
    }

    private ShoppingAddAdapter getAddShoppingItemAdapter() {
        return new ShoppingAddAdapter(getContext(), getShoppingList().getShoppingItems(),
                this);
    }

    private void addFirstItem() {
        if (isShoppingListEmpty()) addNewItem();
    }

    private void addNewItem() {
        ArrayList<ShoppingItem> shoppingItems = getShoppingList().getShoppingItems();
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItems.add(shoppingItem);
        getAdapter().notifyItemInserted(shoppingItems.size() + Globals.DF_DECREMENT);
        changeRvSize(rvShopping);
    }

    private boolean isShoppingListEmpty() {
        return getShoppingList().getShoppingItems().isEmpty();
    }

    private RecyclerView.Adapter getAdapter() {
        return rvShopping.getAdapter();
    }

    private void setShoppingListInShared() {
        ShoppingList shoppingList = getShoppingList();
        Date currentDate = Calendar.getInstance().getTime();
        shoppingList.setDate(TypeManager.dateToString(currentDate));
        String json = TypeManager.shoppingListToJson(shoppingList);
        SharedManager.setValue(getContext(), Shared.SR_SHOPPING, Shared.KY_LIST, json);
    }

    private void endAction() {
        if (isIncomplete()) showShortToast(R.string.ts_shopping);
        else showDialog(R.string.dg_sp_add, getDialogListener());
    }

    private boolean isIncomplete() {
        return isAmountZero() || isNameEmpty();
    }

    private boolean isAmountZero() {
        List<ShoppingItem> items = getShoppingList().getShoppingItems();
        for (ShoppingItem item : items)
            if (item.getAmount() == Globals.DF_ZERO) return true;
        return false;
    }

    private boolean isNameEmpty() {
        List<ShoppingItem> items = getShoppingList().getShoppingItems();
        for (ShoppingItem item : items)
            if (item.getName().isEmpty()) return true;
        return false;
    }

    private DialogInterface.OnClickListener getDialogListener() {
        return (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    setShoppingListInShared();
                    showShortSnack(R.string.sb_sp_add);
                    popFragment();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };
    }

    @Override
    public void removeItem(int position) {
        ArrayList<ShoppingItem> items = getShoppingList().getShoppingItems();
        if (items.size() == Globals.DF_INCREMENT) {
            showShortToast(R.string.ts_item);
        } else {
            items.remove(position);
            getAdapter().notifyItemRemoved(position);
            changeRvSize(rvShopping);
        }
    }
}
