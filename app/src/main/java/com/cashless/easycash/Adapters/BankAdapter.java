package com.cashless.easycash.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cashless.easycash.Beans.Bank;
import com.cashless.easycash.R;
import com.cashless.easycash.SelectBankActivity;

import java.util.ArrayList;

/**
 * Created by Varsha on 05-11-2017.
 */

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Bank> mBankNames;
    private int lastCheckedPosition = -1;

    public BankAdapter(Context c, ArrayList<Bank> e) {
        context = c;
        mBankNames = e;
    }

    @Override
    public BankAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_bank, parent, false);

        BankAdapter.ViewHolder vh = new BankAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(BankAdapter.ViewHolder holder, int position) {
        final Bank currentBank = mBankNames.get(position);
        holder.mBankImg.setImageResource(currentBank.getResId());
        holder.mBankName.setText(currentBank.getName());
        //holder.mBankName.setCompoundDrawablesWithIntrinsicBounds(currentBank.getResId(), 0, 0, 0);
        //holder.mBankRadio.setChecked(currentBank.getRadioButton());
        holder.mBankRadio.setChecked(position == lastCheckedPosition);
        if(holder.mBankRadio.isChecked())
        SelectBankActivity.selected = true;
    }

    @Override
    public int getItemCount() {
        return mBankNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mBankName;
        public RadioButton mBankRadio;
        public ImageView mBankImg;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            mBankImg = (ImageView) itemView.findViewById(R.id.bank_logo);
            mBankName = (TextView) itemView.findViewById(R.id.bank_name);
            mBankRadio = (RadioButton) itemView.findViewById(R.id.radio_button);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.each_bank_view);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastCheckedPosition = getAdapterPosition();

                    notifyDataSetChanged();
                }
            });

            mBankRadio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastCheckedPosition = getAdapterPosition();

                    notifyDataSetChanged();
                }
            });

        }

    }

}