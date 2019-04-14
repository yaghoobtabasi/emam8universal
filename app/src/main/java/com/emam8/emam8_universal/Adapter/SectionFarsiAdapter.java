package com.emam8.emam8_universal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.emam8.emam8_universal.Cat_Farsi_Poems;
import com.emam8.emam8_universal.Model.SecFarsiPoem;
import com.emam8.emam8_universal.R;
import com.emam8.emam8_universal.RecyclerPoem;

import java.util.List;

public class SectionFarsiAdapter extends RecyclerView.Adapter<SectionFarsiAdapter.SectionfarsiViewHolder>  {

    List<SecFarsiPoem> secFarsiPoemList;
    Context context;

    public SectionFarsiAdapter(List<SecFarsiPoem> secFarsiPoems, Context context) {
        this.secFarsiPoemList = secFarsiPoems;
        this.context = context;
    }

    @NonNull
    @Override
    public SectionfarsiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_sec_farsi_poem,parent,false);
        return new SectionfarsiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionfarsiViewHolder holder, final int position) {

        SecFarsiPoem sec = secFarsiPoemList.get(position);
        holder.txtTitleSecFarsi.setText(sec.getTitle());
        holder.txtCountSecFarsi.setText(sec.getCount());


        holder.cardViewSecFarsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sectionid=secFarsiPoemList.get(position).getId();
                final Intent intent;
                intent = new Intent(context, Cat_Farsi_Poems.class);
                intent.putExtra("sectionid",sectionid);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return secFarsiPoemList.size();
    }

    public class SectionfarsiViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitleSecFarsi;
        TextView txtCountSecFarsi;
        CardView cardViewSecFarsi;

        public SectionfarsiViewHolder(View itemView) {
            super(itemView);

            txtTitleSecFarsi = itemView.findViewById(R.id.textView_sec_farsi_poem);
            txtCountSecFarsi = itemView.findViewById(R.id.count_sec_farsi_poem);
            cardViewSecFarsi = itemView.findViewById(R.id.cardView_secFarsi);

        }
    }
}
