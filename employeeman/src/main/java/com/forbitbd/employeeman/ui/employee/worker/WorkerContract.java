package com.forbitbd.employeeman.ui.employee.worker;



import com.forbitbd.androidutils.models.Project;
import com.forbitbd.employeeman.models.Worker;

import java.util.List;

public interface WorkerContract {

    interface Presenter{
        void requestWorkers(String projectId);
        void startAddWorkerActivity(Project project);
        void startAddAttendanceActivity(Project project);
        void deleteWorker(Worker worker);
    }

    interface View{
        void showProgressDialog();
        void hideProgressDialog();
        void addWorkersToAdapter(List<Worker> workers);

        void showToast(String message);
        void startAddWorkerActivity(Project project);
        void startAddAttendanceActivity(Project project);
        void startWorkerDetailActivity(Worker worker);
        void startSingleWorkerAttendanceAvtivity(Worker worker);
        void startEditWorker(Worker worker);
        void showDeleteDialog(Worker worker);
        void removeWorkerFromAdapter(Worker worker);
    }
}
