package com.androidhive.androidsqlite;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        DatabaseHandler db = new DatabaseHandler(this);

        Log.e("INSERT:","inserting.....");
        db.addContact(new Contact("arash","09121234567"));
        db.addContact(new Contact("hamed","09198521354"));
        db.addContact(new Contact("sepehr","09366546546"));


        Log.e("Reading:","READING ALL CONTACTS....");
        List<Contact> contacts = db.getAllContacts();
        for (Contact contact : contacts)
        {
            String log = "Id: "+contact.getId()+
                    "  Name:  "+contact.getName()+
                    "  Phone:  "+contact.getPhone();

            Log.e("RESULT:" ,log+"");
        }

      
    }
}