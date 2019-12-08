package com.forbitbd.employeeman.ui.employee.workerAttendance.dailyWorkerAttendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.AttendanceResponse;
import com.forbitbd.employeeman.models.Worker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WorkerAttendanceAdapter extends RecyclerView.Adapter<WorkerAttendanceAdapter.WorkerAttendanceHolder> {

    private DailyWorkerAttendanceActivity activity;
    private LayoutInflater inflater;
    private List<AttendanceResponse> attendanceResponseList;

    public WorkerAttendanceAdapter(DailyWorkerAttendanceActivity activity) {
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
        this.attendanceResponseList = new ArrayList<>();
    }

    @NonNull
    @Override
    public WorkerAttendanceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.worker_attendance_item_view,viewGroup,false);
        return new WorkerAttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerAttendanceHolder workerAttendanceHolder, int i) {

        AttendanceResponse attendanceResponse = attendanceResponseList.get(i);
        workerAttendanceHolder.bind(attendanceResponse);

    }



    @Override
    public int getItemCount() {
        return attendanceResponseList.size();
    }

    public void update(List<AttendanceResponse> attendanceResponseList){
        this.attendanceResponseList = attendanceResponseList;
        notifyDataSetChanged();
    }

    class WorkerAttendanceHolder extends RecyclerView.ViewHolder{

        TextView tvName,tvDesignation,tvOvertime,tvStatus;




        public WorkerAttendanceHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.name);
            tvDesignation = itemView.findViewById(R.id.designation);
            tvOvertime = itemView.findViewById(R.id.over_time);
            tvStatus = itemView.findViewById(R.id.status);
           // ivImage = itemView.findViewById(R.id.image);


            //radioGroup.setOnCheckedChangeListener((radioGroup, i) -> setState(i));
        }

       /* private void setState(int id){
            switch (id){
                case R.id.present:
                    workerList.get(getAdapterPosition()).setStatus(0);
                    break;

                case R.id.absent:
                    workerList.get(getAdapterPosition()).setStatus(1);
                    break;

                case R.id.leave:
                    workerList.get(getAdapterPosition()).setStatus(2);
                    break;
            }
        }*/

        public void bind(AttendanceResponse attendanceResponse){
            Worker worker = attendanceResponse.getWorker();
            tvName.setText(worker.getName());
            tvDesignation.setText(worker.getDesignation());
           /* Picasso.with(itemView.getContext())
                    .load(worker.getImage())
                    .into(ivImage);*/

            switch (worker.getStatus()){
                case 0:
                    tvStatus.setText("Present");
                    break;

                case 1:
                    tvStatus.setText("Absent");
                    break;

                case 2:
                    tvStatus.setText("Leave");
                    break;
            }

            tvOvertime.setText(String.valueOf(worker.getOver_time()).concat(" Hrs"));
        }
    }
}
