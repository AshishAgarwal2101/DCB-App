package com.cashless.easycash;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DialogPaymentFragment extends DialogFragment{
    String name,vpa,userid,accno;
    TextView nameDialog,vpaDialog;
    Button confirmButton;
    EditText amountDialog;
    public static DialogPaymentFragment newInstance(String name, String vpa, String id, String acc){
        Bundle args=new Bundle();
        args.putString("name",name);
        args.putString("vpa",vpa);
        args.putString("id", id);
        args.putString("accno",acc);
        DialogPaymentFragment fragment=new DialogPaymentFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_payment_fragment, null);

        name = (String)getArguments().getString("name");
        vpa=(String)getArguments().getString("vpa");
        userid=(String)getArguments().get("id");
       // accno=(String)getArguments().getString("accno");

        nameDialog = (TextView)v.findViewById(R.id.nameDialog);
        vpaDialog = (TextView)v.findViewById(R.id.vpaDialog);
        nameDialog.setText("Sender's Name - "+name);
        vpaDialog.setText("Receiver's VPA - "+vpa);

        confirmButton = (Button)v.findViewById(R.id.confirm_button);
        amountDialog = (EditText)v.findViewById(R.id.amountDialog);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(amountDialog.getText().toString());
                Intent intent = new Intent(getActivity(), PasscodeActivity.class);
                intent.putExtra("reason", "sending");
                intent.putExtra("id", userid);
                intent.putExtra("amount", amount);
                //intent.putExtra("accno",accno);
                startActivity(intent);
            }
        });

        return new AlertDialog.Builder(getActivity()).setView(v).create();
    }
}
