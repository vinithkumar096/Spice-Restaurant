package Adapter;

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



        public UserProductsVH(@NonNull View itemView) {
            super(itemView);

            prodName = itemView.findViewById(R.id.prod_name);
            imageView = itemView.findViewById(R.id.prod_image);
            prodPrice= itemView.findViewById(R.id.prod_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.productsPage(getAdapterPosition());
                }
            });
        }
    }
}
