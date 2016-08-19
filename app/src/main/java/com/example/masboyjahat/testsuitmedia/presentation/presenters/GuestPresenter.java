package com.example.masboyjahat.testsuitmedia.presentation.presenters;

import com.example.masboyjahat.testsuitmedia.domain.model.EventModel;
import com.example.masboyjahat.testsuitmedia.domain.model.GuestModel;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.base.BasePresenter;
import com.example.masboyjahat.testsuitmedia.presentation.ui.BaseView;

import java.util.List;


public interface GuestPresenter extends BasePresenter {

    interface View extends BaseView {

        void showAllGuest(List<GuestModel> guestModelList);

        void noData();
        // TODO: Add your view methods
    }

    void getAllGuest();
    // TODO: Add your presenter methods

}
