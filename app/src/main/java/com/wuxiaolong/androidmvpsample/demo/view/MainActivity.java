package com.wuxiaolong.androidmvpsample.demo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wuxiaolong.androidmvpsample.R;
import com.wuxiaolong.androidmvpsample.demo.module.MainModel;
import com.wuxiaolong.androidmvpsample.demo.presenter.MainPresenter;
import com.wuxiaolong.androidmvpsample.common.mvp.view.MvpActivity;

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
        text = findViewById(R.id.text);
        setCenterText(getString(R.string.title));
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
