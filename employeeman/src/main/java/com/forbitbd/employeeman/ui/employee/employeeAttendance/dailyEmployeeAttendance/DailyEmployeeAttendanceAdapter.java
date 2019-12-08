package com.forbitbd.employeeman.ui.employee.employeeAttendance.dailyEmployeeAttendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.Employee;
import com.forbitbd.employeeman.models.EmployeeAttendanceResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DailyEmployeeAttendanceAdapter extends RecyclerView.Adapter<DailyEmployeeAttendanceAdapter.WorkerAttendanceHolder> {

    private DailyEmployeeAttendanceActivity activity;
    private LayoutInflater inflater;
    private List<EmployeeAttendanceResponse> attendanceResponseList;

    public DailyEmployeeAttendanceAdapter(DailyEmployeeAttendanceActivity activity) {
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
        this.attendanceResponseList = new ArrayList<>();
    }

    @NonNull
    @Override
    public WorkerAttendanceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.employee_attendance_item_view,viewGroup,false);
        return new WorkerAttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerAttendanceHolder workerAttendanceHolder, int i) {

        EmployeeAttendanceResponse attendanceResponse = attendanceResponseList.get(i);
        workerAttendanceHolder.bind(attendanceResponse);

    }



    @Override
    public int getItemCount() {
        return attendanceResponseList.size();
    }

    public void update(List<EmployeeAttendanceResponse> attendanceResponseList){
        this.attendanceResponseList = attendanceResponseList;
        notifyDataSetChanged();
    }

    class WorkerAttendanceHolder extends RecyclerView.ViewHolder{

        ImageView ivImage;
        TextView tvName,tvDesignation,tvStatus;




        public WorkerAttendanceHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.name);
            tvDesignation = itemView.findViewById(R.id.designation);
            tvStatus = itemView.findViewById(R.id.status);
            ivImage = itemView.findViewById(R.id.image);


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

        public void bind(EmployeeAttendanceResponse attendanceResponse){
            Employee employee = attendanceResponse.getEmployee();
            tvName.setText(employee.getName());
            tvDesignation.setText(employee.getDesignation());
            Picasso.with(itemView.getContext())
                    .load(employee.getImage())
                    .into(ivImage);

            switch (employee.getStatus()){
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
        }
    }
}
