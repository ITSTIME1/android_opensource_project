package view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasequickchat.R;
import com.example.firebasequickchat.databinding.FragmentContactsBinding;

import adapter.ChatRoom2Adapter;
import adapter.ContactsAdapter;
import model.ChatRoom2Model;
import model.ContactsModel;


// @TODO ObserverArrayList implementation
// @TODO Contacts Adapter implementation


public class ContactsFragment extends Fragment {
    private FragmentContactsBinding fragmentContactsBinding;
    private ObservableArrayList<ContactsModel> contactsModelObservableArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentContactsBinding = FragmentContactsBinding.inflate(inflater, container, false);

        contactsModelObservableArrayList = new ObservableArrayList<>();
        prepareMovieData();

        ContactsAdapter contactsAdapter = new ContactsAdapter(contactsModelObservableArrayList, getContext());
        fragmentContactsBinding.contactsRec.setAdapter(contactsAdapter);
        fragmentContactsBinding.contactsRec.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        fragmentContactsBinding.setContacts(contactsModelObservableArrayList);



        return fragmentContactsBinding.getRoot();
    }

    private void prepareMovieData() {
        contactsModelObservableArrayList.add(new ContactsModel(R.drawable.group, "HongTaeSun", "010-9775-4185"));
        contactsModelObservableArrayList.add(new ContactsModel(R.drawable.group, "HongTaeSun", "010-9775-4185"));
        contactsModelObservableArrayList.add(new ContactsModel(R.drawable.group, "HongTaeSun", "010-9775-4185"));
        contactsModelObservableArrayList.add(new ContactsModel(R.drawable.group, "HongTaeSun", "010-9775-4185"));
        contactsModelObservableArrayList.add(new ContactsModel(R.drawable.group, "HongTaeSun", "010-9775-4185"));
        contactsModelObservableArrayList.add(new ContactsModel(R.drawable.group, "HongTaeSun", "010-9775-4185"));
        contactsModelObservableArrayList.add(new ContactsModel(R.drawable.group, "HongTaeSun", "010-9775-4185"));
        contactsModelObservableArrayList.add(new ContactsModel(R.drawable.group, "HongTaeSun", "010-9775-4185"));
        contactsModelObservableArrayList.add(new ContactsModel(R.drawable.group, "HongTaeSun", "010-9775-4185"));


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentContactsBinding = null;
    }
}
