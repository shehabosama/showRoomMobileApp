package com.android.carview.CarSparFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.carview.R;
import com.android.carview.common.model.ShowRoom;
import com.android.carview.common.model.SparCar;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SparCarAdapter extends RecyclerView.Adapter<SparCarAdapter.ShowRoomHolder> {
    private SparCarInterAction interAction;
    private Context context;
    private List<SparCar> list;

    public SparCarAdapter(Context context, List<SparCar> list, SparCarInterAction interAction) {
        this.context = context;
        this.list = list;
        this.interAction = interAction;
    }

    @NonNull
    @Override
    public ShowRoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_desplay_spar_car, parent, false);

        return new ShowRoomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowRoomHolder holder, int position) {
        SparCar sparCar = list.get(position);
        holder.SparCarName.setText(sparCar.getSpare_name());
        holder.SparCarPrice.setText("Price : "+String.valueOf(sparCar.getPrice()));
        holder.SparCarDescription.setText("description : "+sparCar.getSpar_description());
        holder.SparCarLocation.setText("location : "+sparCar.getLocation());
        Picasso.with(context)
                .load("https://shehab.develogs.com/car-project/uploads/" + sparCar.getSpar_image())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.showImage);
        holder.setListener(sparCar);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface SparCarInterAction {
        void onClickItem(SparCar sparCar);


    }

    public class ShowRoomHolder extends RecyclerView.ViewHolder {

        TextView SparCarName,SparCarDescription,SparCarLocation,SparCarPrice;
        ImageView showImage;

        public ShowRoomHolder(@NonNull View itemView) {
            super(itemView);
            SparCarName = itemView.findViewById(R.id.text_spar_name);
            showImage = itemView.findViewById(R.id.image_spar_car);
            SparCarLocation = itemView.findViewById(R.id.text_spar_location);
            SparCarDescription = itemView.findViewById(R.id.text_spar_description);
            SparCarPrice = itemView.findViewById(R.id.text_spar_price);
        }

        public void setListener(final SparCar sparCar) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interAction.onClickItem(sparCar);
                }
            });

        }
    }
}
