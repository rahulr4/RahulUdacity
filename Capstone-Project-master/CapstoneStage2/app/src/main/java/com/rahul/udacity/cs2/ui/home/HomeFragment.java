package com.rahul.udacity.cs2.ui.home;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.app.qatarcool.R;
import com.app.qatarcool.base.ApplicationController;
import com.app.qatarcool.base.BaseFragment;
import com.app.qatarcool.database.ConnectionsDao;
import com.app.qatarcool.database.PortfolioDao;
import com.app.qatarcool.model.Connections;
import com.app.qatarcool.model.Portfolio;
import com.app.qatarcool.model.RequestBean;
import com.app.qatarcool.model.events.ApiRefreshEvent;
import com.app.qatarcool.ui.bills.BillsActivity;
import com.app.qatarcool.ui.customersupport.CustomerSupportActivity;
import com.app.qatarcool.ui.myconnections.MyConnectionsActivity;
import com.app.qatarcool.ui.myconsumptions.MyConsumptionsActivity;
import com.app.qatarcool.ui.notifications.NotificationsActivity;
import com.app.qatarcool.utils.Lg;
import com.app.qatarcool.utils.Utility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by rahulgupta on 16/11/16.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener, HomeView {
    private String TAG = HomeFragment.class.getSimpleName();
    private TextView mPropertyTv, mConnectionIdTv, mDueAmountTv;
    private View mPayNowTv;
    private HomePresenter homePresenter;

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMain(ApiRefreshEvent apiRefreshEvent) {
        if (!ApplicationController.getApplicationInstance().isNetworkConnected())
            Utility.showSnackBar(mContext, R.string.no_internet_connection);
        try {
            Portfolio portfolio1 = PortfolioDao.getInstance(mContext).getSelectedPortfolio();
            Connections connections = ConnectionsDao.getInstance(mContext).getConnection(portfolio1.getId());

            if (!TextUtils.isEmpty(connections.getExternalConnectionId()))
                mPropertyTv.setText(connections.getExternalConnectionId());

            if (!TextUtils.isEmpty(connections.getConnectionId()))
                mConnectionIdTv.setText(connections.getConnectionId());

            mDueAmountTv.setText(getString(R.string.lbl_qar) + " " + connections.getOpenAmount());
            if (connections.getOpenAmount() <= 0) {
                mPayNowTv.setVisibility(View.GONE);
            } else
                mPayNowTv.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Lg.i(TAG, "Data not available :- " + e.getMessage());
        }
    }

    @Override
    protected void initUi() {
        homePresenter = new HomePresenter(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        findViewById(R.id.my_connections_tv).setOnClickListener(this);
        findViewById(R.id.my_consumption_tv).setOnClickListener(this);
        findViewById(R.id.my_bills_tv).setOnClickListener(this);
        findViewById(R.id.customer_support_tv).setOnClickListener(this);
        mPayNowTv = findViewById(R.id.pay_now_tv);
        mPayNowTv.setOnClickListener(this);

        mPropertyTv = (TextView) findViewById(R.id.property_id_tv);
        mConnectionIdTv = (TextView) findViewById(R.id.connection_id_tv);
        mDueAmountTv = (TextView) findViewById(R.id.due_amount_tv);

        onEventMain(null);

        homePresenter.showFeedbackDialog();
    }

    @Override
    protected int getLayoutById() {
        return R.layout.fragment_home;
    }

    @Override
    public void onMenuItemClick(MenuItem item) {
        super.onMenuItemClick(item);
        switch (item.getItemId()) {
            case R.id.menu_notification:
                Intent intent1 = new Intent(getActivity(), NotificationsActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_consumption_tv:
//                Utility.showSnackBar(getActivity(),getString(R.string.feature_coming_soon));
                Intent intentS = new Intent(mContext, MyConsumptionsActivity.class);
                getActivity().startActivity(intentS);
                break;
            case R.id.my_bills_tv:
//                Utility.showSnackBar(getActivity(),getString(R.string.feature_coming_soon));
                Intent intentSs = new Intent(mContext, BillsActivity.class);
                startActivity(intentSs);
                break;
            case R.id.my_connections_tv:
                if (PortfolioDao.getInstance(getActivity()).isSelected()) {
                    Intent intent = new Intent(getContext(), MyConnectionsActivity.class);
                    startActivity(intent);
                } else {
                    Utility.showSnackBar(getActivity(), getString(R.string.no_connections_text));
                }
                break;
            case R.id.customer_support_tv:
                Intent intent1 = new Intent(getContext(), CustomerSupportActivity.class);
                startActivity(intent1);
                break;
            case R.id.pay_now_tv:
                Portfolio portfolio1 = PortfolioDao.getInstance(mContext).getSelectedPortfolio();
                Connections connections = ConnectionsDao.getInstance(mContext).getConnection(portfolio1.getId());

                RequestBean requestBean = new RequestBean();
                requestBean.setAmount(connections.getOpenAmount() + "");
                homePresenter.payNowApi(requestBean);
                break;
        }

    }

    @Override
    public void onNavigationDrawerItemSelected(NavDrawerEnum navDrawerEnum) {

    }

    @Override
    public Context getViewContext() {
        return mContext;
    }

    @Override
    public void showProgress(boolean showProgress) {
        showProgressDialog(showProgress);
    }
}
