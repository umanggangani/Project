package com.myclass.app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private Context mCtx = this;
    private static final String TAG = "MyFirebaseMsgService";
    SharedPreferences sharedPreferences;
    @Override
    public void onNewToken(String token)
    {
        Log.d(TAG, "onNewToken: " + token);
        final Context ctx = getApplicationContext();
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
                   //     String oldToken = sharedPreferences.getString("sh_regid", "none");
                        String u_id = sharedPreferences.getString("u_id", "none");

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        if(u_id.equals("none")) {
                            editor.putString("regid","none");
                            editor.commit();
                        } else {
                            editor.putString("regid",token);
                            editor.commit();
                            sendRegistrationToServer(token);
                        }
                    }
                });
    }

    private void sendRegistrationToServer(final String token) {
        // TODO: Implement this method to send token to your app server.
        String Webserviceurl=URLs.URL_regidEdit;// upload
        Log.d("AAA", Webserviceurl);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String u_id = mPrefs.getString("u_id","none");
        Log.d(TAG, "sendRegistrationToServer: "+u_id);
        StringRequest request=new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
                String action = sharedPreferences.getString("ST","");
                Log.d(TAG, "getParams: "+action);
                data.put("token",token);
                data.put("id",u_id);
                data.put("action",action);
                Log.d(TAG, "getParams: " + data);
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        if (remoteMessage.getData().size() > 0) {
            Log.d("Firebase", "Message data payload: " + remoteMessage.getData());
            JSONObject level1 = new JSONObject(remoteMessage.getData());
            JSONObject level2 = null;
            try {
                level2 = new JSONObject(level1.getString("data"));
                //Log.d(TAG, "level 2 " + level2.toString());
                JSONObject level3 = level2.getJSONObject("payload");
                String title,message,action;
                title = level3.getString("title");
                message = level3.getString("message");
                action = level3.getString("action");
                Log.d(TAG, "onMessageReceived: " + action);
                Log.d("Gyan", "title = " + title + " message = " + message);

                sendNotification(title,message,action);
                Log.d(TAG, "onMessageReceived: " +action);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendNotification(String messageBody,String message,String action) {
        Intent intent = null;
        if(action.equals("Homework")){
            intent = new Intent(MyFirebaseMessagingService.this,home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/raw/notification");
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(messageBody)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setSound(Uri.parse("android.resource://" + mCtx.getPackageName() + "/" + R.raw.notification))
                        .setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        MediaPlayer mp= MediaPlayer.create(mCtx, R.raw.notification);
        mp.start();
        manager.notify(73195, notificationBuilder.build());

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        //notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }
}
