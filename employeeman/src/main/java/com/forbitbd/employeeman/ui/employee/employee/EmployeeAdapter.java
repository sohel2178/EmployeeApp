package com.forbitbd.employeeman.ui.employee.employee;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.Employee;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeHolder> {

    private EmployeeFragment fragment;
    private List<Employee> employeeList;
    private LayoutInflater inflater;
    private SharedProject.Permission employeePermission;

    public EmployeeAdapter(EmployeeFragment fragment,SharedProject.Permission employeePermission) {
        this.fragment = fragment;
        this.employeeList = new ArrayList<>();
        this.inflater = LayoutInflater.from(fragment.getContext());
        this.employeePermission = employeePermission;
    }

    @NonNull
    @Override
    public EmployeeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.worker_item,viewGroup,false);
        return new EmployeeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeHolder workerHolder, int i) {
        Employee employee = employeeList.get(i);
        workerHolder.bind(employee);
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public void clear(){
        this.employeeList.clear();
        notifyDataSetChanged();
    }

    public void addemployee(Employee employee){
        employeeList.add(employee);
        int position = employeeList.indexOf(employee);
        notifyItemInserted(position);
    }

    public void updateEmployee(Employee employee){
        int position = getPosition(employee);

        if(position != -1){
            employeeList.set(position,employee);
            notifyItemChanged(position);
        }
    }

    public void deleteEmployee(String id){
        int position = getPosition(id);
        Log.d("HHHHHHHH",position+"");
        if(position != -1){
            employeeList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private int getPosition(String id){
        for (Employee x: employeeList){
            if(x.get_id().equals(id)){
                return employeeList.indexOf(x);
            }
        }
        return -1;
    }

    private int getPosition(Employee employee) {
        return getPosition(employee.get_id());
    }

    public List<Employee> getEmployeeList(){
        return this.employeeList;
    }


    class EmployeeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivAttach,ivEdit,ivDelete;
        TextView tvName,tvContact,tvDesignation;

        public EmployeeHolder(@NonNull View itemView) {
            super(itemView);
            ivAttach = itemView.findViewById(R.id.attach);
            ivEdit = itemView.findViewById(R.id.edit);
            ivDelete = itemView.findViewById(R.id.delete);

            tvName = itemView.findViewById(R.id.name);
            tvDesignation = itemView.findViewById(R.id.designation);
            tvContact = itemView.findViewById(R.id.contact);

            itemView.setOnClickListener(this);
            ivEdit.setOnClickListener(this);
            ivDelete.setOnClickListener(this);
            ivAttach.setOnClickListener(this);
            //itemView.setOnLongClickListener(this);

            if(employeePermission.isUpdate()){
                ivEdit.setVisibility(View.VISIBLE);
            }else {
                ivEdit.setVisibility(View.GONE);
            }

            if(employeePermission.isDelete()){
                ivDelete.setVisibility(View.VISIBLE);
            }else {
                ivDelete.setVisibility(View.GONE);
            }
        }


        public void bind(Employee employee){
            tvName.setText(employee.getName());
            tvContact.setText(employee.getContact_no());
            tvDesignation.setText(employee.getDesignation());

        }

        @Override
        public void onClick(View view) {
            if(view==itemView){
                fragment.startSingleEmployeeAttendanceActivity(employeeList.get(getAdapterPosition()));
            }else if(view==ivEdit){
                fragment.startEditEmployeeActivity(employeeList.get(getAdapterPosition()));
            }else if(view==ivDelete){
                fragment.showDeleteDialog(employeeList.get(getAdapterPosition()));
            }else if(view==ivAttach){
                fragment.startZoomImageActivity(employeeList.get(getAdapterPosition()));
            }

        }

       /* @Override
        public boolean onLongClick(View view) {
            fragment.startEmployeeDetailActivity(employeeList.get(getAdapterPosition()));
            return false;
        }*/
    }
}
