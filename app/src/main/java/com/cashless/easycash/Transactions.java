package com.cashless.easycash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.cashless.easycash.Helpers.SPHelper;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;

public class Transactions extends AppCompatActivity {
    int k=0,currentAccount,numberOfTransactions=0,amountInTransaction=0;
    String phn="8888877777",bankName="DCB",vpa="534534@ybl",accno="31241441414",name="Ronit",
            id,branch="Jayanagar",ifsc="ubi00000127",vpaPin="1234",appPin="2345";
    String date="none",with="none",transactionSentOrReceived="none",transactionId1="none";
    ArrayList<SingleTransactionDetail> transactionDetails = new ArrayList<>();
    private RecyclerView transactionsRecyclerView;
    private TransactionsAdapter transactionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        loadAccountBeingUsed();
        loadNumberOfTransactions();
        k=currentAccount;
        //loadTransactionDetails();

        transactionsRecyclerView = (RecyclerView)findViewById(R.id.transactions_recycler_view);
        transactionsRecyclerView.setLayoutManager(new LinearLayoutManager(Transactions.this));

        for (int i=0; i<numberOfTransactions; i++){
            k=i;
            loadTransactionDetails();
            SingleTransactionDetail singleTransactionDetail = new SingleTransactionDetail(transactionId1, date, with, transactionSentOrReceived, amountInTransaction);
            transactionDetails.add(singleTransactionDetail);
        }
        k=currentAccount;

        transactionsAdapter = new TransactionsAdapter();
        transactionsRecyclerView.setAdapter(transactionsAdapter);
    }
    private class TransactionHolder extends RecyclerView.ViewHolder{
        private TextView transactionIdTV,transactionDateTV,transactionSentOrReceivedTV,transactionAmountTV;

        public TransactionHolder(View itemView) {
            super(itemView);
            transactionIdTV = (TextView)itemView.findViewById(R.id.transaction_transactionId);
            transactionDateTV = (TextView)itemView.findViewById(R.id.transaction_transactionDate);
            transactionSentOrReceivedTV = (TextView)itemView.findViewById(R.id.transaction_transactionSentOrReceived);
            transactionAmountTV = (TextView)itemView.findViewById(R.id.transaction_transactionAmount);
        }
        public void bindTransaction(SingleTransactionDetail singleTransactionDetail){
            transactionIdTV.setText(singleTransactionDetail.getTransactionId());
            transactionDateTV.setText(singleTransactionDetail.getDate());
            transactionSentOrReceivedTV.setText(singleTransactionDetail.getSentOrReceived());
            transactionAmountTV.setText("Rs "+singleTransactionDetail.getAmount()+".0");
        }
    }
    private class TransactionsAdapter extends RecyclerView.Adapter<TransactionHolder>{

        @Override
        public TransactionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(Transactions.this);
            View view = inflater.inflate(R.layout.transactions_list_item, parent, false);
            return new TransactionHolder(view);
        }

        @Override
        public void onBindViewHolder(TransactionHolder holder, int position) {
            SingleTransactionDetail singleTransactionDetail = transactionDetails.get(position);
            holder.bindTransaction(singleTransactionDetail);
        }

        @Override
        public int getItemCount() {
            return transactionDetails.size();
        }
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
