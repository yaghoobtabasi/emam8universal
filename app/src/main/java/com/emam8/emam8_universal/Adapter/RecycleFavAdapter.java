package com.emam8.emam8_universal.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.emam8.emam8_universal.Database;
import com.emam8.emam8_universal.Model.Poems;
import com.emam8.emam8_universal.R;

import java.util.List;

public class RecycleFavAdapter extends RecyclerView.Adapter {

    private List<Poems> poem;
    private String catid, gid, poet_id, mode;
    private MediaPlayer mediaPlayer;
    Context mContext;

    private Integer playing_status = 0, playing_position = 0, old_playing_position = 0;
    Database db;

    public RecycleFavAdapter(List<Poems> poem, String catid, String gid, String poet_id, String mode, MediaPlayer mediaPlayer, Context mContext) {
        this.poem = poem;
        this.catid = catid;
        this.gid = gid;
        this.poet_id = poet_id;
        this.mode = mode;
        this.mediaPlayer = mediaPlayer;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_favpoem, parent, false);
        return new RecycleFavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecycleFavViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle, txt_poet;
        public ImageView imgpoet, img_play, img_fav;
        public CardView cardView;

        public RecycleFavViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title_fav);
            txt_poet = (TextView) itemView.findViewById(R.id.txt_poet_fav);
            imgpoet = (ImageView) itemView.findViewById(R.id.img_poet_fav);
            img_play = (ImageView) itemView.findViewById(R.id.play_paus_btn_fav);
            cardView = itemView.findViewById(R.id.cardView_poetPage_fav);
            img_fav = itemView.findViewById(R.id.like_content_fav);

        }

        public ImageView getImage() {
            return this.imgpoet;
        }
    }

    private void change_play_pause_Image(final PoemsAdapter.PoemViewHolder holder, final int position) {
//        holder.img_play.setImageResource(poem.get(position).getYourMethodName());
        holder.img_play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
//        Log.d("new holder",poem.get(position).getTitle());
    }


}
