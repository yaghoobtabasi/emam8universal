package com.emam8.emam8_universal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.emam8.emam8_universal.MainActivity;
import com.emam8.emam8_universal.Model.Poems;
import com.emam8.emam8_universal.R;
import com.emam8.emam8_universal.RecyclerPoem;
import com.emam8.emam8_universal.RecyclerPoetcontents;
import com.emam8.emam8_universal.ShowPoem;
import com.emam8.emam8_universal.ShowRawPoem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;


public class PoemsAdapter extends RecyclerView.Adapter<PoemsAdapter.PoemViewHolder> {

    private List<Poems> poem;

    private String catid, gid, poet_id, mode;
    private MediaPlayer mediaPlayer;
    private Boolean isplay;
    Context mContext;
    private ImageView img_play;
    private Integer playing_status = 0, playing_position = 0,old_playing_position=0;


    public PoemsAdapter(List<Poems> poem, String catid, String gid, String poet_id, String mode, Context mContext, MediaPlayer mediaPlayer) {
        this.poem = poem;
        this.catid = catid;
        this.gid = gid;
        this.poet_id = poet_id;
        this.mode = mode;
        this.mContext = mContext;
        this.mediaPlayer = mediaPlayer;


    }


    @NonNull
    @Override
    public PoemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_poem, parent, false);
        return new PoemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PoemViewHolder holder, final int position) {
        final Poems poems = poem.get(position);
        final String poem_id = poems.getArticle_id();
        final String sabk = poems.getSabk();
        final String state = poems.getState();
        final String poet = poems.getPoet();
        final String profile = poems.getProfile();


        String title = poem.get(position).getTitle();
        String poet_name = poem.get(position).getPoet();
        if (poems.getPoet().length() > 4) {
            title = title + "*" + poems.getPoet();
        }
        String profile_pic = poems.getProfile();
        if (profile_pic.length() < 8) {
            profile_pic = "images/icons/emam8_logo_orange.png";

        }
        String image_path = "https://emam8.com/" + profile_pic;
        Uri uri = Uri.parse(String.valueOf(Uri.parse(image_path)));
        Glide.with(mContext).load(uri).circleCrop().into(holder.imgpoet);
        holder.imgpoet.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        if (sabk.length() > 10) {
            holder.img_play.setVisibility(View.VISIBLE);
            holder.img_play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        } else {
            holder.img_play.setVisibility(View.GONE);
        }


        holder.img_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String audio_url = "https://emam8.com/" + sabk;

                try {
                    Log.d("playing_status", playing_status + " position= " + playing_position);
                    if ((playing_status == 0)) {

                        mediaPlayer.setDataSource(mContext, Uri.parse(audio_url));
                        mediaPlayer.prepareAsync();
                        Log.d("play_pause ", "position=" + position + "  playing_status" + playing_status + " first condition");
                        playing_position = position;
                        old_playing_position=position;

                    }
                    if ((playing_status == 2) && (position == playing_position)) {
                        Log.d("play_pause", "position=" + position + "  playing_status" + playing_status + " second condition");
                        mediaPlayer.start();
                        playing_status = 1;
                        holder.img_play.setImageResource(R.drawable.ic_pause_black_24dp);

                    } else if ((playing_status == 1)&& mediaPlayer.isPlaying() && (position == playing_position) ) {
                        mediaPlayer.pause();
                        Log.d("paly_pause", "is_playing " + "fourth condition");
                        playing_status = 2;
                        holder.img_play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    }

                    if (playing_position != position) {


                        old_playing_position=playing_position;
                        playing_position = position;
                        Log.d("play_pause", "position=" + position + "  playing_status" + playing_status + " third condition"+"old_position=>"+old_playing_position);
                        change_play_pause_Image(holder,old_playing_position);
//                        holder.img_play.setImageResource(poem.get(old_playing_position));poem.get(old_playing_position);


                        mediaPlayer.stop();
                        mediaPlayer.reset();
//                        holder.img_play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
//                        mediaPlayer.release();

                        try {
                            mediaPlayer.setDataSource(mContext, Uri.parse(audio_url));
                            mediaPlayer.prepareAsync();
                            playing_status = 3;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }




                    }


                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            if (mp.isPlaying()) {
                                mp.pause();
                                Log.d("paly_pause", "is_playing");

//                                int id=
                                playing_status = 2;
                                holder.img_play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                            } else {
                                mp.start();
                                Log.d("paly_pause", "is_pause");
                                playing_status = 1;
                                holder.img_play.setImageResource(R.drawable.ic_pause_black_24dp);
                            }
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        holder.txtTitle.setText(title);
        holder.txt_poet.setText(poet_name);
        holder.imgpoet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                String poetid = poems.getPoet_id();
                String catid = PoemsAdapter.this.catid;
                String mode = PoemsAdapter.this.mode;
                String gid = PoemsAdapter.this.gid;
                String poet_id = poetid;
//                                                  Toast.makeText(mContext,"Poet id : "+poetid+"catid="+catid,Toast.LENGTH_SHORT).show();
                i = new Intent(mContext, RecyclerPoetcontents.class);
                i.putExtra("poet_id", poet_id);

                i.putExtra("catid", catid);
                i.putExtra("mode", mode);
                i.putExtra("gid", gid);
                mContext.startActivity(i);
            }
        });


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(mContext,poems.getArticle_id()+"", Toast.LENGTH_SHORT).show();
                String sabk = poems.getSabk();
                Intent i;
                if (sabk.length() > 10) {

                    i = new Intent(mContext, ShowPoem.class);
                    i.putExtra("sabk", poems.getSabk());
                    i.putExtra("article_id", poems.getArticle_id());
                    i.putExtra("state", poems.getState());
                    i.putExtra("poet", poems.getPoet());
                    i.putExtra("title", poems.getTitle());
                    mContext.startActivity(i);
                } else {
                    i = new Intent(mContext, ShowRawPoem.class);
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


    public class PoemViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle, txt_poet;
        public ImageView imgpoet, img_play, img_fav;
        public CardView cardView;

        public PoemViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            txt_poet = (TextView) itemView.findViewById(R.id.txt_poet);
            imgpoet = (ImageView) itemView.findViewById(R.id.img_poet);
            img_play = (ImageView) itemView.findViewById(R.id.play_paus_btn);
            cardView = itemView.findViewById(R.id.cardView_poetPage);


        }

        public ImageView getImage() {
            return this.imgpoet;
        }
    }


    private void change_play_pause_Image(final PoemsAdapter.PoemViewHolder holder,final int position)
    {
//        holder.img_play.setImageResource(poem.get(position).getYourMethodName());
        holder.img_play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
//        Log.d("new holder",poem.get(position).getTitle());
    }


}
