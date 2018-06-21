package com.wuxiaolong.androidmvpsample.demo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tpc.androidmvpsample.common.mvp.view.MvpActivity;
import com.wuxiaolong.androidmvpsample.R;
import com.wuxiaolong.androidmvpsample.demo.module.MainModel;
import com.wuxiaolong.androidmvpsample.demo.presenter.MainPresenter;

/**
 * 由Activity/Fragment实现View里方法，包含一个Presenter的引用
 * Created by WuXiaolong on 2015/9/23.
 * github:https://github.com/WuXiaolong/
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public class MainActivity extends MvpActivity<MainPresenter> implements MainView {

    private TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.texts);
        setCenterText(getString(R.string.title));

        initUpdate();
    }
//版本更新
    private void initUpdate() {
//        //最简方式
//        new UpdateAppManager
//                .Builder()
//                //当前Activity
//                .setActivity(this)
//                //更新地址
//                .setUpdateUrl(mUpdateUrl)
//                //实现httpManager接口的对象
//                .setHttpManager(new UpdateAppHttpUtil())
//                .build()
//                .update();
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }


    @Override
    public void getDataSuccess(MainModel model, String type) {
        //接口成功回调
        dataSuccess(model);
    }

    @Override
    public void getDataFail(String msg) {
        toastShow(getString(R.string.net_error));

    }


    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.button2) {//请求接口
            mvpPresenter.loadDataByRetrofitRxjava("101310222", "");

        }
    }

    private void dataSuccess(MainModel model) {
        MainModel.WeatherinfoBean weatherinfo = model.getWeatherinfo();
        String showData = getResources().getString(R.string.city) + weatherinfo.getCity()
                + getResources().getString(R.string.wd) + weatherinfo.getWD()
                + getResources().getString(R.string.ws) + weatherinfo.getWS()
                + getResources().getString(R.string.time) + weatherinfo.getTime();
        text.setText(showData);
    }
}
