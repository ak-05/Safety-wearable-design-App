package com.example.siddharth.tcoe;


 // Created by Akshay Uppal on 7/11/2016.

 //This is the Database Handler file which controls the handling of the database.It makes sure that the data such as contacts
  //and user profile info gets saved and are not deleted even if user closes the app.

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.telephony.SmsManager;

public class ProfileDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "profileDB.db";
    public static final String TABLE_PRODUCTS = "products";// This is the table which contains the details about the user profile
    public static final String TABLE_PRODUCTS_C = "profiles";//This is the table which contains the details about the contacts.
    public static final String COLUMN_ID = "_id";//This is  a common  attribute for both tables which is basically the primary key
    public static final String COLUMN_PRODUCTNUM = "productnum";//This is the attribute for the contacts table which contains the info about Mobile number of the user.
    public static final String COLUMN_PRODUCTNAME = "productname";//This also a common attribute for both the tables

    //We need to pass database information along to superclass
    public ProfileDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //Here we create both the tables
        String query = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCTNAME + " TEXT " +
                ");";
        db.execSQL(query);

         query = "CREATE TABLE " + TABLE_PRODUCTS_C + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCTNAME + " TEXT, " +
                COLUMN_PRODUCTNUM + " TEXT " +
                ");";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //in case the tables are to be updated

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    //Add a new row to the database
    public void addProduct(Profile product){  //for updating the name in the Profile//
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.get_productname());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public void addProduct_c(Product product){ //for adding a name and a number in Emergency contacts list//
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.get_productname());
        values.put(COLUMN_PRODUCTNUM, product.get_num());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS_C, null, values);
        db.close();
    }

    //Delete a product from the database
    public void deleteProduct(String productName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + "=\"" + productName + "\";");
    }
    public void deleteAllValues() //Deleting the name from the profile
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + ";");
        db.close();
    }

    public void deleteAllValues_c()//Deleting all the contacts from the Emergency List//
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS_C + ";");
        db.close();
    }


    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("productname")) != null) {
                dbString += c.getString(c.getColumnIndex("productname"));
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }
    public String databaseToString_c(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS_C + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("productname")) != null) {
                dbString += c.getString(c.getColumnIndex("productname"));
                dbString += "\n";

            }
            if (c.getString(c.getColumnIndex("productnum")) != null) {
                dbString += c.getString(c.getColumnIndex("productnum"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }


    public void sendSMS() //This method sends the sms to all the contacts in the Emergency Contacts List//
    {

        String person_name = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("productname")) != null) {
                person_name += c.getString(c.getColumnIndex("productname"));
            }
            c.moveToNext();
        }
        db.close();






         db = getWritableDatabase();
         query = "SELECT * FROM " + TABLE_PRODUCTS_C + " WHERE 1";

        //Cursor points to a location in your results
         c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            String phoneNo = null;
            String sms = null;
            String num;
            num = c.getString(c.getColumnIndex("productnum"));

            if (num != null) {




                phoneNo = num;
                sms ="This is "+ person_name +". I am in danger." + "Help me!";
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    // Toast.makeText(getApplicationContext(), "SMS Sent!",
                    //       Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // Toast.makeText(getApplicationContext(),
                    //       "SMS faild, please try again later!",
                    //     Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }




            }
            c.moveToNext();
        }
        db.close();

    }



}

