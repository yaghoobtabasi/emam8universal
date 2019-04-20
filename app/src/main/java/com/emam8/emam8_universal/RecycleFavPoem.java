package com.emam8.emam8_universal;

import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.emam8.emam8_universal.Adapter.PoemsAdapter;
import com.emam8.emam8_universal.Model.Poems;

import java.util.ArrayList;
import java.util.List;

public class RecycleFavPoem extends AppCompatActivity {

    public List<Poems> poem = new ArrayList<>();
    private String catid, mode, gid, poet_id, allow_private, lang, title;
    private RecyclerView recyclerView;
    private Cursor cursor;
    private Database db;
    private MediaPlayer mediaPlayer;


    private PoemsAdapter adapter;
    private LinearLayoutManager layoutManager;


    //variables for pagination
    private Boolean isLoading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount, previoustotal = 0;
    private int view_threshold = 10;
    private int page_number = 1;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_fav_poem);

        recyclerView = (RecyclerView) findViewById(R.id.poem_recycler_fav);
        adapter = new PoemsAdapter(poem, catid, gid, poet_id, mode, RecycleFavPoem.this, mediaPlayer);
        layoutManager = new LinearLayoutManager(RecycleFavPoem.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        load_from_Database();

    }

    private void load_from_Database() {

        db = new Database(this);
        db.open();
        db.useable();

        cursor = db.get_poem();

        if (cursor.getCount() == 0) {

        } else {
            while (cursor.moveToNext()) {
                String title = cursor.getString(1);
                String sabk = cursor.getString(9);
                String poet = cursor.getString(4);
                String article_id = cursor.getString(0);
                String state = cursor.getString(10);
                String profile = cursor.getString(12);
                String poet_id = cursor.getString(3);

                poem.add(new Poems(title, sabk, poet, article_id, state, profile, poet_id));
            }

            adapter.notifyDataSetChanged();
            db.close();
        }


    }
}
