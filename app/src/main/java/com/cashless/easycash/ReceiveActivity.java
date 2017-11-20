package com.cashless.easycash;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class ReceiveActivity extends AppCompatActivity {
    private DatabaseReference myRef;
    public final String TAG = "abc";
    Button receiverDoneButton;
    String userid;
    int amount;
    String phn="8888877777",bankName="DCB",vpa="534534@ybl",accno="31241441414",name="Varsha",id,branch="Jayanagar",ifsc="ubi00000127",vpaPin="1234",appPin="2345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        receiverDoneButton = (Button)findViewById(R.id.receiver_done_button);

        id= UUID.randomUUID().toString();
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("users").child(id).child("name").setValue(name);
        myRef.child("users").child(id).child("vpa").setValue(vpa);

        receiverDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("users").child(id).removeValue();
                Intent intent = new Intent(ReceiveActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    Toast.makeText(ReceiveActivity.this, key, Toast.LENGTH_LONG).show();
                    userid = postSnapshot.child("id").getValue(String.class);
                    amount = postSnapshot.child("amount").getValue(Integer.class);
                    Toast.makeText(ReceiveActivity.this, userid + " " + amount, Toast.LENGTH_LONG).show();
                    if (userid.equals(id)) {

                    }
                }*/
                try {
                    userid = dataSnapshot.child("sending").child("id").getValue(String.class);
                    amount = dataSnapshot.child("sending").child("amount").getValue(Integer.class);
                    //Toast.makeText(ReceiveActivity.this, userid + " " + amount, Toast.LENGTH_LONG).show();
                    if(userid.equals(id)){
                        //Toast.makeText(ReceiveActivity.this, "Notification", Toast.LENGTH_SHORT).show();
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ReceiveActivity.this);
                        mBuilder.setSmallIcon(R.drawable.ico);
                        mBuilder.setContentTitle("Amount "+amount+" credited in your account!");
                        mBuilder.setContentText("Enjoy!!");
                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify(1, mBuilder.build());
                    }
                }catch (Exception e){

                }

                save();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void save() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phone", phn);
        editor.putString("bankName", bankName);
        editor.putString("vpa", vpa);
        editor.putString("accno",accno);
        editor.putString("name", name);
        editor.putString("branch",branch);
        editor.putString("ifsc",ifsc);
        editor.putString("vpapin",vpaPin);
        editor.putString("apppin",appPin);
        editor.commit();
    }
    public void load() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        phn = sharedPreferences.getString("phone", phn);
        bankName = sharedPreferences.getString("bankName", bankName);
        vpa = sharedPreferences.getString("vpa", vpa);
        accno = sharedPreferences.getString("accno",accno);
        name = sharedPreferences.getString("name", name);
        branch = sharedPreferences.getString("branch",branch);
        ifsc = sharedPreferences.getString("ifsc", ifsc);
        vpaPin = sharedPreferences.getString("vpapin",vpaPin);
        appPin = sharedPreferences.getString("apppin",appPin);
    }
}
