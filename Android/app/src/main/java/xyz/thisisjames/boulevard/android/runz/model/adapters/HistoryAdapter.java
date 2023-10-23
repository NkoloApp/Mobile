package xyz.thisisjames.boulevard.android.runz.model.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import xyz.thisisjames.boulevard.android.runz.R;
import xyz.thisisjames.boulevard.android.runz.databinding.RecyclerItemBinding;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;


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


        if (position % 3 == 0){
            holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.luna_paid));
        }else {
            holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.luna_sent));
        }

    }

    @Override
    public int getItemCount() {
        return 2;
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
