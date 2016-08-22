package com.example.masboyjahat.testsuitmedia.presentation.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.masboyjahat.testsuitmedia.R;
import com.example.masboyjahat.testsuitmedia.domain.executor.impl.ThreadExecutor;
import com.example.masboyjahat.testsuitmedia.domain.model.EventModel;
import com.example.masboyjahat.testsuitmedia.domain.model.GuestModel;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.GuestPresenter;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.impl.GuestPresenterImpl;
import com.example.masboyjahat.testsuitmedia.presentation.ui.adapters.GuestAdapter;
import com.example.masboyjahat.testsuitmedia.threading.MainThreadImpl;
import com.example.masboyjahat.testsuitmedia.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    public void showAllGuest(final List<GuestModel> guestModelList) {
        guestAdapter.fillGuestList(guestModelList);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.GUEST_NAME, guestModelList.get(i).getName());
                editor.commit();

                parseDate(guestModelList.get(i).getBirthdate());
                showAlertDialog(guestModelList.get(i).getBirthdate());

            }
        });
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

    private void parseDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date tanggal = null;
        try {
            tanggal = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tanggal);
        if (calendar.get(Calendar.DAY_OF_MONTH) % 2 == 0 &&
                calendar.get(Calendar.DAY_OF_MONTH) % 3 == 0) {
            Toast.makeText(getApplicationContext(), "IOS", Toast.LENGTH_LONG).show();
        }

        if (calendar.get(Calendar.DAY_OF_MONTH) % 2 == 0) {
            Toast.makeText(getApplicationContext(), "Blackberry", Toast.LENGTH_LONG).show();
        }
        if (calendar.get(Calendar.DAY_OF_MONTH) % 3 == 0) {
            Toast.makeText(getApplicationContext(), "Android", Toast.LENGTH_LONG).show();
        }


    }
    public boolean isPrime(int n) {
        if (n > 2 && (n & 1) == 0)
            return false;
        for (int i = 3; i * i <= n; i += 2)
            if (n % i == 0)
                return false;
        return true;
    }

    public void showAlertDialog(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date tanggal = null;
        try {
            tanggal = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tanggal);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        String message;

        if (isPrime(calendar.get(Calendar.MONTH)+1)) {
            message = "Month is prime";
        } else {
            message = "Month is not prime";
        }
        alertDialogBuilder.setTitle("Alert");
        alertDialogBuilder
                .setMessage(message)
                .setNeutralButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
