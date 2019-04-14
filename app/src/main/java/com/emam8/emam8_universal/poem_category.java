package com.emam8.emam8_universal;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.emam8.emam8_universal.Adapter.PoemsCategoryAdapter;
import com.emam8.emam8_universal.Model.Poems_category_model;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class poem_category extends AppCompatActivity {


    private List<Poems_category_model> cat=new ArrayList<>();
    public  ArrayList<String> list_ID_cat,list_total_cat,list_title_cat;
    String temp_id,temp_title,temp_total;
    private RecyclerView recyclerView;
    private PoemsCategoryAdapter adapter;
    private Cursor poem_cursor;
    public String mode;
    Database db;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_category);

        list_title_cat = new ArrayList<>();
        list_total_cat=new ArrayList<>();
        list_ID_cat=new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        mode= bundle.getString("mode");



        recyclerView=(RecyclerView)findViewById(R.id.poem_category_layout);
        adapter=new PoemsCategoryAdapter(cat,poem_category.this,mode);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        db=new Database(getApplicationContext());
        db.useable();
        db.open();
//        db.get_poem();
        poem_cursor=db.get_poem_category();


        setData();
    }

    private void setData(){


        if(poem_cursor.getCount()==0)
        {
//            Toast.makeText(this,"No data to show",Toast.LENGTH_SHORT).show();
        }
        else{
            while (poem_cursor.moveToNext()) {
                temp_title = poem_cursor.getString(1);
                temp_total = poem_cursor.getString(4);
                temp_id = poem_cursor.getString(0);


                cat.add(new Poems_category_model(temp_title, temp_id, temp_total));

                list_title_cat.add(temp_title);
                list_total_cat.add(temp_total);
                list_ID_cat.add(temp_id);


            }


            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

