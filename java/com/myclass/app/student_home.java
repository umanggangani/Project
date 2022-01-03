package com.myclass.app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class student_home extends AppCompatActivity {

    public DrawerLayout drawerLayout1;
    SharedPreferences sharedPreferences;
    Intent intent;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;
    AlertDialog.Builder builder;
    String stu_id,action;
    SharedPreferences.Editor editor;
    LinearLayout notice,meet,video,material,hw,test;
    TextView userName,userName2,classNameTextview;
    ProgressDialog pd;
    TextView classCode;
    ImageView userImage1,userImage2;
   // private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    String name;
    SearchView searchView;
    char first;
    private com.myclass.app.studentClassNameAdapter studentClassNameAdapter;
    private RecyclerView classList;
    private ArrayList<studentClassModel> studentClassModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_student_home);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(student_home.this);

        navigationView = (NavigationView)findViewById(R.id.nav_view1);
        builder = new AlertDialog.Builder(this);
        drawerLayout1 = findViewById(R.id.drawerLayout1);
        notice = findViewById(R.id.snotice);
        video = findViewById(R.id.svideo);
        meet = findViewById(R.id.smeet);
        material = findViewById(R.id.smaterial);
        hw = findViewById(R.id.shw);
        test = findViewById(R.id.stest);
        pd = new ProgressDialog(student_home.this);
        classList = findViewById(R.id.studentClassList);
        userImage1 = findViewById(R.id.userStudentImg);
        studentClassModelArrayList = new ArrayList<studentClassModel>();
        classNameTextview = findViewById(R.id.className);
       // changeClass();

//        classname = sharedPreferences.getString("className","");
//        className.setText("Categories of "+classname);

        searchView = findViewById(R.id.student_search_bar);
        searchView.clearFocus();


        classList();
        name = sharedPreferences.getString("name","");
        userName = findViewById(R.id.sUserName);
        userName.setText(name);
        first = name.charAt(0);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(first),  Color.rgb(179,179,179));
        userImage1.setImageDrawable(drawable);
        userImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(student_home.this,student_profile.class));
            }
        });

        View headerView = navigationView.getHeaderView(0);
        userName2 = headerView.findViewById(R.id.userName2);
        userName2.setText(name);
        userImage2 = headerView.findViewById(R.id.userImg2);
        TextDrawable drawable1 = TextDrawable.builder()
                .buildRound(String.valueOf(first),  Color.rgb(179,179,179));
        userImage2.setImageDrawable(drawable1);

        userImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(student_home.this,student_profile.class));
            }
        });

         stu_id = sharedPreferences.getString("stu_id", "");
         action = sharedPreferences.getString("ST","");
        getSupportActionBar().setTitle("Home");

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout1,R.string.nav_open,R.string.nav_close);
        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button

        drawerLayout1.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id) {
                    case R.id.nav_dashboard:
                        intent = new Intent(student_home.this, student_notice.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_join_class:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(student_home.this);
                        LayoutInflater inflater = (LayoutInflater) student_home.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                        View layout = inflater.inflate(R.layout.join_class, null, false);
                        Button joinButton = (Button) layout.findViewById(R.id.join);
                        Button cancelButton = (Button) layout.findViewById(R.id.cancelButton);
                        classCode = layout.findViewById(R.id.classCode);

                        dialog.setTitle("Join Class");
                        dialog.setView(layout);

                        AlertDialog alertDialog = dialog.create();

                        cancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });
                        joinButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (CheckCode()) {
                                    joinClass(classCode.getText().toString(),alertDialog, (EditText) classCode);
                                }
                            }
                        });
                        alertDialog.show();
                        break;
                    case R.id.nav_help:
                        Toast.makeText(student_home.this, "Help",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_support:
                        Toast.makeText(student_home.this, "Support",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_about:
                        Toast.makeText(student_home.this, "About",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_logout:
                        //Uncomment the below code to Set the message and title from the strings.xml file
                        // builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);
                        // Setting message manually and performing action on button click
                        builder.setMessage("Do you want to close this application ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        logout(stu_id);
                                        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(student_home.this);
                                        sharedPreferences.edit().clear().commit();
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
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String regid = sharedPreferences.getString("regid", "none");
        Log.d(TAG, "onCreate:regid " + regid);
        if (regid.equals("none")) {
            FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(final String newToken) {
                    Log.d(TAG, "onSuccess: " + newToken);
                    String Webserviceurl = URLs.URL_regidEdit;//update token

                    StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "tyggubhh" + response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> data = new HashMap<>();
                            data.put("token", newToken);
                            data.put("id", stu_id);
                            data.put("action",action);
                            Log.d(TAG, "getParams: " + data);
                            return data;
                        }
                    };
                    request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
                    AppController.getInstance().addToRequestQueue(request);
                }
            });
        }
        else {
            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        }

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("cat","Notice");
                intent = new Intent(student_home.this,student_categoties_list.class);
                startActivity(intent);
