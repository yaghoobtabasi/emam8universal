package com.emam8.emam8_universal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.emam8.emam8_universal.Model.Poems;
import com.emam8.emam8_universal.R;
import com.emam8.emam8_universal.ShowPoem;
import com.emam8.emam8_universal.ShowRawPoem;

import java.util.List;


public class PoetsContentsAdapter extends RecyclerView.Adapter<PoetsContentsAdapter.PoemViewHolder> {


    List<Poems> poem;
    String catid,gid,poet_id,mode;
    Context mContext;

    public PoetsContentsAdapter(List<Poems> poem, String catid, String gid, String poet_id,String mode, Context mContext) {
        this.poem = poem;
        this.catid = catid;
        this.gid = gid;
        this.poet_id = poet_id;
        this.mode = mode;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PoemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.poem_list_row,parent,false);

        return new PoemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoemViewHolder holder, final int position) {
        final Poems poems=poem.get(position);
        final String poem_id=poems.getArticle_id();
        final String sabk=poems.getSabk();
        final String state=poems.getState();
        final String poet=poems.getPoet();
        final String profile=poems.getProfile();




        String title=poems.getTitle();
        if(poems.getPoet().length()>4)
        {
            title=title+"*"+poems.getPoet();
        }
        String profile_pic=poems.getProfile();
        if(profile_pic.length()<8)
        {
            profile_pic="images/icons/emam8_logo_orange.png";

        }
        String image_path="https://emam8.com/"+profile_pic;
        Uri uri = Uri.parse(String.valueOf(Uri.parse(image_path)));
        Glide.with(mContext).load(uri).into(holder.imgpoet);


        holder.txtTitle.setText(title);

        holder.rltv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String sabk=poems.getSabk();
                Intent i;
                if(sabk.length()>10){

                    i=new Intent(mContext,ShowPoem.class);
                    i.putExtra("sabk", poems.getSabk());
                    i.putExtra("article_id", poems.getArticle_id());
                    i.putExtra("state", poems.getState());
                    i.putExtra("poet", poems.getPoet());
                    i.putExtra("title", poems.getTitle());
                    mContext.startActivity(i);
                }else
                {
                    i=new Intent(mContext, ShowRawPoem.class);
                    i.putExtra("article_id", poems.getArticle_id());
                    i.putExtra("state", poems.getState());
                    i.putExtra("poet", poems.getPoet());
                    i.putExtra("title", poems.getTitle());
                    mContext.startActivity(i);
                }

//


            }
        });

    }

    @Override
    public int getItemCount() {
        return poem.size();
    }



    public class PoemViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle;
        public ImageView imgpoet;
        public RelativeLayout rltv;
        public PoemViewHolder(View itemView) {
            super(itemView);
            txtTitle=(TextView)itemView.findViewById(R.id.txt_title);
            imgpoet=(ImageView)itemView.findViewById(R.id.img_poet);
            rltv=(RelativeLayout)itemView.findViewById(R.id.rltv);

        }
        public ImageView getImage(){ return this.imgpoet;}
    }
}
