package com.example.aapotti.kymiringappdesigning1;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout mainLayout;

    TextView localTime;

    String kymiringClock;

    TimeZone timeZone;
    Calendar calendar;

    FrameLayout scrollViewFrame;

    ImageView scrollViewImage;

    LinearLayout newsLinearLayout;

    ScrollView mainScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DEFINING ALL THE UI ELEMEnTS
        mainLayout = (ConstraintLayout)findViewById(R.id.mainLayout);

        localTime = (TextView)findViewById(R.id.localTime);

        scrollViewFrame = (FrameLayout)findViewById(R.id.scrollViewFrame);

        scrollViewImage = (ImageView)findViewById(R.id.scrollViewImage);

        newsLinearLayout = (LinearLayout)findViewById(R.id.newsLinearLayout);

        mainScrollView = (ScrollView)findViewById(R.id.mainSrollView);
        mainScrollView.getViewTreeObserver().addOnScrollChangedListener(new ScrollPositionObserver());


        //CHANGE TOOLBAR TEXT COLOR TO BLACK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mainLayout != null) {
                mainLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        //SET LOCAL TIME
        final Handler handler = new Handler();

        final Runnable run = new Runnable() {
            @Override
            public void run()
            {
                timeZone = TimeZone.getTimeZone("GMT+02:00");
                calendar = Calendar.getInstance(timeZone);
                kymiringClock = String.format("%02d" , calendar.get(Calendar.HOUR_OF_DAY))+":"+
                        String.format("%02d" , calendar.get(Calendar.MINUTE))+":" +
                        String.format("%02d" , calendar.get(Calendar.SECOND));

                localTime.setText(kymiringClock);

                handler.postDelayed(this, 1000);
            }
        };
        handler.post(run);
    }

    //IMAGE PARALLAX SCROLL
    private class ScrollPositionObserver implements ViewTreeObserver.OnScrollChangedListener {

        private int mImageViewHeight;

        public ScrollPositionObserver() {
            mImageViewHeight = getResources().getDimensionPixelSize(R.dimen.scrollPicHeight);
        }

        @Override
        public void onScrollChanged() {
            int scrollY = Math.min(Math.max(mainScrollView.getScrollY(), 0), mImageViewHeight);

            // changing position of ImageView
            scrollViewImage.setTranslationY(scrollY / 2);

            // alpha you could set to ActionBar background
            float alpha = scrollY / (float) mImageViewHeight;
        }
    }
}
