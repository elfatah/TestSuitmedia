package com.example.masboyjahat.testsuitmedia.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.masboyjahat.testsuitmedia.R;
import com.example.masboyjahat.testsuitmedia.VolleySingleton;
import com.example.masboyjahat.testsuitmedia.adapter.EventAdapter;
import com.example.masboyjahat.testsuitmedia.models.EventModel;
import com.example.masboyjahat.testsuitmedia.presenters.MainPresenter;
import com.example.masboyjahat.testsuitmedia.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventListActivity extends AppCompatActivity implements IActivity, IActivity.IEventGuest {
    private static final String TAG = EventListActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private List<EventModel> eventModelList = new ArrayList<>();
    private ListView listView;
    private EventAdapter eventAdapter;
    private MainPresenter mainPresenter;
    private SwipeRefreshLayout srEvent;
    private ImageButton ibEventBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_event_list);
        mainPresenter = new MainPresenter(this);
        listView = (ListView) findViewById(R.id.lvListEvent);
        srEvent = (SwipeRefreshLayout) findViewById(R.id.srEvent);
        ibEventBack = (ImageButton)findViewById(R.id.ibEventBack);
        eventAdapter = new EventAdapter(this, eventModelList);
        listView.setAdapter(eventAdapter);
        ibEventBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressDialog = new ProgressDialog(this);
        showProgress();
        populateEventList();
        srEvent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                populateEventList();
                srEvent.setRefreshing(false);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mainPresenter.showEventName(eventModelList.get(i).getNama());
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
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.EVENT_NAME, nama);
        editor.commit();
        finish();
    }

    @Override
    public void guestCallback(String nama, String birthdate) {

    }

    @Override
    public void showAlertDialog(String date) {

    }

    @Override
    protected void onStop() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        super.onStop();
    }

    @Override
    public void populateEventList() {
        eventModelList.clear();
        eventAdapter.notifyDataSetChanged();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constants.EVENT_SERVER_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                EventModel eventModel = new EventModel();
                                eventModel.setNama(jsonObject.getString("name"));
                                Date date = new Date(jsonObject.getString("date").replace('-','/'));
                                SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");

                                eventModel.setTanggal(formatter.format(date));
                                eventModel.setThumnailUrl(jsonObject.getString("imgUrl"));

                                eventModelList.add(eventModel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        eventAdapter.notifyDataSetChanged();
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
}
