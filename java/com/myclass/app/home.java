package com.myclass.app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.amulyakhare.textdrawable.TextDrawable;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class home extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;
    Intent intent;
    ImageView userImg1,userImg2;
    TextView userName1,userName2,schoolName;
    AlertDialog.Builder builder;
    LinearLayout video,meeting,homeWork,material,myClass,student,notice,test;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;
    boolean isClassAvailable = false;
    androidx.appcompat.widget.SearchView search;
    ArrayList<SliderData> sliderDataArrayList;
    SliderView sliderView;
    SliderAdapter adapter;
    ProgressDialog pd;
    String tch_id,action,name,scName;
    char first;

    private static String value;
    public static String getValue() {
        return value;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userImg1 = findViewById(R.id.userTeacherImg);
        userName1 = findViewById(R.id.username1);
        schoolName = findViewById(R.id.schoolname);
        video = findViewById(R.id.video);
        meeting = findViewById(R.id.meet);
        homeWork = findViewById(R.id.hw);
        material = findViewById(R.id.material);
        myClass =findViewById(R.id.myClass);
        student = findViewById(R.id.student);
        notice = findViewById(R.id.notice);
        test = findViewById(R.id.test);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        builder = new AlertDialog.Builder(this);
        drawerLayout = findViewById(R.id.drawerLayout);
        pd = new ProgressDialog(home.this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(home.this);
        editor = sharedPreferences.edit();

        sliderDataArrayList = new ArrayList<>();
        sliderView = findViewById(R.id.slider);
        getSupportActionBar().setTitle("Home");

        isClassAvailable = sharedPreferences.getBoolean("isClassAvailable", false);
        if(!isClassAvailable)
        {
            createClass();
        }

    /*    search = findViewById(R.id.search_bar);
        search.setQueryHint("Search here");

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                value = search.getQuery().toString().trim();
                startActivity(new Intent(home.this,search.class));
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });*/
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                value = search.getQuery().toString().trim();
//                startActivity(new Intent(home.this,search.class));
//            }
//        });
//        search_bar.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        String regid = sharedPreferences.getString("regid", "none");
        Log.d(TAG, "onCreate:regid "  +regid);
        if(regid.equals("none"))
        {
            FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(final String newToken)
                {
                    Log.d(TAG, "onSuccess: " + newToken);
                    StringRequest request=new StringRequest(StringRequest.Method.POST, URLs.URL_regidEdit, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String message = object.getString("message");
                                Log.d(TAG, "tyggubhh" + response);
                                if (message.equals("Success")) {
                                    editor = sharedPreferences.edit();
                                    editor.putString("regid", object.getString("regid"));
                                    editor.commit();
                                }
                                else{
                                    editor.putString("regid","none");
                                    editor.commit();
                                }
                            }
                            catch (Exception e) {
                                pd.dismiss();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError
                        {
                            Map<String,String> data=new HashMap<>();
                            data.put("token", newToken);
                            String tch_id = sharedPreferences.getString("tch_id","");
                            String action = sharedPreferences.getString("ST","");
                            data.put("action",action);
                            data.put("id", tch_id);
                            Log.d(TAG, "getParams: " + data);
                            return data;
                        }
                    };
                    request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
                    AppController.getInstance().addToRequestQueue(request);
                }
            });
        } else {
            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        }

        name = sharedPreferences.getString("name","Hello user");
        scName = sharedPreferences.getString("sc_name","School name");
        tch_id = sharedPreferences.getString("tch_id", "");
        action = sharedPreferences.getString("ST","");


        displayImg();

        first = name.charAt(0);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(first),  Color.rgb(179,179,179));
        userImg1.setImageDrawable(drawable);
        userName1.setText(name);
        schoolName.setText(scName);
        userImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this,profile.class));
            }
        });
        /*
        ImageView imageView4 = findViewById(R.id.mat);
        ImageView imageView1 = findViewById(R.id.vo);
        ImageView imageView2 = findViewById(R.id.me);
        ImageView imageView3 = findViewById(R.id.ho);
        Glide.with(this).load(R.drawable.material1).into(imageView4);
        Glide.with(this).load(R.drawable.video2).into(imageView1);
        Glide.with(this).load(R.drawable.hw3).into(imageView2);
        Glide.with(this).load(R.drawable.hw2).into(imageView3);
        */
        View headerView = navigationView.getHeaderView(0);
        userName2 = headerView.findViewById(R.id.userName2);
        userName2.setText(name);
        userImg2 = headerView.findViewById(R.id.userImg2);
        TextDrawable drawable1 = TextDrawable.builder()
                .buildRound(String.valueOf(first),  Color.rgb(179,179,179));
        userImg2.setImageDrawable(drawable1);
        userImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this,profile.class));
            }
        });

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close);
        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.nav_dashboard:
                        intent = new Intent(home.this, noticeList.class);
                        startActivity(intent);
                       break;
                    case R.id.nav_class:
                        intent = new Intent(home.this,classList.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_student:
                        intent = new Intent(home.this, studentList.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_help:
                        Toast.makeText(home.this, "Help",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_support:
                        Toast.makeText(home.this, "Support",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_about:
                        Toast.makeText(home.this, "About",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_logout:

                        //Uncomment the below code to Set the message and title from the strings.xml file
                       // builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);
                      // Setting message manually and performing action on button click
                        builder.setMessage("Do you want to close this application ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        logout(tch_id);
                                        sharedPreferences.edit().clear().commit();
//                                        startActivity(new Intent(home.this,signin.class));
//                                        finish();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        dialog.cancel();
                                    }
                                });
                        //Creating dialog box
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle("Log out");
                        alert.show();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
        myClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    intent = new Intent(home.this, classList.class);
                    startActivity(intent);
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClassAvailable = sharedPreferences.getBoolean("isClassAvailable", false);
                if(isClassAvailable) {
                    intent = new Intent(home.this, studentList.class);
                    startActivity(intent);
                }
                else
                {
                    message("student");
                }
            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    intent = new Intent(home.this, noticeList.class);
                    startActivity(intent);
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClassAvailable = sharedPreferences.getBoolean("isClassAvailable", false);
                if(isClassAvailable) {
                    intent = new Intent(home.this, videoList.class);
                    startActivity(intent);
                }
                else{
                    message("video");
                }
            }
        });
        meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClassAvailable = sharedPreferences.getBoolean("isClassAvailable", false);
                if(isClassAvailable) {
                    intent = new Intent(home.this, meetList.class);
                    startActivity(intent);
                }
                else{
                    message("meeting");
                }
            }
        });
        homeWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClassAvailable = sharedPreferences.getBoolean("isClassAvailable", false);
                if(isClassAvailable) {
                    intent = new Intent(home.this, homeworkList.class);
                    startActivity(intent);
                }
                else{
                    message("home work");
                }
            }
        });
        material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClassAvailable = sharedPreferences.getBoolean("isClassAvailable", false);
                if(isClassAvailable) {
                    intent = new Intent(home.this, materialList.class);
                    startActivity(intent);
                }
                else{
                    message("material");
                }
            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClassAvailable = sharedPreferences.getBoolean("isClassAvailable", false);
                if(isClassAvailable) {
                    intent = new Intent(home.this, testList.class);
                    startActivity(intent);
                }
                else{
                    message("test");
                }
            }
        });

    }

    private void logout(String id) {
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_logoutregidEdit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if(message.equals("success")) {
                        startActivity(new Intent(home.this,signin.class));
                    }
                    editor.commit();
                } catch (Exception e) {
                    pd.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Log.d("TAG", "onErrorResponse: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("id",id);
                data.put("action",action);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(home.this).add(sr);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.search1) {
            //add the function to perform here
            startActivity(new Intent(home.this, com.myclass.app.search.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void createClass(){
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_checkClass, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if(message.equals("success")) {
                        Log.d(TAG, "onResponse: class  true");
                        editor.putBoolean("isClassAvailable",true);
                    }
                    else{
                        editor.putBoolean("isClassAvailable",false);
                    }
                    editor.commit();
                } catch (Exception e) {
                    pd.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Log.d("TAG", "onErrorResponse: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                String tch_id = sharedPreferences.getString("tch_id","");
                data.put("tch_id",tch_id);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(home.this).add(sr);

    }
    public void message(String item){
        AlertDialog.Builder dialog = new AlertDialog.Builder(home.this);

        View customLayout = getLayoutInflater().inflate(R.layout.creatclass_message, null);

        TextView masseage = (TextView)customLayout.findViewById(R.id.message);
        masseage.setText("Please create class before add "+item);
        Button okButton = (Button)customLayout.findViewById(R.id.okButton);

        dialog.setTitle("Please Create Class");
        dialog.setView(customLayout);
        AlertDialog alertDialog = dialog.create();

        okButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
    public void displayImg(){
//        pd.setTitle("");
//        pd.setMessage("Loading...");
//        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_banner, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                    pd.dismiss();
                    sliderDataArrayList.clear();
                    Log.d("TAG", "onResponse: " + response);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        // on below line we are extracting data from our json object.
                        sliderDataArrayList.add(new SliderData(object.getString("imgUrl")));
                        // passing array list to our adapter class.
                        adapter = new SliderAdapter(home.this, sliderDataArrayList);
                        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                        sliderView.setSliderAdapter(adapter);
                        sliderView.setScrollTimeInSec(3);
                        sliderView.setAutoCycle(true);
                        sliderView.startAutoCycle();
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    pd.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                pd.dismiss();
                Log.d("TAG", "onErrorResponse: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                String tch_id = sharedPreferences.getString("tch_id","");
                Log.d(TAG, "getParams: "+tch_id);

                data.put("tch_id",tch_id);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(home.this).add(sr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
