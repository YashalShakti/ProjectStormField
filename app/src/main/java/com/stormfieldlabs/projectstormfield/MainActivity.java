package com.stormfieldlabs.projectstormfield;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stormfieldlabs.projectstormfield.wifi.WifiApManager;
import com.stormfieldlabs.projectstormfield.wifi.connector;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    Activity mActivity;
    int mCurrentSelectedPosition;
    NavigationView navigationView;
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    CollapsingToolbarLayout collapsingToolbarLayout ;
    CoordinatorLayout coordinatorLayout;
    AppBarLayout appBarLayout;
    FloatingActionButton fab;
    Context context;
    long s ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar = (Toolbar) findViewById(com.stormfieldlabs.projectstormfield.R.id.toolbar_actionbarM);
        mDrawerLayout = (DrawerLayout) findViewById(com.stormfieldlabs.projectstormfield.R.id.navviewDrawerLayout);
        navigationView = (NavigationView)findViewById(com.stormfieldlabs.projectstormfield.R.id.navview);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinator);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        appBarLayout=(AppBarLayout)findViewById(R.id.appBarLayout);
        ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).setBehavior(new AppBarLayout.Behavior() {});
        fab = (FloatingActionButton)findViewById(R.id.fab);
        context=this;
        mTitle = mDrawerTitle = getTitle();
        mActivity=this;
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, com.stormfieldlabs.projectstormfield.R.string.drawer_open, com.stormfieldlabs.projectstormfield.R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primary_dark));


        navigationView.setItemTextColor(getResources().getColorStateList(R.color.abc_primary_text_material_dark));
        navigationView.setItemIconTintList(getResources().getColorStateList(com.stormfieldlabs.projectstormfield.R.color.abc_primary_text_material_dark));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandToolbar();
                //findViewById(R.id.collapsingToolbarLayoutImageView).setBackground(ContextCompat.getDrawable(context, R.drawable.wallpaper1));
                Intent intent=new Intent(context, connector.class);
                startActivity(intent);
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                collapseToolbar();
                return true;
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                s = System.currentTimeMillis();
                mDrawerLayout.closeDrawer(GravityCompat.START);
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                return true;
            }
        });
    }


    public void expandToolbar(){
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if(behavior!=null) {
            behavior.setTopAndBottomOffset(0);
            behavior.onNestedPreScroll(coordinatorLayout, appBarLayout, null, 0, 1, new int[2]);

        }
    }
    public void collapseToolbar()
    {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if(behavior!=null) {
            behavior.onNestedPreScroll(coordinatorLayout, appBarLayout, null, 0, appBarLayout.getHeight(), new int[2]);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event



        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }



        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.stormfieldlabs.projectstormfield.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION, 0);
        Menu menu = navigationView.getMenu();
        menu.getItem(mCurrentSelectedPosition).setChecked(true);
    }
}
