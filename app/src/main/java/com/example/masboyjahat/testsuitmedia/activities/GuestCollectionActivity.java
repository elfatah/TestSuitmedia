package com.example.masboyjahat.testsuitmedia.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.TimeZone;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private List<GuestModel> guestModelList = new ArrayList<GuestModel>();
    private GridView gridView;
    private GuestAdapter guestAdapter;
    private MainPresenter mainPresenter;
    private static final String TAG = GuestCollectionActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_collection);
        mainPresenter = new MainPresenter(this);
        gridView = (GridView) findViewById(R.id.gvGuestList);
        guestAdapter = new GuestAdapter(this, guestModelList);
        gridView.setAdapter(guestAdapter);

        progressDialog = new ProgressDialog(this);
        showProgress();
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
        progressDialog.setMessage("Silahkan tunggu");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void eventCallback(String nama) {

    }

    @Override
    public void guestCallback(String nama, String birthdate) {
        Intent intent = new Intent(GuestCollectionActivity.this, ChooseEventActivity.class);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.GUEST_NAME, nama);
        editor.commit();

        parseDate(birthdate);

        startActivity(intent);
        finish();
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
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constants.GUEST_SERVER_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        hideProgress();
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
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgress();

            }
        });

        VolleySingleton.getInstance().addToRequestQueue(jsonArrayRequest);
    }
}
