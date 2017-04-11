package com.qiaoxg.lucky.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.qiaoxg.lucky.R;
import com.qiaoxg.lucky.ui.fragment.DiscoveryFragment;
import com.qiaoxg.lucky.ui.fragment.LotteryFragment;
import com.qiaoxg.lucky.ui.fragment.MineFragment;
import com.qiaoxg.lucky.ui.fragment.ResultFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final int FRAGMENT_N = 4;

    public static final int[] tabsNormalBackIds = new int[]{R.drawable.tabbar_lottery,
            R.drawable.tabbar_result, R.drawable.tabbar_discover, R.drawable.tabbar_mine};
    public static final int[] tabsActiveBackIds = new int[]{R.drawable.tabbar_lottery_active,
            R.drawable.tabbar_result_active, R.drawable.tabbar_discover_active,
            R.drawable.tabbar_me_active};
    private static final String FRAGMENT_TAG_LOTTERY = "lottery";
    private static final String FRAGMENT_TAG_DISCONVERY = "disconvery";
    private static final String FRAGMENT_TAG_MINE = "mine";
    private static final String FRAGMENT_TAG_RESULT = "result";
    private static final String[] fragmentTags = new String[]{FRAGMENT_TAG_LOTTERY, FRAGMENT_TAG_RESULT, FRAGMENT_TAG_DISCONVERY,
            FRAGMENT_TAG_MINE};

    private Button mDisconverBtn, mLotteryBtn, mResultBtn, mMineBtn;
    private View mFragmentContainer;
    private MineFragment mMineFragment;
    private DiscoveryFragment mDiscoveryFragment;
    private ResultFragment mResultFragment;
    private LotteryFragment mLotteryFragment;
    private Button[] tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        init();
        mLotteryBtn.performClick();
    }

    private void init() {
        tabs = new Button[]{mLotteryBtn, mResultBtn,mDisconverBtn, mMineBtn};
    }

    private void findView() {
        mDisconverBtn = (Button) findViewById(R.id.main_btn_discover);
        mLotteryBtn = (Button) findViewById(R.id.main_btn_lottery);
        mResultBtn = (Button) findViewById(R.id.main_btn_result);
        mMineBtn = (Button) findViewById(R.id.main_btn_mine);
        mFragmentContainer = findViewById(R.id.main_rl_fragment_container);
    }

    /**
     * 处理tabar按钮的点击事件，切换fragment
     * @param v
     */
    public void onTabSelect(View v) {
        int id = v.getId();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragments(manager, transaction);
        setNormalBackgrounds();
        if (id == R.id.main_btn_discover) {
            if (mDiscoveryFragment == null) {
                mDiscoveryFragment = new DiscoveryFragment();
                transaction.add(R.id.main_rl_fragment_container, mDiscoveryFragment, FRAGMENT_TAG_LOTTERY);
            }
            transaction.show(mDiscoveryFragment);
        } else if (id == R.id.main_btn_lottery) {
            if (mLotteryFragment == null) {
                mLotteryFragment = new LotteryFragment();
                transaction.add(R.id.main_rl_fragment_container, mLotteryFragment, FRAGMENT_TAG_DISCONVERY);
            }
            transaction.show(mLotteryFragment);
        } else if (id == R.id.main_btn_mine) {
            if (mMineFragment == null) {
                mMineFragment = new MineFragment();
                transaction.add(R.id.main_rl_fragment_container, mMineFragment, FRAGMENT_TAG_MINE);
            }
            transaction.show(mMineFragment);
        } else if (id == R.id.main_btn_result) {
            if (mResultFragment == null) {
                mResultFragment = new ResultFragment();
                transaction.add(R.id.main_rl_fragment_container, mResultFragment, FRAGMENT_TAG_RESULT);
            }
            transaction.show(mResultFragment);
        }
        int pos;
        for (pos = 0; pos < FRAGMENT_N; pos++) {
            if (tabs[pos] == v) {
                break;
            }
        }
        transaction.commit();
        setTopDrawable(tabs[pos], tabsActiveBackIds[pos]);
    }

    private void setNormalBackgrounds() {
        for (int i = 0; i < tabs.length; i++) {
            Button v = tabs[i];
            setTopDrawable(v, tabsNormalBackIds[i]);
        }
    }

    private void setTopDrawable(Button v, int resId) {
        v.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(resId), null, null);
    }

    private void hideFragments(FragmentManager fragmentManager, FragmentTransaction transaction) {
        for (int i = 0; i < fragmentTags.length; i++) {
            Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags[i]);
            if (fragment != null && fragment.isVisible()) {
                transaction.hide(fragment);
            }
        }
    }
}
