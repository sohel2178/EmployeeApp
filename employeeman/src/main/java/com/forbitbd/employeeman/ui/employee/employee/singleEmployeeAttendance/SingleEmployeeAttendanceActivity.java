package com.forbitbd.employeeman.ui.employee.employee.singleEmployeeAttendance;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.Attendance;
import com.forbitbd.employeeman.models.Employee;

import java.util.Calendar;
import java.util.List;

public class SingleEmployeeAttendanceActivity extends PrebaseActivity implements
        SingleEmployeeAttendanceContract.View, View.OnClickListener {

    private SingleEmployeeAttendancePresenter mPresenter;

    private Project project;
    private Employee employee;

    private SingleEmployeeAttendaceAdapter adapter;

    private int currentMonth,currentYear;
    private TextView tvStatus,tvPresent,tvAbsent,tvLeave,tvState,tvPrev,tvNext;

    private CardView mCardTop,mCardBottom;
    private RelativeLayout mBootomContainer,mTopContainer,mRecyclerContainer;
    private ImageView tvIndicator;

    private int height,initialTranslateY,recyclerTranslater;
    private boolean isExpand = false;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_employee_attendance);

        mPresenter = new SingleEmployeeAttendancePresenter(this);

        this.project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);
        this.employee = (Employee) getIntent().getSerializableExtra(Constant.EMPLOYEE);

        this.adapter = new SingleEmployeeAttendaceAdapter(this);

        this.currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        this.currentYear = Calendar.getInstance().get(Calendar.YEAR);

        initView();
    }

    private void initView() {
        setupToolbar(R.id.toolbar);
        getSupportActionBar().setTitle(employee.getName().concat(" Attendance"));

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        mCardTop = findViewById(R.id.topCard);
        mCardBottom = findViewById(R.id.bottomCard);
        mBootomContainer = findViewById(R.id.bottomContainer);
        mTopContainer = findViewById(R.id.topContainer);
        mRecyclerContainer = findViewById(R.id.recycler_container);

        tvStatus = findViewById(R.id.status);
        tvState = findViewById(R.id.state);
        tvPresent = findViewById(R.id.present);
        tvAbsent = findViewById(R.id.absent);
        tvLeave = findViewById(R.id.leave);

        tvIndicator = findViewById(R.id.indicator);
        tvNext = findViewById(R.id.next);
        tvPrev = findViewById(R.id.prev);

        tvNext.setOnClickListener(this);
        tvPrev.setOnClickListener(this);
        tvIndicator.setOnClickListener(this);

        final RelativeLayout mainContainer = findViewById(R.id.main_container);

        ViewTreeObserver viewTreeObserver = mainContainer.getViewTreeObserver();

        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mainContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int heightTop = mCardTop.getHeight();
                    int heightBottom = mCardBottom.getHeight();
                    int indicatorHeight = tvIndicator.getHeight();

                    int recyclerContainerHeight = mRecyclerContainer.getHeight();

                    initialTranslateY = Math.abs(heightBottom-heightTop);


                    height = (int) (heightTop+getResources().getDimension(R.dimen.default_margin));
                    recyclerTranslater = (int) (indicatorHeight+getResources().getDimension(R.dimen.default_margin));



                    mBootomContainer.setY(-initialTranslateY);

                    recyclerView.setTranslationY(mTopContainer.getHeight());

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
                    params.height = (int) (recyclerContainerHeight-mTopContainer.getHeight()
                            -getResources().getDimension(R.dimen.default_margin));
                    recyclerView.setLayoutParams(params);



                }
            });
        }

        mPresenter.getMonthlyEmployeeAttendance(project.get_id(),employee.get_id(),currentYear,currentMonth);
    }

    @Override
    public void showDialog() {
        showProgressDialog();
    }

    @Override
    public void hideDialog() {
        hideProgressDialog();
    }



    @Override
    public void renderPresentCount(String presentCount) {
        tvPresent.setText(presentCount);
    }

    @Override
    public void renderAbsentCount(String absentCount) {
        tvAbsent.setText(absentCount);
    }

    @Override
    public void renderLeaveCount(String leaveCount) {
        tvLeave.setText(leaveCount);
    }

    @Override
    public void renderAdapter(List<Attendance> attendanceList) {
        tvStatus.setText(getDate());
        adapter.updateAdapter(attendanceList);
    }

    @Override
    public void updateState() {
        if (isExpand){
            tvState.setText("Summery");
        }else{
            tvState.setText("Date");
        }

    }

    @Override
    public void onClick(View view) {
        if(view==tvIndicator){
            animate();
        }else if(view==tvPrev) {
            decrease();
            mPresenter.getMonthlyEmployeeAttendance(project.get_id(),employee.get_id(),currentYear,currentMonth);
        }else if(view==tvNext) {
            increase();
            mPresenter.getMonthlyEmployeeAttendance(project.get_id(),employee.get_id(),currentYear,currentMonth);
        }
    }

    private void animate(){
        ValueAnimator animator = ValueAnimator.ofFloat(1,height);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(300);

        final int recyclerinitialY = (int) recyclerView.getY();


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                int value;
                int rotation;
                int recyclerTranslateValue;

                recyclerTranslateValue = (int) ((recyclerTranslater) * valueAnimator.getAnimatedFraction());

                if (isExpand) {
                    value = (int) ((height) * (1 - valueAnimator.getAnimatedFraction()));

                    rotation = (int) ((1 - valueAnimator.getAnimatedFraction()) * 180);
                    if (value == 0) {
                        mBootomContainer.setTranslationY(-initialTranslateY);
                    } else {
                        mBootomContainer.setTranslationY(value);

                    }

                    recyclerView.setY(recyclerinitialY - recyclerTranslateValue);


                } else {
                    value = (int) ((height) * valueAnimator.getAnimatedFraction());
                    rotation = (int) (180 * valueAnimator.getAnimatedFraction());
                    mBootomContainer.setTranslationY(value);
                    recyclerView.setY(recyclerinitialY + recyclerTranslateValue);
                }

                tvIndicator.setRotation(rotation);


                if (valueAnimator.getAnimatedFraction() == 1) {
                    isExpand = !isExpand;
                    //mPresenter.update
                    SingleEmployeeAttendanceActivity.this.updateState();
                }

            }
        });

        animator.start();



    }

    private void increase(){
        currentMonth++;
        if(currentMonth>11){
            currentYear++;
            currentMonth= currentMonth%12;
        }
    }

    private void decrease(){
        currentMonth--;
        if(currentMonth<0){
            currentYear--;
            currentMonth= currentMonth+12;
        }
    }

    private String getDate(){
        String month = getResources().getStringArray(R.array.month_array)[currentMonth];
        return month.concat(" - ").concat(String.valueOf(currentYear));
    }
}
