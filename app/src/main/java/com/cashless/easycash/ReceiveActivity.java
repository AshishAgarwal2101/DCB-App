package com.cashless.easycash;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cashless.easycash.Helpers.SPHelper;
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
    int amount,k=0;
    String phn="8888877777",bankName="DCB",vpa="534534@ybl",accno="31241441414",name="Varsha",
            id,branch="Jayanagar",ifsc="ubi00000127",vpaPin="1234",appPin="2345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        receiverDoneButton = (Button)findViewById(R.id.receiver_done_button);

        load();

        String name1=name;
        if(name.contains(" ")) {
            name1 = name.substring(0, name.indexOf(" "));
        }
        id= UUID.randomUUID().toString();
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("users").child(id).child("name").setValue(name1);
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

                        //Return to main screen after removing data from firebase
                        myRef.child("users").child(id).removeValue();
                        Intent intent = new Intent(ReceiveActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }catch (Exception e){
                    Log.e("ReceiverActivity", e.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        myRef.child("users").child(id).removeValue();
    }

    public void save() {
        SPHelper.setSP(getApplicationContext(),"phone", phn);
        SPHelper.setSP(getApplicationContext(),"bankName", bankName);
        SPHelper.setSP(getApplicationContext(),"vpa", vpa);
        SPHelper.setSP(getApplicationContext(),"accno",accno);
        SPHelper.setSP(getApplicationContext(),"name", name);
        SPHelper.setSP(getApplicationContext(),"branch",branch);
        SPHelper.setSP(getApplicationContext(),"ifsc",ifsc);
        SPHelper.setSP(getApplicationContext(),"vpapin",vpaPin);
        SPHelper.setSP(getApplicationContext(),"apppin",appPin);
    }
    public void load() {
        phn = SPHelper.getSP(getApplicationContext(),"phone"+k, phn);
        bankName = SPHelper.getSP(getApplicationContext(),"bankName"+k, bankName);
        vpa = SPHelper.getSP(getApplicationContext(),"vpa"+k, vpa);
        accno = SPHelper.getSP(getApplicationContext(),"accno"+k,accno);
        name = SPHelper.getSP(getApplicationContext(),"name"+k, name);
        branch = SPHelper.getSP(getApplicationContext(),"branch"+k,branch);
        ifsc = SPHelper.getSP(getApplicationContext(),"ifsc"+k, ifsc);
        vpaPin = SPHelper.getSP(getApplicationContext(),"vpapin"+k,vpaPin);
        appPin = SPHelper.getSP(getApplicationContext(),"apppin"+k,appPin);
        appPin = SPHelper.getSP(getApplicationContext(),"apppin"+k,appPin);
    }
}
