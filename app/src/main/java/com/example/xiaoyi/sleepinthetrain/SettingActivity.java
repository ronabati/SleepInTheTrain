package com.example.xiaoyi.sleepinthetrain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by xiaoyi on 28/01/2016.
 */
public class SettingActivity extends Activity implements SeekBar.OnSeekBarChangeListener{
    private SeekBar seekBar;
    private TextView tv1;
    private TextView tv2;
    private Button alarmaction;
    private Button ringtone;
    String [] multi_list = {"bell","vibe","bell+vibe"};
    String [] multi_list_2 = {"music1","music2","music3"};

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.setactivity);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);

        seekBar.setOnSeekBarChangeListener(this);
        initEvent();

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
        tv2.setText("Your Choice:"+progress);

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
