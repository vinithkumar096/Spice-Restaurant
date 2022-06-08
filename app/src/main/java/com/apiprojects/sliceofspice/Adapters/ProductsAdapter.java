package com.apiprojects.sliceofspice.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apiprojects.sliceofspice.R;
import com.apiprojects.sliceofspice.models.Category;
import com.apiprojects.sliceofspice.models.Products;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsVH> {

    ClickListener clickListener;
    public ArrayList<Products> list = new ArrayList<>();
    @NonNull
    @Override
    public ProductsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productsitems, parent, false);
        return new ProductsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsVH holder, int position) {
        holder.prodName.setText(list.get(position).getName());
        Glide.with(holder.imageView.getContext()).load(list.get(position).getImage()).into(holder.imageView);

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

        public void delete(int position);

        public void detailsPage(int position);



    }

    public ProductsAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
    public void removeItem(int position) {
        notifyItemRemoved(position);
        list.remove(position);
    }

    public class ProductsVH extends RecyclerView.ViewHolder{
        public TextView prodName;
        public ImageView imageView;
        public ImageView prodEdit;
        public  ImageView prodDelete;


        public ProductsVH(@NonNull View itemView) {
            super(itemView);

            prodName = itemView.findViewById(R.id.prod_name);
            imageView = itemView.findViewById(R.id.prod_image);
            prodEdit= itemView.findViewById(R.id.prod_edit);
            prodDelete=itemView.findViewById(R.id.prod_delete);
            prodDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.delete(getAdapterPosition());
                }
            });
            prodEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.detailsPage(getAdapterPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.productsPage(getAdapterPosition());
                }
            });
        }
    }
}
