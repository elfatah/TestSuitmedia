package com.example.masboyjahat.testsuitmedia.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


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

import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity implements IActivity, IActivity.IEventGuest {
    private ProgressDialog progressDialog;
    private List<EventModel> eventModelList = new ArrayList<EventModel>();
    private ListView listView;
    private EventAdapter eventAdapter;
    private MainPresenter mainPresenter;
    private static final String TAG = EventListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        mainPresenter = new MainPresenter(this);
        listView = (ListView) findViewById(R.id.lvListEvent);
        eventAdapter = new EventAdapter(this, eventModelList);
        listView.setAdapter(eventAdapter);

        progressDialog = new ProgressDialog(this);
        showProgress();
        populateEventList();
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
        Intent intent = new Intent(EventListActivity.this, ChooseEventActivity.class);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.EVENT_NAME, nama);
        editor.commit();
        startActivity(intent);
        finish();
    }

    @Override
    public void guestCallback(String nama, String birthdate) {

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
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constants.EVENT_SERVER_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        hideProgress();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                EventModel eventModel = new EventModel();
                                eventModel.setNama(jsonObject.getString("name"));
                                eventModel.setTanggal(jsonObject.getString("date"));
                                eventModel.setThumnailUrl(jsonObject.getString("imgUrl"));

                                eventModelList.add(eventModel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        eventAdapter.notifyDataSetChanged();
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
