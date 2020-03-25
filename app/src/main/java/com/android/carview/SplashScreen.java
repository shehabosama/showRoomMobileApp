package com.android.carview;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.android.carview.common.base.BaseActivity;
import com.android.carview.slider.Slider;

public class SplashScreen extends BaseActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initializeViews();
    }

    @Override
    protected void initializeViews() {
        progressBar = (ProgressBar)findViewById(R.id.progress_bar_);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffffff"),
                android.graphics.PorterDuff.Mode.MULTIPLY);
        Thread thread = new Thread()
        {

            @Override
            public void run() {

                try{
                    sleep(2000);
                }catch (Exception e){

                    e.printStackTrace();

                }

                finally
                {
                    Slider.startActivity(SplashScreen.this);
                }

            }
        };thread.start();
    }

    @Override
    protected void setListeners() {

    }
}
