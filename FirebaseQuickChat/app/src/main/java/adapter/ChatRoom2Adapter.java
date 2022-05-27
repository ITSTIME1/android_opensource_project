package adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firebasequickchat.databinding.Chat2RoomsBinding;
import model.ChatRoom2Model;



public class ChatRoom2Adapter extends RecyclerView.Adapter<ChatRoom2Adapter.ChatRoom2ViewHolder> {
    ObservableArrayList<ChatRoom2Model> chatRoom2ModelList;
    Context context;

    public ChatRoom2Adapter(ObservableArrayList<ChatRoom2Model> chatRoom2ModelList, Context context) {
        this.chatRoom2ModelList = chatRoom2ModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatRoom2Adapter.ChatRoom2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Chat2RoomsBinding chat2RoomsBinding = Chat2RoomsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChatRoom2ViewHolder(chat2RoomsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoom2Adapter.ChatRoom2ViewHolder holder, int position) {
        ChatRoom2Model chatRoom2Model = chatRoom2ModelList.get(position);
        holder.bind(chatRoom2Model);
    }


    @SuppressLint("NotifyDataSetChanged")
    void setItem(ObservableArrayList<ChatRoom2Model> chatRoom2Models) {
        if(chatRoom2Models == null) {
            return;
        } else {
            this.chatRoom2ModelList = chatRoom2Models;
            notifyDataSetChanged();
        }
    }


    @Override
    public int getItemCount() {
        return chatRoom2ModelList.size();
    }

    public class ChatRoom2ViewHolder extends RecyclerView.ViewHolder {
        Chat2RoomsBinding chat2RoomsBinding;

        public ChatRoom2ViewHolder(@NonNull Chat2RoomsBinding chat2RoomsBinding) {
            super(chat2RoomsBinding.getRoot());
            this.chat2RoomsBinding = chat2RoomsBinding;
        }

        public void bind(ChatRoom2Model chatRoom2Model){
            chat2RoomsBinding.setChatRoom2(chatRoom2Model);
        }
    }
}
