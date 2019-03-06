package com.example.licencep.setmo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public MediaRecorder audioRecorder;
    public Button recording;
    public Button stop;
    public Button play;
    public String filePath;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recording = findViewById(R.id.recording);
        stop = findViewById(R.id.stop);
        play = findViewById(R.id.play);

        filePath = getExternalCacheDir().getAbsolutePath();
        filePath += "/audiorecordtest.3gp";

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prepareRecording();

                view.setFocusable(false);
                stop.setFocusable(true);
                play.setFocusable(false);

                try {
                    audioRecorder.prepare();
                    audioRecorder.start();
                }
                catch(IllegalStateException e){
                    e.printStackTrace();
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                Toast.makeText(MainActivity.this, "start recording", Toast.LENGTH_SHORT).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.setFocusable(false);
                recording.setFocusable(true);
                play.setFocusable(true);

                audioRecorder.stop();

                Toast.makeText(MainActivity.this, "stop recording", Toast.LENGTH_SHORT).show();

            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.setFocusable(false);
                recording.setFocusable(true);
                stop.setFocusable(true);

                MediaPlayer mediaPlayer = new MediaPlayer();

                try {
                    mediaPlayer.setDataSource(filePath);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    Toast.makeText(MainActivity.this, "play recording", Toast.LENGTH_SHORT).show();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void prepareRecording(){

            audioRecorder = new MediaRecorder();
            audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            audioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            audioRecorder.setOutputFile(filePath);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }

}
