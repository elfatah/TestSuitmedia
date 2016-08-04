package com.example.masboyjahat.testsuitmedia.presenters;

import com.example.masboyjahat.testsuitmedia.activities.IActivity;
import com.example.masboyjahat.testsuitmedia.models.EventModel;

/**
 * Created by masboy jahat on 8/3/2016.
 */
public class MainPresenter implements IMainPresenter {
    private EventModel eventModel;
    private IActivity.IEventGuest iEventGuest;
    private IActivity.IHome iHome;
    private IActivity.IChooseEvent iChooseEvent;


    public MainPresenter(IActivity.IEventGuest iEventGuest) {
        this.iEventGuest = iEventGuest;
        eventModel = new EventModel();
    }

    public MainPresenter(IActivity.IHome iHome){
        this.iHome = iHome;
    }

    public MainPresenter(IActivity.IChooseEvent iChooseEvent){
        this.iChooseEvent= iChooseEvent;
    }

    @Override
    public void showChooseEventActivity(String name) {
        iHome.showChooseEvent(name);
    }

    @Override
    public void showGuestEvent(String name) {
        eventModel.setNama(name);
        iEventGuest.eventCallback(eventModel.getNama());
    }

    @Override
    public void showEvent() {
        iChooseEvent.showEvent();
    }

    @Override
    public void showGuest() {
        iChooseEvent.showGuest();
    }
}
