package com.forbitbd.employeeman.ui.employee.workerAdd;


import com.forbitbd.employeeman.models.Worker;

public interface AddWorkerContract {
    interface Presenter{
        void browseClick();
        void bindWorker(Worker worker);
        void dateClick();
        void checkAndSave();
        boolean validate(Worker worker);
        void saveWorker(Worker worker, byte[] bytes);
        void updateWorker(Worker worker, byte[] bytes);
    }

    interface View{
        void openCropImageActivity();
        void openCalendar();
        void checkAndSave();
        void bindWorker(Worker worker);
        void clearPreError();
        void showValidationError(String message, int fieldId);
        void showDialog();
        void hideDialog();
        void finishedAndSendWorker(Worker worker);
        void showErrorToast(String message);
    }
}
