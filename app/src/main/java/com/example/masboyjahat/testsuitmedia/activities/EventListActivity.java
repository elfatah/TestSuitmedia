package com.example.masboyjahat.testsuitmedia.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.masboyjahat.testsuitmedia.R;
import com.example.masboyjahat.testsuitmedia.VolleySingleton;
import com.example.masboyjahat.testsuitmedia.adapter.EventAdapter;
import com.example.masboyjahat.testsuitmedia.models.EventModel;
import com.example.masboyjahat.testsuitmedia.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity implements IActivity, IActivity.IEventGuest {
    private ProgressDialog progressDialog;
    private List<EventModel> eventModelList = new ArrayList<EventModel>();
    private ListView listView;
    private EventAdapter eventAdapter;
    private static final String TAG = EventListActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        listView = (ListView) findViewById(R.id.lvListEvent);
        eventAdapter = new EventAdapter(this, eventModelList);
        listView.setAdapter(eventAdapter);

        progressDialog = new ProgressDialog(this);
        showProgress();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constants.SERVER_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        hideProgress();

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
}
