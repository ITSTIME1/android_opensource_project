package com.example.firebase_chat_basic.adapters;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firebase_chat_basic.databinding.ItemFragmentContactBinding;
import com.example.firebase_chat_basic.viewModel.ContactViewModel;




public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.CustomContactViewHolder> {
    private ContactViewModel contactViewModel;

    public ContactRecyclerAdapter(ContactViewModel contactViewModel) {
        this.contactViewModel = contactViewModel;
    }

    @NonNull
    @Override
    public ContactRecyclerAdapter.CustomContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFragmentContactBinding itemFragmentContactBinding = ItemFragmentContactBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CustomContactViewHolder(itemFragmentContactBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactRecyclerAdapter.CustomContactViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CustomContactViewHolder extends RecyclerView.ViewHolder {
        ItemFragmentContactBinding itemFragmentContactBinding;
        public CustomContactViewHolder(@NonNull ItemFragmentContactBinding itemFragmentContactBinding) {
            super(itemFragmentContactBinding.getRoot());
            this.itemFragmentContactBinding = itemFragmentContactBinding;
        }
    }
}