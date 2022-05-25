package binding;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import adapter.ChatRoomsAdapter;
import model.ChatRoomModel;

public class ChatRoomAdapterBinding {
    @BindingAdapter("bind:item")
    public static void bindItem(RecyclerView recyclerView, ObservableArrayList<ChatRoomModel> chatRoomModelObservableArrayList) {
        ChatRoomsAdapter chatRoomsAdapter = (ChatRoomsAdapter) recyclerView.getAdapter();
        if (chatRoomsAdapter != null) {
        }
    }
}
