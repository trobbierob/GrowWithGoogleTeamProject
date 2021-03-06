package com.madonasyombua.growwithgoogleteamproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.madonasyombua.growwithgoogleteamproject.interfaces.OnFragmentInteractionListener;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.FeedsFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.InterestFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.ProfileFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.ProjectsFragment;
import com.madonasyombua.growwithgoogleteamproject.util.BottomNavigationViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity
        extends AppCompatActivity
        implements OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener{

    private Fragment fragment;
    private static final String TAG ="current-frag";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_container)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;

    private CircleImageView profilePicView;
    private TextView userName;
    private TextView userProfession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        /** Get all user information views from the drawer header view*/
        View drawerHeaderView = navView.getHeaderView(0);
        profilePicView = drawerHeaderView.findViewById(R.id.drawer_header_user_image);
        userName = drawerHeaderView.findViewById(R.id.drawer_header_user_name);
        userProfession = drawerHeaderView.findViewById(R.id.drawer_header_user_profession);

        /**Set action bar, navigation drawer, navigation drawer header*/
        setSupportActionBar(toolbar);
        setupNavDrawer();
        setDrawerHeader();

        fragment = getSupportFragmentManager().findFragmentByTag(TAG);
        if(fragment == null){
            fragment = new FeedsFragment();
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment,TAG);
        transaction.commit();
    }

    /** Set up the drawer */
    private void setupNavDrawer() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_menu);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
            navView.setNavigationItemSelectedListener(this);
            navView.setItemIconTintList(null);
        }
    }

    /** Set user information - profile pic, name, profession */
    private void setDrawerHeader() {
        // Logo image, sample username, sample user profession used here
        // Can be swapped with Picasso or Glide image loader
        profilePicView.setImageResource(R.drawable.logo);
        userName.setText(getString(R.string.sample_user_name));
        userProfession.setText(getString(R.string.sample_user_profession));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // we will start the fragments once we have worked on them.
            switch (item.getItemId()) {
                case R.id.action_feeds:
                    fragment = new FeedsFragment();
                    break;
                case R.id.action_interests:
                    fragment = new InterestFragment();
                    break;
                case R.id.action_projects:
                    fragment = new ProjectsFragment();
                    break;
                case R.id.action_profile:
                    fragment = new ProfileFragment();
                    break;
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, fragment,TAG);
            transaction.commit();

            return true;
        }

    };

    /**When back button pressed hide navigation drawer if open else move task to back*/
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            moveTaskToBack(true);
        }
    }

    /** Implement Navigation Drawer list item click listener*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // After implementation return true for the below cases
        Fragment fragment = null;
        Class fragmentClass;
        switch (item.getItemId()) {
            case R.id.messages:
                // Take user to messages screen
                return false;
            case R.id.manage_profile:
                // Take user to edit profile screen
                return false;

            case R.id.settings:
                // Take user to setting screen
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return false;

            case R.id.about:
                // Take user to about screen
                return false;
            case R.id.help:
                // Take user to help screen
                Intent helpIntent = new Intent(this, HelpActivity.class);
                startActivity(helpIntent);
                return false;
            case R.id.logout:
                // logout
                return false;

        }
        return false;


    }

    /** Implement Navigation Drawer list item click listener */

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

