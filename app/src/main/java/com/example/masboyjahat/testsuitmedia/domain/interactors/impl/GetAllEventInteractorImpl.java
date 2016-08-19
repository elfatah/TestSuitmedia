package com.example.masboyjahat.testsuitmedia.domain.interactors.impl;

import android.util.Log;

import com.example.masboyjahat.testsuitmedia.domain.executor.Executor;
import com.example.masboyjahat.testsuitmedia.domain.executor.MainThread;
import com.example.masboyjahat.testsuitmedia.domain.interactors.GetAllObjectInteractor;
import com.example.masboyjahat.testsuitmedia.domain.interactors.base.AbstractInteractor;
import com.example.masboyjahat.testsuitmedia.domain.model.EventModel;
import com.example.masboyjahat.testsuitmedia.domain.repository.Repository;
import com.example.masboyjahat.testsuitmedia.network.EventRepositoryImpl;
import com.example.masboyjahat.testsuitmedia.network.Network;

import java.util.List;

/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p>
 */
public class GetAllEventInteractorImpl extends AbstractInteractor implements Network.Callback {

    private GetAllObjectInteractor.Callback<EventModel> mCallback;
    //    private Repository<EventModel> mRepository;
    private EventRepositoryImpl eventModels;

    public GetAllEventInteractorImpl(Executor threadExecutor,
                                     MainThread mainThread,
                                     GetAllObjectInteractor.Callback<EventModel> callback) {
        super(threadExecutor, mainThread);
        mCallback = callback;
//        mRepository = repository;
    }

    @Override
    public void run() {
//        final List<EventModel> eventModels = mRepository.getAll();

        eventModels = new EventRepositoryImpl(this);
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                eventModels.getAll();
            }
        });


    }

    @Override
    public void onRequestFinish(List objects) {
        mCallback.onObjectListRetrieved(objects);
    }
}
