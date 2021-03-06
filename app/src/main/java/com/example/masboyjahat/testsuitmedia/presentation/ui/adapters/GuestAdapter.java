package com.example.masboyjahat.testsuitmedia.presentation.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.masboyjahat.testsuitmedia.R;
import com.example.masboyjahat.testsuitmedia.domain.model.GuestModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by masboy jahat on 8/4/2016.
 */
public class GuestAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<GuestModel> guestModelList;


    public GuestAdapter(Activity activity){
        this.activity = activity;
        guestModelList = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return guestModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return guestModelList.get(i);
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
            view = layoutInflater.inflate(R.layout.list_guest_item, null);



        ImageView thumbNail = (ImageView) view
                .findViewById(R.id.ivGuest);
        TextView tvNamaGuest = (TextView) view.findViewById(R.id.tvGuestName);



        GuestModel guestModel = guestModelList.get(i);
        thumbNail.setImageResource(guestModel.getImage());
        tvNamaGuest.setText(guestModel.getName());


        return view;
    }

    public void fillGuestList(@NonNull List<GuestModel> guestModels){
        if(this.guestModelList != null){
            this.guestModelList.clear();
        }
        this.guestModelList= guestModels;
        notifyDataSetChanged();

    }
}
