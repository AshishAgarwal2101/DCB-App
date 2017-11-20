package com.cashless.easycash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FAQs extends AppCompatActivity {

    String fullFAQs;
    TextView textFaqs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        fullFAQs = "Q. What is UPI?\n" +
                "Ans. Unified Payments Interface (UPI) is an instant payment system developed by the National Payments Corporation of India (NPCI), an RBI regulated entity. UPI is built over the IMPS infrastructure and allows you to instantly transfer money between any two parties' bank accounts.\n\n\n"+
                "Q. What is an UPI-PIN?\n" +
                "Ans. PI-PIN (UPI Personal Identification Number) is a 4-6 digit pass code you create/set during first time registration with this App. You have to enter this UPI-PIN to authorize all bank transactions. If you have already set up an UPI-PIN with other UPI Apps you can use the same on BHIM. (Note: Banks issued MPIN is different from the UPI UPI-PIN, please generate a new UPI-PIN in the BHIM app) Note: Please do not share your UPI-PIN with anyone. BHIM does not store or read your UPI-PIN details and your bank's customer support will never ask for it.\n\n\n"+
                "Q. What happens if I enter wrong UPI-PIN during a transaction?\n" +
                "Ans. The transaction will fail if you enter the wrong UPI pin.\n\n\n"+
                "Q. I have selected the Bank name to link with UPI but it does not find my bank A/C\n" +
                "Ans. In such a case, please ensure that the mobile number linked to your bank account is same as the one verified in any UPI App. If it is not the same, your bank accounts will not be fetched by the UPI platform.\n\n\n"+
                "Q. How do I pay an online merchant through UPI?\n" +
                "Ans. When you shop-online, you can pay through UPI when you see UPI as a payment option. On clicking that, you will need to enter your Payment Address (eg - xyz@upi). Once entered, you will receive a collect request on your BHIM app. Enter your UPI-PIN here and your payment will be complete. As easy as this!\n\n\n"+
                "Q. Do money transfers happen on UPI only during banking hours?\n" +
                "Ans. All payments are instant and 24/7, regardless of your bank's working hours.\n\n\n"+
                "Q. I have paid for my transaction but not received anything. Why is that?\n" +
                "Ans. Once you complete a transaction, you should see a success status on the BHIM screen and receive an SMS from your bank. In some cases due to operator issues it can take longer time. In case you have not received your confirmation within an hour please contact the customer support at your bank.\n\n\n"+
                "Q. How is UPI different from IMPS?\n" +
                "Ans. UPI is providing additional benefits to IMPS in the following ways:\n" +
                "1.Provides for a P2P Pull functionality\n" +
                "2.Simplifies Merchant Payments\n" +
                "3.Single APP for money transfer\n" +
                "4.Single click two factor authentication";

        textFaqs = (TextView) findViewById(R.id.text_faqs);
        textFaqs.setText(fullFAQs);
    }
}
