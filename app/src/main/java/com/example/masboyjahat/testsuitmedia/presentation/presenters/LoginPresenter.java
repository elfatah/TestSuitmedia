package com.example.masboyjahat.testsuitmedia.presentation.presenters;

import com.example.masboyjahat.testsuitmedia.presentation.presenters.base.BasePresenter;
import com.example.masboyjahat.testsuitmedia.presentation.ui.BaseView;

/**
 * Created by elfatahwashere on 8/13/2016.
 */
public interface LoginPresenter extends BasePresenter {

    interface View extends BaseView{
        void nameChecked(boolean b);
        void showAlertDialog(boolean b);

    }
    void isPalindrome(String s);
}
