package com.example.firebase_chat_basic.Interface;


/**
 * [BaseInterface]
 *
 * default interface method.
 */
public interface BaseInterface {
    public default void defaultInit(){};
    public default void initRetrofit(){};
    public default void initAdapter(){};
    public default void observerIntent(){};
    public default void initNavigationListener(){};
    public default  void getDataFromActivity(){};
}
