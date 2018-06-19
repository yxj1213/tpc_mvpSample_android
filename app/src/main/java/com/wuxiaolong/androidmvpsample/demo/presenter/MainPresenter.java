package com.wuxiaolong.androidmvpsample.demo.presenter;

import com.wuxiaolong.androidmvpsample.common.mvp.presenter.BasePresenter;
import com.wuxiaolong.androidmvpsample.demo.module.MainModel;
import com.wuxiaolong.androidmvpsample.demo.view.MainView;
import com.wuxiaolong.androidmvpsample.common.retrofit.ApiCallback;

/**
 * Created by WuXiaolong on 2015/9/23.
 * github:https://github.com/WuXiaolong/
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView view) {
        attachView(view);
    }

    public void loadDataByRetrofitRxjava(String cityId, final String type) {
        mvpView.showLoading();
        addSubscription(apiStores.loadDataByRetrofitRxJava(cityId), new ApiCallback<MainModel>() {
            @Override
            public void onSuccess(MainModel model) {
                mvpView.getDataSuccess(model,type);
            }

            @Override
            public void onFailure(String msg) {
                mvpView.getDataFail(msg);
            }

            @Override
            public void onFinish() {
                mvpView.hideLoading();
            }
        });
    }
}
