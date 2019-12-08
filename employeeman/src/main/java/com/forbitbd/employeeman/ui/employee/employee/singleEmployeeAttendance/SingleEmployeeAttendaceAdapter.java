package com.forbitbd.employeeman.ui.employee.employee.singleEmployeeAttendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.Attendance;

import java.util.ArrayList;
import java.util.List;

public class SingleEmployeeAttendaceAdapter extends RecyclerView.Adapter<SingleEmployeeAttendaceAdapter.SingleWorkerAttendaceHolder> {

    private SingleEmployeeAttendanceActivity activity;
    private LayoutInflater inflater;
    private List<Attendance> attendanceList;

    public SingleEmployeeAttendaceAdapter(SingleEmployeeAttendanceActivity activity) {
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
        this.attendanceList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SingleWorkerAttendaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_single_employee_attendance,parent,false);
        return new SingleWorkerAttendaceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleWorkerAttendaceHolder holder, int position) {
        Attendance attendance = attendanceList.get(position);
        holder.bind(attendance);
    }

    public void updateAdapter(List<Attendance> attendanceList){
        this.attendanceList = attendanceList;
        renderHiddenLayout();
        notifyDataSetChanged();
    }

    private void renderHiddenLayout(){
        int presentCount=0;
        int absentCount=0;
        int leaveCount=0;


        for (Attendance attendance:attendanceList){

            if(attendance.getStatus()==0){
                presentCount++;
            }else if(attendance.getStatus()==1){
                absentCount++;
            }else if(attendance.getStatus()==2){
                leaveCount++;
            }
        }

        activity.renderPresentCount(String.valueOf(presentCount).concat(" Days"));
        activity.renderAbsentCount(String.valueOf(absentCount).concat(" Days"));
        activity.renderLeaveCount(String.valueOf(leaveCount).concat(" Days"));
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    class SingleWorkerAttendaceHolder extends RecyclerView.ViewHolder {

        TextView tvDate,tvAttendance;

        public SingleWorkerAttendaceHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.date);
            tvAttendance = itemView.findViewById(R.id.attendance);
        }


        public void bind(Attendance attendance){
            tvDate.setText(String.valueOf(attendance.getDate().getDay()));

            switch (attendance.getStatus()){
                case 0:
                    tvAttendance.setText("Present");
                    break;

                case 1:
                    tvAttendance.setText("Absent");
                    break;

                case 2:
                    tvAttendance.setText("Leave");
                    //tvLeave.setBackground(activity.getResources().getDrawable(R.drawable.circle_colored));
                    break;
            }

        }

    }
}
