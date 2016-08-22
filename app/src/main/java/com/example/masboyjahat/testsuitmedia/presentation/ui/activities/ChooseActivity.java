package com.example.masboyjahat.testsuitmedia.presentation.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.masboyjahat.testsuitmedia.R;
import com.example.masboyjahat.testsuitmedia.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseActivity extends AppCompatActivity {
    @BindView(R.id.btnEvent)
    Button btnEvent;

    @BindView(R.id.btnGuest)
    Button btnGuest;

    @BindView(R.id.txtNama)
    TextView txtNama;

    private ProgressDialog progress;
    private String nama = "", namaEvent = "", namaGuest = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_choose);
        ButterKnife.bind(ChooseActivity.this);
        initUI();

        btnEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseActivity.this, EventListActivity.class);
                startActivity(intent);

            }
        });

        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseActivity.this, GuestListActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

    public void initUI() {
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
    }
}
