package com.fatiner.platehandler.fragments.shopping;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.AddShoppingItemAdapter;
import com.fatiner.platehandler.classes.ShoppingItem;
import com.fatiner.platehandler.classes.ShoppingList;
import com.fatiner.platehandler.details.ShoppingListDetails;
import com.fatiner.platehandler.fragments.PrimaryFragment;
import com.fatiner.platehandler.fragments.recipe.ChooseRecipeFragment;
import com.fatiner.platehandler.globals.BundleGlobals;
import com.fatiner.platehandler.globals.MainGlobals;
import com.fatiner.platehandler.managers.TypeManager;
import com.fatiner.platehandler.managers.shared.SharedShoppingManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateShoppingFragment extends PrimaryFragment {

    @BindView(R.id.rv_positions)
    RecyclerView rvPositions;

    @OnClick(R.id.bt_add)
    public void onClickBtAdd(){
        addNewItem();
    }

    @OnClick(R.id.bt_select)
    public void onClickBtSelect(){
        Fragment fragment = new ChooseRecipeFragment();
        setBoolInBundle(fragment,true, BundleGlobals.BUND_BOOL_FRAG_RECIPES);
        setFragment(fragment);
    }

    @OnClick(R.id.fab_finished)
    public void onClickFabDone(){
        hideKeyboard();
        if(ShoppingListDetails.isShoppingListCorrect()){
            showAlertDialog(R.string.dg_sh_add, getDialogListener());
        } else {
            showShortToast(R.string.ts_item);
        }
    }

    public CreateShoppingFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_shopping, container, false);
        ButterKnife.bind(this, view);
        setToolbarTitle(R.string.tb_sh_add);
        setMenuItem(MainGlobals.ID_SHOPPING_DRAW_MAIN);
        setRecyclerView(
                rvPositions,
                getLayoutManagerNoScroll(LinearLayoutManager.VERTICAL),
                new AddShoppingItemAdapter(getContext(),
                        ShoppingListDetails.getShoppingList().getShoppingItems()));
        addNewItemFirstTime();
        return view;
    }

    private void addNewItemFirstTime(){
        if(isShoppingListEmpty()){
            addNewItem();
        }
    }

    private void addNewItem(){
        ArrayList<ShoppingItem> shoppingItems =
                ShoppingListDetails.getShoppingList().getShoppingItems();
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItems.add(shoppingItem);
        rvPositions.getAdapter().notifyItemInserted(
                shoppingItems.size() + MainGlobals.INT_DECREMENT_VAR_INIT);
    }

    private boolean isShoppingListEmpty(){
        return ShoppingListDetails.getShoppingList().getShoppingItems().isEmpty();
    }

    private void setShoppingListInShared(){
        ShoppingList shoppingList = ShoppingListDetails.getShoppingList();
        Date currentDate = Calendar.getInstance().getTime();
        shoppingList.setDate(TypeManager.dateToString(currentDate));
        String json = TypeManager.shoppingListToJson(shoppingList);
        SharedShoppingManager.setSharedList(getContext(), json);
    }

    private DialogInterface.OnClickListener getDialogListener(){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        setShoppingListInShared();
                        showShortSnack(R.string.sb_sh_add);
                        popFragment();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
    }
}
