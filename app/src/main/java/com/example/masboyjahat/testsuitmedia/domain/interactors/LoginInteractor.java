package com.example.masboyjahat.testsuitmedia.domain.interactors;

import com.example.masboyjahat.testsuitmedia.domain.interactors.base.Interactor;
import com.example.masboyjahat.testsuitmedia.domain.model.LoginModel;

/**
 * Created by elfatahwashere on 8/13/2016.
 */
public interface LoginInteractor extends Interactor {

    interface Callback{
        void onStringChecked(boolean b);
    }

    boolean isPalindrome(LoginModel loginModel);
}
