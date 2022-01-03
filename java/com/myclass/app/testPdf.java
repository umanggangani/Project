package com.myclass.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class testPdf extends AppCompatActivity {

    PDFView pdfView;
    private static final String TAG = "pdf";
    FrameLayout frameLayout;
    Fragment fragment = null;
    private DrawerLayout drawerLy;
    FloatingActionButton ans;
    String pdfurl,action,id,user,Ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_test_pdf);

        pdfView = findViewById(R.id.pdf);
        Intent intent = getIntent();
         pdfurl = intent.getStringExtra("url");
         action = intent.getStringExtra("action");
         id = intent.getStringExtra("id");
         user = intent.getStringExtra("user");
         Ans = intent.getStringExtra("ans");
        int num = Integer.parseInt(intent.getStringExtra("num"));
        Log.d(TAG, "onCreate: "+ pdfurl+action+id+num);
//        String pdfurl =  "https://myclass.zocarro.in/app/image/test/2021-08-05-23:26:03.pdf";
//        String action = "Test";
//        String id = "12";
//        int num = 25;

        new RetrivePDFfromUrl().execute(pdfurl);

        drawerLy = (DrawerLayout) findViewById(R.id.drawerLy);
        ans = findViewById(R.id.ans);
        frameLayout = findViewById(R.id.frameLayout);
        fragment = new right_drawer(id,num,user,Ans);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: okkk ");
                drawerLy.openDrawer(Gravity.RIGHT);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(action);
    }
    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            pdfView.fromStream(inputStream).load();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            if(user.equals("Teacher")) {
                    finish();
                    startActivity(new Intent(testPdf.this, home.class));
                    return true;
            } else {
                    finish();
                    startActivity(new Intent(testPdf.this, student_home.class));
                    return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed() {
        // check if drawer is open
        if (drawerLy.isDrawerOpen(Gravity.RIGHT)) {
            // close drawer when it is open
            drawerLy.closeDrawers();
            Toast.makeText(testPdf.this, "Closed Drawer", Toast.LENGTH_SHORT).show();
        } else {
            // close activity when drawer is closed
            super.onBackPressed();
        }
    }
}