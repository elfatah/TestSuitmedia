package com.example.masboyjahat.testsuitmedia.presenters;

/**
 * Created by masboy jahat on 8/3/2016.
 */
public interface IMainPresenter {

    void showChooseEventActivity(String name);
    void showEventName(String name);
    void showGuestName(String name, String birthdate);

    void showEvent();
    void showGuest();

}
