package com.myclass.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


public class addNotice1 extends Fragment {

    EditText noticeName, noticeDes, noticeAttachment;
    Button addNotice;

    private int PICK_PDF_REQUEST = 1;
    private Uri pdfPath;
    private RequestQueue rQueue;
    String displayName = null;
    ProgressDialog pd;

    ImageView select;
    String noticeNameInput, noticeDesInput;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_notice1, container, false);
        noticeName = view.findViewById(R.id.noticeName);
        noticeDes = view.findViewById(R.id.noticeDes);
        noticeAttachment = view.findViewById(R.id.noticeFile);
        addNotice = view.findViewById(R.id.addNotice);
        select = view.findViewById(R.id.select);
        pd = new ProgressDialog(getActivity());

        addNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckAllFields()) {
                    uploadPDF(displayName,pdfPath);
                }
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"),PICK_PDF_REQUEST);
            }
        });

        return view;
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data)
    {
        if(requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK){
            pdfPath = data.getData();
            String uriString = pdfPath.toString();
            Log.d(TAG, "onActivityResult: " + uriString);
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getActivity().getContentResolver().query(pdfPath, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("nameeeee>>> ", displayName);
                        noticeAttachment.setText(displayName);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("nameeeee>>>>  ", displayName);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private boolean CheckAllFields() {

        if (noticeName.length() == 0) {
            noticeName.setError("Please enter Titel");
            return false;
        }
        if (noticeDes.length() == 0) {
            noticeDes.setError("Please enter des");
            return false;
        }
        if (noticeAttachment.length() == 0) {
            noticeAttachment.setError("Please enter attachment");
            return false;
        }
        return true;
    }

    private void uploadPDF(final String pdfname, Uri pdffile){
        pd.setTitle("");
        pd.setMessage("Loading...");
        pd.show();
        InputStream iStream = null;
        try {
            iStream = getActivity().getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.URL_addNotice,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            Log.d("ressssssoo",new String(response.data));
                            rQueue.getCache().clear();
                            try {
                                pd.dismiss();
                                JSONObject jsonObject = new JSONObject(new String(response.data));
                                //jsonObject.toString().replace("\\\\","");
                                String message = jsonObject.getString("message");
                                if (message.equals("Notice Added Successfuly")) {
                                    Toast.makeText(getActivity(), "Notice added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), noticeList.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                } else {
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                pd.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("TAG", "onErrorResponse: " + error);
                            pd.dismiss();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    noticeNameInput = noticeName.getText().toString();
                    noticeDesInput = noticeDes.getText().toString();

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    String tch_id = sharedPreferences.getString("tch_id","");

                    data.put("name", noticeNameInput);
                    data.put("des", noticeDesInput);
                    data.put("tch_id",tch_id );
                    Log.d(TAG, "getParams: "+data);
                    return data;
                }
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> data = new HashMap<>();
                    data.put("file", new DataPart(pdfname ,inputData));
                    Log.d(TAG, "getParams: "+data);
                    return data;
                }
            };
            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(getActivity());
            rQueue.add(volleyMultipartRequest);

        } catch (FileNotFoundException e) {
            pd.dismiss();
            e.printStackTrace();
        } catch (IOException e) {
            pd.dismiss();
            e.printStackTrace();
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}