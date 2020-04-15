package com.example.prototype_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeScreen extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DrawerLayout drawer;
    private String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // to check the name at home screen...

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth= FirebaseAuth.getInstance();
        mCurrentUser= mAuth.getCurrentUser();

        uname=mCurrentUser.getDisplayName();
        Toast.makeText(HomeScreen.this, uname,Toast.LENGTH_LONG).show();



        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(HomeScreen.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // for screen rotation...
        if (savedInstanceState == null) {



            // since we don't want that when we open the homescreen it should be empty...
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new my_account_fragment()).commit();

            // it will show the first fragment...
            navigationView.setCheckedItem(R.id.nav_account);
        }
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.nav_order: //here we can define what we want to do when that option is selected...


                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new my_order_fragment()).commit();
                break;


            case R.id.nav_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new my_account_fragment()).commit();
                break;

            case R.id.nav_signout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new signout_fragment()).commit();
                break;
            case R.id.nav_faq:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new faq_fragment()).commit();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return  true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}