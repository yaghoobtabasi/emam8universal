package com.emam8.emam8_universal;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class Database extends SQLiteOpenHelper {
    private static final String TAG = Database.class.getSimpleName();
    public final String path="data/data/com.emam8.emam8_universal/databases/";
    private static final String Name="database.db";
    private static final int DATABASE_VERSION = 2;
    public SQLiteDatabase mydb,mDatabase;
    private final Context mycontext;




    public Database(Context context){
        super(context, Name ,null,DATABASE_VERSION);
        this.mycontext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase arg0){ }

    @Override
    public void onUpgrade(SQLiteDatabase arg0,int arg1, int arg2){

    }

    public void useable() {
        boolean checkdb=checkdb();
        if(checkdb){
        }else{ this.getReadableDatabase();
            try{ copydatabase(); }
            catch(IOException e){
            }
        }
    }
    public void writable() {
        boolean checkdb=checkdb();
        if(checkdb){
        }else{ this.getWritableDatabase();
            try{ copydatabase(); }
            catch(IOException e){
            }
        }
    }

    public void open() {
        mydb= SQLiteDatabase.openDatabase(path + Name, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public void close(){
        mydb.close();
    }

    public boolean checkdb(){
        SQLiteDatabase db=null;
        try{db= SQLiteDatabase.openDatabase(path + Name, null, SQLiteDatabase.OPEN_READONLY); }
        catch(SQLException e){ }
        return db!= null? true: false;
    }

    public void copydatabase() throws IOException{ OutputStream myOutput= new FileOutputStream(path+Name);
        byte[]buffer=new byte[1024];
        int lenght;
        InputStream myInput=mycontext.getAssets().open(Name);
        while((lenght=myInput.read(buffer))>0){
            myOutput.write(buffer,0,lenght); }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }

    public String namayesh(int row,int field, String table){
        Cursor Cursor=mydb.rawQuery("SELECT * FROM "+table,null);
        Cursor.moveToPosition(row);
        String str=Cursor.getString(field);
        return str;
    }


    public String fav_count(){
        Cursor Cursor=mydb.rawQuery("select COUNT(*) as cnt FROM content_fav",null);
        Cursor.moveToPosition(0);
        String str=Cursor.getString(0);
        return str;
    }

    public String namayesh_by_id(String id,int field, String table){
//        Log.i("info","article_id ="+id+" table = "+table);
//	    int content_id=Integer.parseInt(id);
        Cursor Cursor=mydb.rawQuery("SELECT * FROM "+table +" WHERE id=?",new String[] {id});
        Cursor.moveToPosition(0);
        String str=Cursor.getString(field);
        return str;
    }

    public Boolean add_to_app_contents(String id,String title,String body,String sname,String cname,String catid,String sectionid,String state,String sabk,String poet_id,String poet_name)
    {
        Database db=new Database(mycontext);
        db.open();
        db.writable();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("title", title);
        cv.put("body", body);
        cv.put("catid", catid);
        cv.put("sectionid", sectionid);
        cv.put("cname", cname);
        cv.put("sname", sname);
        cv.put("sabk", sabk);
        cv.put("poet_id", poet_id);
        cv.put("poet_name", poet_name);
        cv.put("state", state);
        long res=mydb.insert("app_contents",null,cv);
        Log.i("info"," Added app_contents ="+res);
        db.close();

        return true;
    }

    public Boolean check_fav_content(String id)
    {
        Cursor Cursor=mydb.rawQuery("SELECT * FROM content_fav WHERE content_id=? ",new String[] {id} );

        int ids = Cursor.getCount();
        if(ids>0)
            return true;
        else
            return false;
    }

    public Boolean check_section_added(){

        Cursor cursor=mydb.rawQuery("SELECT * FROM section ",null);
        int cnt=cursor.getCount();
        if(cnt>0)
            return true;
        else

        return false;
    }

    public Boolean check_section_exist(String id){
        Cursor Cursor=mydb.rawQuery("SELECT * FROM section WHERE id=? ",new String[] {id} );
        int ids = Cursor.getCount();
        if(ids>0)
            return true;
        else
            return false;
    }

    public Long add_to_section(String id,String title,String count,String ordering,Context mycontext){
        Database db=new Database(mycontext);
        db.open();
        db.writable();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("title", title);
        cv.put("count", count);
        cv.put("ordering", ordering);
        long res=mydb.insert("section",null,cv);
        Log.i("info"," Added section="+res);
        db.close();
        return res;
    }

    public Cursor load_from_section(){
        Cursor Cursor=mydb.rawQuery("SELECT * FROM section ORDER BY ordering",null);
        return Cursor;
    }

    public Boolean check_category_added(String sectionId){

        Cursor cursor=mydb.rawQuery("SELECT * FROM category ",null);
        int cnt=cursor.getCount();
        if(cnt>0)
            return true;
        else

            return false;
    }
    public Boolean check_category_exist(String id){
        Cursor Cursor=mydb.rawQuery("SELECT * FROM category WHERE id=? ",new String[] {id} );
        int ids = Cursor.getCount();
        if(ids>0)
            return true;
        else
            return false;
    }

    public Long add_to_category(String id,String title,String count,String ordering,Context mycontext) {
        Database db = new Database(mycontext);
        db.open();
        db.writable();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("title", title);
        cv.put("count", count);
        cv.put("ordering", ordering);
        long res = mydb.insert("category", null, cv);
        Log.i("info", " Added section=" + res);
        db.close();
        return res;
    }

    public Cursor load_from_category(){
        Cursor Cursor=mydb.rawQuery("SELECT * FROM category ORDER BY ordering",null);
        return Cursor;
    }

    public Cursor get_poem(){



        Cursor Cursor=mydb.rawQuery("SELECT * FROM app_contents",null);

        return Cursor;

    }
    public Cursor get_poem_cat(String catid){



        Cursor Cursor=mydb.rawQuery("SELECT * FROM app_contents where catid=?",new String[]{catid});

        return Cursor;

    }

    public Cursor get_poem_free(){



        Cursor Cursor=mydb.rawQuery("SELECT * FROM app_contents WHERE state=1 ",null);

        return Cursor;

    }

    public Cursor get_poem_free_cat(String catid){



        Cursor Cursor=mydb.rawQuery("SELECT * FROM app_contents WHERE state=1 AND catid=? ",new String[]{catid});

        return Cursor;

    }
    public Cursor get_poem_category(){



        Cursor Cursor=mydb.rawQuery("SELECT * FROM app_cats order by ordering ",null);

        return Cursor;

    }

    public Cursor get_poem_fav(){

        final String MY_QUERY = "SELECT content_fav.content_id AS content_id,app_contents.title AS content_title,app_contents.sabk AS content_sabk,app_contents.state AS content_state FROM content_fav INNER JOIN app_contents ON content_fav.content_id=app_contents.id ";
//        final String MY_QUERY = "SELECT *  FROM content_fav fav LEFT JOIN app_contents content ON fav.content_id=content.id ";
        Cursor Cursor=mydb.rawQuery(MY_QUERY,null);


//        db.rawQuery(MY_QUERY, new String[]{String.valueOf(propertyId)});

        return Cursor;

    }

    //-------------------- z	ظاهرا این خط برای لیست علاقه مندی ها است
    public void Fav_update(String table, String id, String value) {
        ContentValues cv = new ContentValues();
        cv.put("fav", value);
        mydb.update(table, cv, "id='" + id + "'", null);
    }

    public void activate_contents(String ref_ID,String mobile)
    {
        Log.i(TAG,"start Activation");
        mDatabase=SQLiteDatabase.openDatabase(path + Name, null, SQLiteDatabase.OPEN_READWRITE);
//        mDatabase=openOrCreateDatabase(Name,MODE_PRIVATE,null);
        String sql="UPDATE app_contents SET state=?";
        mDatabase.execSQL(sql,new String[]{"1"});
        Log.i(TAG,"Execute active app_contents");


        String sql2="UPDATE version SET ref_code=?,status=?";
        mDatabase.execSQL(sql2,new String[]{ref_ID,"1"});
        mDatabase.close();
        Log.i(TAG,"Execute active version");

        mDatabase.close();
        mydb.close();

        Log.i("info","Update all rows");

    }


    public void write_token(String token,String mobile)
    {
        Log.i(TAG,"start write token"+"mobile ="+mobile+"token="+token);
        mDatabase=SQLiteDatabase.openDatabase(path + Name, null, SQLiteDatabase.OPEN_READWRITE);
//        mDatabase=openOrCreateDatabase(Name,MODE_PRIVATE,null);



        String sql2="UPDATE version SET activation_code=?,mobile=? WHERE 1";
        mDatabase.execSQL(sql2,new String[]{token,mobile});
        mDatabase.close();
        Log.i(TAG,"Execute write token on  version");

        mDatabase.close();
        mydb.close();



    }

    public long add_fav(String article_id) {
        Database db=new Database(mycontext);
        db.open();
        db.writable();
        ContentValues cv = new ContentValues();
        cv.put("content_id", article_id);
        long res=mydb.insert("content_fav",null,cv);
        Log.i("info"," Added fav="+res);
        db.close();
        return res;
    }

    public int del_fav(String article_id) {

        int res_del=mydb.delete("content_fav","content_id "+"="+article_id,null);
//        mydb.close();
        Log.i("info"," Deleted fav="+res_del);
        return res_del;
    }
    public String get_price()
    {
        Cursor Cursor=mydb.rawQuery("SELECT * FROM version",null);
        Cursor.moveToPosition(0);
        String str=Cursor.getString(3);
        String price= str;
        return  price;
    }
    public boolean check_activated_app()
    {


        Cursor Cursor=mydb.rawQuery("SELECT * FROM version",null);
        Cursor.moveToPosition(0);
        String str=Cursor.getString(5);
        String ver= str;
        if(ver.compareTo("1")==0)
        return  true;

        return false;
    }


//------------------------c 	این خط هم برای آپدیت هست ولی استفاده نشده است

    /**
     public void delete_v1_database() {
     boolean delete_old_database = G.preference.getBoolean("DELETE_OLD_DATABASE", false);
     if ( !delete_old_database) {
     // Toast.makeText(this, "welcome", Toast.LENGTH_SHORT).show();

     File dir = new File(DBLOCATION);
     if (dir.isDirectory())
     {
     String[] children = dir.list();
     for (int i = 0; i < children.length; i++)
     {
     new File(dir, children[i]).delete();
     }
     }
     SharedPreferences.Editor editor = G.preference.edit();
     editor.putBoolean("DELETE_OLD_DATABASE", true);
     editor.commit();

     }
     }  **/



}
