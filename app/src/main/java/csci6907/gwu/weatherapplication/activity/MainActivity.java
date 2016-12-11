package csci6907.gwu.weatherapplication.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import csci6907.gwu.weatherapplication.Constant;
import csci6907.gwu.weatherapplication.R;
import csci6907.gwu.weatherapplication.activity.fragment.SettingFragment;
import csci6907.gwu.weatherapplication.activity.fragment.WeatherFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager mfragmentManager = null;
    private SharedPreferences.Editor mEditor = null;
    private SharedPreferences mSharedPreferences = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSharedPreferences = getSharedPreferences(Constant.PREF, MODE_PRIVATE);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mfragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = mfragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, new WeatherFragment());
        ft.commit();

        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean("isFahrenheit", true);
        mEditor.putBoolean("isCurrent", true);
        mEditor.putInt("daysLimit", Constant.DEFAULTDAYS);
        mEditor.putString("zipCode", "");
        mEditor.commit();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSharedPreferences = getSharedPreferences(Constant.PREF, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.clear();
        mEditor.putBoolean("isFahrenheit", savedInstanceState.getBoolean("isFahrenheit"));
        mEditor.putBoolean("isCurrent", savedInstanceState.getBoolean("isCurrent"));
        mEditor.putInt("daysLimit", savedInstanceState.getInt("daysLimit"));
        mEditor.putString("zipCode", savedInstanceState.getString("zipCode"));
        mEditor.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putBoolean("isCurrent", mSharedPreferences.getBoolean("isCurrent", true));
        savedInstanceState.putBoolean("isFahrenheit", mSharedPreferences.getBoolean("isFahrenheit", true));
        savedInstanceState.putInt("daysLimit", mSharedPreferences.getInt("daysLimit", Constant.DEFAULTDAYS));
        savedInstanceState.putString("zipCode", mSharedPreferences.getString("zipCode", ""));
        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        displayView(id);
        return true;
    }
    public void displayView(int id) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        if (id == R.id.nav_weather) {
            fragment = new WeatherFragment();

            title  = getResources().getString(R.string.nav_weather);
        } else if (id == R.id.nav_setting) {
            fragment = new SettingFragment();
            title  = getResources().getString(R.string.nav_setting);
        }

        if (fragment != null) {

            FragmentTransaction ft = mfragmentManager.beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }


}
