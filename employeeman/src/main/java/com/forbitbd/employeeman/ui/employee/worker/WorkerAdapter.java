package com.forbitbd.employeeman.ui.employee.worker;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.androidutils.utils.AppPreference;
import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.Worker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.WorkerHolder> {

    private WorkerFragment fragment;
    private List<Worker> workerList;
    private LayoutInflater inflater;
    private SharedProject.Permission employeePermission;

    public WorkerAdapter(WorkerFragment fragment,SharedProject.Permission employeePermission) {
        this.fragment = fragment;
        this.workerList = new ArrayList<>();
        this.inflater = LayoutInflater.from(fragment.getContext());
        this.employeePermission = employeePermission;
    }

    @NonNull
    @Override
    public WorkerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.worker_item,viewGroup,false);
        return new WorkerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerHolder workerHolder, int i) {
        Worker worker = workerList.get(i);
        workerHolder.bind(worker);
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
        workerList.add(worker);
        int position = workerList.indexOf(worker);
        notifyItemInserted(position);
    }

    public void updatewWorker(Worker worker){
        int position = getPosition(worker);

        if(position != -1){
            workerList.set(position,worker);
            notifyItemChanged(position);
        }
    }

    public void deleteWorker(Worker worker){
        int position = getPosition(worker);

        if(position != -1){
            workerList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private int getPosition(Worker worker) {

        for (Worker x: workerList){
            if(x.get_id().equals(worker.get_id())){
                return workerList.indexOf(x);
            }
        }

        return -1;
    }

    public List<Worker> getWorkerList(){
        return this.workerList;
    }


    class WorkerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivAttach,ivEdit,ivDelete;
        TextView tvName,tvContact,tvDesignation;

        public WorkerHolder(@NonNull View itemView) {
            super(itemView);
            ivAttach = itemView.findViewById(R.id.attach);
            ivEdit = itemView.findViewById(R.id.edit);
            ivDelete = itemView.findViewById(R.id.delete);

            tvName = itemView.findViewById(R.id.name);
            tvDesignation = itemView.findViewById(R.id.designation);
            tvContact = itemView.findViewById(R.id.contact);

            itemView.setOnClickListener(this);
            //itemView.setOnLongClickListener(this);
            ivEdit.setOnClickListener(this);
            ivDelete.setOnClickListener(this);
            ivAttach.setOnClickListener(this);

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


        public void bind(Worker worker){
            tvName.setText(worker.getName());
            tvContact.setText(worker.getContact_no());
            tvDesignation.setText(worker.getDesignation());
        }

        @Override
        public void onClick(View view) {
            if(view==itemView){
                AppPreference.getInstance(view.getContext()).increaseCounter();
                fragment.startSingleWorkerAttendanceAvtivity(workerList.get(getAdapterPosition()));
            }else  if(view==ivEdit){
                AppPreference.getInstance(view.getContext()).increaseCounter();
                fragment.startEditWorker(workerList.get(getAdapterPosition()));
            }else  if(view==ivDelete){
                AppPreference.getInstance(view.getContext()).increaseCounter();
                fragment.showDeleteDialog(workerList.get(getAdapterPosition()));
            }else if(view==ivAttach){
                fragment.startZoomImageActivity(workerList.get(getAdapterPosition()));
            }

        }

       /* @Override
        public boolean onLongClick(View view) {
            fragment.startWorkerDetailActivity(workerList.get(getAdapterPosition()));
            return false;
        }*/
    }
}
