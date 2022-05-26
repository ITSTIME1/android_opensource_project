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
import com.example.firebasequickchat.databinding.FragmentChatBinding;

import adapter.ChatRoomsAdapter;
import model.ChatRoomModel;


// @TODO Bind Item ChatRoom2Model
// @TODO Create ChatRoom2ModelAdapter
// @TODO BindingAdapter
// @TODO Add ObservableArrayList<ChatRoom2Model> in ChatFragment and then I have to set ChatRoom2ModelAdapter


public class ChatFragment extends Fragment {
    private FragmentChatBinding fragmentChatBinding;
    private ObservableArrayList<ChatRoomModel> chatRoomModelObservableArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentChatBinding = FragmentChatBinding.inflate(inflater, container, false);


        chatRoomModelObservableArrayList = new ObservableArrayList<>();
        prepareMovieData();
        ChatRoomsAdapter chatRoomsAdapter = new ChatRoomsAdapter(chatRoomModelObservableArrayList, getContext());
        fragmentChatBinding.chatRoomRec.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        fragmentChatBinding.chatRoomRec.setAdapter(chatRoomsAdapter);
        fragmentChatBinding.setChatRoomList(chatRoomModelObservableArrayList);

        return fragmentChatBinding.getRoot();
    }

    private void prepareMovieData() {
        chatRoomModelObservableArrayList.add(new ChatRoomModel(R.drawable.chatrooms, R.drawable.ic_empty_star, "ChatName", "2022.05.25"));
        chatRoomModelObservableArrayList.add(new ChatRoomModel(R.drawable.chatrooms, R.drawable.ic_empty_star, "LoveName", "2022.05.25"));
        chatRoomModelObservableArrayList.add(new ChatRoomModel(R.drawable.chatrooms, R.drawable.ic_empty_star, "StarName", "2022.05.25"));
        chatRoomModelObservableArrayList.add(new ChatRoomModel(R.drawable.chatrooms, R.drawable.ic_empty_star, "MaVelName", "2022.05.25"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentChatBinding = null;
    }

}
