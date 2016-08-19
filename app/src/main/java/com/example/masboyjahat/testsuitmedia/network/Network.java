package com.example.masboyjahat.testsuitmedia.network;

import java.util.List;

/**
 * Created by elfatahwashere on 8/18/2016.
 */
public interface Network {
    interface Callback<T>{
        void onRequestFinish(List<T> objects);
    }
}
