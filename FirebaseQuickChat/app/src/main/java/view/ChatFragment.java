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

import adapter.ChatRoom2Adapter;
import adapter.ChatRoomsAdapter;
import model.ChatRoom2Model;
import model.ChatRoomModel;



public class ChatFragment extends Fragment {
    private FragmentChatBinding fragmentChatBinding;
    private ObservableArrayList<ChatRoomModel> chatRoomModelObservableArrayList;
    private ObservableArrayList<ChatRoom2Model> chatRoom2ModelObservableArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentChatBinding = FragmentChatBinding.inflate(inflater, container, false);


        chatRoomModelObservableArrayList = new ObservableArrayList<>();
        chatRoom2ModelObservableArrayList = new ObservableArrayList<>();

        prepareMovieData();
        prepareMovieData2();

        ChatRoomsAdapter chatRoomsAdapter = new ChatRoomsAdapter(chatRoomModelObservableArrayList, getContext());
        ChatRoom2Adapter chatRoom2Adapter = new ChatRoom2Adapter(chatRoom2ModelObservableArrayList, getContext());

        fragmentChatBinding.chatRoomRec.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        fragmentChatBinding.chatRoomRec2.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        fragmentChatBinding.chatRoomRec.setAdapter(chatRoomsAdapter);
        fragmentChatBinding.chatRoomRec2.setAdapter(chatRoom2Adapter);

        fragmentChatBinding.setChatRoomList(chatRoomModelObservableArrayList);
        fragmentChatBinding.setChatRoomList2(chatRoom2ModelObservableArrayList);

        return fragmentChatBinding.getRoot();
    }

    private void prepareMovieData() {
        chatRoomModelObservableArrayList.add(new ChatRoomModel(R.drawable.chatrooms, R.drawable.ic_empty_star, "ChatName", "2022.05.25"));
        chatRoomModelObservableArrayList.add(new ChatRoomModel(R.drawable.chatrooms, R.drawable.ic_empty_star, "LoveName", "2022.05.25"));
        chatRoomModelObservableArrayList.add(new ChatRoomModel(R.drawable.chatrooms, R.drawable.ic_empty_star, "StarName", "2022.05.25"));
        chatRoomModelObservableArrayList.add(new ChatRoomModel(R.drawable.chatrooms, R.drawable.ic_empty_star, "MaVelName", "2022.05.25"));
    }

    private void prepareMovieData2() {
        chatRoom2ModelObservableArrayList.add(new ChatRoom2Model(R.drawable.group, "HongTaeSun", "Hello Sir", "2022.05.27", "5"));
        chatRoom2ModelObservableArrayList.add(new ChatRoom2Model(R.drawable.group, "HongTaeSun", "Hello Sir", "2022.05.27", "5"));
        chatRoom2ModelObservableArrayList.add(new ChatRoom2Model(R.drawable.group, "HongTaeSun", "Hello Sir", "2022.05.27", "5"));
        chatRoom2ModelObservableArrayList.add(new ChatRoom2Model(R.drawable.group, "HongTaeSun", "Hello Sir", "2022.05.27", "5"));
        chatRoom2ModelObservableArrayList.add(new ChatRoom2Model(R.drawable.group, "HongTaeSun", "Hello Sir", "2022.05.27", "5"));
        chatRoom2ModelObservableArrayList.add(new ChatRoom2Model(R.drawable.group, "HongTaeSun", "Hello Sir", "2022.05.27", "5"));
        chatRoom2ModelObservableArrayList.add(new ChatRoom2Model(R.drawable.group, "HongTaeSun", "Hello Sir", "2022.05.27", "5"));
        chatRoom2ModelObservableArrayList.add(new ChatRoom2Model(R.drawable.group, "HongTaeSun", "Hello Sir", "2022.05.27", "5"));
        chatRoom2ModelObservableArrayList.add(new ChatRoom2Model(R.drawable.group, "HongTaeSun", "Hello Sir", "2022.05.27", "5"));
        chatRoom2ModelObservableArrayList.add(new ChatRoom2Model(R.drawable.group, "HongTaeSun", "Hello Sir", "2022.05.27", "5"));
        chatRoom2ModelObservableArrayList.add(new ChatRoom2Model(R.drawable.group, "HongTaeSun", "Hello Sir", "2022.05.27", "5"));
        chatRoom2ModelObservableArrayList.add(new ChatRoom2Model(R.drawable.group, "HongTaeSun", "Hello Sir", "2022.05.27", "5"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentChatBinding = null;
    }

}
