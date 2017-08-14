package com.olegsagenadatrytwo.splashwithotherrandomstuff;

import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MessageActivity extends MainActivity {

    private  static final int REQUEST_SEND_SMS = 1;
    private Button btnSend;
    private EditText etPhoneNumber;
    private EditText etMessage;
    private String phoneNumber;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setTitle("Message");


        btnSend = (Button) findViewById(R.id.btnSend);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etMessage = (EditText) findViewById(R.id.etMessage);

        if (ContextCompat.checkSelfPermission(MessageActivity.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MessageActivity.this,
                    Manifest.permission.SEND_SMS)) {
                ActivityCompat.requestPermissions(MessageActivity.this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);
            }
            else {
                ActivityCompat.requestPermissions(MessageActivity.this,
                        new String[] {Manifest.permission.SEND_SMS}, REQUEST_SEND_SMS);
            }
        }

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = etPhoneNumber.getText().toString();
                message = etMessage.getText().toString();

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                    Toast.makeText(MessageActivity.this, "Sent", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(MessageActivity.this, "Failed to send", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case REQUEST_SEND_SMS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MessageActivity.this,
                            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "No Permission Granted", Toast.LENGTH_SHORT).show();
                }
        }

    }
}
