package com.example.masboyjahat.testsuitmedia.domain.interactors;


import com.example.masboyjahat.testsuitmedia.domain.interactors.base.Interactor;

import java.util.List;


public interface GetAllObjectInteractor extends Interactor {

    interface Callback<T> {
        void onObjectListRetrieved(List<T> objects);
        void noData();
        // TODO: Add interactor callback methods here
    }

    interface GuestInteractor{

    }

    // TODO: Add interactor methods here
}
