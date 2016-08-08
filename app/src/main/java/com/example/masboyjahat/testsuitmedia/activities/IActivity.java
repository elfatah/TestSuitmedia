package com.example.masboyjahat.testsuitmedia.activities;

import com.example.masboyjahat.testsuitmedia.models.EventModel;

/**
 * Created by masboy jahat on 8/3/2016.
 */
public interface IActivity {
    void showProgress();
    void hideProgress();

    interface IHome{
        void showChooseEvent(String name);
        Boolean isPalindrome(String text);
    }

    interface IChooseEvent{
        void showEvent();
        void showGuest();
        void initUI();
    }

    interface IEventGuest{
        void eventCallback(String nama);
        void guestCallback(String nama, String birthdate);
        void populateEventList();
    }


}
