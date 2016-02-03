package com.example.xiaoyi.sleepinthetrain;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by xiaoyi on 28/01/2016.
 */
public class SettingActivity extends FragmentActivity implements SeekBar.OnSeekBarChangeListener{
    private SeekBar seekBar;
    private TextView tv1;
    private TextView tv2;
    private Button alarmaction;
    private Button ringtone;
    String [] multi_list = {"Bell","Vibrate","Bell+Vibrate"};
    String [] multi_list_2 = {"music1","music2","music3"};
    Alarm alarm = null;
    Uri ringtoneUri = null;
    CheckBox enable;
    float range;
    LatLng position;




    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.setactivity);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);





        seekBar.setOnSeekBarChangeListener(this);
        initEvent();

        setListeners();
        setupRingtonePicker();

    }

    private void setupRingtonePicker() {

    }

    private void setListeners() {

    }

    private void initEvent() {
        findViewById(R.id.alarmaction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();


            }
        });

        findViewById(R.id.ringtone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog1();


            }
        });
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alarm Action Setting");
        builder.setSingleChoiceItems(multi_list, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String str = multi_list[which];


            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void showDialog1(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ringtone check");
        builder.setSingleChoiceItems(multi_list_2, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String str = multi_list_2[which];

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tv1.setText("Choosing");
        tv2.setText("Your Choice:" + progress + "meters");

        ChangeRadius(progress);


    }

    public void ChangeRadius(long progress) {



        Intent intent = new Intent();
        intent.setClass(this,MapsActivity.class);
        intent.putExtra("POINT_RADIUS_CHANGED",progress);
        startActivity(intent);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        tv1.setText("Beginning");

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        tv1.setText("Completed");
    }
}
