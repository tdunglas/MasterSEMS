package com.example.licencep.setmo_android;

import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    public TextView textView;
    public ProgressBar progressBar;
    public Button btn_record;
    public Button btn_play;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    //
    public int sampleRate = 44100;
    public AudioRecord audio = null;
    public int bufferSize;
    public boolean isRecording;
    public boolean isPlaying;

    //
    private final String s1 = "RECORD";
    private final String s2 = "PLAY";
    private final String s3 = "STOP";
    private final String s11 = "RECORDING";
    private final String s12 = "STOP RECORDING";
    private final String s21 = "PLAYING";
    private final String s22 = "STOP PLAYING";
    public String filePath;

    protected View.OnClickListener onClickListener_record = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button btn = (Button) view;

            if(btn.getText().equals(s1)){
                btn_record.setText(s3);
                btn_play.setClickable(false);
                textView.setText(s11);
                isRecording = true;

                new RecordingTask().execute();
            }
            else {
                btn_record.setText(s1);
                btn_play.setClickable(true);
                textView.setText(s12);
                isRecording = false;
            }
        }
    };

    protected View.OnClickListener onClickListener_play = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button btn = (Button) view;

            if(btn.getText().equals(s2)){
                btn_play.setText(s3);
                btn_record.setClickable(false);
                textView.setText(s21);
                isPlaying = true;

                new PlayingTask().execute();
            }
            else {
                btn_play.setText(s2);
                btn_record.setClickable(true);
                textView.setText(s22);
                isPlaying = false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar);
        btn_record = findViewById(R.id.buttonLeft);
        btn_play = findViewById(R.id.buttonRight);


        btn_record.setText(s1);
        btn_play.setText(s2);
        btn_play.setClickable(false);
        isRecording = false;
        isPlaying = false;
        filePath = getExternalCacheDir().getAbsolutePath();
        filePath += "/audiorecordtest.3gp";

        btn_record.setOnClickListener(onClickListener_record);
        btn_play.setOnClickListener(onClickListener_play);

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

    private class RecordingTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            try {
                bufferSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT);
                audio = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT, bufferSize);
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }

        @Override
        protected Void doInBackground(Void... voids) {

            String filename = filePath;
            OutputStream os = null;

            try {
                os = new FileOutputStream(filename);
            } catch(FileNotFoundException e) {
                e.printStackTrace();
            }

            byte[] audioData = new byte[bufferSize];
            int read;
            int maxAmplitude = 0;


            isRecording = false;

            try {
                //bufferSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO,
                //        AudioFormat.ENCODING_PCM_16BIT);
                //audio = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate,
                //        AudioFormat.CHANNEL_IN_MONO,
                //       AudioFormat.ENCODING_PCM_16BIT, bufferSize);

                audio.startRecording();
                isRecording = true;

                double max = 1;
                while (isRecording) {

                    read = audio.read(audioData,0,bufferSize);

                    if(AudioRecord.ERROR_INVALID_OPERATION != read){
                        try {
                            os.write(audioData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    double sum = 0;
                    int value = 0;
                    for (int i = 0; i < bufferSize; i++) {
                        sum += audioData [i] * audioData [i];
                    }
                    if (bufferSize > 0) {
                        final double amplitude = sum / bufferSize;
                        value = (int) Math.sqrt(amplitude);
                    }

                    int amplitude =  (audioData[0] & 0xff) << 8 | audioData[1];
                    double amplitudeDb = 20 * Math.log10((double)Math.abs(amplitude) / 32768);

                    if (value > max){
                        max = value;
                    }

                    value = (int) Math.abs(amplitudeDb);

                    Log.d("DEBUGG", "value : " + value + " amplitudeDb : " + amplitudeDb + " max : " + maxAmplitude);
                    progressBar.setProgress(value);
                }

                try {
                    os.close();
                } catch (IOException io) {
                    io.printStackTrace();
                }

            } catch (Exception e) {
                android.util.Log.e("TrackingFlow", "Exception", e);
            }


            return null;
        }
    }

    private class PlayingTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String fileName = filePath;
            //File file = new File(fileName);

            byte[] audioData;

            try {
                InputStream inputStream = new FileInputStream(fileName);

                int minBufferSize = AudioTrack.getMinBufferSize(sampleRate,AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
                audioData = new byte[minBufferSize];


                AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
                        AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT,
                        minBufferSize, AudioTrack.MODE_STREAM);

                audioTrack.play();

                int i;
                while((i = inputStream.read(audioData)) != -1) {
                    audioTrack.write(audioData,0,i);
                    if(!isPlaying) {
                        break;
                    }
                }

                audioTrack.stop();
                audioTrack.release();

            } catch(FileNotFoundException fe) {
                fe.printStackTrace();
            } catch(IOException io) {
                io.printStackTrace();
            }
            return null;
        }
    }
}
