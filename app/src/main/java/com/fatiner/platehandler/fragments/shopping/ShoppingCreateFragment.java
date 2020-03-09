package com.fatiner.platehandler.fragments.shopping;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.ShoppingAddAdapter;
import com.fatiner.platehandler.classes.ShoppingItem;
import com.fatiner.platehandler.classes.ShoppingList;
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
import butterknife.OnClick;

public class ShoppingCreateFragment extends PrimaryFragment implements ShoppingAddAdapter.ShoppingAddListener {

    @BindView(R.id.rv_positions) RecyclerView rvPositions;

    @OnClick(R.id.bt_add)
    void clickBtAdd() {
        addNewItem();
    }

    @OnClick(R.id.bt_select)
    void clickBtSelect() {
        setFragment(new RecipeChooseFragment(), true, Globals.BN_BOOL);
    }

    @OnClick(R.id.fab_finished)
    void clickFabFinished() {
        hideKeyboard();
        endAction();
    }

    @OnClick(R.id.iv_tt_add)
    void clickIvTtAdd() {
        showDialog(R.string.hd_sh_item, R.string.tt_sh_item);
    }

    @OnClick(R.id.iv_tt_list)
    void clickIvTtList() {
        showDialog(R.string.hd_sh_list, R.string.tt_sh_list);
    }

    public ShoppingCreateFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_shopping_create, container, false);
        init(this, view, R.id.it_shopping, R.string.tb_sh_add, false);
        initAction();
        return view;
    }

    private ShoppingList getShoppingList() {
        return ShoppingListDetails.getShoppingList();
    }

    private void initAction() {
        manageRv();
        addFirstItem();
    }

    private void manageRv() {
        setRv(rvPositions, getManager(Globals.GL_ONE), getAddShoppingItemAdapter());
        changeRvSize(rvPositions);
    }

    private ShoppingAddAdapter getAddShoppingItemAdapter() {
        return new ShoppingAddAdapter(getContext(), getShoppingList().getShoppingItems(), this);
    }

    private void addFirstItem() {
        if(isShoppingListEmpty()) addNewItem();
    }

    private void addNewItem() {
        ArrayList<ShoppingItem> shoppingItems = getShoppingList().getShoppingItems();
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItems.add(shoppingItem);
        getAdapter().notifyItemInserted(shoppingItems.size() + Globals.DF_DECREMENT);
        changeRvSize(rvPositions);
    }

    private boolean isShoppingListEmpty() {
        return getShoppingList().getShoppingItems().isEmpty();
    }

    private RecyclerView.Adapter getAdapter() {
        return rvPositions.getAdapter();
    }

    private void setShoppingListInShared() {
        ShoppingList shoppingList = getShoppingList();
        Date currentDate = Calendar.getInstance().getTime();
        shoppingList.setDate(TypeManager.dateToString(currentDate));
        String json = TypeManager.shoppingListToJson(shoppingList);
        SharedManager.setValue(getContext(), Shared.SR_SHOPPING, Shared.KY_LIST, json);
    }

    private void endAction() {
        if(isIncomplete()) showShortToast(R.string.ts_shopping);
        else showDialog(R.string.dg_sh_add, getDialogListener());
    }

    private boolean isIncomplete() {
        return isAmountZero() || isNameEmpty();
    }

    private boolean isAmountZero() {
        List<ShoppingItem> items = getShoppingList().getShoppingItems();
        for(ShoppingItem item : items)
            if(item.getAmount() == Globals.DF_ZERO) return true;
        return false;
    }

    private boolean isNameEmpty() {
        List<ShoppingItem> items = getShoppingList().getShoppingItems();
        for(ShoppingItem item : items)
            if(item.getName().isEmpty()) return true;
        return false;
    }

    private DialogInterface.OnClickListener getDialogListener() {
        return (dialog, which) -> {
            switch(which) {
                case DialogInterface.BUTTON_POSITIVE:
                    setShoppingListInShared();
                    showShortSnack(R.string.sb_sh_add);
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
        if(items.size() == Globals.DF_INCREMENT) {
            showShortToast(R.string.ts_item);
        } else {
            items.remove(position);
            getAdapter().notifyItemRemoved(position);
        }
    }
}
