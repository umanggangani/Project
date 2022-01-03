package com.myclass.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amulyakhare.textdrawable.TextDrawable;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class student_profile extends AppCompatActivity {

    TextView studentName,studentContact,studentEmail,studentBloodGroup,studentAddress,studentRemark,studentParentsName,studentParentsNumber;
    EditText eStudentName,eStudentContact,eStudentEmail,eStudentBloodGroup,eStudentAddress,eStudentRemark,eStudentParentsName,eStudentParentsNumber;
    String sName,sContact,sEmail,sBloodGroup,sAddress,sRemark,sParentsName,sParentsNumber;
    SharedPreferences sharedPreferences;
    Button editProfile,changePassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ImageView userProfileImg;
    char first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(student_profile.this);

        sName = sharedPreferences.getString("name","Hello user");
        sContact = sharedPreferences.getString("cont","");
        sEmail = sharedPreferences.getString("email","");
        sBloodGroup = sharedPreferences.getString("bloodgroup","");
        sAddress = sharedPreferences.getString("address","");
        sRemark = sharedPreferences.getString("remark","");
        sParentsName = sharedPreferences.getString("parentsName","");
        sParentsNumber = sharedPreferences.getString("parentsNumber","");

        studentName = findViewById(R.id.pStudentName);
        studentContact = findViewById(R.id.pStudentContact);
        studentEmail = findViewById(R.id.pStudentEmail);
        studentAddress = findViewById(R.id.pStudentAddress);
        studentBloodGroup = findViewById(R.id.pStudentBloodGroup);
        studentRemark = findViewById(R.id.pStudentRemark);
        editProfile = findViewById(R.id.editStudentProfile);
        changePassword = findViewById(R.id.changeStudentPassword);
        userProfileImg = findViewById(R.id.userStudentProfile);
        studentParentsName = findViewById(R.id.pStudentParentsName);
        studentParentsNumber = findViewById(R.id.pStudentParentsNumber);

        if(!sAddress.equals("NA"))
        {
            studentAddress.setText(sAddress);
        }
        else{  studentAddress.setText("");}
        if(!sBloodGroup.equals("NA"))
        {
            studentBloodGroup.setText(sBloodGroup);
        }
        else{studentBloodGroup.setText("");}
        if(!sRemark.equals("NA"))
        {
            studentRemark.setText(sRemark);
        }
        else{ studentRemark.setText("");}
        if(!sParentsName.equals("NA"))
        {
            studentParentsName.setText(sParentsName);
        }
        else{studentParentsName.setText("");}
        if(!sParentsNumber.equals("NA"))
        {
            studentParentsNumber.setText(sParentsNumber);
        }
        else{studentParentsNumber.setText("");}
        studentName.setText(sName);
        studentContact.setText(sContact);
        studentEmail.setText(sEmail);
//        studentAddress.setText("");
//        studentBloodGroup.setText("");
//        studentRemark.setText("");
//        studentParentsName.setText("");
//        studentParentsNumber.setText("");

        first = sName.charAt(0);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(first), Color.rgb(179,179,179));
        userProfileImg.setImageDrawable(drawable);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(student_profile.this);
                LayoutInflater inflater = (LayoutInflater)student_profile.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.edit_student_profile, null, false);

                Button editProfileButton = (Button)layout.findViewById(R.id.editStudentProfileButton);
                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                dialog.setTitle("Edit Profile");
                dialog.setView(layout);
                AlertDialog alertDialog = dialog.create();

                eStudentName = layout.findViewById(R.id.eStudentName);
                eStudentEmail = layout.findViewById(R.id.eStudentEmail);
                eStudentContact = layout.findViewById(R.id.eStudentPhone);
                eStudentAddress = layout.findViewById(R.id.eStudentAddress);
                eStudentBloodGroup = layout.findViewById(R.id.eStudentBooldGroup);
                eStudentRemark = layout.findViewById(R.id.eStudentRemark);
                eStudentParentsName = layout.findViewById(R.id.eStudentParentsName);
                eStudentParentsNumber = layout.findViewById(R.id.eStudentParentsNumber);

                if(!sAddress.equals("NA"))
                {
                    eStudentAddress.setText(sAddress);
                }else{eStudentAddress.setText("");}
                if(!sBloodGroup.equals("NA"))
                {
                    eStudentBloodGroup.setText(sBloodGroup);
                }else{eStudentBloodGroup.setText("");}
                if(!sRemark.equals("NA"))
                {
                    eStudentRemark.setText(sRemark);
                }else{eStudentRemark.setText("");}
                if(!sParentsName.equals("NA"))
                {
                    eStudentParentsName.setText(sParentsName);
                }else{ eStudentParentsName.setText("");}
                if(!sParentsNumber.equals("NA"))
                {
                    eStudentParentsNumber.setText(sParentsNumber);
                }else{eStudentParentsNumber.setText("");}
               eStudentName.setText(sName);
               eStudentContact.setText(sContact);
               eStudentEmail.setText(sEmail);
//               eStudentAddress.setText("");
//               eStudentBloodGroup.setText("");
//               eStudentRemark.setText("");
//               eStudentParentsName.setText("");
//               eStudentParentsNumber.setText("");

               eStudentContact.setEnabled(false);

                cancelButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        alertDialog.dismiss();
                    }
                });
                editProfileButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {

                        if (CheckAllfields()) {
                            api(eStudentName.getText().toString(),eStudentEmail.getText().toString(),
                                    eStudentAddress.getText().toString(),eStudentBloodGroup.getText().toString(),
                                    eStudentRemark.getText().toString(),alertDialog);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name",eStudentName.getText().toString());
                            editor.putString("email",eStudentEmail.getText().toString());
                            editor.putString("address",eStudentAddress.getText().toString());
                            editor.putString("bloodgroup",eStudentBloodGroup.getText().toString());
                            editor.putString("remark",eStudentRemark.getText().toString());
                            editor.putString("parentsName",eStudentParentsName.getText().toString());
                            editor.putString("parentsNumber",eStudentParentsNumber.getText().toString());
                            editor.commit();

                        }
                    }
                });
                alertDialog.show();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                startActivity(new Intent(student_profile.this,student_home.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean CheckAllfields() {
        if (eStudentName.length() == 0) {
            eStudentName.setError("Please enter name");
            return false;
        }
        if (eStudentName.length() < 3) {
            eStudentName.setError("Please enter valid name");
            return false;
        }
        if (eStudentEmail.length() == 0) {
            eStudentEmail.setError("Please enter email ");
            return false;
        }
        if (!eStudentEmail.getText().toString().matches(emailPattern)) {
            eStudentEmail.setError("Please enter valid e-mail");
            return false;
        }
        return true;
    }

    public void api(String name, String email,String address, String bloodGroup,String remark ,AlertDialog alertDialog){
        ProgressDialog pd = new ProgressDialog(student_profile.this);
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_student_editProfile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd.dismiss();
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");

                    if (message.equals("Update")) {
                        finish();
                        startActivity(getIntent());
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(student_profile.this, message, Toast.LENGTH_SHORT).show();
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

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(student_profile.this);
                String stu_id = sharedPreferences.getString("stu_id","");
                Log.d(TAG, "getParams: "+stu_id);
                data.put("name",name);
                data.put("email",email);
                data.put("address",address);
                data.put("bloodgroup",bloodGroup);
                data.put("remark",remark);
                data.put("stu_id",stu_id);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(student_profile.this).add(sr);
    }
}