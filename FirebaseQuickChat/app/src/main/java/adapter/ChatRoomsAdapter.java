package adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasequickchat.BR;
import com.example.firebasequickchat.databinding.ChatRoomsBinding;

import java.util.ArrayList;
import java.util.List;

import model.ChatRoomModel;

public class ChatRoomsAdapter extends RecyclerView.Adapter<ChatRoomsAdapter.ChatRoomViewHolder> {
    private List<ChatRoomModel> chatRoomModelObservableArrayList;
    private Context context;

    public ChatRoomsAdapter(List<ChatRoomModel> chatRoomModelObservableArrayList, Context context) {
        this.chatRoomModelObservableArrayList = chatRoomModelObservableArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatRoomsAdapter.ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChatRoomsBinding chatRoomsBinding = ChatRoomsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChatRoomViewHolder(chatRoomsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomsAdapter.ChatRoomViewHolder holder, int position) {
        ChatRoomModel chatRoomModel = chatRoomModelObservableArrayList.get(position);
        holder.bind(chatRoomModel);
    }


    @SuppressLint("NotifyDataSetChanged")
    void setItem(List<ChatRoomModel> chatRoomModels) {
        if(chatRoomModels == null) {
            return;
        }
        this.chatRoomModelObservableArrayList = chatRoomModels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return chatRoomModelObservableArrayList.size();
    }

    public class ChatRoomViewHolder extends RecyclerView.ViewHolder {
        ChatRoomsBinding chatRoomsBinding;

        public ChatRoomViewHolder(@NonNull ChatRoomsBinding chatRoomsBinding) {
            super(chatRoomsBinding.getRoot());
            this.chatRoomsBinding = chatRoomsBinding;
        }

        public void bind(ChatRoomModel chatRoomModel){
            chatRoomsBinding.setChatRoom(chatRoomModel);
        }
    }
}
