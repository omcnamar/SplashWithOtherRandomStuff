package com.olegsagenadatrytwo.splashwithotherrandomstuff;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;


/**
 * Created by omcna on 8/13/2017.
 */

public class TimerThread extends Thread {
    private PictureDialogFragment dialog;
    public static final String TAG = "TimerThread";
    Handler handler = new Handler(Looper.getMainLooper());

    public TimerThread(PictureDialogFragment dialog){
        this.dialog = dialog;
    }

    @Override
    public void run() {
        Log.d(TAG, "run: ");
        super.run();
        try {
            Thread.sleep(3000);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
