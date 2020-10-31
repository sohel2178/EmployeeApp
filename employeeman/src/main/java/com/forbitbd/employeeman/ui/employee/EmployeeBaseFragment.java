package com.forbitbd.employeeman.ui.employee;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.SharedProject;


public abstract class EmployeeBaseFragment extends Fragment {

    private EmployeeActivity activity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof EmployeeActivity){
            this.activity = (EmployeeActivity) getActivity();
        }
    }

    public void showDialog(){
        activity.showProgressDialog();
    }

    public void hideDialog(){
        activity.hideProgressDialog();
    }

    public void showToast(String message){
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public Project getProject(){
        return activity.getProject();
    }

    public SharedProject getSharedProject(){
        return activity.getSharedProject();
    }

    public EmployeeActivity get_activity(){
        return activity;
    }
}
