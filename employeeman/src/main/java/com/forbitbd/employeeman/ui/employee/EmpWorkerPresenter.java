package com.forbitbd.employeeman.ui.employee;


public class EmpWorkerPresenter implements EmpWorkerContract.Presenter {

    private EmpWorkerContract.View mView;

    public EmpWorkerPresenter(EmpWorkerContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void startAddEmployeeActivity() {
        mView.startAddEmployeeActivity();
    }

    @Override
    public void startAddWorkerActivity() {
        mView.startAddWorkerActivity();
    }

    @Override
    public void startWorkerAttendanceActivity() {
        mView.startWorkerAttendanceActivity();
    }

    @Override
    public void startEmployeeAttendanceActivity() {
        mView.startEmployeeAttendanceActivity();
    }
}
