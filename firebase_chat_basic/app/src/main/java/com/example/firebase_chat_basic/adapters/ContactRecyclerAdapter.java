package com.example.firebase_chat_basic.adapters;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_chat_basic.databinding.ItemFragmentContactBinding;
import com.example.firebase_chat_basic.view.activity.ProfileActivity;
import com.example.firebase_chat_basic.viewModel.ContactViewModel;




public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.CustomContactViewHolder> {
    private final ContactViewModel contactViewModel;

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
        holder.bind(contactViewModel, position);

    }

    @Override
    public int getItemCount() {
        return contactViewModel.getContactModelList().size();
    }

    public class CustomContactViewHolder extends RecyclerView.ViewHolder {
        ItemFragmentContactBinding itemFragmentContactBinding;
        public CustomContactViewHolder(@NonNull ItemFragmentContactBinding itemFragmentContactBinding) {
            super(itemFragmentContactBinding.getRoot());
            this.itemFragmentContactBinding = itemFragmentContactBinding;
        }

        public void bind(ContactViewModel contactViewModel, int position){
            itemFragmentContactBinding.setContactViewModel(contactViewModel);
            itemFragmentContactBinding.setPos(position);


            // contact item click
            itemFragmentContactBinding.contactItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent contactIntent = new Intent(view.getContext(), ProfileActivity.class);

                    // 이름, 프로필 이미지, 배경 이미지, 상태 메세지, 전화번호
                    contactIntent.putExtra("client_phone_number", contactViewModel.get_phone_number(position));
                    contactIntent.putExtra("client_name", contactViewModel.get_user_name(position));
                    contactIntent.putExtra("client_profile_image", contactViewModel.get_profile_image(position));
                    contactIntent.putExtra("client_background_image", contactViewModel.get_background_profile_image(position));
                    contactIntent.putExtra("client_state_message", contactViewModel.get_state_message(position));
                    contactIntent.putExtra("chatKey", contactViewModel.get_chat_key(position));
                    contactIntent.putExtra("client_my_uid", contactViewModel.get_my_uid(position));
                    contactIntent.putExtra("client_other_uid", contactViewModel.get_other_uid(position));


                    view.getContext().startActivity(contactIntent);
                }
            });
        }
    }
}
