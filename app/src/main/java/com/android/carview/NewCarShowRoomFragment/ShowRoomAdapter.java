package com.android.carview.NewCarShowRoomFragment;

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
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowRoomAdapter extends RecyclerView.Adapter<ShowRoomAdapter.ShowRoomHolder> {
    private ShowRoomInterAction interAction;
    private Context context;
    private List<ShowRoom> list;

    public ShowRoomAdapter(Context context, List<ShowRoom> list, ShowRoomInterAction interAction) {
        this.context = context;
        this.list = list;
        this.interAction = interAction;
    }

    @NonNull
    @Override
    public ShowRoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_desplay_show_room, parent, false);

        return new ShowRoomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowRoomHolder holder, int position) {
        ShowRoom showRoom = list.get(position);
        holder.showRoomName.setText(showRoom.getShroomName());
        Picasso.with(context)
                .load("https://shehab.develogs.com/car-project/uploads/" + showRoom.getShroomImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.showImage);
        holder.setListener(showRoom);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ShowRoomInterAction {
        void onClickItem(ShowRoom showRoom);

        void onClickLocation(ShowRoom showRoom);
    }

    public class ShowRoomHolder extends RecyclerView.ViewHolder {

        TextView showRoomName;
        CircleImageView showImage;
        ImageView imageView;
        public ShowRoomHolder(@NonNull View itemView) {
            super(itemView);
            showRoomName = itemView.findViewById(R.id.text_show_room_name);
            showImage = itemView.findViewById(R.id.show_rom_image);
            imageView = itemView.findViewById(R.id.show_room_location);
        }

        public void setListener(final ShowRoom showRoom) {

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(interAction!=null)
                        interAction.onClickLocation(showRoom);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(interAction!=null)
                        interAction.onClickItem(showRoom);
                }
            });
        }
    }
}
