package com.wuxiaolong.androidmvpsample.demo.presenter;

import com.tpc.androidmvpsample.common.mvp.presenter.BasePresenter;
import com.tpc.androidmvpsample.common.retrofit.ApiCallback;
import com.tpc.androidmvpsample.common.retrofit.ApiClient;
import com.wuxiaolong.androidmvpsample.demo.ApiStores;
import com.wuxiaolong.androidmvpsample.demo.module.MainModel;
import com.wuxiaolong.androidmvpsample.demo.view.MainView;

/**
 * Created by WuXiaolong on 2015/9/23.
 * github:https://github.com/WuXiaolong/
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public class MainPresenter extends BasePresenter<MainView> {

    protected ApiStores apiStores;
    public MainPresenter(MainView view) {
        attachView(view);
        apiStores = ApiClient.retrofit().create(ApiStores.class);

    }

    public void loadDataByRetrofitRxjava(String cityId, final String type) {
        mvpView.showLoading();
        addSubscription(apiStores.loadDataByRetrofitRxJava(cityId),  new ApiCallback<MainModel>() {
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
