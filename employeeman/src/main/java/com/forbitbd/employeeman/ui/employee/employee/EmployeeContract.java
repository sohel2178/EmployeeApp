package com.forbitbd.employeeman.ui.employee.employee;


import com.forbitbd.androidutils.models.Project;
import com.forbitbd.employeeman.models.Employee;

import java.util.List;

public interface EmployeeContract {

    interface Presenter{
        void requestProjectEmployees(String projectId);
        void deleteEmployee(Employee employee);
        void startAddEmployeeActivity(Project project);
        void startAddEmployeeAttendanceActivity(Project project);
    }

    interface View{
        void showProgressDialog();
        void hideProgressDialog();
        void renderAdapter(List<Employee> employeeList);

        void showToast(String message);
        void startAddEmployeeActivity(Project project);
        void startAddEmployeeAttendanceActivity(Project project);
        void startEmployeeDetailActivity(Employee employee);
        void startSingleEmployeeAttendanceActivity(Employee employee);
        void startEditEmployeeActivity(Employee employee);
        void startZoomImageActivity(Employee employee);
        void showDeleteDialog(Employee employee);
        void updateEmployee(Employee employee);
        void removeEmployeeFromAdapter(String id);
    }
}
