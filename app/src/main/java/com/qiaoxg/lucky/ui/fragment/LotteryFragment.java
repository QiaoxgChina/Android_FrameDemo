package com.qiaoxg.lucky.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiaoxg.lucky.R;
import com.qiaoxg.lucky.app.BaseFragment;
import com.qiaoxg.lucky.bean.ADInfo;
import com.qiaoxg.lucky.ui.adapter.LotteryTypeAdapter;
import com.qiaoxg.lucky.utils.ImageLoaderUtils;
import com.qiaoxg.lucky.utils.ViewFactory;
import com.qiaoxg.lucky.ui.common.adcycleviewpager.CycleViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/3/31.
 */

public class LotteryFragment extends BaseFragment {

    private static final String TAG = "LotteryFragment";

    private TextView mTitleTv;
    private View mFragmentView;

    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private CycleViewPager cycleViewPager;
    private RecyclerView mTypeRv;
    private LotteryTypeAdapter mTypeAdapter;

    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_lottery, container, false);
        initView();
        initTypeAAdapter();
        initialize();
        return mFragmentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initTypeAAdapter() {
        mTypeAdapter = new LotteryTypeAdapter(getActivity());
        mTypeRv.setAdapter(mTypeAdapter);
    }

    private void initView() {
        mTypeRv = (RecyclerView) mFragmentView.findViewById(R.id.lottery_rv_type);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(),3);//每行显示2个item
        mTypeRv.setLayoutManager(linearLayoutManager);

        mTitleTv = (TextView) mFragmentView.findViewById(R.id.title_tv);
        mTitleTv.setText("大厅");

        //防止scrollView自动滑动到底部
        View parentView = mFragmentView.findViewById(R.id.parent_ll_layout);
        parentView.setFocusable(true);
        parentView.setFocusableInTouchMode(true);
        parentView.requestFocus();
    }

    private void initialize() {

        cycleViewPager = (CycleViewPager) getActivity().getFragmentManager().findFragmentById(R.id.fragment_cycle_viewpager_content);

        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i);
            infos.add(info);
        }

        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(getActivity(), infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(getActivity(), infos.get(i).getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(getActivity(), infos.get(0).getUrl()));

        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);

        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, mAdCycleViewListener);
        //设置轮播
        cycleViewPager.setWheel(true);

        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(2000);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
                Toast.makeText(getActivity(),
                        "position-->" + info.getContent(), Toast.LENGTH_SHORT)
                        .show();
            }

        }

    };

}
