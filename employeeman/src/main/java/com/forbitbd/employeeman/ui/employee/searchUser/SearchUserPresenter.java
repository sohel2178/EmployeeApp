package com.forbitbd.employeeman.ui.employee.searchUser;


import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.androidutils.models.User;
import com.forbitbd.employeeman.api.EmployeeClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchUserPresenter implements SearchUserContract.Presenter {

    private SearchUserContract.View mView;

    public SearchUserPresenter(SearchUserContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void loadUsers(String query) {
        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);
        mView.showProgressDialog();

        client.getQueryUsers(query)
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.addUsers(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });

    }

    @Override
    public void filter(String query) {
        mView.filter(query);
    }
}
