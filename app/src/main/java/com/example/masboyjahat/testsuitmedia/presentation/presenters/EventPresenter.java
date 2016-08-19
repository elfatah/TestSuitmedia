package com.example.masboyjahat.testsuitmedia.presentation.presenters;

import com.example.masboyjahat.testsuitmedia.domain.model.EventModel;
import com.example.masboyjahat.testsuitmedia.domain.model.GuestModel;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.base.BasePresenter;
import com.example.masboyjahat.testsuitmedia.presentation.ui.BaseView;

import java.util.List;


public interface EventPresenter extends BasePresenter {

    interface View extends BaseView {
        // Yang bakal ditampilkan di UI
        void showAllEvent(List<EventModel> eventModelList);
        void noData();
        // TODO: Add your view methods
    }

    //Method khusus di layer presenter
    void getAllEvents();
    // TODO: Add your presenter methods

}
