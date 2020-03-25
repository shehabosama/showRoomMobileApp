package com.android.carview.common.SqlHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.carview.common.helper.Message;


public class myDbAdapter {
    myDbHelper myhelper;
    public myDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public long insertData(String name, String pass, String email,String userId,String Address)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.MyPASSWORD, pass);
        contentValues.put(myDbHelper.MyEmail,email);
        contentValues.put(myDbHelper.USER_ID,userId);
        contentValues.put(myDbHelper.ADDRESS,Address);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }
    public String getEmployeeName(String typeData) {
        Cursor cursor = null;
        String empName = "";
        try {
            SQLiteDatabase db = myhelper.getWritableDatabase();
            if(typeData.equals("name")){
                cursor = db.rawQuery("SELECT "+myDbHelper.NAME+" FROM "+myDbHelper.TABLE_NAME, null);
            }else if(typeData.equals("email")){
                cursor = db.rawQuery("SELECT "+myDbHelper.MyEmail+" FROM "+myDbHelper.TABLE_NAME, null);
            }else if (typeData.equals("address")){
                cursor = db.rawQuery("SELECT "+myDbHelper.ADDRESS+" FROM "+myDbHelper.TABLE_NAME, null);
            }else if (typeData.equals("user_id")){
                cursor = db.rawQuery("SELECT "+myDbHelper.USER_ID+" FROM "+myDbHelper.TABLE_NAME, null);
            }
            //  cursor = db.rawQuery("SELECT "+myDbHelper.NAME+" FROM "+myDbHelper.TABLE_NAME, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                if(typeData.equals("name")){
                    empName = cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
                }else if(typeData.equals("email")){
                    empName = cursor.getString(cursor.getColumnIndex(myDbHelper.MyEmail));
                }else if (typeData.equals("address")){
                    empName = cursor.getString(cursor.getColumnIndex(myDbHelper.ADDRESS));
                }else if (typeData.equals("user_id")){
                    empName = cursor.getString(cursor.getColumnIndex(myDbHelper.USER_ID));
                }
            }
            return empName;
        }finally {
            cursor.close();
        }
    }
    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.NAME, myDbHelper.MyPASSWORD, myDbHelper.MyEmail};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            String password =cursor.getString(cursor.getColumnIndex(myDbHelper.MyPASSWORD));
            String email =cursor.getString(cursor.getColumnIndex(myDbHelper.MyEmail));
            buffer.append(cid+ "   " + name + "   " + password + "   " + email +" \n");
        }
        return buffer.toString();
    }

    public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(myDbHelper.TABLE_NAME , myDbHelper.NAME+" = ?",whereArgs);
        return  count;
    }

    public int updateName(String email , String newName,String address)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME,newName);
        contentValues.put(myDbHelper.ADDRESS,address);
        String[] whereArgs= {email};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.MyEmail+" = ?",whereArgs );
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String ADDRESS = "Address";
        private static final String USER_ID = "User_id";
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "myTable";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String NAME = "Name";    //Column II
        private static final String MyPASSWORD= "Password";    // Column III
        private static final String MyEmail= "email";
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ,"+ MyPASSWORD+" VARCHAR(225),"+MyEmail+" VARCHAR(255),"+ADDRESS+" VARCHAR(255),"+USER_ID+" VARCHAR(255));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }
        }
    }
}

