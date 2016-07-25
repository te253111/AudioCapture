package com.example.cylonz.audiocapture;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cylonz.audiocapture.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    MediaPlayer m;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkpermission();

        m = new MediaPlayer();

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.mp4";



        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.btnplay.setEnabled(false);
        binding.btnstop.setEnabled(false);
        binding.btnrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    myAudioRecorder=new MediaRecorder();
                    myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.MPEG_4);
                    myAudioRecorder.setOutputFile(outputFile);
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                }catch (Exception e){
                    e.printStackTrace();
                }

                binding.btnrec.setEnabled(false);
                binding.btnplay.setEnabled(false);
                binding.btnstop.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });

        binding.btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnrec.setEnabled(true);
                binding.btnplay.setEnabled(true);
                binding.btnstop.setEnabled(false);
                try{
                    myAudioRecorder.stop();
                    myAudioRecorder.release();
                }catch (Exception e){
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), "Audio recorded successfully",Toast.LENGTH_LONG).show();
            }
        });

        binding.btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    m.reset();
                    m.setDataSource(outputFile);
                    m.prepare();
                    m.start();
                }catch (Exception e){
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkpermission() {

        final List<String> permissionsList = new ArrayList<String>();

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.RECORD_AUDIO);
        }

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (permissionsList.size() > 0) {
                ActivityCompat.requestPermissions(this,permissionsList.toArray(new String[permissionsList.size()]),124);
        }

    }

}
