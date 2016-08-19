package com.example.masboyjahat.testsuitmedia.presentation.ui.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.masboyjahat.testsuitmedia.R;
import com.example.masboyjahat.testsuitmedia.domain.executor.impl.ThreadExecutor;
import com.example.masboyjahat.testsuitmedia.domain.model.EventModel;
import com.example.masboyjahat.testsuitmedia.domain.model.LoginModel;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.EventPresenter;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.EventPresenter.View;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.LoginPresenter;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.impl.LoginPresenterImpl;
import com.example.masboyjahat.testsuitmedia.presentation.ui.BaseView;
import com.example.masboyjahat.testsuitmedia.threading.MainThreadImpl;
import com.example.masboyjahat.testsuitmedia.utils.Constants;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoginPresenter.View {

    @BindView(R.id.btnNext)
    Button btnNext;

    @BindView(R.id.etNama)
    EditText etNama;

    private ProgressDialog progress;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        ButterKnife.bind(MainActivity.this);

        progress = new ProgressDialog(this);
        loginPresenter = new LoginPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);

        btnNext.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                loginPresenter.isPalindrome(etNama.getText().toString());
            }
        });

    }


    @Override
    public void nameChecked(boolean b) {
        showAlertDialog(b);
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
                        showProgress();

                        showChooseEvent(etNama.getText().toString());
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showChooseEvent(String name) {
        Intent intent = new Intent(MainActivity.this, ChooseActivity.class);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.USERNAME, name);
        editor.commit();
        startActivity(intent);

        hideProgress();

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
