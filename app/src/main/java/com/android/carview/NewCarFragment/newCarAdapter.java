package com.android.carview.NewCarFragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.carview.R;
import com.android.carview.common.model.Car;
import com.android.carview.common.model.MyFavorite;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class newCarAdapter extends RecyclerView.Adapter<newCarAdapter.ServiceHoder> {

    private List<Car> carList;
    private List<MyFavorite> myFavorites;
    private Context context;
    private ImageView[] dots;
    private AdapterCarInterAction adapterCarInterAction;


    public newCarAdapter(List<Car> carList,List<MyFavorite> myFavorites, Context context,AdapterCarInterAction adapterCarInterAction) {
        this.carList = carList;
        this.myFavorites = myFavorites;
        this.context = context;
        this.adapterCarInterAction = adapterCarInterAction;
    }

    @NonNull
    @Override
    public newCarAdapter.ServiceHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custome_new_car_itme,viewGroup,false);
        return new newCarAdapter.ServiceHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final newCarAdapter.ServiceHoder holder, int position) {

        final Car car = carList.get(position);


//        Picasso.with(context)
//                .load("https://shehabosama.000webhostapp.com/uploads/"+car.getCars().get(0).getCarImage())
//                .placeholder(R.drawable.ic_launcher_foreground)
//                .into(holder.imageView);

        if (carList.isEmpty()){
            adapterCarInterAction.onRefrishing();
        }

        holder.name.setText(car.getCarName());
      //  holder.descroption.setText(car.getCarDescription());
        holder.expTv1.setText(car.getCarDescription());
        //ImageAdapter imageAdapter = new ImageAdapter(car.getCars(),context);
        ImagePagerAdapter imageAdapter = new ImagePagerAdapter(context,car.getCars());
        holder.viewPager.setAdapter(imageAdapter);
        //holder.addDots(car.getCars().size());
        holder.viewPager.addOnPageChangeListener(null);
        holder.setListener(car);

        holder.drawPageSelectionIndicators(0,car.getCars().size());
        holder.getFavorites(car);

        if(Double.parseDouble(car.getSell_price())==0.0){
            holder.price.setText(car.getPrice());
            holder.sell_price.setText("");
        }else{
            holder.price.setText(car.getPrice());
            holder.price.setPaintFlags( holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.sell_price.setText(car.getSell_price());
        }

      //  Log.e(TAG, "onBindViewHolder: "+ car.getCarCategory() );
        if(car.getCarCategory().equals("2")){
            holder.btn_usage.setText("New");
        }else{
            holder.btn_usage.setText("Used");
        }



        if(!holder.toggleCheck){
            holder.imageView.setImageResource(R.drawable.ic_favorite_black_24dp);

        }else{
            holder.imageView.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        }


    }



    @Override
    public int getItemCount() {
        return carList.size();
    }

     interface AdapterCarInterAction{
        void onRefrishing();

         void onClickFavorite(Car car);

         void onClickUnFavorite(Car car);
         void onClickItem(Car car);
     }
    public class ServiceHoder extends RecyclerView.ViewHolder {
        ViewPager viewPager;
        TextView name, descroption,price,sell_price;
        LinearLayout linearLayout;
        ImageView imageView;
        boolean toggleCheck = true;
        Button btn_usage;
        ExpandableTextView expTv1;

// IMPORTANT - call setText on the ExpandableTextView to set the text content to display

        public ServiceHoder(@NonNull View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.view_pager);
            name = itemView.findViewById(R.id.name);
            descroption = itemView.findViewById(R.id.description);
            linearLayout = itemView.findViewById(R.id.linear);
            imageView = itemView.findViewById(R.id.toggle_favorite);
            btn_usage = itemView.findViewById(R.id.usage_image);
            expTv1 = itemView.findViewById(R.id.expand_text_view);
            price = itemView.findViewById(R.id.price);
            sell_price = itemView.findViewById(R.id.sell_price);
            



        }

        public void getFavorites(Car car){
            for(int i =0 ;i<myFavorites.size();i++){
               // Log.e(TAG, "onBindViewHolder: "+myFavorites.get(i).getCarId() );
                if(myFavorites.get(i).getCarId().equals(car.getCarId())){
                    if(toggleCheck){

                        toggleCheck = false;
                        Log.e(TAG, "onBindViewHolder: "+myFavorites.get(i).getCarId()+" || "+car.getCarId() +" || "+toggleCheck );
                    }else{
                        toggleCheck = true;
                        Log.e(TAG, "onBindViewHolder: "+myFavorites.get(i).getCarId()+" || "+car.getCarId() +" || "+toggleCheck );

                    }

                }else{
//                    if(!toggleCheck){
//                        toggleCheck = true;
//                        Log.e(TAG, "onBindViewHolder: "+myFavorites.get(i).getCarId()+" || "+car.getCarId() +" || "+toggleCheck );
//                    }else{
//                        toggleCheck = false;
//                    }

                }
            }
        }
        public void setListener(final Car car){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(toggleCheck){
                        if(adapterCarInterAction !=null)
                        {
                           imageView.setImageResource(R.drawable.ic_favorite_black_24dp);
                            adapterCarInterAction.onClickFavorite(car);
                        }
                    }else {
                        if(adapterCarInterAction !=null){
                            imageView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                            adapterCarInterAction.onClickUnFavorite(car);


                        }


                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCarInterAction.onClickItem(car);
                }
            });

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    drawPageSelectionIndicators(position,car.getCars().size());

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

//        public void selectDot(int idx, int NUM_PAGES) {
//            Resources res = context.getResources();
//            for (int i = 0; i < NUM_PAGES; i++) {
//                int drawableId = (i == idx) ? (R.drawable.dots) : (R.drawable.dots_default);
//                Drawable drawable = res.getDrawable(drawableId);
//                dots.get(i).setImageDrawable(drawable);
//            }
//        }

//        public void addDots(int NUM_PAGES) {
//            dots = new ArrayList<>();
//            for (int i = 0; i < NUM_PAGES; i++) {
//                ImageView dot = new ImageView(context);
//                dot.setPadding(2,2,2,2);
//                dot.setImageDrawable(context.getResources().getDrawable(R.drawable.dots_default));
//
////                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
////                        LinearLayout.LayoutParams.WRAP_CONTENT,
////                        LinearLayout.LayoutParams.WRAP_CONTENT
////                );
//                linearLayout.addView(dot);
//
//                dots.add(dot);
//            }
//
//        }


        private void drawPageSelectionIndicators(int mPosition,int dotsCount){
            if(linearLayout!=null) {
                linearLayout.removeAllViews();
            }
           // linearLayout=(LinearLayout)findViewById(R.id.viewPagerCountDots);
            dots = new ImageView[dotsCount];
            for (int i = 0; i < dotsCount; i++) {
                dots[i] = new ImageView(context);
                if(i==mPosition)
                    dots[i].setImageDrawable(context.getResources().getDrawable(R.drawable.dots));
                else
                    dots[i].setImageDrawable(context.getResources().getDrawable(R.drawable.dots_default));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                params.setMargins(4, 0, 4, 0);
                linearLayout.addView(dots[i], params);
            }
        }
    }
}
