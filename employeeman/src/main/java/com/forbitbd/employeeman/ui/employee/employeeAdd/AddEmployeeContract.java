package com.forbitbd.employeeman.ui.employee.employeeAdd;


import com.forbitbd.employeeman.models.Employee;

public interface AddEmployeeContract {

    interface Presenter{
        void browseClick();
        boolean validate(Employee employee);
        void saveEmployee(Employee employee,byte[] bytes);
        void updateEmployee(Employee employee,byte[] bytes);
        void checkAndSave();
        void bind();
    }

    interface View{
        void startImagePicker();
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
