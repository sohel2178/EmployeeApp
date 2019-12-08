package com.forbitbd.employeeman.ui.employee.employee;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.androidutils.dialog.delete.DeleteDialog;
import com.forbitbd.androidutils.dialog.delete.DialogClickListener;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.Employee;
import com.forbitbd.employeeman.ui.employee.EmployeeBaseFragment;
import com.forbitbd.employeeman.ui.employee.employee.singleEmployeeAttendance.SingleEmployeeAttendanceActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeFragment extends EmployeeBaseFragment implements EmployeeContract.View{

    private EmployeePresenter mPresenter;
    private EmployeeAdapter adapter;


    public EmployeeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new EmployeePresenter(this);
        this.adapter = new EmployeeAdapter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        mPresenter.requestProjectEmployees(getProject().get_id());
    }

    @Override
    public void showProgressDialog() {
        showDialog();

    }

    @Override
    public void hideProgressDialog() {
        hideDialog();
    }

    @Override
    public void renderAdapter(List<Employee> employeeList) {
        adapter.clear();
        for (Employee x: employeeList){
            adapter.addemployee(x);
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startAddEmployeeActivity(Project project) {

    }

    @Override
    public void startAddEmployeeAttendanceActivity(Project project) {
        /*Intent intent = new Intent(getContext(), EmployeeAttendanceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,project);
        //bundle.putSerializable(Constant.WORKER_LIST, (Serializable) adapter.getWorkerList());
        intent.putExtras(bundle);
        startActivity(intent);*/

    }

    @Override
    public void startEmployeeDetailActivity(Employee employee) {
        /*Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,getProject());
        bundle.putSerializable(Constant.EMPLOYEE,employee);

        Intent intent = new Intent(getContext(), EmployeeDetailActivity.class);
        intent.putExtras(bundle);

        startActivityForResult(intent,UPDATE_REQUEST_CODE);*/

    }

    @Override
    public void startSingleEmployeeAttendanceActivity(Employee employee) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,getProject());
        bundle.putSerializable(Constant.EMPLOYEE,employee);
        Intent intent = new Intent(getContext(), SingleEmployeeAttendanceActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void startEditEmployeeActivity(Employee employee) {
        get_activity().startEditEmployeeActivity(employee);
    }

    @Override
    public void showDeleteDialog(final Employee employee) {
        final DeleteDialog deleteDialog = new DeleteDialog();
        deleteDialog.setCancelable(false);

        Bundle bundle = new Bundle();
        bundle.putString(Constant.CONTENT,"Do you really want to delete this Employee??");
        deleteDialog.setArguments(bundle);
        deleteDialog.setListener(new DialogClickListener() {
            @Override
            public void positiveButtonClick() {
                deleteDialog.dismiss();
                mPresenter.deleteEmployee(employee);
            }
        });

        deleteDialog.show(getChildFragmentManager(),"HHHHH");
    }

    @Override
    public void updateEmployee(Employee employee) {
        adapter.updateEmployee(employee);
    }

    @Override
    public void removeEmployeeFromAdapter(String id) {
        Log.d("HHHHHHHH","CALL IN FRAGMENT");
        adapter.deleteEmployee(id);
    }

    public void addEmployee(Employee employee){
        adapter.addemployee(employee);
    }

}