//                startActivity(new Intent(student_home.this,student_notice.class));
            }
        });
        meet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(student_home.this,student_categoties_list.class);
                intent.putExtra("cat","Meet");
                startActivity(intent);
//                startActivity(new Intent(student_home.this,student_meet.class));
            }
        });
        material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(student_home.this,student_categoties_list.class);
                intent.putExtra("cat","Material");
                startActivity(intent);
//                startActivity(new Intent(student_home.this,student_material.class));
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(student_home.this,student_categoties_list.class);
                intent.putExtra("cat","Video");
                startActivity(intent);
//                startActivity(new Intent(student_home.this,student_video.class));
            }
        });
        hw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(student_home.this,student_categoties_list.class);
                intent.putExtra("cat","Home Work");
                startActivity(intent);
//                startActivity(new Intent(student_home.this,student_homework.class));
            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(student_home.this,student_categoties_list.class);
                intent.putExtra("cat","Test");
                startActivity(intent);
//                startActivity(new Intent(student_home.this,student_test.class));
            }
        });
    }

    private boolean CheckCode() {
        if (classCode.length() == 0) {
            classCode.setError("Please enter code ");
            return false;
        }
        if (classCode.length()  != 4) {
            classCode.setError("Please enter valid code");
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                         startActivity(new Intent(student_home.this,signin.class));
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
                // String tch_id = sharedPreferences.getString("tch_id","");
                data.put("action",action);
                data.put("id",id);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(student_home.this).add(sr);
    }

    private void classList(){
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_student_classList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    studentClassModelArrayList.clear();
                    Log.d("TAG", "onResponse: " + response);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        // on below line we are extracting data from our json object.
                        studentClassModelArrayList.add(new studentClassModel(object.getString("cid"), object.getString("classname"),object.getString("teachername")));
                        // passing array list to our adapter class.
                    }
                    studentClassNameAdapter = new studentClassNameAdapter(studentClassModelArrayList, student_home.this, new StudentOnClassSelectListener() {
                        @Override
                        public void selectClassName(String className) {

                            classNameTextview.setText("Categories of "+className);
                        }
                    });
                    // setting layout manager to our recycler view.
                    classList.setLayoutManager(new LinearLayoutManager(student_home.this,LinearLayoutManager.HORIZONTAL,false));
                    // setting adapter to our recycler view.
                    classList.setAdapter(studentClassNameAdapter);
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
                data.put("stu_id",sharedPreferences.getString("stu_id",""));
                Log.d(TAG, "f: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(student_home.this).add(sr);
    }

    private void joinClass(String classCode, AlertDialog alertDialog, EditText classCodeText){
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_student_joinClass, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if (message.equals("Requested")) {
                        alertDialog.dismiss();
                        finish();
                        startActivity(getIntent());
                    }
                    else
                    {
                        classCodeText.setError(message);
                    }
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
                data.put("stu_id",sharedPreferences.getString("stu_id",""));
                data.put("code",classCode);
                Log.d(TAG, "f: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(student_home.this).add(sr);
    }

}