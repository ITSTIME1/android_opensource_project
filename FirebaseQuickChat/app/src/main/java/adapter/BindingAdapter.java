package adapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import model.ChatRoom2Model;
import model.ChatRoomModel;


/**
 * [Binding Adapter Method]
 *
 * For ObservableList
 */
public class BindingAdapter {
    @androidx.databinding.BindingAdapter("bind:item")
    public static void bindItem(RecyclerView recyclerView, ObservableArrayList<ChatRoomModel> chatRoomModelObservableArrayList) {
        ChatRoomsAdapter chatRoomsAdapter =(ChatRoomsAdapter) recyclerView.getAdapter();
        if (chatRoomsAdapter != null) {
            chatRoomsAdapter.setItem(chatRoomModelObservableArrayList);
        }
    }

    @androidx.databinding.BindingAdapter("bind:item")
    public static void bindItem2(RecyclerView recyclerView, ObservableArrayList<ChatRoom2Model> chatRoom2ModelObservableArrayList) {
        ChatRoom2Adapter chatRoom2Adapter =(ChatRoom2Adapter) recyclerView.getAdapter();
        if (chatRoom2Adapter != null) {
            chatRoom2Adapter.setItem(chatRoom2ModelObservableArrayList);
        }
    }


}
