package com.forbitbd.employeeman.ui.employee;

import com.forbitbd.employeeman.models.Employee;
import com.forbitbd.employeeman.models.Worker;

public interface EmpWorkerContract {

    interface Presenter{
        void startAddEmployeeActivity();
        void startAddWorkerActivity();
        void startWorkerAttendanceActivity();
        void startEmployeeAttendanceActivity();
    }

    interface View{
        void startAddEmployeeActivity();
        void startAddWorkerActivity();
        void startWorkerAttendanceActivity();
        void startEmployeeAttendanceActivity();
        void editWorker(Worker worker);
        void startEditEmployeeActivity(Employee employee);
    }
}
