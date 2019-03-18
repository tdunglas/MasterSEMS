package com.example.licencep.setmo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {

    public Button recording;
    public Button stop;
    public Button play;
    public String filePath;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    public int sampleRate = 8000;
    public AudioRecord audio;
    public int bufferSize;
    public boolean isRecording = false;

    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recording = findViewById(R.id.recording);
        stop = findViewById(R.id.stop);
        play = findViewById(R.id.play);
        progressBar = findViewById(R.id.progressBar);

        filePath = getExternalCacheDir().getAbsolutePath();
        filePath += "/audiorecordtest.3gp";

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        progressBar.setMax(100);
        progressBar.setProgress(0);

        recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(){
                    @Override
                    public void run(){

                        prepareRecording();

                        stop.setFocusable(true);
                        play.setFocusable(false);

                        try {
                            recording();
                        }
                        catch(IllegalStateException e){
                            e.printStackTrace();
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                }.start();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.setFocusable(false);
                recording.setFocusable(true);
                play.setFocusable(true);

                isRecording = false;
                audio.stop();
                progressBar.setProgress(0);

            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.setFocusable(false);
                recording.setFocusable(true);
                stop.setFocusable(true);

                playRecording();

            }
        });
    }

    public void prepareRecording(){

        try {
            bufferSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT);
            audio = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, bufferSize);

        } catch (Exception e) {
            android.util.Log.e("TrackingFlow", "Exception", e);
        }

    }

    public void recording(){

        String filename = filePath;
        OutputStream os = null;

        try {
            os = new FileOutputStream(filename);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] audioData = new byte[bufferSize];
        int read = 0;
        int maxAmplitude = 0;

        audio.startRecording();
        isRecording = true;

        while (isRecording) {

            read = audio.read(audioData,0,bufferSize);

            if(AudioRecord.ERROR_INVALID_OPERATION != read){
                try {
                    os.write(audioData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (read > maxAmplitude) {
                maxAmplitude = read;
            }

            int value = (read / maxAmplitude) * 100;
            progressBar.setProgress(value);
            progressBar.setVisibility(View.VISIBLE);

        }

        try {
            os.close();
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    public void playRecording(){

        String fileName = filePath;
        File file = new File(fileName);

        byte[] audioData = null;

        try {
            InputStream inputStream = new FileInputStream(fileName);

            int minBufferSize = AudioTrack.getMinBufferSize(sampleRate,AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
            audioData = new byte[minBufferSize];


            AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
                    AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT,
                    minBufferSize, AudioTrack.MODE_STREAM);

            audioTrack.play();

            int maxAmplitude = 0;
            int i=0;
            while((i = inputStream.read(audioData)) != -1) {
                audioTrack.write(audioData,0,i);

                if (i > maxAmplitude) {
                    maxAmplitude = i;
                }

                //int value = (i / maxAmplitude) * 100;
                //int value = 75;
                //progressBar.setProgress(value);
                //progressBar.setVisibility(View.VISIBLE);

                //new myAsyncTask().execute();
            }

            audioTrack.stop();
            audioTrack.release();

        } catch(FileNotFoundException fe) {
            fe.printStackTrace();
        } catch(IOException io) {
            io.printStackTrace();
        }
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

    private class myAsyncTask extends AsyncTask<Void, Void, Void> {

        int i;

        @Override
        protected Void doInBackground(final Void ... params){

            int value = 75 + i;
            progressBar.setProgress(value);
            progressBar.setVisibility(View.VISIBLE);
            i++;

            return null;
        }

        @Override
        protected void onPreExecute() {
        }
    }

}
