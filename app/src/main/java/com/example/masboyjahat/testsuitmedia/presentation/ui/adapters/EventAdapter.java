package com.example.masboyjahat.testsuitmedia.presentation.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.masboyjahat.testsuitmedia.AndroidApplication;
import com.example.masboyjahat.testsuitmedia.R;
import com.example.masboyjahat.testsuitmedia.domain.model.EventModel;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by masboy jahat on 8/4/2016.
 */
public class EventAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;
    private  List<EventModel> eventModelList;

    private ImageLoader imageLoader = AndroidApplication.getInstance().getImageLoader();

    public EventAdapter(Activity activity){
        this.activity = activity;
        eventModelList = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return eventModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return eventModelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (layoutInflater == null)
            layoutInflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = layoutInflater.inflate(R.layout.list_event_item, null);

        if (imageLoader == null)
            imageLoader = AndroidApplication.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView) view
                .findViewById(R.id.thumbnail);
        TextView tvNamaEvent = (TextView) view.findViewById(R.id.tvNameEvent);
        TextView tvTanggal = (TextView) view.findViewById(R.id.tvTanggalEvent);


        EventModel eventModel = eventModelList.get(i);
        thumbNail.setImageUrl(eventModel.getThumnailUrl(), imageLoader);
        tvNamaEvent.setText(eventModel.getNama());
        tvTanggal.setText(eventModel.getTanggal());

        return view;
    }

    public void fillEventList(@NonNull List<EventModel> eventModels){

        if(this.eventModelList != null){
            this.eventModelList.clear();
        }
        this.eventModelList = eventModels;
        notifyDataSetChanged();

    }


}
