package com.example.masboyjahat.testsuitmedia.network.impl;

import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.masboyjahat.testsuitmedia.AndroidApplication;
import com.example.masboyjahat.testsuitmedia.R;
import com.example.masboyjahat.testsuitmedia.domain.model.EventModel;
import com.example.masboyjahat.testsuitmedia.domain.model.GuestModel;
import com.example.masboyjahat.testsuitmedia.domain.repository.Repository;
import com.example.masboyjahat.testsuitmedia.network.Network;
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
public class GuestRepositoryImpl implements Repository<GuestModel> {
    private Network.Callback<GuestModel> guestModelCallback;


    public GuestRepositoryImpl(Network.Callback<GuestModel> guestModelCallback) {
        this.guestModelCallback = guestModelCallback;
    }


    @Override
    public boolean insert(GuestModel model) {
        return false;
    }

    @Override
    public boolean update(GuestModel model) {
        return false;
    }

    @Override
    public GuestModel get(Object id) {
        return null;
    }

    @Override
    public boolean delete(GuestModel model) {
        return false;
    }

    @Override
    public List<GuestModel> getAll() {
        final List<GuestModel> guestModelList = new ArrayList<>();

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

                                Log.e("Guest", guestModel.getName());
                                guestModelList.add(guestModel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        guestModelCallback.onRequestFinish(guestModelList);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Event List", error.getMessage());

            }

        });

        AndroidApplication.getInstance().addToRequestQueue(jsonArrayRequest);
        return guestModelList;
    }


}
