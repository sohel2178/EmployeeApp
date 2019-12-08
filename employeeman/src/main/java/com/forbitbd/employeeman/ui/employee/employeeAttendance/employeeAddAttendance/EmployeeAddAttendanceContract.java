package com.forbitbd.employeeman.ui.employee.employeeAttendance.employeeAddAttendance;



import com.forbitbd.employeeman.models.AttendanceResponse;
import com.forbitbd.employeeman.models.Employee;
import com.forbitbd.employeeman.models.EmployeeAttendanceResponse;

import java.util.Date;
import java.util.List;

public interface EmployeeAddAttendanceContract {

    interface Presenter{
        void openCalendar();
        void saveClick();
        void uploadEmployeeAttendanceToServer(List<Employee> employeeList, Date date, String projectId);
        void getAllProjectEmployee(String projectId);
    }

    interface View{
        void openCalendar();
        void saveClick();
        void renderEmployeeList(List<Employee> employeeList);
        void showDialog();
        void hideDialog();
        void back(EmployeeAttendanceResponse attendanceResponse);
        void showToast(String message);
    }
}
