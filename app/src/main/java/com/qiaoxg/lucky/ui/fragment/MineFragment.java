package com.qiaoxg.lucky.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiaoxg.lucky.R;
import com.qiaoxg.lucky.app.BaseFragment;
import com.qiaoxg.lucky.app.MyApplication;
import com.qiaoxg.lucky.bean.UserBean;
import com.qiaoxg.lucky.data.SharedPrefercencesUtils;
import com.qiaoxg.lucky.ui.activity.LoginActivity;
import com.qiaoxg.lucky.utils.FileUtils;
import com.qiaoxg.lucky.utils.UIHelper;

import java.io.File;

/**
 * Created by admin on 2017/3/31.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "MineFragment";

    private static final int IMAGE_PICK_REQUEST = 1;
    private static final int CROP_REQUEST = 2;

    private TextView mTitleTv, mLogoutTv, mNickNameTv;
    private View mFragmentView;
    private RelativeLayout mInfoRl;
    private ImageView mHeaderIv;

    private UserBean mCurrentUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
        return mFragmentView;
    }

    private void initData() {
        mCurrentUser = MyApplication.getCurrLoginUser();
    }

    private void initView() {
        mNickNameTv = (TextView) mFragmentView.findViewById(R.id.mine_tv_nickName);
        mHeaderIv = (ImageView) mFragmentView.findViewById(R.id.mine_iv_header);
        mInfoRl = (RelativeLayout) mFragmentView.findViewById(R.id.mine_rl_info);
        mInfoRl.setOnClickListener(this);
        mTitleTv = (TextView) mFragmentView.findViewById(R.id.title_tv);
        mTitleTv.setText("我的");

        mLogoutTv = (TextView) mFragmentView.findViewById(R.id.mine_logout_btn);
        mLogoutTv.setOnClickListener(this);

        if (mCurrentUser != null && !TextUtils.isEmpty(mCurrentUser.getIconUrl())) {
            String name = mCurrentUser.getNickName();
            if (TextUtils.isEmpty(name)){
                name= mCurrentUser.getUsername();
            }
            mNickNameTv.setText(name);
            ImageLoader.getInstance().displayImage(mCurrentUser.getIconUrl(), mHeaderIv);
        } else {
            Bitmap bitmap = FileUtils.getBitmapByLocalPath(FileUtils.getLocalUserHeaderTempPath());
            if (bitmap != null) {
                mHeaderIv.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_rl_info:
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, IMAGE_PICK_REQUEST);
                break;
            case R.id.mine_logout_btn:
                SharedPrefercencesUtils.clearLoginUserInfo();
                UIHelper.gotoActivity(getActivity(), LoginActivity.class);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_PICK_REQUEST) {
                Uri uri = data.getData();
                startImageCrop(uri, 100, 100, CROP_REQUEST);
            } else if (requestCode == CROP_REQUEST) {
                Bundle extras = data.getExtras();
                Bitmap bitmap = extras.getParcelable("data");
                final String path = FileUtils.saveCropAvatar(bitmap);
                Log.e(TAG, "onActivityResult: path is " + path);
                mHeaderIv.setImageBitmap(bitmap);
            }
        }
    }

    public Uri startImageCrop(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        String outputPath = FileUtils.getLocalTempDir();
        Uri outputUri = Uri.fromFile(new File(outputPath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", false); // face detection
        startActivityForResult(intent, requestCode);
        return outputUri;
    }

}
