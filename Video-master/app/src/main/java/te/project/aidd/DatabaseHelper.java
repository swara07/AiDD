package te.project.aidd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="register.db";
    public static final String TABLE_NAME="registeruser";
    public static final String TABLE_COLOR = "colormatch";
    public static final String TABLE_COLORMATCH_ANALYSIS="colormatchanalysis";
    public static final String COL_1="ID";
    public static final String COL_2="name";
    public static final String COL_3="email";
//    email here is doctors email and username is parents email
    public static final String COL_4="username";
    public static final String COL_5="password";
    public static final String COL_6="childPassword";
    private static final String game_1 = "game_1";
    private static final String game_2 = "game_2";
    private static final String game_3 = "game_3";
    private static final String game_4 = "game_4";
    private static final String game_5 = "game_5";
    private static int countID=1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, email TEXT,username TEXT UNIQUE , password TEXT,childPassword TEXT)");
        db.execSQL("CREATE TABLE colormatch ( ID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR,parentname VARCHAR, game_1 INTEGER, game_2 INTEGER, game_3 INTEGER, game_4 INTEGER, game_5 INTEGER)");
        db.execSQL("CREATE TABLE colormatchanalysis ( ID INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR,parentname VARCHAR, game_1 INTEGER, game_2 INTEGER, game_3 INTEGER, game_4 INTEGER, game_5 INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLORMATCH_ANALYSIS);
        onCreate(db);

    }
    public long addUser(String name, String email,String user, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cV=new ContentValues();
        ContentValues cc = new ContentValues();
        cV.put("name",name);
        cV.put("email",email);
        cV.put("username",user);
        cV.put("password",password);
        cV.put("childPassword",name+String.valueOf(countID));
        cc.put("name", name);
        cc.put("parentname",user);
        cc.put(game_1, 0);
        cc.put(game_2, 0);
        cc.put(game_3, 0);
        cc.put(game_4, 0);
        cc.put(game_5, 0);
        long res=db.insert("registeruser",null,cV);
        db.insert(TABLE_COLOR, null, cc);
        db.insert(TABLE_COLORMATCH_ANALYSIS,null,cc);
        countID++;
        db.close();
        return res;
    }

    public Boolean checkUser(String username,String password){
//        String[] columns={COL_1};
        SQLiteDatabase db=this.getReadableDatabase();
//        String selection=COL_4 + "=?" + "and" + COL_5 + "=?";
//        String[] selectionArgs={username,password};
//        Cursor cursor=db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        Cursor cursor=db.rawQuery("select * from registeruser where username=? and password=?", new String[]{username,password});
//        int count=cursor.getCount();
//        cursor.close();
//        db.close();
//        if(count>0)
//            return true;
//        else
//            return false;
        if (cursor.getCount()>0)return true;
        else return false;
    }
    public Boolean childCheckUser(String name,String password ){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from registeruser where name=? and childPassword=?", new String[]{name,password});
        if (cursor.getCount()>0)return true;
        else return false;
    }
    public void addscore(int score, String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT * FROM colormatch WHERE name=?", new String[]{name});
        if (cursor.moveToFirst()) {
            int c1 = cursor.getInt(cursor.getColumnIndex(game_2));
            int c2 = cursor.getInt(cursor.getColumnIndex(game_3));
            int c3 = cursor.getInt(cursor.getColumnIndex(game_4));
            int c4 = cursor.getInt(cursor.getColumnIndex(game_5));
            values.put(game_1, c1);
            values.put(game_2, c2);
            values.put(game_3, c3);
            values.put(game_4, c4);
            values.put(game_5, score);
        }
        db.update(TABLE_COLOR, values, "name=?", new String[]{name});
        db.close();

    }

    public int[] color_match_arr(String name) {
        int[] array=new int[5];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM colormatch WHERE parentname=?", new String[]{name});
        if (cursor.moveToFirst()) {
            int c1 = cursor.getInt(cursor.getColumnIndex(game_1));
            int c2 = cursor.getInt(cursor.getColumnIndex(game_2));
            int c3 = cursor.getInt(cursor.getColumnIndex(game_3));
            int c4 = cursor.getInt(cursor.getColumnIndex(game_4));
            int c5 =  cursor.getInt(cursor.getColumnIndex(game_5));
            array[0]=c1;
            array[1]=c2;
            array[2]=c3;
            array[3]=c4;
            array[4]=c5;

        }
        return array;

    }
    public void time_analysis(int analysis, String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT * FROM colormatchanalysis  WHERE name=?", new String[]{name});
        if (cursor.moveToFirst()) {
            int c1 = cursor.getInt(cursor.getColumnIndex(game_2));
            int c2 = cursor.getInt(cursor.getColumnIndex(game_3));
            int c3 = cursor.getInt(cursor.getColumnIndex(game_4));
            int c4 = cursor.getInt(cursor.getColumnIndex(game_5));
            values.put(game_1, c1);
            values.put(game_2, c2);
            values.put(game_3, c3);
            values.put(game_4, c4);
            values.put(game_5, analysis);
        }
        db.update(TABLE_COLORMATCH_ANALYSIS, values, "name=?", new String[]{name});
        db.close();

    }

    public int[] time_analysis_graph(String name) {
        int[] array=new int[5];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM colormatchanalysis WHERE parentname=?", new String[]{name});
        if (cursor.moveToFirst()) {
            int c1 = cursor.getInt(cursor.getColumnIndex(game_1));
            int c2 = cursor.getInt(cursor.getColumnIndex(game_2));
            int c3 = cursor.getInt(cursor.getColumnIndex(game_3));
            int c4 = cursor.getInt(cursor.getColumnIndex(game_4));
            int c5 =  cursor.getInt(cursor.getColumnIndex(game_5));
            array[0]=c1;
            array[1]=c2;
            array[2]=c3;
            array[3]=c4;
            array[4]=c5;

        }
        return array;

    }

}
