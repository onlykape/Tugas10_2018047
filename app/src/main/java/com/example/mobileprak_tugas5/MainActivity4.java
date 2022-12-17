package com.example.mobileprak_tugas5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import android.widget.TextView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity4 extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        textViewResult = findViewById(R.id.text_view_result);
        dl = (DrawerLayout) findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();

        preferences = getSharedPreferences("AndroidHiveLogin", 0);
        editor = preferences.edit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_chat) {
                    Intent a = new Intent(MainActivity4.this, MainActivity.class);
                    startActivity(a);
                } else if (id == R.id.nav_alarm) {
                    Intent a = new Intent(MainActivity4.this, MainActivity2.class);
                    startActivity(a);
                } else if (id == R.id.nav_bug) {
                    Intent a = new Intent(MainActivity4.this, MainActivity3.class);
                    startActivity(a);
                } else if (id == R.id.nav_api) {
                    Intent a = new Intent(MainActivity4.this, MainActivity4.class);
                    startActivity(a);
                }
                else if (id == R.id.nav_share) {
                    editor.clear();
                    editor.commit();
                    Intent a = new Intent(MainActivity4.this, MainActivity5.class);
                    startActivity(a);
                }
                return true;
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://testimonialapi.toolcarton.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API jsonPlaceHolderApi = retrofit.create(API.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code Eror: " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                for (Post post : posts) {
                    String content = "";
                    content += "Nama : " + post.getName() + "\n";
                    content += "Lokasi : " + post.getLocation() + "\n";
                    content += "Pesan : " + post.getMessage() + "\n";
                    content += "Rating : " + post.getRating() + "\n\n";

                    textViewResult.append(content);
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) ||
                super.onOptionsItemSelected(item);
    }
}