package com.forbitbd.employeeman.ui.employee.workerAttendance.attendance;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.Worker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceHolder> {

    private AttendanceActivity activity;
    private List<Worker> workerList;
    private LayoutInflater inflater;

    public AttendanceAdapter(AttendanceActivity activity) {
        this.activity = activity;
        this.workerList = new ArrayList<>();
        this.inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public AttendanceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R
                .layout.worker_attendance_item,viewGroup,false);
        return new AttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceHolder attendanceHolder, int i) {
        Worker worker = workerList.get(i);
        attendanceHolder.bind(worker);
    }

    @Override
    public int getItemCount() {
        return workerList.size();
    }

    public void clear(){
        this.workerList.clear();
        notifyDataSetChanged();
    }

    public void addWorker(Worker worker){
        this.workerList.add(worker);
        int position = workerList.indexOf(worker);
        notifyItemInserted(position);
    }

    public List<Worker> getWorkerList(){
        return this.workerList;
    }

    class AttendanceHolder extends RecyclerView.ViewHolder{

        ImageView ivImage;
        TextView tvName,tvDesignation;

        RadioGroup radioGroup;
        EditText etOverTime;


        public AttendanceHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.name);
            tvDesignation = itemView.findViewById(R.id.designation);
            ivImage = itemView.findViewById(R.id.image);
            etOverTime = itemView.findViewById(R.id.over_time);

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
                    workerList.get(getAdapterPosition()).setOver_time(overTime);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

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
                workerList.get(getAdapterPosition()).setStatus(0);
            }else if(id==R.id.absent){
                workerList.get(getAdapterPosition()).setStatus(1);
            }else if(id==R.id.leave){
                workerList.get(getAdapterPosition()).setStatus(2);
            }
        }

        public void bind(Worker worker){
            tvName.setText(worker.getName());
            tvDesignation.setText(worker.getDesignation());
            Picasso.with(itemView.getContext())
                    .load(worker.getImage())
                    .into(ivImage);
        }
    }
}
