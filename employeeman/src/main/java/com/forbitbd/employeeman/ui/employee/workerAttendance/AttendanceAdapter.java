package com.forbitbd.employeeman.ui.employee.workerAttendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.AttendanceResponse;

import java.util.ArrayList;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceHolder> {


    private WorkerAttendanceActivity activity;
    private LayoutInflater inflater;
    private List<AttendanceResponse> attendanceResponseList;


    public AttendanceAdapter(WorkerAttendanceActivity activity) {
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
        this.attendanceResponseList = new ArrayList<>();
    }

    @NonNull
    @Override
    public AttendanceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_attendance,viewGroup,false);
        return new AttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceHolder attendanceHolder, int i) {
        AttendanceResponse attendanceResponse = attendanceResponseList.get(i);
        attendanceHolder.bind(attendanceResponse);

    }

    @Override
    public int getItemCount() {
        return attendanceResponseList.size();
    }

    public void addAttendanceResponse(AttendanceResponse attendanceResponse){
        attendanceResponseList.add(attendanceResponse);
        int position = attendanceResponseList.indexOf(attendanceResponse);
        notifyItemInserted(position);
    }

    public void deleteAttendanceResponse(AttendanceResponse attendanceResponse){
        int position = getPosition(attendanceResponse);
        attendanceResponseList.remove(position);
        notifyItemRemoved(position);
    }

    public void clear(){
        attendanceResponseList.clear();
        notifyDataSetChanged();
    }

    public void positionItem(AttendanceResponse attendanceResponse){
        if(attendanceResponseList.size()==0){
            addAttendanceResponse(attendanceResponse);
        }else{
            int position = getCorrectPosition(attendanceResponse);
            attendanceResponseList.add(position,attendanceResponse);
            notifyItemInserted(position);
        }
    }

    private int getCorrectPosition(AttendanceResponse attendanceResponse){
        for (AttendanceResponse x: attendanceResponseList){
            if(attendanceResponse.get_id().getDay()<x.get_id().getDay()){
                return attendanceResponseList.indexOf(x);
            }
        }
        return attendanceResponseList.size();
    }



    private int getPosition(AttendanceResponse attendanceResponse){
        for (AttendanceResponse x: attendanceResponseList){
            if(x.get_id().getYear()==attendanceResponse.get_id().getYear() &&
            x.get_id().getMonth()==attendanceResponse.get_id().getMonth() &&
            x.get_id().getDay()== attendanceResponse.get_id().getDay()){
                return attendanceResponseList.indexOf(x);
            }
        }
        return -1;
    }

    class AttendanceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvDate,tvTotalAttendance,tvTotalOvertime;

        public AttendanceHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.date);
            tvTotalAttendance = itemView.findViewById(R.id.total_attendance);
            tvTotalOvertime = itemView.findViewById(R.id.total_overtime);

            itemView.setOnClickListener(this);
        }

        public void bind(AttendanceResponse attendanceResponse){
            tvDate.setText(attendanceResponse.get_id().getDay()+"");
            tvTotalAttendance.setText(String.valueOf(attendanceResponse.getTotal_present()).concat(" Nos"));
            tvTotalOvertime.setText(String.valueOf(attendanceResponse.getTotal_overtime()).concat(" Hrs"));
        }

        @Override
        public void onClick(View view) {

            activity.startDailyAttendanceActivity(attendanceResponseList.get(getAdapterPosition()));

        }
    }
}
