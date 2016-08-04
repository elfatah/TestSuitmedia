package com.example.masboyjahat.testsuitmedia.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.masboyjahat.testsuitmedia.models.EventModel;
import com.example.masboyjahat.testsuitmedia.presenters.MainPresenter;
import com.example.masboyjahat.testsuitmedia.util.Constants;
import com.example.masboyjahat.testsuitmedia.R;

public class ChooseEventActivity extends AppCompatActivity implements IActivity, IActivity.IChooseEvent {
    private Button btnEvent, btnGuest;
    private TextView txtNama;
    private String nama = "", namaEvent = "", namaGuest = "";
    private ProgressDialog progress;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_event);
        btnEvent = (Button) findViewById(R.id.btnEvent);
        btnGuest = (Button) findViewById(R.id.btnGuest);
        txtNama = (TextView) findViewById(R.id.txtNama);
        progress = new ProgressDialog(this);
        mainPresenter = new MainPresenter(this);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        nama = sharedPreferences.getString(Constants.USERNAME, "");
        namaEvent = sharedPreferences.getString(Constants.EVENT_NAME, "");
        namaGuest = sharedPreferences.getString(Constants.GUEST_NAME, "");
        txtNama.setText(nama);
        if (!namaEvent.equals("")) {
            btnEvent.setText(namaEvent);
        }
        if (!namaGuest.equals("")) {
            btnGuest.setText(namaGuest);
        }
        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.showGuest();
            }
        });

        btnEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.showEvent();
            }
        });

    }

    @Override
    protected void onDestroy() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progress.setMessage("Silahkan tunggu");
        progress.show();

    }


    @Override
    public void hideProgress() {
        progress.dismiss();
    }

    @Override
    public void showEvent() {
        Intent intent = new Intent(ChooseEventActivity.this, EventListActivity.class);
        startActivity(intent);


    }

    @Override
    public void showGuest() {
        Intent intent = new Intent(ChooseEventActivity.this, GuestCollectionActivity.class);
        startActivity(intent);

    }


}
