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


/**
 * [ContactRecyclerAdapter]
 *
 * <Topic>
 *
 *     This adapter displays new friends account.
 *
 * </Topic>
 */


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
        holder.init(contactViewModel, position);

    }

    @Override
    public int getItemCount() {
        if(contactViewModel.getContactModelList().size() == 0) {
            return 0;
        } else {
            return contactViewModel.getContactModelList().size();
        }
    }

    public static class CustomContactViewHolder extends RecyclerView.ViewHolder {
        public ItemFragmentContactBinding itemFragmentContactBinding;
        public CustomContactViewHolder(@NonNull ItemFragmentContactBinding itemFragmentContactBinding) {
            super(itemFragmentContactBinding.getRoot());
            this.itemFragmentContactBinding = itemFragmentContactBinding;
        }

        public void init(ContactViewModel contactViewModel, int position){
            itemFragmentContactBinding.setContactViewModel(contactViewModel);
            itemFragmentContactBinding.setPos(position);


            // contact item click
            itemFragmentContactBinding.contactUserListLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent contactIntent = new Intent(view.getContext(), ProfileActivity.class);

                    // 이름, 프로필 이미지, 배경 이미지, 상태 메세지, 전화번호
                    contactIntent.putExtra("client_phone_number", contactViewModel.getPhoneNumber(position));
                    contactIntent.putExtra("client_name", contactViewModel.getUserName(position));
                    contactIntent.putExtra("client_profile_image", contactViewModel.getProfileImage(position));
                    contactIntent.putExtra("client_background_image", contactViewModel.get_background_profile_image(position));
                    contactIntent.putExtra("client_state_message", contactViewModel.getStateMessage(position));
                    contactIntent.putExtra("chatKey", contactViewModel.getChatPrivateKey(position));
                    contactIntent.putExtra("client_my_uid", contactViewModel.getMyUID(position));
                    contactIntent.putExtra("client_other_uid", contactViewModel.getOtherUID(position));


                    view.getContext().startActivity(contactIntent);
                }
            });
        }
    }
}
