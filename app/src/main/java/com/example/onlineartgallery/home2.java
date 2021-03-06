package com.example.onlineartgallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.onlineartgallery.Fragment.HomeFragment;
import com.example.onlineartgallery.Fragment.NotificationFragment;
import com.example.onlineartgallery.Fragment.ProfileFragment;
import com.example.onlineartgallery.Fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class home2 extends AppCompatActivity {

    BottomNavigationView bottomNavigationView ;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

    }

    private  BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {

                        case R.id.nav_home:

                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.nav_profile:
                          SharedPreferences.Editor editor= getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                          editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                           editor.apply();
                          selectedFragment = new ProfileFragment();

                            //selectedFragment = null;
                            //startActivity(new Intent(home2.this,profile.class) );

                            break;

                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;

                        case R.id.nav_add:
                            selectedFragment = null;
                           startActivity(new Intent(home2.this,PostActivity.class) );
                            break;

                        case R.id.nav_heart:

                            selectedFragment = new NotificationFragment();
                            break;



                    }
                    if(selectedFragment!=null){

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                        Log.d("SelectedFragmented","success");
                    }
                    else{
                        Log.d("SelectedFragmented","failed");
                    }


                    return true;
                }
            };
}