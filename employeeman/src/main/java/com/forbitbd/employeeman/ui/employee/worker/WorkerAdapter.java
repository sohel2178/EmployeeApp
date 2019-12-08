package com.forbitbd.employeeman.ui.employee.worker;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.Worker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.WorkerHolder> {

    private WorkerFragment fragment;
    private List<Worker> workerList;
    private LayoutInflater inflater;

    public WorkerAdapter(WorkerFragment fragment) {
        this.fragment = fragment;
        this.workerList = new ArrayList<>();
        this.inflater = LayoutInflater.from(fragment.getContext());
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

        ImageView ivImage,ivEdit,ivDelete;
        TextView tvName,tvContact,tvDesignation;

        public WorkerHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.image);
            ivEdit = itemView.findViewById(R.id.edit);
            ivDelete = itemView.findViewById(R.id.delete);

            tvName = itemView.findViewById(R.id.name);
            tvDesignation = itemView.findViewById(R.id.designation);
            tvContact = itemView.findViewById(R.id.contact);

            itemView.setOnClickListener(this);
            //itemView.setOnLongClickListener(this);
            ivEdit.setOnClickListener(this);
            ivDelete.setOnClickListener(this);
        }


        public void bind(Worker worker){
            tvName.setText(worker.getName());
            tvContact.setText(worker.getContact_no());
            tvDesignation.setText(worker.getDesignation());

            if(worker.getImage()!= null && !worker.getImage().equals("")){
                Picasso.with(fragment.getContext())
                        .load(worker.getImage())
                        .into(ivImage);
            }
        }

        @Override
        public void onClick(View view) {
            if(view==itemView){
                fragment.startSingleWorkerAttendanceAvtivity(workerList.get(getAdapterPosition()));
            }else  if(view==ivEdit){
                Log.d("KKKKKK","CALL EDIT");
                fragment.startEditWorker(workerList.get(getAdapterPosition()));
            }else  if(view==ivDelete){
                fragment.showDeleteDialog(workerList.get(getAdapterPosition()));
            }

        }

       /* @Override
        public boolean onLongClick(View view) {
            fragment.startWorkerDetailActivity(workerList.get(getAdapterPosition()));
            return false;
        }*/
    }
}
