package com.example.siddharth.tcoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EmActivity extends AppCompatActivity {

   // MyDBHandler dbHandler ;
    ProfileDBHandler pr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em);
        pr = new ProfileDBHandler(this, null, null, 3);
     //   dbHandler=new MyDBHandler(this, null, null, 2);
        TextView t= (TextView)findViewById(R.id.t);
        t.setText(pr.databaseToString_c());
       // Toast.makeText(this,dbHandler.databaseToString(),Toast.LENGTH_LONG).show();


    }
    public void deleteAll(View view)
    {

        pr.deleteAllValues_c();
    }
}
