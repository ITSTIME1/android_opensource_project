package adapter;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatRoomsAdapter extends RecyclerView.Adapter<ChatRoomsAdapter.ChatRoomViewHolder> {
    @NonNull
    @Override
    public ChatRoomsAdapter.ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomsAdapter.ChatRoomViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ChatRoomViewHolder extends RecyclerView.ViewHolder {
        public ChatRoomViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
