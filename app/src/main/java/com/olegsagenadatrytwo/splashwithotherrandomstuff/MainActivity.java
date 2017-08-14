package com.olegsagenadatrytwo.splashwithotherrandomstuff;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.FragmentManager;
import android.content.res.TypedArray;
import android.graphics.drawable.Icon;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PDFView pdfViewer;
    private String[] list;
    private TypedArray listOfIcons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
        pdfViewer = (PDFView) findViewById(R.id.pdfViewer);
        pdfViewer.fromAsset("sample.pdf").load();
        list = getResources().getStringArray(R.array.list);
        listOfIcons = getResources().obtainTypedArray(R.array.icons);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_view_pdf:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.action_show_image_dialog:
                //display PictureDialogFragment
                FragmentManager manager = getFragmentManager();
                PictureDialogFragment pictureDialogFragment = new PictureDialogFragment();
                pictureDialogFragment.show(manager, "pictureDialog");
                //after seconds hide
                TimerThread timerThread = new TimerThread(pictureDialogFragment);
                timerThread.start();
                break;
            case R.id.action_dialog_default:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("This is default Alert Dialog")
                        .setMessage("Click yes or no")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }

                        })
                        .setNegativeButton("No", null)
                        .show();

                break;
            case R.id.action_dialog_custom:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.custom_dialog);

                Button ok = (Button) dialog.findViewById(R.id.ok);
                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                //if button is clicked, close the custom dialog
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

                break;
            case R.id.action_dialog_array:
                final AlertDialog aDialog;
                final AlertDialog.Builder arrayDialog = new AlertDialog.Builder(this);
                arrayDialog.setTitle("Array Dialog").setMultiChoiceItems(list, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {

                    }
                });
                arrayDialog.setCancelable(false);
                arrayDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                arrayDialog.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                arrayDialog.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                aDialog = arrayDialog.create();
                aDialog.show();
                break;
            case R.id.action_message:
                Intent message = new Intent(this, MessageActivity.class);
                startActivity(message);
                break;
            case R.id.action_notification:
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications)
                        .setContentTitle("My notification")
                        .setContentText("Go to home page!");
                Intent intent1 = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);
                mBuilder.setContentIntent(pendingIntent);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
                mNotificationManager.notify(001, mBuilder.build());
                break;
            case R.id.action_timer:
                Intent intent2 = new Intent(this, TimerActivity.class);
                startActivity(intent2);
                break;
        }
        return true;
    }
}
