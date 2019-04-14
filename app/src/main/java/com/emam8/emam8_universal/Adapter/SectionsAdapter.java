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
import android.widget.Toast;

import com.emam8.emam8_universal.Model.Sections;
import com.emam8.emam8_universal.R;
import com.emam8.emam8_universal.category_list;

import java.util.List;

public class SectionsAdapter extends RecyclerView.Adapter<SectionsAdapter.SectionViewHolder> {

    List<Sections> sections;
    Context mContext;

    public SectionsAdapter(List<Sections> sections, Context mContext) {
        this.sections = sections;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.section_list_row,parent,false);

        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SectionViewHolder holder, final int position) {
            Sections section=sections.get(position);
            holder.txtTitleSec.setText(section.getTitle());

            holder.rltvsection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sectionid=sections.get(position).getSectionid();
                    final Intent intent;
                     intent = new Intent(mContext, category_list.class);
                    intent.putExtra("sectionid",sectionid);
                    mContext.startActivity(intent);

                    Toast.makeText(mContext,sections.get(position).getSectionid()+" ",Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitleSec;
        TextView txtCount;
        RelativeLayout rltvsection;
        public SectionViewHolder(View itemView) {
            super(itemView);
            mContext=itemView.getContext();
            txtTitleSec=(TextView)itemView.findViewById(R.id.txt_section_title);
//            rltvsection=(RelativeLayout)itemView.findViewById(R.id.rltvsec);
        }
    }

}
