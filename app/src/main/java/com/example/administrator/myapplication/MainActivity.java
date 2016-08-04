package com.example.administrator.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.common.base.BaseActivity;
import com.example.administrator.myapplication.testpackage.NewsFragment;
import com.example.administrator.myapplication.viewhelper.DragLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nineoldandroids.view.ViewHelper;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private SimpleDraweeView userPhoto;
    private TextView userName;
    private TextView userMail;
    private ListView lvTab;
    private SimpleDraweeView titleIcon;
    private TextView titleName;
    private FrameLayout frameContainer;
    private DragLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate", "onCreate");
    }

    @Override
    protected void setMainContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        findView();
        initDragLayout();
        setView();
    }

    private void findView() {
        userPhoto = (SimpleDraweeView) findViewById(R.id.user_photo);
        userName = (TextView) findViewById(R.id.user_name);
        userMail = (TextView) findViewById(R.id.user_mail);
        lvTab = (ListView) findViewById(R.id.lv);
        titleIcon = (SimpleDraweeView) findViewById(R.id.title_icon);
        titleName = (TextView) findViewById(R.id.tv_title);
        frameContainer = (FrameLayout) findViewById(R.id.frame_container);
        dl = (DragLayout) findViewById(R.id.dl);
        titleIcon.setOnClickListener(this);
    }

    private void initDragLayout() {
        dl = (DragLayout) findViewById(R.id.dl);
        dl.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {
//                lv.smoothScrollToPosition(new Random().nextInt(30));
            }

            @Override
            public void onClose() {
//                shake();
            }

            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(titleIcon, 1 - percent);
            }
        });
    }

    private void setView() {
        replaceFragment(R.id.frame_container, new NewsFragment());
        Uri uri = Uri.parse("res:// /" + R.mipmap.default_face);
        titleIcon.setImageURI(uri);
        userPhoto.setImageURI(uri);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_icon:
                dl.open();
                break;
        }
    }
}
