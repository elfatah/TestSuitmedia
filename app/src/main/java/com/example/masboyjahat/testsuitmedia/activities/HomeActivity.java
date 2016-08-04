package com.example.masboyjahat.testsuitmedia.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.masboyjahat.testsuitmedia.util.Constants;
import com.example.masboyjahat.testsuitmedia.R;
import com.example.masboyjahat.testsuitmedia.presenters.MainPresenter;
import com.example.masboyjahat.testsuitmedia.util.Dummy;

public class HomeActivity extends AppCompatActivity implements IActivity, IActivity.IHome {
    private ProgressDialog progress;
    private Button btnNext;
    private EditText etNama;
    private MainPresenter mainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mainPresenter = new MainPresenter(this);
        progress = new ProgressDialog(this);
        Log.d("JSON", Dummy.jsonEvent);
        btnNext = (Button) findViewById(R.id.btnNext);
        etNama = (EditText) findViewById(R.id.etNama);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainPresenter.showChooseEventActivity(etNama.getText().toString());
            }
        });
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
    public void showChooseEvent(String name) {
        showProgress();
        Intent intent = new Intent(HomeActivity.this, ChooseEventActivity.class);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.USERNAME, name);
        editor.commit();
        startActivity(intent);
        hideProgress();

    }


}
