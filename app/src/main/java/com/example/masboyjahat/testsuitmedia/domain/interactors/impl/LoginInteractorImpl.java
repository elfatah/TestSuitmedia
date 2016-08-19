package com.example.masboyjahat.testsuitmedia.domain.interactors.impl;

import android.util.Log;

import com.example.masboyjahat.testsuitmedia.domain.executor.Executor;
import com.example.masboyjahat.testsuitmedia.domain.executor.MainThread;
import com.example.masboyjahat.testsuitmedia.domain.interactors.LoginInteractor;
import com.example.masboyjahat.testsuitmedia.domain.interactors.base.AbstractInteractor;
import com.example.masboyjahat.testsuitmedia.domain.model.LoginModel;

/**
 * Created by elfatahwashere on 8/13/2016.
 */
public class LoginInteractorImpl extends AbstractInteractor implements LoginInteractor {
    private LoginInteractor.Callback mCallback;
    private LoginModel loginModel;

    public LoginInteractorImpl(Executor threadExecutor, MainThread mainThread, LoginInteractor.Callback callback, LoginModel s) {
        super(threadExecutor, mainThread);

        mCallback = callback;
        loginModel = s;
    }

    @Override
    public void run() {
        final boolean isPalindrome = isPalindrome(loginModel);

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onStringChecked(isPalindrome);
            }
        });

    }

    @Override
    public boolean isPalindrome(LoginModel loginModel) {
        String text = loginModel.getName();
        int size = text.length();
        String awal, akhir;
        int middle = ((int) Math.floor(size / 2));
        awal = text.substring(0, middle);
        if (size % 2 == 0) {
            akhir = text.substring(middle);
        } else {
            akhir = text.substring(middle + 1);
        }
        StringBuffer a = new StringBuffer(akhir);
        Log.e("split", awal + "-" + akhir);
        return awal.equals(a.reverse().toString());
    }
}
