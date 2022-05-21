package com.example.playground;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.playground.databinding.ItemTestRecBinding;
import java.util.List;

public class TestRecyclerAdapter extends RecyclerView.Adapter<TestRecyclerAdapter.ViewHolder> {

    private final List<DataModel> dataModelList;
    private final Context context;

    public TestRecyclerAdapter(List<DataModel> dataModelList, Context context) {
        this.dataModelList = dataModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public TestRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTestRecBinding itemTestRecBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_test_rec, parent, false);
        return new ViewHolder(itemTestRecBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TestRecyclerAdapter.ViewHolder holder, int position) {
        DataModel dataModel = dataModelList.get(position);
        holder.getBinding(dataModel);


    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTestRecBinding itemTestRecBinding;

        public ViewHolder(@NonNull ItemTestRecBinding itemTestRecBinding) {
            super(itemTestRecBinding.getRoot());
            this.itemTestRecBinding = itemTestRecBinding;
        }

        public void getBinding(Object obj) {
            itemTestRecBinding.setVariable(BR.model, obj);
            itemTestRecBinding.executePendingBindings();
        }
    }
}
