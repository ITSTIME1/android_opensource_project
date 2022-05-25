package view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;

import com.example.firebasequickchat.R;
import com.example.firebasequickchat.databinding.FragmentChatBinding;
import adapter.ChatRoomsAdapter;
import model.ChatRoomModel;


// @TODO fragment_chat need to declare to bind:item

public class ChatFragment extends Fragment {
    private FragmentChatBinding fragmentChatBinding;
    private ChatRoomsAdapter chatRoomsAdapter;
    private ObservableArrayList<ChatRoomModel> chatRoomModelObservableArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentChatBinding = FragmentChatBinding.inflate(inflater, container, false);



        chatRoomsAdapter = new ChatRoomsAdapter();
        chatRoomModelObservableArrayList = new ObservableArrayList<>();
        fragmentChatBinding.chatRoomRec.setAdapter(chatRoomsAdapter);
        fragmentChatBinding.setChatRoomList(chatRoomModelObservableArrayList);

        prepareMovieData();


        return fragmentChatBinding.getRoot();
    }

    private void prepareMovieData() {
        chatRoomModelObservableArrayList.add(new ChatRoomModel(R.drawable.chatrooms, R.drawable.ic_empty_star, "ChatName", "2022.05.25"));
        chatRoomModelObservableArrayList.add(new ChatRoomModel(R.drawable.chatrooms, R.drawable.ic_empty_star, "LoveName", "2022.05.25"));
        chatRoomModelObservableArrayList.add(new ChatRoomModel(R.drawable.chatrooms, R.drawable.ic_empty_star, "StarName", "2022.05.25"));
        chatRoomModelObservableArrayList.add(new ChatRoomModel(R.drawable.chatrooms, R.drawable.ic_empty_star, "MavleName", "2022.05.25"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentChatBinding = null;
    }

}
