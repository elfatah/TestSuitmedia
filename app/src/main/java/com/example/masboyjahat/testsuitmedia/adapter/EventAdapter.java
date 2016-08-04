package com.example.masboyjahat.testsuitmedia.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.masboyjahat.testsuitmedia.R;
import com.example.masboyjahat.testsuitmedia.VolleySingleton;
import com.example.masboyjahat.testsuitmedia.models.EventModel;

import java.util.List;

/**
 * Created by masboy jahat on 8/4/2016.
 */
public class EventAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<EventModel> eventModelList;

    ImageLoader imageLoader = VolleySingleton.getInstance().getImageLoader();

    public EventAdapter(Activity activity, List<EventModel> eventModelList){
        this.activity = activity;
        this.eventModelList = eventModelList;
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
            view = layoutInflater.inflate(R.layout.list_events, null);

        if (imageLoader == null)
            imageLoader = VolleySingleton.getInstance().getImageLoader();

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
}
