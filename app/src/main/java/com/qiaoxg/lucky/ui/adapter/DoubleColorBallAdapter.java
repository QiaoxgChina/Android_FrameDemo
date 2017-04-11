package com.qiaoxg.lucky.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiaoxg.lucky.R;
import com.qiaoxg.lucky.app.AppConstants;
import com.qiaoxg.lucky.bean.DoubleColorBall;
import com.qiaoxg.lucky.ui.common.ActivityConstants;
import com.qiaoxg.lucky.ui.listener.MySelectBallListener;
import com.qiaoxg.lucky.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;

import static com.qiaoxg.lucky.app.AppConstants.BALL_MAX_SELECTED_NUMBER_RED;

/**
 * Created by admin on 2017/4/1.
 */

public class DoubleColorBallAdapter extends RecyclerView.Adapter<DoubleColorBallAdapter.BallViewHolder> {

    private Context mContext;
    private List<DoubleColorBall> mBallList;
    private int mBallType;//0:red;1:blue
    private ColorStateList mBlueCs, mRedCs, mSelectedCs;
    private MySelectBallListener mySelectBallListener;
    private List<DoubleColorBall> mSelectedRedBalls, mSelectedBlueBalls;

    public DoubleColorBallAdapter(Context context, int ballType, MySelectBallListener listener) {
        this.mContext = context;
        this.mBallType = ballType;
        this.mySelectBallListener = listener;
        mBallList = new ArrayList<>();
        initTypeData();
        mBlueCs = mContext.getResources().getColorStateList(R.color.color_blue_ball);
        mRedCs = mContext.getResources().getColorStateList(R.color.color_red_ball);
        mSelectedCs = mContext.getResources().getColorStateList(R.color.color_FFFFFF);
        mSelectedRedBalls = new ArrayList<>();
        mSelectedBlueBalls = new ArrayList<>();
    }

    private void initTypeData() {
        if (mBallType == AppConstants.BALL_TYPE_BLUE) {
            for (int i = 0; i < AppConstants.BALL_NUMBER_BLUE; i++) {
                DoubleColorBall ball = new DoubleColorBall();
                ball.setNumber(i + 1 + "");
                ball.setSelected(false);
                ball.setType(AppConstants.BALL_TYPE_BLUE);
                mBallList.add(ball);
            }
        } else {
            for (int i = 0; i < AppConstants.BALL_NUMBER_RED; i++) {
                DoubleColorBall ball = new DoubleColorBall();
                ball.setNumber(i + 1 + "");
                ball.setSelected(false);
                ball.setType(AppConstants.BALL_TYPE_RED);
                mBallList.add(ball);
            }
        }
    }

    @Override
    public BallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_double_color_ball, parent, false);
        return new BallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BallViewHolder holder, int position) {
        DoubleColorBall ball = mBallList.get(position);
        holder.titleTv.setText(ball.getNumber());
        holder.parentView.setTag(ball);
        if (ball.getType() == AppConstants.BALL_TYPE_BLUE) {
            if (ball.isSelected()) {
                holder.titleTv.setTextColor(mSelectedCs);
                holder.titleTv.setBackgroundResource(R.drawable.shape_bg_blueball_selected);
            } else {
                holder.titleTv.setTextColor(mBlueCs);
                holder.titleTv.setBackgroundResource(R.drawable.shape_bg_ball_normal);
            }
        } else {
            if (ball.isSelected()) {
                holder.titleTv.setTextColor(mSelectedCs);
                holder.titleTv.setBackgroundResource(R.drawable.shape_bg_redball_selected);
            } else {
                holder.titleTv.setTextColor(mRedCs);
                holder.titleTv.setBackgroundResource(R.drawable.shape_bg_ball_normal);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mBallList.size();
    }

    public class BallViewHolder extends RecyclerView.ViewHolder {

        private View parentView;
        private TextView titleTv;

        public BallViewHolder(View v) {
            super(v);
            titleTv = (TextView) v.findViewById(R.id.doubleColorBall_tv_number);
            parentView = v.findViewById(R.id.doubleColorBall_rl_parentView);
            parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DoubleColorBall ball = (DoubleColorBall) v.getTag();
                    if (ball.getType() == AppConstants.BALL_TYPE_BLUE) {
                        if (mSelectedBlueBalls.contains(ball)) {
                            mSelectedBlueBalls.remove(ball);
                        } else {
                            mSelectedBlueBalls.add(ball);
                        }
                        mySelectBallListener.onSelectBlueBallChanged(mSelectedBlueBalls);
                    } else {
                        if (mSelectedRedBalls.contains(ball)) {
                            mSelectedRedBalls.remove(ball);
                        } else {
                            if (mSelectedRedBalls.size() >= BALL_MAX_SELECTED_NUMBER_RED) {
                                UIHelper.showToast("最多投注16个红球");
                                return;
                            } else {
                                mSelectedRedBalls.add(ball);
                            }
                        }
                        mySelectBallListener.onSelectRedBallChanged(mSelectedRedBalls);
                    }
                    ball.setSelected(!ball.isSelected());
                    notifyDataSetChanged();
                }
            });
        }
    }

}
