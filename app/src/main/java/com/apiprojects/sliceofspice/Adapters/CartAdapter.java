package com.apiprojects.sliceofspice.Adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apiprojects.sliceofspice.R;
import com.apiprojects.sliceofspice.models.CartDetails;
import com.apiprojects.sliceofspice.models.Products;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter  extends RecyclerView.Adapter<CartAdapter.CartVH>{
    ClickListener clickListener;
    boolean show;
    boolean counterShow;
    public interface ClickListener {
        public void delete(int position);

    }
    public CartAdapter(boolean show,boolean counterShow,ClickListener clickListener) {
        this.clickListener = clickListener;
        this.show=show;
        this.counterShow=counterShow;
    }
    public ArrayList<CartDetails> list = new ArrayList<>();
    @NonNull
    @Override
    public CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitems, parent, false);
        return new CartVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartVH holder, int position) {
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
    public void setItems(ArrayList<CartDetails> events) {
        this.list=events;
        notifyDataSetChanged();
    }
    public void removeItem(int position) {
        notifyItemRemoved(position);
        list.remove(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CartVH extends RecyclerView.ViewHolder{
        public TextView prodName;
        public ImageView imageView;
        public TextView prodPrice;
        public  ImageView prodDelete;
        public TextView descPrice;
        public LinearLayout counter;
        public ImageView minus,add;
        public TextView count;


        public CartVH(@NonNull View itemView) {
            super(itemView);
            descPrice=itemView.findViewById(R.id.desc_price);
            prodName = itemView.findViewById(R.id.cart_name);
            imageView = itemView.findViewById(R.id.cart_image);
            prodPrice=itemView.findViewById(R.id.cart_price);
            prodDelete=itemView.findViewById(R.id.cart_delete);
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
            if(!counterShow){
                counter.setVisibility(View.GONE);
            }
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int cnt=Integer.parseInt(count.getText().toString());

                    count.setText(Integer.toString(cnt+1));
                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Integer.parseInt(count.getText().toString())!=0){
                        int cnt=Integer.parseInt(count.getText().toString());
                        count.setText(Integer.toString(cnt- 1));
                    }


                }
            });

        }
    }
}
