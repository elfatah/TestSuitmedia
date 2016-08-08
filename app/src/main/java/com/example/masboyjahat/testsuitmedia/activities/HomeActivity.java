package com.example.masboyjahat.testsuitmedia.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.masboyjahat.testsuitmedia.util.Constants;
import com.example.masboyjahat.testsuitmedia.R;
import com.example.masboyjahat.testsuitmedia.presenters.MainPresenter;

public class HomeActivity extends AppCompatActivity implements IActivity, IActivity.IHome {
    private ProgressDialog progress;
    private Button btnNext;
    private EditText etNama;
    private MainPresenter mainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        mainPresenter = new MainPresenter(this);
        progress = new ProgressDialog(this);
        btnNext = (Button) findViewById(R.id.btnNext);
        etNama = (EditText) findViewById(R.id.etNama);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(isPalindrome(etNama.getText().toString()));
            }
        });
    }

    @Override
    public void showProgress() {

        progress.setMessage(getString(R.string.loading_message));
        progress.show();

    }

    @Override
    protected void onDestroy() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        super.onDestroy();
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

    @Override
    public Boolean isPalindrome(String text) {
        int size = text.length();
        String awal, akhir;
        int middle = ((int) Math.floor(size / 2));
        awal = text.substring(0, middle);
        if (size % 2 == 0) {
            akhir = text.substring(middle);
        } else {
            akhir = text.substring(middle + 1);
        }
        StringBuffer a = new StringBuffer(akhir);
        Log.e("split", awal + "-" + akhir);
        return awal.equals(a.reverse().toString());
    }

    @Override
    public void showAlertDialog(boolean isPalindrome) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        String message;
        if (isPalindrome) {
            message = "is Palindrome";
        } else {
            message = "not Palindrome";
        }
        alertDialogBuilder.setTitle("Alert");
        alertDialogBuilder
                .setMessage(message)
                .setNeutralButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mainPresenter.showChooseEventActivity(etNama.getText().toString());

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
