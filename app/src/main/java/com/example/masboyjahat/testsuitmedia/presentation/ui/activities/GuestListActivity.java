package com.example.masboyjahat.testsuitmedia.presentation.ui.activities;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.GridView;

import com.example.masboyjahat.testsuitmedia.R;
import com.example.masboyjahat.testsuitmedia.domain.executor.impl.ThreadExecutor;
import com.example.masboyjahat.testsuitmedia.domain.model.GuestModel;
import com.example.masboyjahat.testsuitmedia.network.GuestRepositoryImpl;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.GuestPresenter;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.impl.GuestPresenterImpl;
import com.example.masboyjahat.testsuitmedia.presentation.ui.adapters.GuestAdapter;
import com.example.masboyjahat.testsuitmedia.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuestListActivity extends AppCompatActivity implements GuestPresenter.View {

    private GuestAdapter guestAdapter;
    private GuestPresenter guestPresenter;
    private ProgressDialog progress;

    @BindView(R.id.gvGuestList)
    GridView gridView;

    @BindView(R.id.srGuest)
    SwipeRefreshLayout srGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_guest_list);
        ButterKnife.bind(GuestListActivity.this);
        progress = new ProgressDialog(this);


        guestAdapter = new GuestAdapter(this);
        gridView.setAdapter(guestAdapter);
        guestPresenter = new GuestPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this
        );

        srGuest.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                guestPresenter.getAllGuest();
                srGuest.setRefreshing(false);
            }
        });
        showProgress();
        guestPresenter.getAllGuest();

    }

    @Override
    protected void onStart() {
        super.onStart();

        guestAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAllGuest(List<GuestModel> guestModelList) {
        guestAdapter.fillGuestList(guestModelList);

        hideProgress();
    }

    @Override
    public void noData() {

    }



    @Override
    public void showProgress() {
        progress.setMessage(getString(R.string.loading_message));
        progress.show();
    }

    @Override
    public void hideProgress() {
        progress.dismiss();

    }
    @Override
    public void showError(String message) {

    }
}
