package com.example.xiaoyi.sleepinthetrain;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by xiaoyi on 28/01/2016.
 */
public class SettingActivity extends FragmentActivity implements SeekBar.OnSeekBarChangeListener {
    private SeekBar seekBar;
    private TextView tv1;
    private TextView tv2;
    private Button alarmaction;
    private Button ringtone;
    private Button btn;
    String[] multi_list = {"Bell", "Vibrate", "Bell+Vibrate"};
    //    String [] multi_list_2 = {"music1","music2","music3"};
    Alarm alarm = null;
    Uri ringtoneUri = null;
    CheckBox enable;
    float range;
    LatLng position;
    private Context mContext;
    private static final int Ringtone = 0;
    private static final int Alarm = 1;
    private static final int Notification = 2;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.setactivity);
        mContext = this;
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
//        btn = (Button) findViewById(R.id.button);


//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(SettingActivity.this, MapsActivity.class);
//                startActivity(intent);
//            }
//        });


        seekBar.setOnSeekBarChangeListener(this);
//        initEvent();
        findViewById(R.id.alarmaction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();


            }
        });

        findViewById(R.id.ringtone).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,RingtoneManager.TYPE_ALL);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "select your ringtone");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, false);
//                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_INCLUDE_DRM, true);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, false);
                startActivityForResult(intent, Ringtone);


            }


        });

        setListeners();
        setupRingtonePicker();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void setupRingtonePicker() {

    }

    private void setListeners() {

    }

//    private void initEvent() {
//        findViewById(R.id.alarmaction).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog();
//
//
//            }
//        });
//
//        findViewById(R.id.ringtone).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
//                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "select your ringtone");
//                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, false);
//                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_INCLUDE_DRM, true);
//                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, false);
//                SettingActivity.this.startActivityForResult(intent, Ringtone);
//
//
//            }
//
//
//        });
//    }


    private void showDialog() {
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

//    private void showDialog1(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Ringtone check");
//        builder.setSingleChoiceItems(multi_list_2, 0, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String str = multi_list_2[which];
//
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        // default progress = 1000;
        tv1.setText("Choosing");
        tv2.setText("Your Choice:" + progress + "meters");

        ChangeRadius(progress);


    }


    public void ChangeRadius(int progress) {


        Intent intent = new Intent();
        intent.setClass(this, MapsActivity.class);
        intent.putExtra("POINT_RADIUS_CHANGED", progress);
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Setting Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.xiaoyi.sleepinthetrain/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Setting Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.xiaoyi.sleepinthetrain/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        boolean canDo =  Settings.System.canWrite(this);

        if (canDo == false){
            Intent grantIntent = new   Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            startActivity(grantIntent);

        }

        else {

            Uri uri = data
                    .getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

                        RingtoneManager.setActualDefaultRingtoneUri(this,
                                RingtoneManager.TYPE_NOTIFICATION, uri);


            Intent intent = new Intent();
            intent.setClass(this, MapsActivity.class);
            startActivity(intent);

                }



    }

}
