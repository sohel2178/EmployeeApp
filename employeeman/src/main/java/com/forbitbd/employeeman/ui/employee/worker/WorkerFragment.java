package com.forbitbd.employeeman.ui.employee.worker;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.androidutils.dialog.delete.DeleteDialog;
import com.forbitbd.androidutils.dialog.delete.DialogClickListener;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.Worker;
import com.forbitbd.employeeman.ui.employee.EmployeeBaseFragment;
import com.forbitbd.employeeman.ui.employee.worker.singleWorkerAttendance.SingleWorkerAttendanceActivity;
import com.forbitbd.employeeman.ui.employee.workerAdd.AddWorkerActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkerFragment extends EmployeeBaseFragment implements  WorkerContract.View {

    private WorkerAdapter adapter;
    private WorkerPresenter mPresenter;


    public WorkerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new WorkerAdapter(this);
        mPresenter = new WorkerPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_worker, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        mPresenter.requestWorkers(getProject().get_id());

    }

   /* @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.fab_worker:
                mPresenter.startAddWorkerActivity(getProject());
                break;

            case R.id.fab_attendance:
                mPresenter.startAddAttendanceActivity(getProject());
                break;
        }

    }*/



    @Override
    public void showProgressDialog() {
        showDialog();

    }

    @Override
    public void hideProgressDialog() {
        hideDialog();
    }

    public void addWorker(Worker worker){
        adapter.addWorker(worker);
    }

    @Override
    public void addWorkersToAdapter(List<Worker> workerList) {
        adapter.clear();

        for (Worker x: workerList){
            adapter.addWorker(x);
        }


    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void startAddWorkerActivity(Project project) {

    }

    @Override
    public void startAddAttendanceActivity(Project project) {
       /* Intent intent = new Intent(getContext(), WorkerAttendanceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,project);
        //bundle.putSerializable(Constant.WORKER_LIST, (Serializable) adapter.getWorkerList());
        intent.putExtras(bundle);
        startActivity(intent);*/
    }

    @Override
    public void startWorkerDetailActivity(Worker worker) {
        /*Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,getProject());
        bundle.putSerializable(Constant.WORKER,worker);

        Intent intent = new Intent(getContext(), WorkerDetailActivity.class);
        intent.putExtras(bundle);

        startActivityForResult(intent,UPDATE_REQUEST_CODE);*/
    }

    @Override
    public void startSingleWorkerAttendanceAvtivity(Worker worker) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,getProject());
        bundle.putSerializable(Constant.WORKER,worker);

        Intent intent = new Intent(getContext(), SingleWorkerAttendanceActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void startEditWorker(Worker worker) {
        get_activity().editWorker(worker);
    }

    @Override
    public void showDeleteDialog(final Worker worker) {
        final DeleteDialog deleteDialog = new DeleteDialog();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.CONTENT,"Do you really want to delete this worker?");
        deleteDialog.setArguments(bundle);
        deleteDialog.setCancelable(false);
        deleteDialog.setListener(new DialogClickListener() {
            @Override
            public void positiveButtonClick() {
                deleteDialog.dismiss();
                mPresenter.deleteWorker(worker);
            }
        });

        deleteDialog.show(getChildFragmentManager(),"HHHHH");

    }

    @Override
    public void removeWorkerFromAdapter(Worker worker) {
        adapter.deleteWorker(worker);
    }

    public void updateWorker(Worker worker){
        adapter.updatewWorker(worker);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("HHHHHH",requestCode+"");

       /* if(requestCode==REQUEST_CODE && resultCode== Activity.RESULT_OK){
            Worker worker = (Worker) data.getSerializableExtra(Constant.WORKER);

            if(worker!=null){
                adapter.addWorker(worker);
            }
        }

        if(requestCode==UPDATE_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            Worker worker = (Worker) data.getSerializableExtra(Constant.WORKER);
            String status = data.getStringExtra(Constant.STATUS);
            if(worker!=null){
                if(status.equals(Constant.UPDATE)){
                    adapter.updatewWorker(worker);
                }else if(status.equals(Constant.DELETE)){
                    adapter.deleteWorker(worker);
                }

            }
        }*/
    }
}
