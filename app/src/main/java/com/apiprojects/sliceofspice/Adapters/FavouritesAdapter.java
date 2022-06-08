package com.apiprojects.sliceofspice.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apiprojects.sliceofspice.R;
import com.apiprojects.sliceofspice.models.CartDetails;
import com.apiprojects.sliceofspice.models.Favourites;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavVH>{
    FavouritesAdapter.ClickListener clickListener;
    boolean show;
    public interface ClickListener {
        public void delete(int position);

    }
    public ArrayList<Favourites> list = new ArrayList<>();
    public FavouritesAdapter(boolean show, FavouritesAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
        this.show=show;
    }
    @NonNull
    @Override
    public FavVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitems, parent, false);
        return new FavVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavVH holder, int position) {
        holder.prodName.setText(list.get(position).getName());
        Glide.with(holder.imageView.getContext()).load(list.get(position).getImage()).into(holder.imageView);
        holder.prodPrice.setText(list.get(position).getPrice() + "$");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItems(ArrayList<Favourites> events) {
        this.list=events;
        notifyDataSetChanged();
    }
    public void removeItem(int position) {
        notifyItemRemoved(position);
        list.remove(position);
    }

    public class FavVH extends RecyclerView.ViewHolder{
        public TextView prodName;
        public ImageView imageView;
        public TextView prodPrice;
        public  ImageView prodDelete;
        public TextView descPrice;
        public LinearLayout counter;
        public ImageView minus,add;
        public TextView count;


        public FavVH(@NonNull View itemView) {
            super(itemView);
            descPrice=itemView.findViewById(R.id.desc_price);
            prodName = itemView.findViewById(R.id.cart_name);
            imageView = itemView.findViewById(R.id.cart_image);
            prodPrice=itemView.findViewById(R.id.cart_price);
            prodDelete=itemView.findViewById(R.id.cart_delete);
            descPrice.setVisibility(View.GONE);
            counter= itemView.findViewById(R.id.cnter);
            minus=itemView.findViewById(R.id.minus);
            add=itemView.findViewById(R.id.plus);
            count=itemView.findViewById(R.id.count);
            prodDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.delete(getAdapterPosition());
                }
            });
            if(!show){
                prodDelete.setVisibility(View.GONE);
            }



                counter.setVisibility(View.GONE);

        }
    }
}
