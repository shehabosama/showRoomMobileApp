package com.android.carview.EmergencyNumbersFragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.carview.R;
import com.android.carview.common.model.Emergence;

import java.util.List;

public class EmergenceAdapter extends RecyclerView.Adapter<EmergenceAdapter.EmergenceHolder> {
    private EmergenceInterAction interAction;
    private Context context;
    private List<Emergence> list;

    public EmergenceAdapter(Context context, List<Emergence> list, EmergenceInterAction interAction) {
        this.context = context;
        this.list = list;
        this.interAction = interAction;
    }

    @NonNull
    @Override
    public EmergenceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_desplay_emergence, parent, false);

        return new EmergenceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmergenceHolder holder, int position) {
        Emergence emergence = list.get(position);
        holder.emergenceName.setText(emergence.getName());
        holder.emergenceNumber.setText(emergence.getPhone());
        holder.setListener(emergence);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface EmergenceInterAction {

        void onClickItem(Emergence emergence);
    }

    public class EmergenceHolder extends RecyclerView.ViewHolder {
        TextView emergenceName,emergenceNumber;
        public EmergenceHolder(@NonNull View itemView) {
            super(itemView);
            emergenceName=itemView.findViewById(R.id.emergence_name);
            emergenceNumber=itemView.findViewById(R.id.emergence_number);
        }

        public void setListener(final Emergence emergence) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interAction.onClickItem(emergence);
                }
            });
        }
    }
}
