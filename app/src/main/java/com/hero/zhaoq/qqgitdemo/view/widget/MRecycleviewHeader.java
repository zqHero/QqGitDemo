package com.hero.zhaoq.qqgitdemo.view.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshHeaderLayout;
import com.hero.zhaoq.qqgitdemo.R;

/**
 * author: zhaoqiang
 * date:2017/10/13 / 15:23
 * zhaoqiang:zhaoq_hero@163.com
 */
public class MRecycleviewHeader extends SwipeRefreshHeaderLayout{

    public MRecycleviewHeader(Context context) {
        this(context, null);
    }

    public MRecycleviewHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MRecycleviewHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHeaderHeight = getResources().getDimensionPixelOffset(R.dimen.refresh_header_height);
    }

    private final int mHeaderHeight;

    private ImageView ivArrow;
    private ImageView ivSuccess;
    private TextView tvRefresh;
    private ImageView refreshIng;

    private RotateAnimation rotateDown;
    private RotateAnimation rotateUp;

    private boolean rotated = false;

    //填充布局 ：
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        tvRefresh = (TextView) findViewById(R.id.tvRefresh);
        ivArrow = (ImageView) findViewById(R.id.ivArrow);
        ivSuccess = (ImageView) findViewById(R.id.ivSuccess);
        refreshIng = (ImageView) findViewById(R.id.refreshing_img);

        // 箭头向下
        rotateDown = new RotateAnimation(180,0,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);
        rotateDown.setInterpolator(new LinearInterpolator());
        rotateDown.setDuration(150);
        rotateDown.setFillAfter(true);

        // 箭头向上
        rotateUp = new RotateAnimation(0, 180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateUp.setInterpolator(new LinearInterpolator());
        rotateUp.setDuration(150);
        rotateUp.setFillAfter(true);
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            ivArrow.setVisibility(VISIBLE);
            refreshIng.setVisibility(GONE);
            ivSuccess.setVisibility(GONE);
            if (y > mHeaderHeight) {
                tvRefresh.setText("释放刷新");
                if (!rotated) {
                    ivArrow.clearAnimation();
                    ivArrow.startAnimation(rotateUp);
                    rotated = true;
                }
            } else if (y < mHeaderHeight) {
                if (rotated) {
                    ivArrow.clearAnimation();
                    ivArrow.startAnimation(rotateDown);
                    rotated = false;
                }
                tvRefresh.setText("下拉刷新");
            }
        }
    }

    @Override
    public void onRefresh() {
        ivSuccess.setVisibility(GONE);
        ivArrow.clearAnimation();
        ivArrow.setVisibility(GONE);
        refreshIng.setVisibility(VISIBLE);
        //开启动画 ：
        mStartAnimation();
        tvRefresh.setText("正在刷新");
    }

    //开始 旋转动画
    private void mStartAnimation() {
        refreshIng.setImageResource(R.drawable.recycle_view_header_refreshing);
        AnimationDrawable animationDrawable = (AnimationDrawable)
                refreshIng.getDrawable();
        animationDrawable.start();
    }

    @Override
    public void onComplete() {
        rotated = false;
        ivSuccess.setVisibility(VISIBLE);
        ivArrow.clearAnimation();
        ivArrow.setVisibility(GONE);
        refreshIng.setVisibility(GONE);
        tvRefresh.setText("完成");
    }

    @Override
    public void onReset() {
        rotated = false;
        ivSuccess.setVisibility(GONE);
        ivArrow.clearAnimation();
        ivArrow.setVisibility(GONE);
        refreshIng.setVisibility(GONE);
    }
}
