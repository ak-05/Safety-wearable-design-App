package com.example.siddharth.tcoe;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class emergencyActivity extends AppCompatActivity {

    /*  have to add permissions to write to external storage if output is being written to external storage.
        also permission to record audio.
     */

    private Button cancelEmergency;
    //private MediaRecorder myRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        cancelEmergency = (Button) findViewById(R.id.cancel_button);

        cancelEmergency.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v) {
                stop(v);

            }
        });
    }

    public void stop(View view){
        try {
            MainActivity.myRecorder.stop();
            MainActivity.myRecorder.release();
            MainActivity.myRecorder  = null;

            /*stopBtn.setEnabled(false);
            playBtn.setEnabled(true);
            text.setText("Recording Point: Stop recording");*/

            Toast.makeText(getApplicationContext(), "Stop recording...",
                    Toast.LENGTH_SHORT).show();
        } catch (IllegalStateException e) {
            //  it is called before start()
            e.printStackTrace();
        } catch (RuntimeException e) {
            // no valid audio/video data has been received
            e.printStackTrace();
        }
    }
}
