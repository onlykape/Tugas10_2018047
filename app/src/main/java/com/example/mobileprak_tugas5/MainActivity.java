package com.example.mobileprak_tugas5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.content.SharedPreferences;
import com.google.android.material.navigation.NavigationView;
import com.example.mobileprak_tugas5.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private ActivityMainBinding binding;
    RecyclerView recyclerView;
    String s1[],s2[],s3[];
    int images [] = {R.mipmap.kotakexoticshothair,R.mipmap.kotakmainecoon,R.mipmap.kotakbengal,R.mipmap.kotakpersia};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dl = (DrawerLayout) findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();

        preferences = getSharedPreferences("AndroidHiveLogin", 0);
        editor = preferences.edit();


        final OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class).build();
            binding.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WorkManager.getInstance().enqueueUniqueWork(
                            "Notifikasi", ExistingWorkPolicy.REPLACE, request);
                }
            });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_chat) {
                    Intent a = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(a);
                } else if (id == R.id.nav_alarm) {
                    Intent a = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(a);
                } else if (id == R.id.nav_bug) {
                    Intent a = new Intent(MainActivity.this, MainActivity3.class);
                    startActivity(a);
                }
                else if (id == R.id.nav_api) {
                    Intent a = new Intent(MainActivity.this, MainActivity4.class);
                    startActivity(a);
                }
                else if (id == R.id.nav_share) {
                    editor.clear();
                    editor.commit();
                    Intent a = new Intent(MainActivity.this, MainActivity5.class);
                    startActivity(a);
                }
                return true;
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        s1 = getResources().getStringArray(R.array.kucing);
        s2 = getResources().getStringArray(R.array.harga);
        s3 = getResources().getStringArray(R.array.star);
        KomponenAdapter appAdapter = new KomponenAdapter(this, s1, s2, s3, images);
        recyclerView.setAdapter(appAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

        }