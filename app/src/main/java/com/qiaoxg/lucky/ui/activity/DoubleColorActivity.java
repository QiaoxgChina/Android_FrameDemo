package com.qiaoxg.lucky.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiaoxg.lucky.R;
import com.qiaoxg.lucky.app.AppConstants;
import com.qiaoxg.lucky.app.BaseActivity;
import com.qiaoxg.lucky.bean.DoubleColorBall;
import com.qiaoxg.lucky.ui.adapter.DoubleColorBallAdapter;
import com.qiaoxg.lucky.ui.common.ActivityConstants;
import com.qiaoxg.lucky.ui.common.DBASpaceItemDecoration;
import com.qiaoxg.lucky.ui.listener.MySelectBallListener;
import com.qiaoxg.lucky.utils.LotteryUtils;
import com.qiaoxg.lucky.utils.UIHelper;

import java.util.List;

import static com.qiaoxg.lucky.app.AppConstants.BALL_MIN_SELECTED_NUMBER_RED;
import static com.qiaoxg.lucky.app.AppConstants.HOW_MUCH_PER_NOTE;

public class DoubleColorActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener, GestureDetector.OnGestureListener, MySelectBallListener {

    private static final int MSG_UPDATE_NOTE_INFO = 0;

    private static final String TAG = "DoubleColorActivity";

    private TextView mTitleTv, mHidenLayout, mTotalNumberTv, mTotalMoneyTv;
    private DoubleColorBallAdapter mActiveRedBallAdapter, mActiveBlueBallAdapter;
    private RecyclerView mActiveRedRv, mActiveBlueRv;
    private RelativeLayout mActiveSRL;

    private GestureDetector mGestureDetector;

    private RelativeLayout.LayoutParams mActiveRlLayoutParam;
    private View mEmptyLayout;

    private int mHidenLayoutHeight = 0;

    private long mTotalNumber;//总共的投注数
    private List<DoubleColorBall> mSelectedRedBall;
    private List<DoubleColorBall> mSelectedBlueBall;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_NOTE_INFO:
                    updateNoteInfo();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_color);
        initTitleBar();
        initView();
        initAdapter();
        mGestureDetector = new GestureDetector(this);
        mGestureDetector.setIsLongpressEnabled(true);
        mActiveRlLayoutParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void initAdapter() {
        mActiveRedBallAdapter = new DoubleColorBallAdapter(this, AppConstants.BALL_TYPE_RED, this);
        mActiveRedRv.setAdapter(mActiveRedBallAdapter);
        mActiveBlueBallAdapter = new DoubleColorBallAdapter(this, AppConstants.BALL_TYPE_BLUE, this);
        mActiveBlueRv.setAdapter(mActiveBlueBallAdapter);
    }

    private void initView() {
        GridLayoutManager glm = new GridLayoutManager(this, 7);
        GridLayoutManager glm2 = new GridLayoutManager(this, 7);
        DBASpaceItemDecoration space = new DBASpaceItemDecoration(20);
        mActiveRedRv = (RecyclerView) findViewById(R.id.active_rv_redBall);
        mActiveRedRv.setLayoutManager(glm);
        mActiveRedRv.addItemDecoration(space);
        mActiveBlueRv = (RecyclerView) findViewById(R.id.active_rv_blueBall);
        mActiveBlueRv.setLayoutManager(glm2);
        mActiveBlueRv.addItemDecoration(space);

        mTotalMoneyTv = (TextView) findViewById(R.id.doubleColor_tv_money);
        mTotalNumberTv = (TextView) findViewById(R.id.doubleColor_tv_num);

        mActiveSRL = (RelativeLayout) findViewById(R.id.doubleColor_rl_activeLayout);
        mEmptyLayout = findViewById(R.id.emptyLayout);
//        mEmptyLayout.setOnTouchListener(this);
//        mEmptyLayout.setLongClickable(true);

        mHidenLayout = (TextView) findViewById(R.id.doubleColor_rl_hidenLayout);
        ViewTreeObserver vto = mHidenLayout.getViewTreeObserver();

        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mHidenLayoutHeight = mHidenLayout.getMeasuredHeight();
                Log.e(TAG, "initView: hideLayout height is " + mHidenLayoutHeight);
                return true;
            }
        });
    }


    private void initTitleBar() {
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mTitleTv.setText(getIntent().getStringExtra(ActivityConstants.PARAM_ACTIVITY_TITLE));
        ImageButton backIb = (ImageButton) findViewById(R.id.title_back);
        UIHelper.showWidegt(backIb, true);
        backIb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                goBack(DoubleColorActivity.this);
                break;
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.e(TAG, "onDown: y is " + (int) e.getY());
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.e(TAG, "onSingleTapUp: y is " + (int) e.getY());
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        int y1 = (int) e1.getY();
        int y2 = (int) e2.getY();
        Log.e(TAG, "========onScroll: y1 is " + y1 + " And y2 is " + y2 + " 移动的距离 ： " + (y2 - y1));
        int position = y2 - y1;
        if (y2 == 0) {

        }
        if (isCanPullDown && position > 0) {
            setActiveLayoutPosition(position);
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    private int mLastPosition = 140;
    private boolean isCanPullDown = true;

    private void setActiveLayoutPosition(int y) {
        if ((y + 140) <= 140) {
            mLastPosition = 140;
        } else if (y >= mHidenLayoutHeight) {
            mLastPosition = mHidenLayoutHeight + 150;
            isCanPullDown = false;
        } else {
            mLastPosition = mLastPosition + y;
        }
        mActiveRlLayoutParam.setMargins(0, mLastPosition, 0, 0);
        mActiveSRL.setLayoutParams(mActiveRlLayoutParam);
    }

    public void updateNoteInfo() {
        if (mSelectedBlueBall == null || mSelectedRedBall == null){
            return;
        }
        int redBallSize = mSelectedRedBall.size();
        int blueBallSize = mSelectedBlueBall.size();
        UIHelper.showToast("red : " + redBallSize + " / blue : " + blueBallSize);
        if (blueBallSize < 0 || redBallSize < BALL_MIN_SELECTED_NUMBER_RED) {
            return;
        }
        if (redBallSize == BALL_MIN_SELECTED_NUMBER_RED) {
            mTotalNumber = blueBallSize;
        } else {
            mTotalNumber = LotteryUtils.doubleColorOrdinary(redBallSize, blueBallSize);
        }
        mTotalNumberTv.setText(mTotalNumber + " 注");
        mTotalMoneyTv.setText(mTotalNumber * HOW_MUCH_PER_NOTE + " 元");
    }

    @Override
    public void onSelectRedBallChanged(List<DoubleColorBall> numberList) {
        this.mSelectedRedBall = numberList;
        mHandler.sendEmptyMessage(MSG_UPDATE_NOTE_INFO);
    }

    @Override
    public void onSelectBlueBallChanged(List<DoubleColorBall> numberList) {
        this.mSelectedBlueBall = numberList;
        mHandler.sendEmptyMessage(MSG_UPDATE_NOTE_INFO);
    }
}
