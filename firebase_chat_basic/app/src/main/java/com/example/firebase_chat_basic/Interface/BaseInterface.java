package com.example.firebase_chat_basic.Interface;


/**
 * [BaseInterface]
 *
 * <Topic>
 *
 *     This interface is for "base method"
 *
 * </Topic>
 */

public interface BaseInterface {
    public default void initialize(){};
    public default void initializeAdapter(){};
    public default void intentObserver(){};
    public default void initializeNavigation(){};
    public default void getDataIntent(){};
    public default void onBackPressed(){};
}
