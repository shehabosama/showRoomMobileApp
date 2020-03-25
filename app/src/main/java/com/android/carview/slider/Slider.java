package com.android.carview.slider;

import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.carview.Login.LoginActivity;
import com.android.carview.MainActivity.MainActivity;
import com.android.carview.R;
import com.android.carview.common.SqlHelper.*;
import com.android.carview.common.base.BaseActivity;

import java.util.TimerTask;

public class Slider extends BaseActivity {
    private ViewPager viewPager;
    private String type;
    private String DEFAULT=null;
    private Button login_btn;
    private ImageView imageView;
    private myDbAdapter helper;

    public static void startActivity(Context context){
        context.startActivity(new Intent(context,Slider.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        initializeViews();
        setListeners();
    }

    @Override
    protected void initializeViews() {
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        helper = new myDbAdapter(this);
        String data = helper.getData();

        if (data.length()>0){
            startActivity(new Intent(getApplicationContext(), MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
        }else {


            SharedPreferences sharedPreferences_ret = getSharedPreferences("Data", Context.MODE_PRIVATE);
            type = sharedPreferences_ret.getString("kind_person", DEFAULT);
            if (type != null) {
                LoginActivity.startActivity(Slider.this);
            }

        }

        login_btn = findViewById(R.id.next_btn);
        login_btn.setVisibility(View.GONE);
        imageView = findViewById(R.id.image_next);
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void setListeners() {
        imageView.setOnClickListener(imageViewClickListener);
        login_btn.setOnClickListener(login_btnListener);
        viewPager.addOnPageChangeListener(viewpagerListener);
    }

    ViewPager.OnPageChangeListener viewpagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position==2) {
                login_btn.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
            }else
            {
                login_btn.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    View.OnClickListener login_btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            type="test";
            SharedPreferences sharedPreferences=getSharedPreferences("Data",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("kind_person",type);
            editor.apply();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    };
    View.OnClickListener imageViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(viewPager.getCurrentItem()==0)
            {
                viewPager.setCurrentItem(1);
            }
            else if(viewPager.getCurrentItem() == 1)
            {
                viewPager.setCurrentItem(2);
                imageView.setVisibility(View.GONE);

            }else if(viewPager.getCurrentItem() == 2)
            {
                type="test";
                SharedPreferences sharedPreferences=getSharedPreferences("Data",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("kind_person",type);
                editor.commit();

                imageView.setVisibility(View.GONE);
            }
        }
    };
    public  class MyTimeTask extends TimerTask
    {

        @Override
        public void run() {
            Slider.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem() == 0 )
                    {
                        viewPager.setCurrentItem(1);

                    }else if(viewPager.getCurrentItem() == 1)
                    {
                        viewPager.setCurrentItem(2);
                    }else
                    {
                        viewPager.setCurrentItem(0);
                    }
                }
            });

        }
    }
}
