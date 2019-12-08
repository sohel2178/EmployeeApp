package com.forbitbd.employeeman.ui.employee.worker.singleWorkerAttendance;


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
import com.forbitbd.employeeman.models.Worker;

import java.util.Calendar;
import java.util.List;

public class SingleWorkerAttendanceActivity extends PrebaseActivity
        implements SingleWorkerAttendanceContract.View, View.OnClickListener {

    private SingleWorkerAttendancePresenter mPresenter;

    private Project project;
    private Worker worker;

    private SingleWorkerAttendaceAdapter adapter;

    private int currentMonth,currentYear;
    private TextView tvStatus,tvPresent,tvOvertime,tvState,tvNext,tvPrev;

    private CardView mCardTop,mCardBottom;
    private RelativeLayout mBootomContainer,mTopContainer,mRecyclerContainer;
    private ImageView tvIndicator;

    private int height,initialTranslateY,recyclerTranslater;
    private boolean isExpand = false;

    private RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_worker_attendance);

        mPresenter = new SingleWorkerAttendancePresenter(this);

        this.project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);
        this.worker = (Worker) getIntent().getSerializableExtra(Constant.WORKER);

        this.adapter = new SingleWorkerAttendaceAdapter(this);

        this.currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        this.currentYear = Calendar.getInstance().get(Calendar.YEAR);

        initView();
    }

    private void initView() {
        setupToolbar(R.id.toolbar);
        getSupportActionBar().setTitle(worker.getName().concat(" Attendance"));

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
        tvOvertime = findViewById(R.id.over_time);
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

        mPresenter.getMonthlyAttendance(project.get_id(),worker.get_id(),currentYear,currentMonth);
    }

    @Override
    public void onClick(View view) {

        if(view==tvIndicator){
            animate();
        }else if(view==tvPrev){
            decrease();
            mPresenter.getMonthlyAttendance(project.get_id(),worker.get_id(),currentYear,currentMonth);
        }else if(view==tvNext){
            increase();
            mPresenter.getMonthlyAttendance(project.get_id(),worker.get_id(),currentYear,currentMonth);
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
                    SingleWorkerAttendanceActivity.this.updateState();
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

    @Override
    public void showDialog() {
        showProgressDialog();
    }

    @Override
    public void hideDialog() {
        hideProgressDialog();
    }

    @Override
    public void renderOvertime(String overtimeTotal) {
        tvOvertime.setText(overtimeTotal);
    }

    @Override
    public void renderPresentCount(String presentCount) {
        tvPresent.setText(presentCount);
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

    private String getDate(){
        String month = getResources().getStringArray(R.array.month_array)[currentMonth];
        return month.concat(" - ").concat(String.valueOf(currentYear));
    }
}
