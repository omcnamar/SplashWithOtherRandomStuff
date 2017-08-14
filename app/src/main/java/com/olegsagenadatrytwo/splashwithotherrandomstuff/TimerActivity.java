package com.olegsagenadatrytwo.splashwithotherrandomstuff;

import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends MainActivity implements TimerFragmentTextView.OnFragmentInteractionListener,
                                                                TimerButtonsFragment.OnFragmentInteractionListener{

    private static final String TAG = "TimerActivity";
    private Button startButton;
    private Button pauseButton;

    private TextView timerValue;

    private long startTime = 0L;

    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    String timerValueString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        setTitle("Timer");

    }

    @Override
    public void onFragmentInteractionText(Uri uri) {

    }

    @Override
    public void onFragmentInteractionButtons(int id) {
        TimerFragmentTextView fragment = (TimerFragmentTextView) getSupportFragmentManager().findFragmentById(R.id.frTimerText);
        switch (id){
            case R.id.btnStart:
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
                timerValue = fragment.clock;
                break;
            case R.id.btnStop:
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
                break;
            case R.id.btnReset:
                timerValue = fragment.clock;
                timerValue.setText("00:00:00");
                timeInMilliseconds = 0L;
                timeSwapBuff = 0L;
                updatedTime = 0L;
                startTime = SystemClock.uptimeMillis();
                break;
        }
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            Log.d(TAG, "run: ");

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }

    };


}
