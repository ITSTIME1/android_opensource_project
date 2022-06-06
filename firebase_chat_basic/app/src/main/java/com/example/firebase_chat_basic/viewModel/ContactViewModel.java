package com.example.firebase_chat_basic.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.adapters.ContactRecyclerAdapter;
import com.example.firebase_chat_basic.model.ContactModel;

import java.util.ArrayList;
import java.util.List;


public class ContactViewModel extends ViewModel implements BaseInterface {
    private List<ContactModel> contactModelList;
    private ContactRecyclerAdapter contactRecyclerAdapter;

    // constructor init
    public ContactViewModel() {
        if(contactModelList == null && contactRecyclerAdapter == null) {
            contactModelList = new ArrayList<>();
            contactRecyclerAdapter = new ContactRecyclerAdapter(this);
        }
    }

    @Override
    public void initRetrofit() {
        BaseInterface.super.initRetrofit();
        /*
         * @TODO Retrofit List
         */
    }
}
