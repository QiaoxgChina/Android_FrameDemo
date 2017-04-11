package com.qiaoxg.lucky.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiaoxg.lucky.R;
import com.qiaoxg.lucky.bean.LotteryType;
import com.qiaoxg.lucky.ui.activity.DoubleColorActivity;
import com.qiaoxg.lucky.ui.common.ActivityConstants;
import com.qiaoxg.lucky.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/4/1.
 */

public class LotteryTypeAdapter extends RecyclerView.Adapter<LotteryTypeAdapter.TypeViewHolder> {

    private Context mContext;
    private List<LotteryType> lotteryTypeList;

    public LotteryTypeAdapter(Context context) {
        this.mContext = context;
        initTypeData();
    }

    private void initTypeData() {
        if (lotteryTypeList == null) {
            lotteryTypeList = new ArrayList<>();
            LotteryType type = new LotteryType();
            type.setTitle("双色球");
            type.setPicIndx(R.drawable.shuangseqiu);
            type.setContent("等待开奖");
            lotteryTypeList.add(type);

            LotteryType type1 = new LotteryType();
            type1.setTitle("大乐透");
            type1.setPicIndx(R.drawable.daletou);
            type1.setContent("等待开奖");
            lotteryTypeList.add(type1);

            LotteryType type2 = new LotteryType();
            type2.setTitle("竞猜篮球");
            type2.setPicIndx(R.drawable.jingcailanqiu);
            type2.setContent("等待开奖");
            lotteryTypeList.add(type2);

            LotteryType type3 = new LotteryType();
            type3.setTitle("七星彩");
            type3.setPicIndx(R.drawable.qixingcai);
            type3.setContent("等待开奖");
            lotteryTypeList.add(type3);

            LotteryType type4 = new LotteryType();
            type4.setTitle("竞技场");
            type4.setPicIndx(R.drawable.jingjichang);
            type4.setContent("等待开奖");
            lotteryTypeList.add(type4);

            LotteryType type5 = new LotteryType();
            type5.setTitle("胜负关");
            type5.setPicIndx(R.drawable.shengfuguan);
            type5.setContent("等待开奖");
            lotteryTypeList.add(type5);

            LotteryType type6 = new LotteryType();
            type6.setTitle("十一选五");
            type6.setPicIndx(R.drawable.shiyixuanwu);
            type6.setContent("等待开奖");
            lotteryTypeList.add(type6);

            LotteryType type7 = new LotteryType();
            type7.setTitle("体育资讯");
            type7.setPicIndx(R.drawable.tiyuzixun);
            type7.setContent("等待开奖");
            lotteryTypeList.add(type7);

            LotteryType type8 = new LotteryType();
            type8.setTitle("足球单场");
            type8.setPicIndx(R.drawable.zuqiudanchang);
            type8.setContent("等待开奖");
            lotteryTypeList.add(type8);
        }
    }

    @Override
    public TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_lottery_type_tow, null);
        return new TypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TypeViewHolder holder, int position) {
        LotteryType type = lotteryTypeList.get(position);
//        holder.contentTv.setText(type.getContent());
        holder.headerIv.setImageResource(type.getPicIndx());
        holder.titleTv.setText(type.getTitle());
        holder.parentView.setTag(type);
    }

    @Override
    public int getItemCount() {
        return lotteryTypeList.size();
    }

    public class TypeViewHolder extends RecyclerView.ViewHolder {

        private View parentView;
        private TextView titleTv, contentTv;
        private ImageView headerIv;

        public TypeViewHolder(View v) {
            super(v);
            titleTv = (TextView) v.findViewById(R.id.lottery_tv_title);
//            contentTv = (TextView) v.findViewById(R.id.lottery_tv_content);
            headerIv = (ImageView) v.findViewById(R.id.lottery_iv_header);
            parentView = v.findViewById(R.id.lottery_rl_parentView);
            parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LotteryType type = (LotteryType) v.getTag();
                    if (type.getTitle().equals("双色球")) {
                        Intent i = new Intent(mContext, DoubleColorActivity.class);
                        i.putExtra(ActivityConstants.PARAM_ACTIVITY_TITLE, type.getTitle());
                        mContext.startActivity(i);
                    } else {
                        UIHelper.showToast(type.getTitle());
                    }
                }
            });
        }
    }

}
