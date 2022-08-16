package com.example.firebase_chat_basic.Interface;


/**
 * [BaseInterface]
 *
 * <Topic>
 *     This interface is for "base method"
 * </Topic>
 */

public interface BaseInterface {
    public default void default_init(){};
    public default void init_adapter(){};
    public default void observer_intent(){};
    public default void init_navigation(){};
    public default void get_data_intent(){};
    public default void onBackPressed(){};
}
