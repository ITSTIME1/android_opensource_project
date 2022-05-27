package adapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import model.ChatRoom2Model;
import model.ChatRoomModel;


public class BindingAdapter {
    @androidx.databinding.BindingAdapter("bind:item")
    public static void bindItem(RecyclerView recyclerView, ObservableArrayList<ChatRoomModel> chatRoomModelObservableArrayList) {
        ChatRoomsAdapter adapter =(ChatRoomsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItem(chatRoomModelObservableArrayList);
        }
    }

    @androidx.databinding.BindingAdapter("bind:item")
    public static void bindItem2(RecyclerView recyclerView, ObservableArrayList<ChatRoom2Model> chatRoom2ModelObservableArrayList) {
        ChatRoom2Adapter adapter =(ChatRoom2Adapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItem(chatRoom2ModelObservableArrayList);
        }
    }


}
