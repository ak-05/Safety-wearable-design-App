package com.example.siddharth.tcoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;



public class ProfilePage extends AppCompatActivity {

    EditText name;
    ProfileDBHandler p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        name = (EditText) findViewById(R.id.name);
        p = new ProfileDBHandler(this, null, null, 3);
        String s = p.databaseToString();
        if(!p.equals(""))
        {
            name.setText(s);
        }

        name.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                String st= s.toString();
                p.deleteAllValues();
                Profile pro = new Profile(st);
                p.addProduct(pro);


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }


}

