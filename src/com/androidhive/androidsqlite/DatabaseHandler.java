package com.androidhive.androidsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Arash on 05/11/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    final static String DATABASE_NAME = "contactsManager";
    final static int DATABASE_VERSION = 1;

    final static String TABLE_CONTACTS = "CONTACTS";

    final static String KEY_ID = "id";
    final static  String KEY_NAME = "name";
    final static String KEY_PHONE = "phone_number";

    public DatabaseHandler(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String CREATE_CONTACTS_TABLE =
                    "CREATE TABLE "+TABLE_CONTACTS+ "("
                    +KEY_ID + " INTEGER PRIMARY KEY,"
                    +KEY_NAME + " TEXT,"
                    +KEY_PHONE + " TEXT"+ ")";

        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_CONTACTS);

        onCreate(sqLiteDatabase);


    }

    public void addContact(Contact contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, contact.getName());
        contentValues.put(KEY_PHONE, contact.getPhone());

        db.insert(TABLE_CONTACTS,null,contentValues);
        db.close();
    }


    public Contact getContacts(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS,new String[]
                {KEY_ID,KEY_NAME,KEY_PHONE},
                KEY_ID+ "=?",
                new String[]{String.valueOf(id)},null,null,null,null);

            if (cursor != null)
            cursor.moveToFirst();

            Contact contact = new Contact(
                    Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1),
                            cursor.getString(2));

            return contact;


    }


    public List<Contact> getAllContacts()
    {
        List<Contact> contactList = new ArrayList<Contact>();

        String selectQuery = "SELECT * FROM "+TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor  = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst())
        {
            do{
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));

                contactList.add(contact);
            }while (cursor.moveToNext());
        }

        return contactList;

    }


    public int updateContact(Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME,contact.getName());
        values.put(KEY_PHONE,contact.getPhone());

        return db.update(TABLE_CONTACTS,values,KEY_ID + " =?",
                new String[]{String.valueOf(contact.getId())});


    }

    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS,KEY_ID+" =?",
                new String[]{String.valueOf(contact.getId())});

        db.close();
    }

    public void exportDB(){

        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath =
                "/data/"+"com.androidhive.androidsqlite"+"/databases/"+
                        DATABASE_NAME;

        String backupPath = DATABASE_NAME;

        File currentDB = new File(data,currentDBPath);
        File backupDB = new File(sd,backupPath);

        try{
            source = new FileInputStream(currentDB).getChannel();
            destination =new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source,0,source.size());
            source.close();
            destination.close();
        }catch (Exception ex)
        {

        }


    }
}
