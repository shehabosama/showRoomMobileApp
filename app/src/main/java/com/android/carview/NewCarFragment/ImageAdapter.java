package com.android.carview.NewCarFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.carview.R;
import com.android.carview.common.model.Car;
import com.android.carview.common.model.Car_image;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ServiceHoder> {

    private List<Car_image> car_images;
    private Context context;

    public ImageAdapter(List<Car_image> car_images, Context context) {
        this.car_images = car_images;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageAdapter.ServiceHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custome_desplay_item, viewGroup, false);
        return new ImageAdapter.ServiceHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ServiceHoder holder, int position) {

        Car_image car_image = car_images.get(position);
        Picasso.with(context)
                .load("https://shehab.develogs.com/car-project/uploads/" + car_image.getCarImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imageView);


    }


    @Override
    public int getItemCount() {
        return car_images.size();
    }

    public class ServiceHoder extends RecyclerView.ViewHolder {
        ImageView imageView;



        public ServiceHoder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);


        }
    }
}