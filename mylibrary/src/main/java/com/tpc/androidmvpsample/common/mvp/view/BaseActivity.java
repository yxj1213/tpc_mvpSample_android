package com.tpc.androidmvpsample.common.mvp.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tpc.androidmvpsample.R;
import com.tpc.androidmvpsample.common.util.ToolBarUtil;
import com.wuxiaolong.androidutils.library.LogUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;


public abstract class BaseActivity extends AppCompatActivity {
    public Activity mActivity;
    private CompositeDisposable mCompositeDisposable;
    private List<Call> calls;
    private ToolBarUtil mToolBarHelper;
    public Toolbar toolbar;
    public static String url="http://www.weather.com.cn/";

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mActivity = this;
        //实现沉浸式主题风格
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                   /* *//**//*| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION*//**//**/
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//注：这里注释掉 View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION 是因为在华为手机上键盘键在屏幕上，如果放开，那么会被虚拟按键遮盖住开发的界面
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else {
            //实现沉浸式主题风格
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setTranslucentStatus(true);
            }
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            //设置沉浸的颜色
            tintManager.setStatusBarTintResource(R.color.transparent);
        }

        if (isShowToolBar()) {
            mToolBarHelper = new ToolBarUtil(this, layoutResID);
            toolbar = mToolBarHelper.getToolBar();
            setContentView(mToolBarHelper.getContentView());
            //*把 toolbar 设置到Activity 中*//*
            setSupportActionBar(toolbar);
            //*自定义的一些操作*//*
            onCreateCustomToolBar(toolbar);
        } else {
            super.setContentView(layoutResID);
        }
    }

    /**
     * 向子类提供一个可调用的方法，来控制是否显示ToolBar
     * true:代表子类用的就是弗雷的ToolBar
     * false:意思就是子类不需要父类的ToolBar,子类自己去实现吧
     */
    protected boolean isShowToolBar() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //实现沉浸式主题风格
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        //设置沉浸的颜色
        tintManager.setStatusBarTintResource(R.color.transparent);
    }

    //设置中间的文字
    public void setCenterText(String text) {
        setTitle("");
        mToolBarHelper.setCenterText(text);
    }

    //设置右边的文字
    public void setRightText(String text) {
        mToolBarHelper.setRightText(text);
    }

    //设置右边的图片
    public void setRightImg(int resId) {
        mToolBarHelper.setRightImg(resId);
    }

    //设置右侧文字单击事件
    public void setRightTextClick(View.OnClickListener rightTextClick) {
        mToolBarHelper.setRightTextClick(rightTextClick);
    }

    //设置图片右侧单击事件
    public void setRightImgClick(View.OnClickListener rightImgClick) {
        mToolBarHelper.setRightImgClick(rightImgClick);
    }

    //设置toolbar关闭界面
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
    }

    @TargetApi(19)
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        mActivity = this;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        mActivity = this;
    }


    @Override
    protected void onDestroy() {
        callCancel();
        onUnsubscribe();
        super.onDestroy();
    }

    public void addCalls(Call call) {
        if (calls == null) {
            calls = new ArrayList<>();
        }
        calls.add(call);
    }

    private void callCancel() {
        if (calls != null && calls.size() > 0) {
            for (Call call : calls) {
                if (!call.isCanceled())
                    call.cancel();
            }
            calls.clear();
        }
    }

    /**
     * observable 被观察者
     */
    public <T> void addSubscription(Observable<T> observable, DisposableObserver<T> observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(observer);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);//实现订阅关系
    }

    public void addSubscription(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void onUnsubscribe() {
        LogUtil.d("onUnSubscribe");
        //取消注册，以避免内存泄露
        if (mCompositeDisposable != null)
            mCompositeDisposable.dispose();
    }

    public void toastShow(int resId) {
        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
    }

    public void toastShow(String resId) {
        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
    }

    public ProgressDialog progressDialog;

    public ProgressDialog showProgressDialog() {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("加载中");
        progressDialog.show();
        return progressDialog;
    }

    public ProgressDialog showProgressDialog(CharSequence message) {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            // progressDialog.hide();会导致android.view.WindowLeaked
            progressDialog.dismiss();
        }
    }

}
