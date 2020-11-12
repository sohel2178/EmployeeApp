package com.forbitbd.employeeman.ui.employee.employeeAttendance.employeeAddAttendance;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.Employee;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAddAttendanceAdapter extends RecyclerView.Adapter<EmployeeAddAttendanceAdapter.AttendanceHolder> {

    private EmployeeAddAttendanceActivity activity;
    private List<Employee> employeeList;
    private LayoutInflater inflater;

    public EmployeeAddAttendanceAdapter(EmployeeAddAttendanceActivity activity) {
        this.activity = activity;
        this.employeeList = new ArrayList<>();
        this.inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public AttendanceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.employee_attendance_item,viewGroup,false);
        return new AttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceHolder attendanceHolder, int i) {
        Employee employee = employeeList.get(i);
        attendanceHolder.bind(employee);
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public void clear(){
        this.employeeList.clear();
        notifyDataSetChanged();
    }

    public void addEmployee(Employee employee){
        this.employeeList.add(employee);
        int position = employeeList.indexOf(employee);
        notifyItemInserted(position);
    }

    public List<Employee> getEmployeeList(){
        return this.employeeList;
    }

    class AttendanceHolder extends RecyclerView.ViewHolder{

        ImageView ivImage;
        TextView tvName,tvDesignation;

        RadioGroup radioGroup;


        public AttendanceHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.name);
            tvDesignation = itemView.findViewById(R.id.designation);
            ivImage = itemView.findViewById(R.id.image);
            /*etOverTime = itemView.findViewById(R.id.over_time);

            etOverTime.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    double overTime;
                    if(charSequence.length()>0){
                        overTime = Double.parseDouble(String.valueOf(charSequence));
                    }else{
                        overTime=0;
                    }
                    employeeList.get(getAdapterPosition()).setOver_time(overTime);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });*/

            radioGroup = itemView.findViewById(R.id.radio_group);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    AttendanceHolder.this.setState(i);
                }
            });
        }

        private void setState(int id){
            if(id==R.id.present){
                employeeList.get(getAdapterPosition()).setStatus(0);
            }else if(id==R.id.absent){
                employeeList.get(getAdapterPosition()).setStatus(1);
            }else if(id==R.id.leave){
                employeeList.get(getAdapterPosition()).setStatus(2);
            }
        }

        public void bind(Employee employee){
            tvName.setText(employee.getName());
            tvDesignation.setText(employee.getDesignation());
            Picasso.with(itemView.getContext())
                    .load(employee.getImage())
                    .into(ivImage);

            if(employee.getStatus()==0){
                radioGroup.check(R.id.present);
            }else if(employee.getStatus()==1){
                radioGroup.check(R.id.absent);
            }else if(employee.getStatus()==2){
                radioGroup.check(R.id.leave);
            }
        }
    }
}
