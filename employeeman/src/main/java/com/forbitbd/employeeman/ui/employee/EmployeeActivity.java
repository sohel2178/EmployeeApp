package com.forbitbd.employeeman.ui.employee;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.androidutils.utils.AppPreference;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.androidutils.utils.ViewPagerAdapter;
import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.Employee;
import com.forbitbd.employeeman.models.Worker;
import com.forbitbd.employeeman.ui.employee.employee.EmployeeFragment;
import com.forbitbd.employeeman.ui.employee.employeeAdd.AddEmployeeActivity;
import com.forbitbd.employeeman.ui.employee.employeeAttendance.EmployeeAttendanceActivity;
import com.forbitbd.employeeman.ui.employee.worker.WorkerFragment;
import com.forbitbd.employeeman.ui.employee.workerAdd.AddWorkerActivity;
import com.forbitbd.employeeman.ui.employee.workerAttendance.WorkerAttendanceActivity;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class EmployeeActivity extends PrebaseActivity implements EmpWorkerContract.View, View.OnClickListener {

    private static final int ADD_EMPLOYEE=5000;
    private static final int UPDATE_EMPLOYEE=6000;
    private static final int ADD_WORKER=7000;
    private static final int UPDATE_WORKER=8000;


    private SharedProject sharedProject;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;

    private FloatingActionButton fabWorker,fabEmployee,fabWorkerAttendance,fabEmployeeAttendance;

    private EmployeeFragment employeeFragment;
    private WorkerFragment workerFragment;

    private EmpWorkerPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        this.sharedProject = (SharedProject) getIntent().getSerializableExtra(Constant.PROJECT);

        mPresenter = new EmpWorkerPresenter(this);
        initView();
    }

    @Override
    protected void onResume() {

        super.onResume();
        setTitle(sharedProject.getProject().getName()+" | "+"Work Forces");
        if(AppPreference.getInstance(this).getCounter()>Constant.COUNTER){
            showInterAd();
        }
    }

    public Project getProject(){
        return this.sharedProject.getProject();
    }

    public SharedProject getSharedProject(){
        return this.sharedProject;
    }

    private void initView() {
        setupToolbar(R.id.toolbar);

        setupBannerAd(R.id.adView);

        employeeFragment = new EmployeeFragment();
        workerFragment = new WorkerFragment();

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        fabWorker = findViewById(R.id.fab_worker);
        fabEmployee = findViewById(R.id.fab_employee);
        fabWorkerAttendance = findViewById(R.id.fab_worker_attendance);
        fabEmployeeAttendance = findViewById(R.id.fab_employee_attendance);

        fabWorker.setOnClickListener(this);
        fabEmployee.setOnClickListener(this);
        fabWorkerAttendance.setOnClickListener(this);
        fabEmployeeAttendance.setOnClickListener(this);

        if(sharedProject.getEmployee().isWrite()){
            fabEmployee.setVisibility(View.VISIBLE);
            fabWorker.setVisibility(View.VISIBLE);
        }else{
            fabEmployee.setVisibility(View.GONE);
            fabWorker.setVisibility(View.GONE);
        }

    }



    private void setupViewPager(ViewPager viewPager) {

        if(pagerAdapter==null){
            pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        }else {
            pagerAdapter.clear();
        }
        pagerAdapter.addFragment(employeeFragment, "Employees");
        pagerAdapter.addFragment(workerFragment, "Workers");

        viewPager.setAdapter(pagerAdapter);

    }

    @Override
    public void onClick(View v) {
        AppPreference.getInstance(this).increaseCounter();
        if(v==fabEmployee){
            mPresenter.startAddEmployeeActivity();
        }else if(v==fabWorker){
            mPresenter.startAddWorkerActivity();
        }else if(v==fabEmployeeAttendance){
            mPresenter.startEmployeeAttendanceActivity();
        }else if(v==fabWorkerAttendance){
            mPresenter.startWorkerAttendanceActivity();
        }
    }

    @Override
    public void startAddEmployeeActivity() {
        Intent intent = new Intent(this, AddEmployeeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,sharedProject.getProject());
        intent.putExtras(bundle);
        startActivityForResult(intent,ADD_EMPLOYEE);
    }

    @Override
    public void startAddWorkerActivity() {
         Intent intent = new Intent(this, AddWorkerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,sharedProject.getProject());
        intent.putExtras(bundle);
        startActivityForResult(intent,ADD_WORKER);
    }

    @Override
    public void startWorkerAttendanceActivity() {
        Intent intent = new Intent(this, WorkerAttendanceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,sharedProject);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void startEmployeeAttendanceActivity() {
        Intent intent = new Intent(this, EmployeeAttendanceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,sharedProject);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void editWorker(Worker worker) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,sharedProject.getProject());
        bundle.putSerializable(Constant.WORKER,worker);
        Intent intent = new Intent(getApplicationContext(), AddWorkerActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,UPDATE_WORKER);
    }

    @Override
    public void startEditEmployeeActivity(Employee employee) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,sharedProject.getProject());
        bundle.putSerializable(Constant.EMPLOYEE,employee);
        Intent intent = new Intent(getApplicationContext(), AddEmployeeActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,UPDATE_EMPLOYEE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==ADD_EMPLOYEE && resultCode==RESULT_OK){
            Employee employee = (Employee) data.getSerializableExtra(Constant.EMPLOYEE);

            if(employee!=null){
                employeeFragment.addEmployee(employee);
            }
        }else if(requestCode==ADD_WORKER && resultCode==RESULT_OK){
            Worker worker = (Worker) data.getSerializableExtra(Constant.WORKER);

            if(worker!=null){
                workerFragment.addWorker(worker);
            }

        }else if(requestCode==UPDATE_WORKER && resultCode == RESULT_OK){
            Worker worker = (Worker) data.getSerializableExtra(Constant.WORKER);

            if(worker!=null){
                workerFragment.updateWorker(worker);
            }
        }else if(requestCode==UPDATE_EMPLOYEE && resultCode==RESULT_OK){
            Employee employee = (Employee) data.getSerializableExtra(Constant.EMPLOYEE);

            if(employee!=null){
                employeeFragment.updateEmployee(employee);
            }
        }
    }
}
