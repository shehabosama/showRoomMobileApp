package com.android.carview.FavoritesFragment;

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
import com.android.carview.common.model.MyFavorite;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

public class FavoriteCarAdapter extends RecyclerView.Adapter<FavoriteCarAdapter.ServiceHoder> {

    private List<MyFavorite> carList;
    private Context context;
    private ImageView[] dots;
    private AdapterCarInterAction adapterCarInterAction;
    private boolean toggleCheck=false;

    public FavoriteCarAdapter(List<MyFavorite> carList, Context context, AdapterCarInterAction adapterCarInterAction) {
        this.carList = carList;
        this.context = context;
        this.adapterCarInterAction = adapterCarInterAction;
    }

    @NonNull
    @Override
    public FavoriteCarAdapter.ServiceHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custome_new_car_itme,viewGroup,false);
        return new FavoriteCarAdapter.ServiceHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteCarAdapter.ServiceHoder holder, int position) {

        final MyFavorite car = carList.get(position);

//        Picasso.with(context)
//                .load("https://shehabosama.000webhostapp.com/uploads/"+car.getCars().get(0).getCarImage())
//                .placeholder(R.drawable.ic_launcher_foreground)
//                .into(holder.imageView);

        if (carList.isEmpty()){
            adapterCarInterAction.onRefrishing();
        }

        holder.name.setText(car.getCarName());
        holder.expandableTextView.setText(car.getCarDescription());


        //ImageAdapter imageAdapter = new ImageAdapter(car.getCars(),context);
        ImagePagerAdapter imageAdapter = new ImagePagerAdapter(context,car.getCars());
        holder.viewPager.setAdapter(imageAdapter);
        //holder.addDots(car.getCars().size());
        holder.viewPager.addOnPageChangeListener(null);
        holder.setListener(car);
        if(Double.parseDouble(car.getSell_price())==0.0){
            holder.price.setText(car.getPrice());
            holder.sell_price.setText("");
        }else{
            holder.price.setText(car.getPrice());
            holder.price.setPaintFlags( holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.sell_price.setText(car.getSell_price());
        }
        holder.drawPageSelectionIndicators(0,car.getCars().size());
        holder.imageView.setImageResource(R.drawable.ic_favorite_black_24dp);
        if(car.getCarCategory().equals("2")){
            holder.btn_usage.setText("New");
        }else{
            holder.btn_usage.setText("Used");
        }



    }



    @Override
    public int getItemCount() {
        return carList.size();
    }

     interface AdapterCarInterAction{
        void onRefrishing();

         void onClickUnFavorite(MyFavorite car);

         void onClickItem(MyFavorite car);
     }
    public class ServiceHoder extends RecyclerView.ViewHolder {
        ViewPager viewPager;
        TextView name, descroption,price,sell_price;;
        LinearLayout linearLayout;
        ImageView imageView;
        Button btn_usage;
        ExpandableTextView expandableTextView;
        public ServiceHoder(@NonNull View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.view_pager);
            name = itemView.findViewById(R.id.name);
            descroption = itemView.findViewById(R.id.description);
            linearLayout = itemView.findViewById(R.id.linear);
            imageView = itemView.findViewById(R.id.toggle_favorite);
            btn_usage = itemView.findViewById(R.id.usage_image);
            expandableTextView = itemView.findViewById(R.id.expand_text_view);
            price = itemView.findViewById(R.id.price);
            sell_price = itemView.findViewById(R.id.sell_price);


        }

        public void setListener(final MyFavorite car){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCarInterAction.onClickItem(car);
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(toggleCheck){
                        imageView.setImageResource(R.drawable.ic_favorite_black_24dp);
                        toggleCheck = false;

                    }else{
                        adapterCarInterAction.onClickUnFavorite(car);
                        imageView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        toggleCheck=true;
                    }
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
