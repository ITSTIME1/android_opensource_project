package adapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;
import model.ChatRoomModel;


public class BindingAdapter {
    @androidx.databinding.BindingAdapter("bind:item")
    public static void bindItem(RecyclerView recyclerView, ObservableArrayList<ChatRoomModel> chatRoomModelObservableArrayList) {
        ChatRoomsAdapter adapter =(ChatRoomsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItem(chatRoomModelObservableArrayList);
        }
    }
}
