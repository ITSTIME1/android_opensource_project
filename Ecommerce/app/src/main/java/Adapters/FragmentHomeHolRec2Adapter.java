package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;

import java.util.ArrayList;

import Models.FragmentHomeHolRec2ItemModel;

public class FragmentHomeHolRec2Adapter extends RecyclerView.Adapter<FragmentHomeHolRec2Adapter.ViewHolder> {

    ArrayList<FragmentHomeHolRec2ItemModel> fragmentHomeHolRec2ItemModelArrayList;
    Context context;

    public FragmentHomeHolRec2Adapter(ArrayList<FragmentHomeHolRec2ItemModel> fragmentHomeHolRec2ItemModelArrayList, Context context) {
        this.fragmentHomeHolRec2ItemModelArrayList = fragmentHomeHolRec2ItemModelArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public FragmentHomeHolRec2Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_fragment_hol_rec2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentHomeHolRec2Adapter.ViewHolder holder, int position) {

        holder.fragmentHomeRec2ItemImage.setImageResource(fragmentHomeHolRec2ItemModelArrayList.get(position).getFrgHomeHolRec2Image());
        holder.fragmentHomeRec2ItemTitle.setText(fragmentHomeHolRec2ItemModelArrayList.get(position).getFrgHomeHolRec2Title());
        holder.fragmentHomeRec2ItemDescription.setText(fragmentHomeHolRec2ItemModelArrayList.get(position).getFrgHomeHolRec2Description());
        holder.fragmentHomeRec2ItemPrice.setText(fragmentHomeHolRec2ItemModelArrayList.get(position).getFrgHomeHolRec2Price());
        holder.fragmentHomeRec2ItemOrder.setText(fragmentHomeHolRec2ItemModelArrayList.get(position).getFrgHomeHolRec2Order());

    }

    @Override
    public int getItemCount() {
        return fragmentHomeHolRec2ItemModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fragmentHomeRec2ItemImage;
        TextView fragmentHomeRec2ItemTitle;
        TextView fragmentHomeRec2ItemDescription;
        TextView fragmentHomeRec2ItemPrice;
        TextView fragmentHomeRec2ItemOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fragmentHomeRec2ItemImage = itemView.findViewById(R.id.home_fragment_hol_rec2_image);
            fragmentHomeRec2ItemTitle = itemView.findViewById(R.id.home_fragment_hol_rec2_item_title);
            fragmentHomeRec2ItemDescription = itemView.findViewById(R.id.home_fragment_hol_rec2_item_description);
            fragmentHomeRec2ItemPrice = itemView.findViewById(R.id.home_fragment_hol_rec2_item_price);
            fragmentHomeRec2ItemOrder = itemView.findViewById(R.id.home_fragment_hol_rec2_order);
        }
    }
}
