package com.android.carview.ShowRoomCarsFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.carview.R;
import com.android.carview.common.model.Car_image;
import com.android.carview.common.model.ShowRoomCarimage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    private boolean swipeLocked;
    private List<ShowRoomCarimage> car_images;
   // private Integer[] images = {R.drawable.ic_account_circle_black_24dp, R.drawable.groove, R.drawable.ic_airport_shuttle_black_24dp};


    public ImagePagerAdapter(Context context,List<ShowRoomCarimage> car_images) {
        this.context = context;
        this.car_images = car_images;
    }

    @Override
    public int getCount() {
        return car_images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.coustom_layout_view_images, null);
        ShowRoomCarimage car_image = car_images.get(position);

        final ImageView imageView = (ImageView) view.findViewById(R.id.image_view_pager);
        Picasso.with(context)
                .load("https://shehab.develogs.com/car-project/uploads/" + car_image.getCarImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageView);

        ViewPager vp = ((ViewPager) container);
        vp.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }



}