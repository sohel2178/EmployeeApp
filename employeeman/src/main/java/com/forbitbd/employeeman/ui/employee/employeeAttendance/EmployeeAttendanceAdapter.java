package com.forbitbd.employeeman.ui.employee.employeeAttendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.AttendanceResponse;
import com.forbitbd.employeeman.models.EmployeeAttendanceResponse;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAttendanceAdapter extends RecyclerView.Adapter<EmployeeAttendanceAdapter.AttendanceHolder> {


    private EmployeeAttendanceActivity activity;
    private LayoutInflater inflater;
    private List<EmployeeAttendanceResponse> attendanceResponseList;


    public EmployeeAttendanceAdapter(EmployeeAttendanceActivity activity) {
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
        this.attendanceResponseList = new ArrayList<>();
    }

    @NonNull
    @Override
    public AttendanceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_employee_attendance,viewGroup,false);
        return new AttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceHolder attendanceHolder, int i) {
        EmployeeAttendanceResponse attendanceResponse = attendanceResponseList.get(i);
        attendanceHolder.bind(attendanceResponse);
    }

    @Override
    public int getItemCount() {
        return attendanceResponseList.size();
    }

    public void addAttendanceResponse(EmployeeAttendanceResponse attendanceResponse){
        attendanceResponseList.add(attendanceResponse);
        int position = attendanceResponseList.indexOf(attendanceResponse);
        notifyItemInserted(position);
    }

    public void deleteAttendanceResponse(EmployeeAttendanceResponse attendanceResponse){
        int position = getPosition(attendanceResponse);
        attendanceResponseList.remove(position);
        notifyItemRemoved(position);
    }

    public void clear(){
        attendanceResponseList.clear();
        notifyDataSetChanged();
    }

    public void positionItem(EmployeeAttendanceResponse attendanceResponse){
        if(attendanceResponseList.size()==0){
            addAttendanceResponse(attendanceResponse);
        }else{
            int position = getCorrectPosition(attendanceResponse);
            attendanceResponseList.add(position,attendanceResponse);
            notifyItemInserted(position);
        }
    }

    private int getCorrectPosition(EmployeeAttendanceResponse attendanceResponse){
        for (EmployeeAttendanceResponse x: attendanceResponseList){
            if(attendanceResponse.get_id().getDay()<x.get_id().getDay()){
                return attendanceResponseList.indexOf(x);
            }
        }
        return attendanceResponseList.size();
    }



    private int getPosition(EmployeeAttendanceResponse attendanceResponse){
        for (EmployeeAttendanceResponse x: attendanceResponseList){
            if(x.get_id().getYear()==attendanceResponse.get_id().getYear() &&
            x.get_id().getMonth()==attendanceResponse.get_id().getMonth() &&
            x.get_id().getDay()== attendanceResponse.get_id().getDay()){
                return attendanceResponseList.indexOf(x);
            }
        }
        return -1;
    }

    class AttendanceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvDate,tvPresent,tvAbsent,tvLeave;

        public AttendanceHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.date);
            tvPresent = itemView.findViewById(R.id.present);
            tvAbsent = itemView.findViewById(R.id.absent);
            tvLeave = itemView.findViewById(R.id.leave);

            itemView.setOnClickListener(this);
        }

        public void bind(EmployeeAttendanceResponse attendanceResponse){
            tvDate.setText(attendanceResponse.get_id().getDay()+"");
            tvPresent.setText(String.valueOf(attendanceResponse.getTotal_present()).concat(" Nos"));
            tvAbsent.setText(String.valueOf(attendanceResponse.getTotal_absent()).concat(" Nos"));
            tvLeave.setText(String.valueOf(attendanceResponse.getTotal_leave()).concat(" Nos"));
        }

        @Override
        public void onClick(View view) {
            activity.startDailyAttendanceActivity(attendanceResponseList.get(getAdapterPosition()));
        }
    }
}
