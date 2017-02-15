package com.cly.myapplication.activities;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cly.myapplication.R;
import com.cly.myapplication.fragments.InsideFragment;
import com.cly.myapplication.fragments.MiaoShaFragment;
import com.cly.myapplication.fragments.OpenGLFragment;
import com.cly.myapplication.fragments.ViewPagerFragment;
import com.cly.myapplication.utils.ActivityUtils;
import com.cly.myapplication.views.Progress1;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = ((Toolbar) findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);

        initNavigationDrawer();


    }

    private void initNavigationDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.demo1:
                        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), InsideFragment.newInstance(), R.id.fl_content);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.settings:
                        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), MiaoShaFragment.newInstance(), R.id.fl_content);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.trash:
                        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), ViewPagerFragment.newInstance(), R.id.fl_content);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.fourth:
                        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), OpenGLFragment.newInstance(), R.id.fl_content);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        finish();
                        break;

                }
                return true;
            }
        });

        View header = navigationView.getHeaderView(0);
        TextView tv_email = (TextView) header.findViewById(R.id.tv_email);
        tv_email.setText("conglongyu@vip.qq.com");
        drawerLayout = ((DrawerLayout) findViewById(R.id.drawerlayout));
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
