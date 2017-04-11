package com.qiaoxg.lucky.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiaoxg.lucky.R;
import com.qiaoxg.lucky.app.BaseFragment;

import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by admin on 2017/3/31.
 */

public class ResultFragment extends BaseFragment {

    private static final String TAG = "ResultFragment";

    private TextView mTitleTv;
    private View mFragmentView;

    private RecyclerView mLotteryListRv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_result, container, false);
        initView();
        return mFragmentView;
    }

    private void initView(){
        mTitleTv = (TextView) mFragmentView.findViewById(R.id.title_tv);
        mTitleTv.setText("开奖");

//        mLotteryListRv = (RecyclerView) mFragmentView.findViewById(R.id.result_rv_lotteryList);
//        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
//        manager.setOrientation(VERTICAL);
//        mLotteryListRv.setLayoutManager(manager);
    }
}
