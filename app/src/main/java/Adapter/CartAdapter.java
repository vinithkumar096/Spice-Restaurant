package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    public interface ClickListener {
        public void delete(int position);

    }
    public CartAdapter(boolean show,ClickListener clickListener) {
        this.clickListener = clickListener;
        this.show=show;
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


        public CartVH(@NonNull View itemView) {
            super(itemView);

            prodName = itemView.findViewById(R.id.cart_name);
            imageView = itemView.findViewById(R.id.cart_image);
            prodPrice=itemView.findViewById(R.id.cart_price);
            prodDelete=itemView.findViewById(R.id.cart_delete);
            prodDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.delete(getAdapterPosition());
                }
            });
            if(!show){
                prodDelete.setVisibility(View.GONE);
            }

        }
    }
}
