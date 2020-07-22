package com.fatiner.platehandler.fragments.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.fatiner.platehandler.R;
import com.fatiner.platehandler.adapters.recyclerview.ProductAdapter;
import com.fatiner.platehandler.fragments.primary.PrimaryFragment;
import com.fatiner.platehandler.globals.Db;
import com.fatiner.platehandler.globals.Globals;
import com.fatiner.platehandler.globals.Shared;
import com.fatiner.platehandler.managers.QueryManager;
import com.fatiner.platehandler.models.Product;
import com.fatiner.platehandler.viewmodels.product.ProductChooseViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProductChooseFragment extends PrimaryFragment implements
        ProductAdapter.ProductListener {

    private ProductChooseViewModel viewModel;
    private CompositeDisposable disposables;

    @BindView(R.id.rv_products)
    RecyclerView rvProducts;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    @OnClick(R.id.fab_add)
    void clickFabAdd() {
        resetProductDetails();
        setFragment(ProductManageFragment.getInstance(false, Globals.DF_DECREMENT));
    }

    public static ProductChooseFragment getInstance() {
        return new ProductChooseFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_product_choose, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initOptions(R.id.it_product, R.string.tb_pd_choose, true);
        initViewModelEssentials();
        observeGetProducts();
    }

    private void initViewModelEssentials() {
        viewModel = new ViewModelProvider(this).get(ProductChooseViewModel.class);
        disposables = new CompositeDisposable();
    }

    private ProductAdapter getProductAdapter(List<Product> products) {
        return new ProductAdapter(getContext(), products, this);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pd_choose, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.it_settings) {
            setFragment(ProductSettingsFragment.getInstance());
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private SimpleSQLiteQuery getProductsQuery() {
        String where = QueryManager.getWhere(QueryManager.getPdConditions(getContext()));
        String orderBy = QueryManager.getOrderBy(getContext(), Shared.SR_PRODUCT, Db.CL_PD_NAME);
        return QueryManager.getRowsQuery(Db.TB_PRODUCT, where, orderBy);
    }

    private void observeGetProducts() {
        viewModel.getProducts(getProductsQuery())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getProductObserver());
    }

    private SingleObserver<List<Product>> getProductObserver() {
        return new SingleObserver<List<Product>>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onSuccess(List<Product> products) {
                setRv(rvProducts, getManager(getColumnAmountChoose()), getProductAdapter(products));
                checkIfRvEmpty(rvProducts, tvEmpty);
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(R.string.ts_database);
            }
        };
    }

    @Override
    public void clickProduct(int id) {
        resetProductDetails();
        setFragment(ProductShowFragment.getInstance(id));
    }

    @Override
    public void onStop() {
        super.onStop();
        disposables.clear();
    }
}
