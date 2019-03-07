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

import com.emam8.emam8_universal.Model.Poems_category_model;
import com.emam8.emam8_universal.R;
import com.emam8.emam8_universal.RecyclerPoem;


import java.util.List;

public class PoemsCategoryAdapter extends RecyclerView.Adapter<PoemsCategoryAdapter.Category_View_Holder> {

    List<Poems_category_model> category;
    Context mContext;
    String mode;

    public PoemsCategoryAdapter(List<Poems_category_model> category, Context mContext, String mode) {
        this.category = category;
        this.mContext = mContext;
        this.mode = mode;
    }

    @NonNull
    @Override
    public Category_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_row,parent,false);
        return new Category_View_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Category_View_Holder holder, int position) {
        Poems_category_model cat=category.get(position);
        final String total=cat.getTotal();
        final String id=cat.getCatid();
        final String title=cat.getTitle();





        holder.txtTitle.setText(title);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.compareTo("all")==0){
                    Intent intent = new Intent(mContext, RecyclerPoem.class);
                    intent.putExtra("catid",id);
                    mContext.startActivity(intent);
                }





            }
        });
//        holder.txtTotal.setText(cat.getTotal());

    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class Category_View_Holder extends RecyclerView.ViewHolder{
        public RelativeLayout relativeLayout;
        public TextView txtTitle;

    public Category_View_Holder(View itemView) {
        super(itemView);
        txtTitle=(TextView) itemView.findViewById(R.id.txt_title_cat);
        relativeLayout=(RelativeLayout) itemView.findViewById(R.id.rltv_cat);
//        txtTotal=(TextView) itemView.findViewById(R.id.cat_total);

    }
}

}
