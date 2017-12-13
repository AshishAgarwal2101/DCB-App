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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ReceiveActivity extends AppCompatActivity {
    private DatabaseReference myRef;
    public final String TAG = "abc";
    Button receiverDoneButton;
    String userid,from,to;
    int k=0,currentAccount=0,numberOfTransactions=0,amountInTransaction=0;
    String phn="8888877777",bankName="DCB",vpa="534534@ybl",accno="31241441414",name="Varsha",
            id,branch="Jayanagar",ifsc="ubi00000127",vpaPin="1234",appPin="2345";
    String date="none",with="none",transactionSentOrReceived="Received",transactionId1="none";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        receiverDoneButton = (Button)findViewById(R.id.receiver_done_button);

        loadAccountBeingUsed();
        loadNumberOfTransactions();
        k=currentAccount;
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
                    userid = dataSnapshot.child("sending").child(id).getKey();
                    Toast.makeText(ReceiveActivity.this, userid,Toast.LENGTH_SHORT).show();
                    amountInTransaction = dataSnapshot.child("sending").child(userid).child("amount").getValue(Integer.class);
                    transactionId1=dataSnapshot.child("sending").child(userid).child("transactionId").getValue(String.class);
                    from=dataSnapshot.child("sending").child(userid).child("from").getValue(String.class);
                    with=from;
                    to=name+"("+accno.substring(0,4)+"XXXXXXXX"+accno.substring(accno.length()-4, accno.length())+")";

                    //Toast.makeText(ReceiveActivity.this, userid + " " + amount, Toast.LENGTH_LONG).show();
                    if(userid.equals(id)){
                        //Toast.makeText(ReceiveActivity.this, "Notification", Toast.LENGTH_SHORT).show();
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ReceiveActivity.this);
                        mBuilder.setSmallIcon(R.drawable.ico);
                        mBuilder.setContentTitle("Amount "+amountInTransaction+" credited in your account!");
                        mBuilder.setContentText("Enjoy!!");
                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify(1, mBuilder.build());

                        //setting transaction details in databse
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date currentDate = new Date();
                        date = dateFormat.format(currentDate);
                        myRef.child("transactions").child(transactionId1).child("date").setValue(date);
                        myRef.child("transactions").child(transactionId1).child("from").setValue(from);
                        myRef.child("transactions").child(transactionId1).child("to").setValue(to);
                        myRef.child("transactions").child(transactionId1).child("amount").setValue(amountInTransaction);

                        myRef.child("sending").child(userid).child("status").setValue("success");
                        myRef.child("sending").child(userid).child("amountReceived").setValue(amountInTransaction);

                        k=numberOfTransactions;
                        saveTransactionDetails();
                        k=currentAccount;
                        numberOfTransactions++;
                        saveNumberOfTransactions();

                        //Return to main screen after removing data from firebase
                        myRef.child("users").child(id).removeValue();
                        myRef.removeEventListener(this);
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
        SPHelper.setSP(getApplicationContext(),"phone"+k, phn);
        SPHelper.setSP(getApplicationContext(),"bankName"+k, bankName);
        SPHelper.setSP(getApplicationContext(),"vpa"+k, vpa);
        SPHelper.setSP(getApplicationContext(),"accno"+k,accno);
        SPHelper.setSP(getApplicationContext(),"name"+k, name);
        SPHelper.setSP(getApplicationContext(),"branch"+k,branch);
        SPHelper.setSP(getApplicationContext(),"ifsc"+k,ifsc);
        SPHelper.setSP(getApplicationContext(),"vpapin"+k,vpaPin);
        SPHelper.setSP(getApplicationContext(),"apppin"+k,appPin);
        //++i;
    }

    public void load() {
        phn = SPHelper.getSP(getApplicationContext(),"phone"+k, "8888877777");
        bankName = SPHelper.getSP(getApplicationContext(),"bankName"+k, "DCB");
        vpa = SPHelper.getSP(getApplicationContext(),"vpa"+k, "534534@ybl");
        accno = SPHelper.getSP(getApplicationContext(),"accno"+k,"31241441414");
        name = SPHelper.getSP(getApplicationContext(),"name"+k, "Ronit");
        branch = SPHelper.getSP(getApplicationContext(),"branch"+k,"Jayanagar");
        ifsc = SPHelper.getSP(getApplicationContext(),"ifsc"+k, "ubi00000127+");
        vpaPin = SPHelper.getSP(getApplicationContext(),"vpapin"+k,"1234");
        appPin = SPHelper.getSP(getApplicationContext(),"apppin"+k,"2345");
        //++k;
    }
    public void loadAccountBeingUsed(){
        currentAccount = SPHelper.getSP1(getApplicationContext(),"currentAccount",currentAccount);
    }
    public void saveCurrentAccountBeingUsed(){
        SPHelper.setSP1(getApplicationContext(),"currentAccount",currentAccount);
    }
    public void loadNumberOfTransactions(){
        numberOfTransactions = SPHelper.getSP1(getApplicationContext(), currentAccount+"numberOfTransactions", numberOfTransactions);
    }
    public void saveNumberOfTransactions(){
        SPHelper.setSP1(getApplicationContext(), currentAccount+"numberOfTransactions", numberOfTransactions);
    }
    public void saveTransactionDetails(){
        SPHelper.setSP(getApplicationContext(),currentAccount+"transactionId"+k, transactionId1);
        SPHelper.setSP(getApplicationContext(),currentAccount+"date"+k, date);
        SPHelper.setSP(getApplicationContext(), currentAccount+"to"+k, with);
        SPHelper.setSP(getApplicationContext(), currentAccount+"transactionSentOrReceived"+k, transactionSentOrReceived);
        SPHelper.setSP1(getApplicationContext(), currentAccount+"amount"+k, amountInTransaction);
    }
    public void loadTransactionDetails(){
        transactionId1 = SPHelper.getSP(getApplicationContext(),currentAccount+"transactionId"+k, transactionId1);
        date = SPHelper.getSP(getApplicationContext(),currentAccount+"date"+k, date);
        with = SPHelper.getSP(getApplicationContext(), currentAccount+"to"+k, with);
        transactionSentOrReceived = SPHelper.getSP(getApplicationContext(), currentAccount+"transactionSentOrReceived"+k, transactionSentOrReceived);
        amountInTransaction = SPHelper.getSP1(getApplicationContext(), currentAccount+"amount"+k, amountInTransaction);
    }
}
