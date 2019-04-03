package com.example.licencep.setmo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.androidplot.xy.StepMode.INCREMENT_BY_VAL;
import static com.example.licencep.setmo.FFT.complexetoNumber;
import static com.example.licencep.setmo.FFT.fft;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.FastLineAndPointRenderer;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;


public class MainActivity extends AppCompatActivity {

    public Button recording;
    public Button stop;
    public Button play;
    public String filePath;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    //public int sampleRate = 8000;
    public int sampleRate = 44100;
    public AudioRecord audio;
    public int bufferSize;
    public boolean isRecording = false;

    public ProgressBar progressBar;

    //graphic var
    private XYPlot plot;
    public byte[] dataBytes;

    public float[] audioDataFloat;
    public List<Float> analogs;

    public int green = Color.parseColor("#69e800");
    public int red = Color.parseColor("#e82900");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recording = findViewById(R.id.recording);
        stop = findViewById(R.id.stop);
        play = findViewById(R.id.play);
        progressBar = findViewById(R.id.progressBar);

        recording.setBackgroundColor(Color.GREEN);
        stop.setBackgroundColor(Color.RED);
        play.setBackgroundColor(Color.RED);

        filePath = getExternalCacheDir().getAbsolutePath();
        filePath += "/audiorecordtest.3gp";

        Log.e("PATH", " ------ PATH : " + filePath + " ------");
        Log.e("PATH", " ------ PATH : " + filePath + " ------");
        Log.e("PATH", " ------ PATH : " + filePath + " ------");

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        analogs = new ArrayList<Float>();

        progressBar.setMax(100);
        progressBar.setProgress(0);

        recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(){
                    @Override
                    public void run(){

                        stop.setFocusable(true);
                        play.setFocusable(false);

                        recording.setBackgroundColor(red);
                        stop.setBackgroundColor(green);
                        play.setBackgroundColor(red);

                        try {

                            prepareRecording();
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

                recording.setBackgroundColor(green);
                stop.setBackgroundColor(red);
                play.setBackgroundColor(green);

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




                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        playRecording();
                    }
                }).start();

                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Log.d("UI thread", "I am the UI thread");
                        showGraph();
                    }
                });

                //playRecording();

                //showGraph();

                audio.release();
                audio = null;


                recording.setBackgroundColor(green);
                stop.setBackgroundColor(red);
                play.setBackgroundColor(red);
            }
        });
    }

    public void prepareRecording(){

        try {
            bufferSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT);
                    //AudioFormat.ENCODING_PCM_FLOAT);
            audio = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, bufferSize);
                    //AudioFormat.ENCODING_PCM_FLOAT, bufferSize);

        } catch (Exception e) {
            android.util.Log.e("TrackingFlow", "Exception", e);
        }

    }

    public void recording(){

        String filename = filePath;
        OutputStream os = null;

        try {
            File file = new File(filePath);

            if(!file.exists()){
                file.createNewFile();
            }

            os = new FileOutputStream(filename);
        } catch(IOException e) {
            e.printStackTrace();
        }

        byte[] audioData = new byte[bufferSize];
        int read = 0;

        audioDataFloat = new float[bufferSize];

        audio.startRecording();
        isRecording = true;

        double max = 1;
        float average = 0;
        analogs.clear();


        while (isRecording) {

            read = audio.read(audioData,0,bufferSize);
            //read = audio.read(audioDataFloat,0,bufferSize, AudioRecord.READ_NON_BLOCKING);
            //read = audio.read(audioDataFloat,0,bufferSize, AudioRecord.READ_BLOCKING);

            for(int i=0; i<bufferSize; i++){

                /*
                if(i == bufferSize / 10){

                    average /= bufferSize / 10;
                    analogs.add(average);
                    average = 0;
                }
                */

                average += audioData[i];
                //average = audioData[i];

                //analogs.add(average);

            }
            //average /= bufferSize / 10;
            average /= bufferSize;
            analogs.add(average);
            Log.e("audioData", "------ audioData average " + average + " ------ buffer size " + bufferSize);

            if(AudioRecord.ERROR_INVALID_OPERATION != read){
                try {
                    os.write(audioData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(average > max){
                max = average;
            }

            int val = (int)(average / max) * 100;
            progressBar.setProgress(val);

        }

        try {
            os.close();
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    public void playRecording(){

        String fileName = filePath;

        byte[] audioData = null;

        try {
            InputStream inputStream = new FileInputStream(fileName);

            int minBufferSize = AudioTrack.getMinBufferSize(sampleRate,AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
            //int minBufferSize = AudioTrack.getMinBufferSize(sampleRate,AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_FLOAT);
            audioData = new byte[minBufferSize];


            AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
                    AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT,
                    //AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_FLOAT,
                    minBufferSize, AudioTrack.MODE_STREAM);

            audioTrack.play();

            int i=0;
            while((i = inputStream.read(audioData)) != -1) {
                audioTrack.write(audioData,0,i);

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

    public void showGraph(){

        Log.d("result double array"," START --- DEBBUG ");
        Log.d("result double array"," START --- DEBBUG ");
        Log.d("result double array"," START --- DEBBUG ");

        Complex[] values;

        try {

            InputStream inputStream = new FileInputStream(filePath);

            int limit = 1;
            while(limit < analogs.size()){
                limit *= 2;
            }

            int size = limit;
            double[] result = new double[size];
            values = new Complex[size];

            double maxf = 0;
            double minf = 0;

            for (int i = 0; i < size; i++) {

                if(i < analogs.size()){
                    result[i] = analogs.get(i);

                    if(result[i] > maxf){
                        maxf = result[i];
                    }

                    if(result[i] < minf){
                        minf = result[i];
                    }

                    values[i] = new Complex(i, 0);
                    values[i] = new Complex(-2*result[i]*100+1, 0);
                }
                else {
                    values[i] = new Complex(i, 0);
                    values[i] = new Complex(0, 0);
                }


                Log.d("vals","------ debugg - i " + i + " : " + result[i] + " size analogs " + analogs.size() + " ; current max " + maxf + " ------");

            }


            plot = (XYPlot) findViewById(R.id.plot);

            plot.clear();

            final Number[] domainLabels = new Number[size];

            Complex[] y = fft(values);
            Number[] data_imag = complexetoNumber(y);


            XYSeries series1 = new SimpleXYSeries(
                    Arrays.asList(data_imag), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "frequencies");



            LineAndPointFormatter format = new LineAndPointFormatter();

            //format.setInterpolationParams(new CatmullRomInterpolator.Params(20, CatmullRomInterpolator.Type.Centripetal));
            plot.addSeries(series1, format);


/*
            // create formatters to use for drawing a series using LineAndPointRenderer
            // and configure them from xml:
            LineAndPointFormatter series1Format =
                    new LineAndPointFormatter(this, R.xml.line_point_formatter_with_labels);

            LineAndPointFormatter series2Format =
                    new LineAndPointFormatter(this, R.xml.line_point_formatter_with_labels_2);

            // add an "dash" effect to the series2 line:
            series2Format.getLinePaint().setPathEffect(new DashPathEffect(new float[] {

                    // always use DP when specifying pixel sizes, to keep things consistent across devices:
                    PixelUtils.dpToPix(20),
                    PixelUtils.dpToPix(15)},
                    0));

            // just for fun, add some smoothing to the lines:
            // see: http://androidplot.com/smooth-curves-and-androidplot/
            series1Format.setInterpolationParams(
                    new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

*/
            double rangeStep = 1;
            double domainStep = 1;

            rangeStep = (maxf - minf) / 10;

            plot.setRangeBoundaries(minf, maxf, BoundaryMode.FIXED);
            plot.setRangeStep(INCREMENT_BY_VAL, rangeStep);

            domainStep = analogs.size() / 10;

            plot.setDomainBoundaries(0, analogs.size(), BoundaryMode.FIXED);
            plot.setDomainStep(INCREMENT_BY_VAL, domainStep);

            // add a new series' to the xyplot:
            //plot.addSeries(series1, series1Format);

            plot.getGraph().setLineLabelEdges(
                    XYGraphWidget.Edge.BOTTOM,
                    XYGraphWidget.Edge.LEFT
            );

            /*
            plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
                @Override
                public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos)
                {
                    return toAppendTo;//toAppendTo.append(" TEST ");
                }

                @Override
                public Object parseObject(String source, ParsePosition pos) {
                    // unused
                    return null;
                }

            });
            */

            plot.redraw();
        }
        catch( IOException e){
            e.printStackTrace();
        }

        Log.d("result double array"," END --- DEBBUG ");
        Log.d("result double array"," END --- DEBBUG ");
        Log.d("result double array"," END --- DEBBUG ");



    }

}