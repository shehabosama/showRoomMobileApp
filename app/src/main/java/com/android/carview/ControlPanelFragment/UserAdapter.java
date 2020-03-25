package com.android.carview.ControlPanelFragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.carview.R;
import com.android.carview.common.model.UserList;
import java.util.List;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder>{
    CompoundButton.OnCheckedChangeListener adminSwitchListener;
    private List<UserList> userLists;
    private Context context;
    private AdapterOrdersInteractions adapterOrdersInteractions;
    private boolean check = false;
    private boolean check2 = false;
    public UserAdapter(Context context,List<UserList> userLists,AdapterOrdersInteractions adapterOrdersInteractions) {
        this.userLists = userLists;
        this.context = context;
        this.adapterOrdersInteractions = adapterOrdersInteractions;
    }
    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_desplay_user,parent,false);

        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        UserList userList = userLists.get(position);
        holder.setListener(userList);
        holder.userName.setText(userList.getUsername());
        holder.email.setText(userList.getEmail());
        holder.adminSwitch.setOnCheckedChangeListener (null);
        if(userList.is_admin.equals("1")){

            holder.adminSwitch.setChecked(true);

        }
        if(userList.is_bloked == 1){
            holder.blockCheck.setChecked(true);
        }
        holder.adminSwitch.setOnCheckedChangeListener (adminSwitchListener);

    }

    @Override
    public int getItemCount() {
        return userLists.size();
    }
    public interface AdapterOrdersInteractions {
        void onDeleteUser(UserList userList);
     //   void onPermissionUser(UserList userList,boolean isChecked);
        void makeAdminPermission(UserList userList);
        void makeNormalUser(UserList userList);
        void onBlockUser(UserList userList, boolean isChecked);
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        TextView userName, email;
        Switch adminSwitch;
        CheckBox blockCheck;
        ImageView imageDelete;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.text_user_name);
            email = itemView.findViewById(R.id.text_email);
            adminSwitch = itemView.findViewById(R.id.switch_admin);
            blockCheck = itemView.findViewById(R.id.check_block);
            imageDelete = itemView.findViewById(R.id.image_delete);

        }

        public void setListener(final UserList userList) {
            imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapterOrdersInteractions != null)
                        adapterOrdersInteractions.onDeleteUser(userList);
                }
            });
            adminSwitchListener = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        Log.e(TAG, "onCheckedChanged: "+true );
                        adapterOrdersInteractions.makeAdminPermission(userList);
                    }else {
                        Log.e(TAG, "onCheckedChanged: "+false );
                        adapterOrdersInteractions.makeNormalUser(userList);
                    }
//                    if (adapterOrdersInteractions != null)
//                        adapterOrdersInteractions.onPermissionUser(userList,isChecked);
                }
            };

                blockCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (adapterOrdersInteractions != null)
                        adapterOrdersInteractions.onBlockUser(userList,blockCheck.isChecked());

                    }
                });


        }
    }
}
