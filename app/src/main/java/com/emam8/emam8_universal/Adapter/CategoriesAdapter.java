package com.emam8.emam8_universal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emam8.emam8_universal.Model.Categories;
import com.emam8.emam8_universal.R;
import com.emam8.emam8_universal.RecyclerPoem;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategorieViewHolder>{

    List<Categories> categoriesList;
    Context mContext;

    public CategoriesAdapter(List<Categories> categoriesList, Context mContext) {
        this.categoriesList = categoriesList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CategorieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_list_row,parent,false);

        return new CategoriesAdapter.CategorieViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CategorieViewHolder holder, final int position) {
        Categories category=categoriesList.get(position);
        holder.txtTitleCat.setText(category.getTitle());

        holder.rltvcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String catid=categoriesList.get(position).getCatid();
                final Intent intent;
                intent = new Intent(mContext, RecyclerPoem.class);
                intent.putExtra("catid",catid);
                mContext.startActivity(intent);
//                Toast.makeText(mContext,categoriesList.get(position).getCatid()+" ",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class CategorieViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitleCat;
        RelativeLayout rltvcat;
        public CategorieViewHolder(View itemView) {
            super(itemView);
            mContext=itemView.getContext();
            txtTitleCat=(TextView)itemView.findViewById(R.id.txt_category_title);
            rltvcat=(RelativeLayout)itemView.findViewById(R.id.rltvcat);
        }
    }

}
