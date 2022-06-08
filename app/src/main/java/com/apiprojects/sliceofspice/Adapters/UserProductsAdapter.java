package com.apiprojects.sliceofspice.Adapters;

import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apiprojects.sliceofspice.R;
import com.apiprojects.sliceofspice.models.Products;
import com.apiprojects.sliceofspice.models.User;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserProductsAdapter extends RecyclerView.Adapter<UserProductsAdapter.UserProductsVH>{
    ClickListener clickListener;

    @NonNull
    @Override
    public UserProductsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userproductsitem, parent, false);
        return new UserProductsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProductsVH holder, int position) {
        holder.prodName.setText(list.get(position).getName());
        Glide.with(holder.imageView.getContext()).load(list.get(position).getImage()).into(holder.imageView);
        holder.prodPrice.setText(list.get(position).getPrice() + "$");
        if( list.get(position).getPrice()!=null && list.get(position).getOffer()!=null){
            double afterDiscount= Float.parseFloat(list.get(position).getPrice())*((100-Float.parseFloat(list.get(position).getOffer()))/100);
            holder.descPrice.setText(String.format("%.2f", afterDiscount) + "$");
            holder.prodPrice.setPaintFlags( holder.prodPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            holder.descPrice.setText("No Discount");
        }



    }

    public void setItems(ArrayList<Products> events) {
        this.list=events;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface ClickListener {
        public void productsPage(int position);




    }

    public UserProductsAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
    public ArrayList<Products> list = new ArrayList<>();
    public class UserProductsVH extends RecyclerView.ViewHolder{
        public TextView prodName;
        public ImageView imageView;
        public TextView prodPrice;
        public TextView descPrice;


        public UserProductsVH(@NonNull View itemView) {
            super(itemView);

            prodName = itemView.findViewById(R.id.prod_name);
            imageView = itemView.findViewById(R.id.prod_image);
            prodPrice= itemView.findViewById(R.id.prod_price);
            descPrice=itemView.findViewById(R.id.disc_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.productsPage(getAdapterPosition());
                }
            });
        }
    }
}
