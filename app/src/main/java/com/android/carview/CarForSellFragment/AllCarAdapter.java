package com.android.carview.CarForSellFragment;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.carview.R;
import com.android.carview.common.model.Car;

import java.util.List;

public class AllCarAdapter extends RecyclerView.Adapter<AllCarAdapter.ServiceHoder> {

    private List<Car> carList;
    private Context context;
    private ImageView[] dots;
    private AdapterAllCarInterAction adapterAllCarInterAction;
    private boolean toggleCheck = true;
    public AllCarAdapter(List<Car> carList, Context context, AdapterAllCarInterAction adapterAllCarInterAction) {
        this.carList = carList;
        this.context = context;
        this.adapterAllCarInterAction = adapterAllCarInterAction;
    }

    @NonNull
    @Override
    public AllCarAdapter.ServiceHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custome_new_car_itme,viewGroup,false);
        return new AllCarAdapter.ServiceHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AllCarAdapter.ServiceHoder holder, int position) {

        final Car car = carList.get(position);
//        Picasso.with(context)
//                .load("https://shehabosama.000webhostapp.com/uploads/"+car.getCars().get(0).getCarImage())
//                .placeholder(R.drawable.ic_launcher_foreground)
//                .into(holder.imageView);

        if (carList.isEmpty()){
            adapterAllCarInterAction.onRefrishing();
        }


            holder.mainLin.setVisibility(View.VISIBLE);
            holder.name.setText(car.getCarName());
            holder.descroption.setText(car.getCarDescription());

            //ImageAdapter imageAdapter = new ImageAdapter(car.getCars(),context);
            ImagePagerAdapter imageAdapter = new ImagePagerAdapter(context,car.getCars());
            holder.viewPager.setAdapter(imageAdapter);
            //holder.addDots(car.getCars().size());
            holder.viewPager.addOnPageChangeListener(null);
            holder.setListener(car);
            holder.drawPageSelectionIndicators(0,car.getCars().size());
            holder.price.setText(car.getPrice());

           holder.price.setPaintFlags( holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
           holder.sellPrice.setText(car.getSell_price());
           holder.imageView.setVisibility(View.GONE);
        if(car.getCarCategory().equals("2")){
            holder.usage.setText("New");
        }else{
            holder.usage.setText("Used");
        }





    }



    @Override
    public int getItemCount() {
        return carList.size();
    }

    public interface AdapterAllCarInterAction {
        void onRefrishing();
        void onClickItem(Car car);
    }
    public class ServiceHoder extends RecyclerView.ViewHolder {
        ViewPager viewPager;
        TextView name, descroption,price,sellPrice;
        LinearLayout linearLayout,mainLin;
        ImageView imageView;
        Button usage;


        public ServiceHoder(@NonNull View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.view_pager);
            name = itemView.findViewById(R.id.name);
            descroption = itemView.findViewById(R.id.description);
            linearLayout = itemView.findViewById(R.id.linear);
            mainLin = itemView.findViewById(R.id.main_lin);
            imageView = itemView.findViewById(R.id.toggle_favorite);
            price = itemView.findViewById(R.id.price);
            sellPrice = itemView.findViewById(R.id.sell_price);
            usage = itemView.findViewById(R.id.usage_image);

        }

        public void setListener(final Car car){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(toggleCheck){
                        imageView.setImageResource(R.drawable.ic_favorite_black_24dp);
                        toggleCheck = false;
                    }else {
                        imageView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        toggleCheck=true;

                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterAllCarInterAction.onClickItem(car);
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
