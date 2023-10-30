package xyz.thisisjames.boulevard.android.runz.model.adapters;

import static xyz.thisisjames.boulevard.android.runz.model.Constants.patTimeHM;
import static xyz.thisisjames.boulevard.android.runz.viewmodel.AppBase.baseDateFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import xyz.thisisjames.boulevard.android.runz.R;
import xyz.thisisjames.boulevard.android.runz.databinding.RecyclerItemBinding;
import xyz.thisisjames.boulevard.android.runz.model.data.Invoice;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;

    private int mShowing ;

    private List<Invoice> mValues;

    public HistoryAdapter(int count, List<Invoice> transactions ){
        this.mShowing = count ;
        this.mValues = transactions;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext() ;
        return new ViewHolder(
                RecyclerItemBinding.inflate(LayoutInflater.from(context),parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Invoice invoice = mValues.get(position);

        if (invoice.getInvoicePaymentStatus() == null ){
            holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.luna_sent));
        }else {
            holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.luna_paid));
        }

        holder.receiver.setText(invoice.getInvoiceRecipientName());
        holder.amount.setText(invoice.getInvoiceAmount());

        holder.snippet.setText(invoice.getInvoiceRecipientName().substring(0,1));

        holder.time.setText(getTimeStamp(invoice.getInvoiceSendTime()));
    }

    @Override
    public int getItemCount() {
        return mShowing;
    }


    private String getTimeStamp(Long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        Calendar calendat = Calendar.getInstance();
        calendat.setTimeInMillis(System.currentTimeMillis());

        long diffInMillies = Math.abs(calendar.getTimeInMillis()-calendat.getTimeInMillis());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        if (diff == 0L){
            return baseDateFormat(calendar.getTimeInMillis(), patTimeHM);
        }

        return String.format("%d days ago",diff);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView snippet, amount, receiver, time;
        private AppCompatImageView image;

        public ViewHolder(@NonNull RecyclerItemBinding binding) {
            super(binding.getRoot());
            snippet = binding.itemSnippet;
            amount = binding.amount ;
            receiver = binding.reciever;
            time  = binding.time;
            image = binding.image;
        }
    }



}
