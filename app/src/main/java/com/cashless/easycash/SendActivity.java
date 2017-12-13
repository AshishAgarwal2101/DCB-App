package com.cashless.easycash;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SendActivity extends FragmentActivity {
    private DatabaseReference myRef;
    public final String TAG = "abc";
    String name,vpa;
    TextView receiverTextView[] = new TextView[9];
    Button receiverIcon[] = new Button[9];
    int numberUsers;
    ArrayList<Receiver> receivers = new ArrayList<Receiver>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        for(int i=1; i<=9; i++) {
            int res1 = getResources().getIdentifier("receiverTextView" + i, "id", getPackageName());
            int res2 = getResources().getIdentifier("receiverIcon" + i, "id", getPackageName());
            //Toast.makeText(SendActivity.this, res+"", Toast.LENGTH_SHORT).show();
            receiverTextView[i-1] = (TextView) findViewById(res1);
            receiverIcon[i-1] = (Button)findViewById(res2);

            receiverTextView[i-1].setVisibility(View.INVISIBLE);
            receiverIcon[i-1].setVisibility(View.INVISIBLE);
        }

        myRef = FirebaseDatabase.getInstance().getReference();

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(SendActivity.this, dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                numberUsers=0;
                receivers = new ArrayList<Receiver>();
                for(int i=1; i<=9; i++) {
                    receiverTextView[i-1].setVisibility(View.INVISIBLE);
                    receiverIcon[i-1].setVisibility(View.INVISIBLE);
                }
                for (DataSnapshot postSnapshot: dataSnapshot.child("users").getChildren()) {
                    String id=postSnapshot.getKey();
                    //Toast.makeText(SendActivity.this, id+"", Toast.LENGTH_SHORT).show();
                    name=postSnapshot.child("name").getValue(String.class);
                    vpa=postSnapshot.child("vpa").getValue(String.class);
                    //Toast.makeText(SendActivity.this, name+" "+accountNo, Toast.LENGTH_SHORT).show();

                    receivers.add(new Receiver(id, name, vpa));
                    // TODO: handle the post
                }
                for(Receiver receiver:receivers){
                    //receiverTextView[numberUsers].setVisibility(View.VISIBLE);
                    receiverIcon[numberUsers].setVisibility(View.VISIBLE);
                    receiverIcon[numberUsers].setText(receiver.getName());
                    numberUsers++;
                }
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //Name = dataSnapshot.child("Passengers").child(pnr).child("Name").getValue(String.class);
                save();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        for(int i=0; i<9; i++){
            final int j=i;
            receiverIcon[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name1 = receivers.get(j).getName();
                    String vpa1 = receivers.get(j).getVpa();
                    String userid1 = receivers.get(j).getId();
                    String accinfo = receivers.get(j).getName()+" "+receivers.get(j).getVpa();
                    //Toast.makeText(SendActivity.this, accinfo, Toast.LENGTH_SHORT).show();
                    FragmentManager manager = getSupportFragmentManager();
                    DialogPaymentFragment dialog = DialogPaymentFragment.newInstance(name1, vpa1, userid1,getIntent().getStringExtra("accno"));
                    dialog.show(manager, "DialogPayment");
                }
            });
        }
    }

    public void DB(){

    }

    public void save() {
        //SPHelper.setSP(getApplicationContext(), "pnr", pnr);
    }

    /*public void user(View view) {
        pnr = et.getText().toString();
        DB();
        Intent intent = new Intent(MainActivity.this, user.class);
        startActivity(intent);
        //Toast.makeText(MainActivity.this, Name, Toast.LENGTH_SHORT).show();
        finish();
    }*/
}
