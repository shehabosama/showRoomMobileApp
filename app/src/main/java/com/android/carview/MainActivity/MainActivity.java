package com.android.carview.MainActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.carview.AboutUSFragment.AboutUSFragment;
import com.android.carview.AddCategory.AddCategoryFragment;
import com.android.carview.ControlPanelFragment.ControlPanelFragment;
import com.android.carview.HomeFragment.DepartmentAdapter;
import com.android.carview.HomeFragment.HomeFragment;


import com.android.carview.Login.LoginActivity;
import com.android.carview.ProfileFragment.ProfileFragment;
import com.android.carview.R;
import com.android.carview.SettingsFragment.SettingsFragment;
import com.android.carview.common.SqlHelper.myDbAdapter;
import com.android.carview.common.base.BaseActivity;
import com.android.carview.common.helper.AppPreferences;
import com.android.carview.common.helper.Constants;
import com.android.carview.common.helper.Message;
import com.android.carview.common.model.Department;
import com.android.carview.common.model.DepartmentResponse;
import com.android.carview.common.model.UploadResult;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {


    private Toolbar toolbar;
    private TextView Navusername;
    private FrameLayout relativeLayout;
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private myDbAdapter helper;
    private boolean isAdmin;
   // public EditText editText;

    public static void startActivity (Context context){
        context.startActivity(new Intent(context,MainActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        setListeners();
    }

    @Override
    protected void initializeViews() {
        toolbar = findViewById(R.id.homeToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.home);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        actionBarDrawerToggle= new ActionBarDrawerToggle(MainActivity.this,mDrawerLayout,R.string.open,R.string.close);
        helper = new myDbAdapter(this);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        isAdmin = AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN,MainActivity.this,false);
       // editText = findViewById(R.id.editTextToolbar);

        actionBarDrawerToggle.syncState();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT))
                {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                }else
                {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView=(NavigationView)findViewById(R.id.nav);
        View nav_view=navigationView.inflateHeaderView(R.layout.header);
        Navusername=(TextView)nav_view.findViewById(R.id.nav_user_full_name);
        Navusername.setText(helper.getEmployeeName("name"));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;
            }
        });


        if(AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN,MainActivity.this,false)){
            controlMenuView("1",R.id.addCar,"",true);
            controlMenuView("1",R.id.action_control,"",true);
        }else{
            controlMenuView("1",R.id.addCar,"",false);
            controlMenuView("1",R.id.action_control,"",false);
        }

        replaceFragment(R.id.container_body, HomeFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_HOME));


    }


    @Override
    protected void setListeners() {
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        if(isAdmin){
//            getMenuInflater().inflate(R.menu.user_menu, menu);
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    private void UserMenuSelector(MenuItem item) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(item.getItemId()) {

            case R.id.action_home:
                if(isAdmin){
                    //editText.setVisibility(View.VISIBLE);
                }

                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_HOME)) != null) {
                    Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_HOME))));
                } else {
                    replaceFragment(R.id.container_body, HomeFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_HOME));
                }
                mDrawerLayout.closeDrawers();
                break;
            case R.id.addCar:
                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_CATEGORY)) != null) {
                    Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_CATEGORY))));
                } else {
                    replaceFragment(R.id.container_body, AddCategoryFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_CATEGORY));
                }
                mDrawerLayout.closeDrawers();
                break;
            case R.id.action_profile:
               // editText.setVisibility(View.GONE);
                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_PROFILE)) != null) {
                    Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_PROFILE))));
                } else {
                    replaceFragment(R.id.container_body, ProfileFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_PROFILE));
                }
                mDrawerLayout.closeDrawers();
                break;
            case R.id.action_control:
              //  editText.setVisibility(View.GONE);
                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_CONTROL)) != null) {
                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_CONTROL))));
                } else {
                    replaceFragment(R.id.container_body, ControlPanelFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_CONTROL));
                }
                mDrawerLayout.closeDrawers();
                break;
            case R.id.action_settings:
              //  editText.setVisibility(View.GONE);
                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_SETTONGS)) != null) {
                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_SETTONGS))));

                } else {
                    replaceFragment(R.id.container_body, SettingsFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_SETTONGS));
                }
                mDrawerLayout.closeDrawers();
                break;
            case R.id.action_about_us:
                //editText.setVisibility(View.GONE);
                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_ABOUTUS)) != null) {
                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_ABOUTUS))));
                } else {

                    replaceFragment(R.id.container_body, AboutUSFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_ABOUTUS));
                }
                mDrawerLayout.closeDrawers();
                break;
            case R.id.action_logout:
                //editText.setVisibility(View.GONE);
                String data = helper.getEmployeeName("name");
                helper.delete(data);
                LoginActivity.startActivity(MainActivity.this);
                break;
        }

    }

    private void controlMenuView(String type,int id,String value,boolean visibility)
    {

        Menu nav_Menu = navigationView.getMenu();
        if (type.equals("1")){
            nav_Menu.findItem(id).setVisible(visibility);

        }else{
            nav_Menu.findItem(id).setTitle(value);

        }
    }


}
