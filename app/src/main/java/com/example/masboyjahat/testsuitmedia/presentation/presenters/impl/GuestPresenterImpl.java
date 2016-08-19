package com.example.masboyjahat.testsuitmedia.presentation.presenters.impl;

import android.util.Log;

import com.example.masboyjahat.testsuitmedia.domain.executor.Executor;
import com.example.masboyjahat.testsuitmedia.domain.executor.MainThread;
import com.example.masboyjahat.testsuitmedia.domain.interactors.GetAllObjectInteractor;
import com.example.masboyjahat.testsuitmedia.domain.interactors.impl.GetAllEventInteractorImpl;
import com.example.masboyjahat.testsuitmedia.domain.interactors.impl.GetAllGuestInteractorImpl;
import com.example.masboyjahat.testsuitmedia.domain.model.EventModel;
import com.example.masboyjahat.testsuitmedia.domain.model.GuestModel;
import com.example.masboyjahat.testsuitmedia.domain.repository.Repository;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.EventPresenter;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.GuestPresenter;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.base.AbstractPresenter;

import java.util.List;

/**
 * Created by dmilicic on 12/13/15.
 */
public class GuestPresenterImpl extends AbstractPresenter implements GuestPresenter,
        GetAllObjectInteractor.Callback<GuestModel> {

    private View mView;
    private Repository<GuestModel> mGuestModelRepository;

    public GuestPresenterImpl(Executor executor,
                              MainThread mainThread,
                              View view) {
        super(executor, mainThread);
        mView = view;
//        mGuestModelRepository = guestModelRepository;
    }

    @Override
    public void resume() {
        getAllGuest();

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onObjectListRetrieved(List<GuestModel> objects) {
        mView.showAllGuest(objects);
    }

    @Override
    public void noData() {
        mView.noData();
    }

    @Override
    public void getAllGuest() {
        GetAllGuestInteractorImpl getAllGuestInteractor = new GetAllGuestInteractorImpl(mExecutor,
                mMainThread,
                this);
        getAllGuestInteractor.execute();
    }

}
