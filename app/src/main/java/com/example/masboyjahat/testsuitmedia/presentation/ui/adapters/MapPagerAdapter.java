package com.example.masboyjahat.testsuitmedia.presentation.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.masboyjahat.testsuitmedia.AndroidApplication;
import com.example.masboyjahat.testsuitmedia.R;
import com.example.masboyjahat.testsuitmedia.domain.model.EventModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elfatahwashere on 8/20/2016.
 */

public class MapPagerAdapter extends PagerAdapter {

    private List<EventModel> eventModelList;
    private LayoutInflater inflater;
    private ImageLoader imageLoader = AndroidApplication.getInstance().getImageLoader();
    private Activity activity;
//    private ViewGroup itemView;

    public MapPagerAdapter(Activity activity) {
        this.activity = activity;
        eventModelList = new ArrayList<>();

    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        if (itemView == null)
          ViewGroup  itemView = (ViewGroup) inflater.inflate(R.layout.list_event_item, view, false);
//        if (itemView.getParent() != null)
//            ((ViewGroup) itemView.getParent()).removeView(itemView);
        if (imageLoader == null)
            imageLoader = AndroidApplication.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView) itemView
                .findViewById(R.id.thumbnail);
        TextView tvNamaEvent = (TextView) itemView.findViewById(R.id.tvNameEvent);
        TextView tvTanggal = (TextView) itemView.findViewById(R.id.tvTanggalEvent);

        EventModel eventModel = eventModelList.get(position);

        thumbNail.setImageUrl(eventModel.getThumnailUrl(), imageLoader);
        tvNamaEvent.setText(eventModel.getNama());
        tvTanggal.setText(eventModel.getTanggal());

        view.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return eventModelList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void fillEventList(@NonNull List<EventModel> eventModels) {

        if (this.eventModelList != null) {
            this.eventModelList.clear();
        }
        this.eventModelList = eventModels;
        notifyDataSetChanged();

    }

}

