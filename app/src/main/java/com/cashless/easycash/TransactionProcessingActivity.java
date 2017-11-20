package com.cashless.easycash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class TransactionProcessingActivity extends AppCompatActivity {
    private DatabaseReference myRef;
    TextView transactionId, processingTextView;
    LinearLayout transactionDetails;
    Button transactionHomeButton;
    private ImageView imageView;
    String userid;
    int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_processing);

        int randomNum = 10000000 + (int)(Math.random() * 99999999);
        String transactionIdRandom = "ABC"+randomNum;

        userid = getIntent().getExtras().getString("id");
        amount = getIntent().getExtras().getInt("amount");
        //Toast.makeText(TransactionProcessingActivity.this, userid+" "+amount, Toast.LENGTH_SHORT).show();

        imageView = (GifImageView) findViewById(R.id.show_check_gif);
        transactionId = (TextView)findViewById(R.id.transaction_id);
        processingTextView = (TextView)findViewById(R.id.processingTextView);
        transactionDetails = (LinearLayout)findViewById(R.id.transaction_details);
        transactionHomeButton = (Button)findViewById(R.id.transaction_home_button);

        GifDrawable gifDrawable = null;
        try {
            gifDrawable = new GifDrawable(getResources(), R.drawable.checkmark);
            gifDrawable.setLoopCount(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageDrawable(gifDrawable);

        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("sending").child("id").setValue(userid);
        myRef.child("sending").child("amount").setValue(amount);

        transactionId.setText(transactionIdRandom);
        transactionDetails.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                processingTextView.setVisibility(View.GONE);
                transactionDetails.setVisibility(View.VISIBLE);

            }
        }, 2000);

        transactionHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("sending").removeValue();
                Intent intent = new Intent(TransactionProcessingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
