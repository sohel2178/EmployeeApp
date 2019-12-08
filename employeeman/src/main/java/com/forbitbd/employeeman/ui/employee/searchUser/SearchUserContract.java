package com.forbitbd.employeeman.ui.employee.searchUser;


import com.forbitbd.androidutils.models.User;

import java.util.List;

public interface SearchUserContract {

    interface Presenter{
        void loadUsers(String query);
        void filter(String query);
    }

    interface View{
        void filter(String query);
        void showProgressDialog();
        void hideProgressDialog();
        void addUsers(List<User> users);
        void sendUserBack(User user);
    }
}
