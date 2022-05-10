package Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;

import Models.FragmentHomeHolRecItemModel;

public class FragmentHomeHolRecAdapter extends RecyclerView.Adapter<FragmentHomeHolRecAdapter.ViewHolder> {

    BottomSheetDialog bottomSheetDialog;
    ArrayList<FragmentHomeHolRecItemModel> fragmentHomeHolRecItemModelArrayList;
    Context context;

    public FragmentHomeHolRecAdapter(ArrayList<FragmentHomeHolRecItemModel> fragmentHomeHolRecItemModelArrayList, Context context) {
        this.fragmentHomeHolRecItemModelArrayList = fragmentHomeHolRecItemModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public FragmentHomeHolRecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_fragment_hol_rec, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentHomeHolRecAdapter.ViewHolder holder, int position) {


        holder.fragmentHolRecItemImage.setImageResource(fragmentHomeHolRecItemModelArrayList.get(position).getFrgHomeHolRecImage());
        holder.fragmentHolRecItemTitle.setText(fragmentHomeHolRecItemModelArrayList.get(position).getFragHomeHolRecTitle());
        holder.fragmentHolRecItemDescription.setText(fragmentHomeHolRecItemModelArrayList.get(position).getFragHomeHolRecDescription());
        holder.fragmentHolRecItemPrice.setText(fragmentHomeHolRecItemModelArrayList.get(position).getFragHomeHolRecPrice());
        holder.fragmentHolRecItemOrder.setText(fragmentHomeHolRecItemModelArrayList.get(position).getFragHomeHolRecOrderText());

    }

    @Override
    public int getItemCount() {
        return fragmentHomeHolRecItemModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView fragmentHolRecItemImage;
        private TextView fragmentHolRecItemTitle;
        private TextView fragmentHolRecItemDescription;
        private TextView fragmentHolRecItemPrice;
        private final TextView fragmentHolRecItemOrder;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fragmentHolRecItemImage = itemView.findViewById(R.id.home_fragment_hol_rec_image);
            fragmentHolRecItemTitle = itemView.findViewById(R.id.home_fragment_hol_rec_item_title);
            fragmentHolRecItemDescription = itemView.findViewById(R.id.home_fragment_hole_rec_item_description);
            fragmentHolRecItemPrice = itemView.findViewById(R.id.home_fragment_hol_rec_item_price);
            fragmentHolRecItemOrder = itemView.findViewById(R.id.home_fragment_hol_rec_order);


            // When item clicked
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Create BottomDialog Instance
                    bottomSheetDialog = new BottomSheetDialog(context, com.google.android.material.R.style.Theme_Design_Light_BottomSheetDialog);
                    // Connect bottomSheet
                    View bottomView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_layout, null);
                    bottomSheetDialog.setContentView(bottomView);
                    bottomSheetDialog.show();
                }

            });

        }

    }
}
