package com.apiprojects.sliceofspice.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apiprojects.sliceofspice.R;
import com.apiprojects.sliceofspice.models.Category;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserCatAdapter extends RecyclerView.Adapter<UserCatAdapter.UserCategoryVH>  {
    public ArrayList<Category> list = new ArrayList<>();
   ClickListener clickListener;
    public interface ClickListener {
        public void productsPage(int position);

    }
    public void setItems(ArrayList<Category> events) {
        this.list=events;
        notifyDataSetChanged();
    }
    public UserCatAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public UserCategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usercatitems, parent, false);
        return new UserCategoryVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCategoryVH holder, int position) {
        holder.catName.setText(list.get(position).getName());
        Glide.with(holder.imageView.getContext()).load(list.get(position).getImageurl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UserCategoryVH extends RecyclerView.ViewHolder{
        public TextView catName;
        public ImageView imageView;



        public UserCategoryVH(@NonNull View itemView) {
            super(itemView);

            catName = itemView.findViewById(R.id.cat_name);
            imageView = itemView.findViewById(R.id.cat_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.productsPage(getAdapterPosition());
                }
            });
        }
    }
}
