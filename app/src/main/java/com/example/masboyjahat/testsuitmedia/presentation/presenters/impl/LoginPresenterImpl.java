package com.example.masboyjahat.testsuitmedia.presentation.presenters.impl;

import com.example.masboyjahat.testsuitmedia.domain.executor.Executor;
import com.example.masboyjahat.testsuitmedia.domain.executor.MainThread;
import com.example.masboyjahat.testsuitmedia.domain.interactors.LoginInteractor;
import com.example.masboyjahat.testsuitmedia.domain.interactors.impl.LoginInteractorImpl;
import com.example.masboyjahat.testsuitmedia.domain.model.LoginModel;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.LoginPresenter;
import com.example.masboyjahat.testsuitmedia.presentation.presenters.base.AbstractPresenter;

/**
 * Created by elfatahwashere on 8/13/2016.
 */
public class LoginPresenterImpl extends AbstractPresenter implements LoginPresenter, LoginInteractor.Callback {

    LoginPresenter.View mView;

    public LoginPresenterImpl(Executor executor, MainThread mainThread, View view) {
        super(executor, mainThread);

        mView = view;

    }



    @Override
    public void resume() {

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
    public void isPalindrome(String text) {
        LoginModel loginModel = new LoginModel(text);
        LoginInteractorImpl loginInteractor = new LoginInteractorImpl(mExecutor, mMainThread, this, loginModel);
        loginInteractor.execute();
    }

    @Override
    public void onStringChecked(boolean b) {
        mView.nameChecked(b);
    }
}
