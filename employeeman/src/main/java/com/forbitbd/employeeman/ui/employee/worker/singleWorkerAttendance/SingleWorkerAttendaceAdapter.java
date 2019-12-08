package com.forbitbd.employeeman.ui.employee.worker.singleWorkerAttendance;

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

public class SingleWorkerAttendaceAdapter extends RecyclerView.Adapter<SingleWorkerAttendaceAdapter.SingleWorkerAttendaceHolder> {

    private SingleWorkerAttendanceActivity activity;
    private LayoutInflater inflater;
    private List<Attendance> attendanceList;

    public SingleWorkerAttendaceAdapter(SingleWorkerAttendanceActivity activity) {
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
        this.attendanceList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SingleWorkerAttendaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_single_worker_attendance,parent,false);
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
        int overtimeTotal=0;


        for (Attendance attendance:attendanceList){

            if(attendance.getStatus()==0){
                presentCount++;
            }
            overtimeTotal = (int) (overtimeTotal+attendance.getOver_time());

        }

        activity.renderOvertime(String.valueOf(overtimeTotal).concat(" Hrs"));
        activity.renderPresentCount(String.valueOf(presentCount).concat(" Days"));
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    class SingleWorkerAttendaceHolder extends RecyclerView.ViewHolder{

        TextView tvDate,tvOverTime,tvPresent,tvAbsent,tvLeave;

        public SingleWorkerAttendaceHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.date);
            tvOverTime = itemView.findViewById(R.id.over_time);
            tvPresent = itemView.findViewById(R.id.present);
            tvAbsent = itemView.findViewById(R.id.absent);
            tvLeave = itemView.findViewById(R.id.leave);
        }


        public void bind(Attendance attendance){
            tvDate.setText(String.valueOf(attendance.getDate().getDay()));
            tvOverTime.setText(String.valueOf(attendance.getOver_time()).concat(" Hrs"));

            switch (attendance.getStatus()){
                case 0:
                    tvPresent.setBackground(activity.getResources().getDrawable(R.drawable.circle_colored));
                    break;

                case 1:
                    tvAbsent.setBackground(activity.getResources().getDrawable(R.drawable.circle_colored));
                    break;

                case 2:
                    tvLeave.setBackground(activity.getResources().getDrawable(R.drawable.circle_colored));
                    break;
            }

        }

    }
}
