package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasequickchat.databinding.ContactsItemBinding;

import java.util.List;

import model.ChatRoom2Model;
import model.ContactsModel;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {
    List<ContactsModel> contactsModelList;
    Context context;

    public ContactsAdapter(List<ContactsModel> contactsModelList, Context context) {
        this.contactsModelList = contactsModelList;
        this.context = context;
    }


    @NonNull
    @Override
    public ContactsAdapter.ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContactsItemBinding contactsItemBinding = ContactsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ContactsViewHolder(contactsItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ContactsViewHolder holder, int position) {
        ContactsModel contactsModel = contactsModelList.get(position);
        holder.bind(contactsModel);
    }

    @SuppressLint("NotifyDataSetChanged")
    void setItem(ObservableArrayList<ContactsModel> contactsModelList) {
        if(contactsModelList == null) {
            return;
        } else {
            this.contactsModelList = contactsModelList;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return contactsModelList.size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {
        ContactsItemBinding contactsItemBinding;

        public ContactsViewHolder(@NonNull ContactsItemBinding contactsItemBinding) {
            super(contactsItemBinding.getRoot());
            this.contactsItemBinding = contactsItemBinding;
        }

        public void bind(ContactsModel contactsModel){
            contactsItemBinding.setContactsItem(contactsModel);
        }
    }
}
