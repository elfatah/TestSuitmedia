package com.example.masboyjahat.testsuitmedia.presentation.ui.activities;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.masboyjahat.testsuitmedia.R;
import com.example.masboyjahat.testsuitmedia.domain.executor.impl.ThreadExecutor;
import com.example.masboyjahat.testsuitmedia.domain.model.EventModel;
import com.example.masboyjahat.testsuitmedia.network.EventRepositoryImpl;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.EventPresenter;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.impl.EventPresenterImpl;
import com.example.masboyjahat.testsuitmedia.presentation.ui.adapters.EventAdapter;
import com.example.masboyjahat.testsuitmedia.threading.MainThreadImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventListActivity extends AppCompatActivity implements EventPresenter.View {
    private EventPresenter eventPresenter;
    private EventAdapter eventAdapter;
    private ProgressDialog progress;

    @BindView(R.id.lvListEvent)
    ListView listView;

    @BindView(R.id.ibEventBack)
    ImageButton ibEventBack;

    @BindView(R.id.srEvent)
    SwipeRefreshLayout srEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_event_list);

        ButterKnife.bind(EventListActivity.this);

        progress = new ProgressDialog(this);

        eventAdapter = new EventAdapter(this);
        listView.setAdapter(eventAdapter);
        eventPresenter = new EventPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this);


        ibEventBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        srEvent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                eventPresenter.getAllEvents();
                srEvent.setRefreshing(false);
            }
        });
        showProgress();
        eventPresenter.getAllEvents();


    }

    @Override
    public void showAllEvent(List<EventModel> eventModelList) {

        eventAdapter.fillEventList(eventModelList);

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
