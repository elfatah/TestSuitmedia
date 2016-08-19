package com.example.masboyjahat.testsuitmedia.network;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.masboyjahat.testsuitmedia.AndroidApplication;
import com.example.masboyjahat.testsuitmedia.domain.model.EventModel;
import com.example.masboyjahat.testsuitmedia.domain.repository.Repository;
import com.example.masboyjahat.testsuitmedia.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by elfatahwashere on 8/15/2016.
 */
public class EventRepositoryImpl implements Repository<EventModel> {
    private Network.Callback<EventModel> eventModelCallback;

public EventRepositoryImpl(Network.Callback<EventModel>eventModelCallback){
    this.eventModelCallback = eventModelCallback;
}

    @Override
    public boolean insert(EventModel model) {
        return false;
    }

    @Override
    public boolean update(EventModel model) {
        return false;
    }

    @Override
    public EventModel get(Object id) {
        return null;
    }

    @Override
    public boolean delete(EventModel model) {
        return false;
    }

    @Override
    public List<EventModel> getAll() {
        final List<EventModel> eventModelList = new ArrayList<>();
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

                        eventModelCallback.onRequestFinish(eventModelList);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Event List",error.getMessage());


            }
        });
        AndroidApplication.getInstance().addToRequestQueue(jsonArrayRequest);
        return eventModelList;
    }

}
