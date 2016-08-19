package com.example.masboyjahat.testsuitmedia.presentation.presenters.impl;

import android.util.Log;

import com.example.masboyjahat.testsuitmedia.domain.executor.Executor;
import com.example.masboyjahat.testsuitmedia.domain.executor.MainThread;
import com.example.masboyjahat.testsuitmedia.domain.interactors.GetAllObjectInteractor;
import com.example.masboyjahat.testsuitmedia.domain.interactors.impl.GetAllEventInteractorImpl;
import com.example.masboyjahat.testsuitmedia.domain.model.EventModel;
import com.example.masboyjahat.testsuitmedia.domain.repository.Repository;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.base.AbstractPresenter;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.EventPresenter;

import java.util.List;

/**
 * Created by dmilicic on 12/13/15.
 */
public class EventPresenterImpl extends AbstractPresenter implements EventPresenter,
        GetAllObjectInteractor.Callback<EventModel> {

    private EventPresenter.View mView;
    private Repository<EventModel> mEventModelRepository;

    public EventPresenterImpl(Executor executor,
                              MainThread mainThread,
                              View view) {
        super(executor, mainThread);
        mView = view;
//        mEventModelRepository = eventModelRepository;
    }

    @Override
    public void resume() {
        getAllEvents();

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
    public void onObjectListRetrieved(List<EventModel> objects) {
        mView.showAllEvent(objects);
    }

    @Override
    public void noData() {
        mView.noData();
    }

    @Override
    public void getAllEvents() {
        GetAllEventInteractorImpl getAllEvent = new GetAllEventInteractorImpl(mExecutor,
                mMainThread,
                this);
    getAllEvent.execute();
    }

}
