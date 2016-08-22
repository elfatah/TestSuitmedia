package com.example.masboyjahat.testsuitmedia.domain.interactors.impl;

import com.example.masboyjahat.testsuitmedia.domain.executor.Executor;
import com.example.masboyjahat.testsuitmedia.domain.executor.MainThread;
import com.example.masboyjahat.testsuitmedia.domain.interactors.GetAllObjectInteractor;
import com.example.masboyjahat.testsuitmedia.domain.interactors.base.AbstractInteractor;
import com.example.masboyjahat.testsuitmedia.domain.model.GuestModel;
import com.example.masboyjahat.testsuitmedia.network.impl.GuestRepositoryImpl;
import com.example.masboyjahat.testsuitmedia.network.Network;

import java.util.List;

/**
 * This is an interactor boilerplate with a reference to a model repository.
 * <p>
 */
public class GetAllGuestInteractorImpl extends AbstractInteractor implements Network.Callback {

    private GetAllObjectInteractor.Callback<GuestModel> mCallback;
//    private Repository<GuestModel> mRepository;
    private GuestRepositoryImpl guestRepository;

    public GetAllGuestInteractorImpl(Executor threadExecutor,
                                     MainThread mainThread,
                                     GetAllObjectInteractor.Callback<GuestModel> callback) {
        super(threadExecutor, mainThread);
        mCallback = callback;
//        mRepository = repository;
    }

    @Override
    public void run() {
//        final List<GuestModel> guestModels = mRepository.getAll();
        guestRepository = new GuestRepositoryImpl(this);
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
//                mCallback.onObjectListRetrieved(guestModels);
                guestRepository.getAll();
            }
        });


    }

    @Override
    public void onRequestFinish(List objects) {
        mCallback.onObjectListRetrieved(objects);
    }
}
