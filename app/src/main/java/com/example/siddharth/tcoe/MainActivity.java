/*

*/

package com.example.siddharth.tcoe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button emergency;
    public static MediaRecorder myRecorder;
    private String outputFile = null;
    private Button addContacts;
    private Button emergencyContacts;
    final int PICK_CONTACT_REQUEST = 1;
    MyDBHandler dbHandler ;
    ProfileDBHandler pr;

    data toStore, stored;
    ArrayList<data> contacts = new ArrayList<data>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emergency = (Button) findViewById(R.id.emergency_button);
        addContacts = (Button) findViewById(R.id.add_button);

        //  outputFile = Environment.getExternalStorageDirectory().
        //        getAbsolutePath() + "/javacodegeeksRecording.3gpp";

        //myRecorder = new MediaRecorder();
        //myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        //myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        //myRecorder.setOutputFile(outputFile);

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsg(v);
                //startRecording(v);
                //emergencyPage(v);
            }
        });
        dbHandler=new MyDBHandler(this, null, null, 2);
        pr = new ProfileDBHandler(this, null, null, 3);
        addContacts.setOnClickListener(new View.OnClickListener()   {
            @Override
            public void onClick(View v) {
                addContacts(v);
            }
        });
        //if(contacts.isEmpty()){
    }

    public void devices(View v)   {
        Intent intent = new Intent(this, emergencyActivity.class);
        startActivity(intent);
    }

    public void sendMsg(View view)  {


        //if(!(contacts.isEmpty()))
        /*for(data stored : contacts) {
            phoneNo = "+917728061650";
            sms = "help me";
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                Toast.makeText(getApplicationContext(), "SMS Sent!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "SMS faild, please try again later!",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }*/

        pr.sendSMS();
    }

    public void startRecording(View view){
        try {
            myRecorder.prepare();
            myRecorder.start();
        } catch (IllegalStateException e) {
            // start:it is called before prepare()
            // prepare: it is called after start() or before setOutputFormat()
            e.printStackTrace();
        } catch (IOException e) {
            // prepare() fails
            e.printStackTrace();
        }
        //text.setText("Recording Point: Recording");
        emergency.setEnabled(false);
        Toast.makeText(getApplicationContext(), "Start recording...",
                Toast.LENGTH_SHORT).show();
    }

    public void contacts(View view)
    {
        //  Toast.makeText(this,dbHandler.databaseToString(),Toast.LENGTH_LONG).show();
        Intent i = new Intent(this,EmActivity.class);
        //i.putExtra("mess",dbHandler);
        startActivity(i);
    }
    public void deleteAll(View view)
    {
        pr.deleteAllValues_c();
    }

    public void changeProfile(View view)
    {
        Intent in= new Intent(this,ProfilePage.class);
        startActivity(in);

    }


    public void addContacts(View view)  {
        Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, PICK_CONTACT_REQUEST);                      // add a constant integer as a request code
    }

    private  static final String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (PICK_CONTACT_REQUEST) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c =  getContentResolver().query(contactData, null, null, null, null);
                    //startManagingCursor(c);
                    if (c.moveToFirst()) {
                        int numberIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String num = c.getString(numberIndex);
                        int nameIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        String name = c.getString(nameIndex);
                        //String name = c.getString(c.getColumnIndexOrThrow(Contacts.People.NAME));
                        //String number = c.getString(c.getColumnIndexOrThrow(Contacts.People.NUMBER));
                        //perrsonname.setText(name);
//                        Toast.makeText(this,  name + " has number " + num, Toast.LENGTH_LONG).show();
                        Product product = new Product(name,num);
                        pr.addProduct_c(product);
                    }
                }
                break;
        }

        /*// Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                //Uri contactUri = data.getData();
                // Do something with the contact here (bigger example below)
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};


                Uri contactData = data.getData();
                Cursor c =  managedQuery(contactData, null, null, null, null);
                startManagingCursor(c);
                if (c.moveToFirst()) {
                    String name = c.getString(c.getColumnIndexOrThrow(Contacts.People.NAME));
                    String number = c.getString(c.getColumnIndexOrThrow(Contacts.People.NUMBER));
                    perrsonname.setText(name);
                    Toast.makeText(this,  name + " has number " + number, Toast.LENGTH_LONG).show();
                }

                Cursor cursor = getContentResolver().query(contactUri, projection,
                        null, null, null);
                // If the cursor returned is valid, get the phone number
                if (cursor != null && cursor.moveToFirst()) {
                    int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String number = cursor.getString(numberIndex);
                    //String number = cursor.getString(0);

                    // Do something with the phone number
                    int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    String name = cursor.getString(nameIndex);
                    //toStore = new data(name, number);
                    //contacts.add(toStore);

                    TextView display = (TextView) findViewById(R.id.toDisplay);
                    display.setText(number);
                }
                cursor.close();
            }*/
    }



    public class data {
        String number = null;
        String name =  null;

        public data(String name, String number) {
            this.name = name;
            this.number = number;
        }
    }

}




