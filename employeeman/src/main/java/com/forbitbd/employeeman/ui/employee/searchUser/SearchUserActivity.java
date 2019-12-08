package com.forbitbd.employeeman.ui.employee.searchUser;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.User;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.employeeman.R;

import java.util.List;

public class SearchUserActivity extends PrebaseActivity
        implements SearchUserContract.View, SearchView.OnQueryTextListener {

    private SearchUserPresenter mPresenter;

    private UserAdapter adapter;

    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        this.project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);

        mPresenter = new SearchUserPresenter(this);
        adapter = new UserAdapter(this);



        initView();
    }

    private void initView() {
        setupToolbar(R.id.toolbar);
        getSupportActionBar().setTitle(R.string.search_user);

        RecyclerView rvUsers = findViewById(R.id.rv_user);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.setAdapter(adapter);

        SearchView mSearchView  = findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.length()==1){
            mPresenter.loadUsers(newText);
        }else{
            mPresenter.filter(newText);
        }
        return false;
    }

    @Override
    public void filter(String query) {
        adapter.getFilter().filter(query);
    }

    @Override
    public void addUsers(List<User> users) {
        adapter.clear();
        adapter.addUsers(users);
    }

    @Override
    public void sendUserBack(User user) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.USER,user);
        intent.putExtras(bundle);

        setResult(RESULT_OK,intent);

        finish();
    }
}
