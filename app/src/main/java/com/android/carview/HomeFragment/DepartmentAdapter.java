package com.android.carview.HomeFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.carview.R;
import com.android.carview.common.model.Department;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.DeptViewholder>{

    private List<Department> departmentList;
    private Context context;
    private AdapterDepartmentInterAction adapterDepartmentInterAction;


    public DepartmentAdapter(Context context,List<Department> departmentList,AdapterDepartmentInterAction departmentInterAction) {
        this.departmentList = departmentList;
        this.context = context;
        this.adapterDepartmentInterAction = departmentInterAction;
    }

    @NonNull
    @Override
    public DeptViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.custome_desplay_item, parent, false);
        return new DeptViewholder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull DeptViewholder holder, int position) {
        Department department = departmentList.get(position);
//        Picasso.with(context)
//                .load("https://shehabosama.000webhostapp.com/uploads/"+department.getDept_image())
//                .placeholder(R.drawable.ic_launcher_foreground)
//                .into(holder.deptImage);
        holder.deptImage.setImageResource(department.getDept_image());
        holder.deptName.setText(department.getDept_name());
        holder.setListener(position,department.getDept_name());

    }


    @Override
    public int getItemCount() {
        return departmentList.size();
    }

    public interface AdapterDepartmentInterAction{
        void onClickDepartment(int position,String deptName);
    }

    public class DeptViewholder extends RecyclerView.ViewHolder{
        private ImageView deptImage;
        private TextView deptName;
        public DeptViewholder(@NonNull View itemView) {
            super(itemView);
            deptImage = itemView.findViewById(R.id.imageview);
            deptName = itemView.findViewById(R.id.textview);
        }
        public void setListener(final int position, final String name){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(adapterDepartmentInterAction !=null)
                        adapterDepartmentInterAction.onClickDepartment(position,name);
                }
            });
        }
    }
}
