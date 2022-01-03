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

public class profile extends AppCompatActivity {

    TextView name,schoolName,email,contact,address;
    EditText newName,newSchoolName,newEmail,newAddress,newContact,oldpass,newpass;
    String nameInput,schoolNameInput,emailInput,contactInput,addressInput;
    Button changepassword,editProfile;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    SharedPreferences sharedPreferences;
    ImageView userProfile;
    char first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.pName);
        schoolName = findViewById(R.id.pSchoolName);
        email = findViewById(R.id.pEmail);
        contact = findViewById(R.id.pContact);
        changepassword = findViewById(R.id.changepassword);
        editProfile = findViewById(R.id.editProfile);
        address = findViewById(R.id.pAddress);
        userProfile = findViewById(R.id.userTeacherProfile);



        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(profile.this);

        nameInput = sharedPreferences.getString("name","Hello user");
        schoolNameInput = sharedPreferences.getString("sc_name","");
        emailInput = sharedPreferences.getString("email","");
        contactInput = sharedPreferences.getString("cont","");
        addressInput = sharedPreferences.getString("address","");

//        if(!addressInput.isEmpty())
//        {
//            address.setVisibility(View.VISIBLE);
//            address.setText(addressInput);
//        }
//        else
//        {
//            address.setVisibility(View.GONE);
//        }
        first = nameInput.charAt(0);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(first),  Color.rgb(179,179,179));
        userProfile.setImageDrawable(drawable);
        name.setText(nameInput);
        schoolName.setText(schoolNameInput);
        email.setText(emailInput);
        contact.setText(contactInput);
        address.setText(addressInput);

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(profile.this);
                LayoutInflater inflater = (LayoutInflater)profile.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.changepassword, null, false);

                Button changeButton = (Button)layout.findViewById(R.id.changeButton);
                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                dialog.setTitle("Change Password");
                dialog.setView(layout);

                AlertDialog alertDialog = dialog.create();

                cancelButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        alertDialog.dismiss();
                    }
                });
                changeButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        oldpass = alertDialog.findViewById(R.id.oldpass);
                        newpass = alertDialog.findViewById(R.id.newpass);
                        if (CheckAllFields()) {
                            updatepassword();
                        }
                    }
                });
                alertDialog.show();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(profile.this);
                LayoutInflater inflater = (LayoutInflater)profile.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.edit_proflie, null, false);

                Button editProfileButton = (Button)layout.findViewById(R.id.editProfileButton);
                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                dialog.setTitle("Edit Profile");
                dialog.setView(layout);
                AlertDialog alertDialog = dialog.create();

                newName = layout.findViewById(R.id.eName);
                newEmail = layout.findViewById(R.id.eEmail);
                newSchoolName = layout.findViewById(R.id.eSchoolName);
                newContact = layout.findViewById(R.id.ePhone);
                newAddress = layout.findViewById(R.id.eAddress);

                newName.setText(nameInput);
                newEmail.setText(emailInput);
                newContact.setText(contactInput);
                newAddress.setText(addressInput);
                newSchoolName.setText(schoolNameInput);

                newContact.setEnabled(false);

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
                            api(newName.getText().toString(),newSchoolName.getText().toString(),
                                    newEmail.getText().toString(),newAddress.getText().toString(),alertDialog);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name",newName.getText().toString());
                            editor.putString("sc_name",newSchoolName.getText().toString());
                            editor.putString("email",newEmail.getText().toString());
                            editor.putString("address",newAddress.getText().toString());
                            editor.commit();
                        }
                    }
                });
                alertDialog.show();
            }
        });

      /*  name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(profile.this);
                LayoutInflater inflater = (LayoutInflater)profile.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.edit_name, null, false);

                Button changeButton = (Button)layout.findViewById(R.id.changeButton);
                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                dialog.setTitle("Change Name");
                dialog.setView(layout);

                AlertDialog alertDialog = dialog.create();

                cancelButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        alertDialog.dismiss();
                    }
                });
                changeButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        newName = alertDialog.findViewById(R.id.newName);

                        if (CheckName()) {
                            api("name",newName.getText().toString(),alertDialog);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name",newName.getText().toString());
                            editor.commit();
                            finish();
                            startActivity(getIntent());
                        }
                    }
                });
                alertDialog.show();
                return false;
            }
        });
        schoolName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(profile.this);
                LayoutInflater inflater = (LayoutInflater)profile.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.edit_schoolname, null, false);

                Button changeButton = (Button)layout.findViewById(R.id.changeButton);
                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                dialog.setTitle("Change School Name");
                dialog.setView(layout);
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
                cancelButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        alertDialog.dismiss();
                    }
                });
                changeButton.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View v)
                    {
                        newSchoolName = alertDialog.findViewById(R.id.newschoolname);
                        if (CheckSchoolName()) {
                            api("sc_name",newSchoolName.getText().toString().trim(),alertDialog);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("sc_name",newSchoolName.getText().toString().trim());
                            editor.commit();
                            finish();
                            startActivity(getIntent());
                        }
                    }
                });

                return false;
            }
        });
        email.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(profile.this);
                LayoutInflater inflater = (LayoutInflater)profile.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.edit_email, null, false);

                Button changeButton = (Button)layout.findViewById(R.id.changeButton);
                Button cancelButton = (Button)layout.findViewById(R.id.cancelButton);
                dialog.setTitle("Change Email");
                dialog.setView(layout);

                AlertDialog alertDialog = dialog.create();

                cancelButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        alertDialog.dismiss();
                    }
                });
                changeButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        newEmail = alertDialog.findViewById(R.id.newemail);
                        if (CheckEmail()) {

                           api("email",newEmail.getText().toString(),alertDialog);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email",newEmail.getText().toString());
                            editor.commit();
                            finish();
                            startActivity(getIntent());
                        }
                    }
                });
                alertDialog.show();
                return false;
            }
        });*/

    }

    private boolean CheckAllfields() {
        if (newName.length() == 0) {
            newName.setError("Please enter name");
            return false;
        }
        if (newName.length() < 3) {
            newName.setError("Please enter valid name");
            return false;
        }
        if (newSchoolName.length() == 0) {
            newSchoolName.setError("Please enter school name");
            return false;
        }
        if (newSchoolName.length() < 3) {
            newSchoolName.setError("Please enter valid School name");
            return false;
        }
        if (newEmail.length() == 0) {
            newEmail.setError("Please enter email ");
            return false;
        }
        if (!newEmail.getText().toString().matches(emailPattern)) {
            newEmail.setError("Please enter valid e-mail");
            return false;
        }
        return true;
    }

    private void updatepassword() {

        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_updatepassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("TAG", "onResponse: " + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");

                    if (message.equals("update")) {
                        Toast.makeText(profile.this, "Password change", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(profile.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(profile.this);
                String tch_id = sharedPreferences.getString("tch_id","");
                Log.d(TAG, "getParams: "+tch_id);
                data.put("oldpass",oldpass.getText().toString());
                data.put("newpass",newpass.getText().toString());
                data.put("tch_id",tch_id);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(profile.this).add(sr);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                startActivity(new Intent(profile.this,home.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean CheckAllFields() {

        if (oldpass.length() == 0) {
            oldpass.setError("Please enter old Password");
            return false;
        }
        if (oldpass.length() < 8) {
            oldpass.setError("Please enter valid password");
            return false;
        }
        if (newpass.length() == 0) {
            newpass.setError("Please enter new password");
            return false;
        }
        if (newpass.length() < 8) {
            newpass.setError("Please enter valid password");
            return false;
        }
        return true;
    }
   /* private boolean CheckName() {

        if (newName.length() == 0) {
            newName.setError("Please enter name");
            return false;
        }
        if (newName.length() < 3) {
            newName.setError("Please enter valid name");
            return false;
        }
        return true;
    }
    private boolean CheckSchoolName() {

        if (newSchoolName.length() == 0) {
            newSchoolName.setError("Please enter school name");
            return false;
        }
        if (newSchoolName.length() < 3) {
            newSchoolName.setError("Please enter valid School name");
            return false;
        }
        return true;
    }
    private boolean CheckEmail() {

        if (newEmail.length() == 0) {
            newEmail.setError("Please enter email ");
            return false;
        }
        if (!newEmail.getText().toString().matches(emailPattern)) {
            newEmail.setError("Please enter valid e-mail");
            return false;
        }
        return true;
    }*/

    public void api(String name, String sc_name,String email, String address, AlertDialog alertDialog){
        ProgressDialog pd = new ProgressDialog(profile.this);
        pd.setTitle("");
        pd.setMessage("Loading...");
      //  pd.show();
        StringRequest sr = new StringRequest(StringRequest.Method.POST,URLs.URL_updateprofile, new Response.Listener<String>() {
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
                        Toast.makeText(profile.this, message, Toast.LENGTH_SHORT).show();
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

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(profile.this);
                String tch_id = sharedPreferences.getString("tch_id","");
                Log.d(TAG, "getParams: "+tch_id);
                data.put("name",name);
                data.put("sc_name",sc_name);
                data.put("email",email);
                data.put("address",address);
                data.put("tch_id",tch_id);
                Log.d(TAG, "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(profile.this).add(sr);
    }
}