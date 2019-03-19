package com.emam8.emam8_universal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emam8.emam8_universal.Model.CatFarsiPoem;
import com.emam8.emam8_universal.R;
import com.emam8.emam8_universal.RecyclerPoem;

import java.util.List;

public class CategoryFarsiAdapter extends RecyclerView.Adapter<CategoryFarsiAdapter.CategoryFarsiViewHolder> {

    List<CatFarsiPoem> catFarsiPoems;
    Context context;

    public CategoryFarsiAdapter(List<CatFarsiPoem> catFarsiPoems, Context context) {
        this.catFarsiPoems = catFarsiPoems;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryFarsiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_cat_farsi_poem,parent,false);
        return new CategoryFarsiAdapter.CategoryFarsiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryFarsiViewHolder holder, final int position) {

        final CatFarsiPoem cat = catFarsiPoems.get(position);
        holder.txtTitleCatFarsi.setText(cat.getTitle());
        holder.txtCountCatFarsi.setText(cat.getCount());

        holder.cardViewCatFarsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String catid=catFarsiPoems.get(position).getId();
                final Intent intent;
//                intent = new Intent(context, ListPoemsFarsiPoem.class);
                intent = new Intent(context, RecyclerPoem.class);
                intent.putExtra("catid",catid);
                intent.putExtra("mode","all");
                intent.putExtra("gid","0");
                context.startActivity(intent);
                Log.i("inf", "onClick:"+catid);

            }
        });
    }

    @Override
    public int getItemCount() {
        return catFarsiPoems.size();
    }

    public class CategoryFarsiViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitleCatFarsi;
        TextView txtCountCatFarsi;
        CardView cardViewCatFarsi;

        public CategoryFarsiViewHolder(View itemView) {
            super(itemView);

            txtTitleCatFarsi = itemView.findViewById(R.id.textView_cat_farsi_poem);
            txtCountCatFarsi = itemView.findViewById(R.id.count_cat_farsi_poem);
            cardViewCatFarsi = itemView.findViewById(R.id.cardView_catFarsi);
        }
    }
}
