package com.forbitbd.employeeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Button;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.employeeman.ui.employee.EmployeeActivity;

public class MainActivity extends AppCompatActivity {

    private String project_id = "5f87633670279032e9ffdf24";

   // {"images":[],"_id":"5dbe73e488262501774c4efb","name":"My Project","location":"Dhaka","description":"Construction Project","start_date":"2019-11-02T17:29:41.000Z","completion_date":"2022-11-06T17:29:51.000Z","user":"5dbe73b388262501774c4efa","created_at":"2019-11-03T06:29:56.648Z","__v":0}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Project project = new Project();
        project.set_id("5f87633670279032e9ffdf24");
        project.setName("My Project");
        project.setLocation("Dhaka");
        project.setDescription("Construction Project");
        project.setUser("5ee221fdf127667cc70e13ed");

        Button btnClickMe = findViewById(R.id.clickMe);
        btnClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EmployeeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PROJECT",project);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });


    }
}
