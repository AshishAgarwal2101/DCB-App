package com.cashless.easycash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Ashish on 11/5/2017.
 */

public class CheckShowActivity extends AppCompatActivity {
Intent in;
    private DatabaseReference myRef;
    String vpas,phone;
    static int i=0,k=0;
    String phn="8888877777",bankName="DCB",vpa="534534@ybl",accno="31241441414",name="Ronit",id,branch="Jayanagar",ifsc="ubi00000127",vpaPin="1234",appPin="2345";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_verified);

        in= new Intent(this,PasscodeActivity.class);
        in.putExtra("reason", "hello");
        in.putExtra("id", "id1");
        in.putExtra("amount", 0);
        new LongOperation().execute("");

        myRef = FirebaseDatabase.getInstance().getReference();

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                //Toast.makeText(SendActivity.this, dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                for (DataSnapshot postSnapshot: dataSnapshot.child("vpaValidation").getChildren()) {
                    accno=postSnapshot.getKey();
                    //Toast.makeText(SendActivity.this, id+"", Toast.LENGTH_SHORT).show();
                    phn=postSnapshot.child("phone").getValue(String.class);
                    vpa=postSnapshot.child("vpa").getValue(String.class);
                    //Toast.makeText(SendActivity.this, name+" "+accountNo, Toast.LENGTH_SHORT).show();
                    save();

                    // TODO: handle the post
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }

        });


        imageView = (GifImageView) findViewById(R.id.show_check_gif);

        GifDrawable gifDrawable = null;
        try {
            gifDrawable = new GifDrawable(getResources(), R.drawable.checkmark);
            gifDrawable.setLoopCount(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageDrawable(gifDrawable);

    }
    public void save() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phone"+i, phn);
        editor.putString("bankName"+i, bankName);
        editor.putString("vpa"+i, vpa);
        editor.putString("accno"+i,accno);
        editor.putString("name"+i, name);
        editor.putString("branch"+i,branch);
        editor.putString("ifsc"+i,ifsc);
        editor.putString("vpapin"+i,vpaPin);
        editor.putString("apppin"+i,appPin);
        ++i;
        editor.commit();

    }

    public void load() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        phn = sharedPreferences.getString("phone"+k, phn);
        bankName = sharedPreferences.getString("bankName"+k, bankName);
        vpa = sharedPreferences.getString("vpa"+k, vpa);
        accno = sharedPreferences.getString("accno"+k,accno);
        name = sharedPreferences.getString("name"+k, name);
        branch = sharedPreferences.getString("branch"+k,branch);
        ifsc = sharedPreferences.getString("ifsc"+k, ifsc);
        vpaPin = sharedPreferences.getString("vpapin"+k,vpaPin);
        appPin = sharedPreferences.getString("apppin"+k,appPin);
        ++k;
    }


    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            startActivity(in);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}
