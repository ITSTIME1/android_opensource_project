package com.example.firebase_chat_basic.viewModel;
import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import com.example.firebase_chat_basic.Interface.BaseInterface;
import com.example.firebase_chat_basic.adapters.ContactRecyclerAdapter;
import com.example.firebase_chat_basic.model.ContactModel;

import java.util.ArrayList;



public class ContactViewModel extends AndroidViewModel implements BaseInterface {
    private ArrayList<ContactModel> contactModelList;
    private ContactRecyclerAdapter contactRecyclerAdapter;
    private String firebase_my_key;
    private String firebase_my_phone_number;

    // constructor init
    public ContactViewModel(Application application, String getCurrentMyUID) {
        super(application);
        if(getCurrentMyUID != null) {
            firebase_my_key = getCurrentMyUID;
        }
        if(contactModelList == null && contactRecyclerAdapter == null) {
            contactModelList = new ArrayList<>();
            contactRecyclerAdapter = new ContactRecyclerAdapter(this);
        }
    }



    public ContactRecyclerAdapter getContactRecyclerAdapter() {
        return contactRecyclerAdapter;
    }

    public ArrayList<ContactModel> getContactModelList () {
        return contactModelList;
    }


}
