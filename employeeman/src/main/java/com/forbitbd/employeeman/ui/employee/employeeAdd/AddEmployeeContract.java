package com.forbitbd.employeeman.ui.employee.employeeAdd;


import com.forbitbd.employeeman.models.Employee;

public interface AddEmployeeContract {

    interface Presenter{
        void browseClick();
        boolean validate(Employee employee);
        void saveEmployee(Employee employee);
        void updateEmployee(Employee employee);
        void checkAndSave();
        void bind();
    }

    interface View{
        void startSearchUserActivity();
        void checkAndSave();
        void clearPreError();
        void showValidationError(String message, int fieldId);
        void showProgressDialog();
        void hideProgressDialog();
        void finishedAndSendEmployee(Employee employee);
        void showErrorToast(String message);
        void bind();
    }
}
