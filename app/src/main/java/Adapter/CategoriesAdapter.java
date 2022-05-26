package Adapter;

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

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryVH> {
    public ArrayList<Category> list = new ArrayList<>();
    ClickListener clickListener;
    public interface ClickListener {
        public void productsPage(int position);

        public void delete(int position);

        public void detailsPage(int position);



    }
    public CategoriesAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
    public void removeItem(int position) {
        notifyItemRemoved(position);
        list.remove(position);
    }

    public void setItems(ArrayList<Category> events) {
        this.list=events;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoriesitem, parent, false);
        return new CategoryVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVH holder, int position) {
        holder.catName.setText(list.get(position).getName());
        Glide.with(holder.imageView.getContext()).load(list.get(position).getImageurl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CategoryVH extends RecyclerView.ViewHolder{
        public TextView catName;
        public ImageView imageView;
        public ImageView catEdit;
        public  ImageView catDelete;


        public CategoryVH(@NonNull View itemView) {
            super(itemView);

            catName = itemView.findViewById(R.id.cat_name);
            imageView = itemView.findViewById(R.id.cat_image);
            catEdit= itemView.findViewById(R.id.cat_edit);
            catDelete=itemView.findViewById(R.id.cat_delete);
            catDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.delete(getAdapterPosition());
                }
            });
            catEdit.setOnClickListener(new View.OnClickListener() {
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
