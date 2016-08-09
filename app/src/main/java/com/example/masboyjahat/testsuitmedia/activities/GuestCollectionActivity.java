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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.masboyjahat.testsuitmedia.R;
import com.example.masboyjahat.testsuitmedia.VolleySingleton;
import com.example.masboyjahat.testsuitmedia.adapter.EventAdapter;
import com.example.masboyjahat.testsuitmedia.adapter.GuestAdapter;
import com.example.masboyjahat.testsuitmedia.models.EventModel;
import com.example.masboyjahat.testsuitmedia.models.GuestModel;
import com.example.masboyjahat.testsuitmedia.presenters.MainPresenter;
import com.example.masboyjahat.testsuitmedia.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GuestCollectionActivity extends AppCompatActivity implements IActivity, IActivity.IEventGuest {
    private ProgressDialog progressDialog;
    private List<GuestModel> guestModelList = new ArrayList<>();
    private GridView gridView;
    private GuestAdapter guestAdapter;
    private MainPresenter mainPresenter;
    private static final String TAG = GuestCollectionActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_guest_collection);
        mainPresenter = new MainPresenter(this);
        gridView = (GridView) findViewById(R.id.gvGuestList);
        guestAdapter = new GuestAdapter(this, guestModelList);
        gridView.setAdapter(guestAdapter);

        progressDialog = new ProgressDialog(this);

        populateEventList();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mainPresenter.showGuestName(guestModelList.get(i).getName(), guestModelList.get(i).getBirthdate());

            }
        });
    }


    @Override
    public void showProgress() {
        progressDialog.setMessage(getString(R.string.loading_message));
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
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

    @Override
    public void eventCallback(String nama) {

    }

    @Override
    public void guestCallback(String nama, String birthdate) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.GUEST_NAME, nama);
        editor.commit();

        parseDate(birthdate);
        showAlertDialog(birthdate);

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

    @Override
    public void populateEventList() {
        showProgress();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constants.GUEST_SERVER_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                GuestModel guestModel = new GuestModel();
                                guestModel.setName(jsonObject.getString("name"));
                                guestModel.setBirthdate(jsonObject.getString("birthdate"));
                                guestModel.setImage(R.drawable.guest);

                                guestModelList.add(guestModel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        guestAdapter.notifyDataSetChanged();
                        hideProgress();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                hideProgress();

            }
        });

        VolleySingleton.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    public boolean isPrime(int n) {
        if (n > 2 && (n & 1) == 0)
            return false;
        for (int i = 3; i * i <= n; i += 2)
            if (n % i == 0)
                return false;
        return true;
    }
}
