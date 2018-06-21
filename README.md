Integration
====
How to
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file
------- 

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency
------- 

	dependencies {
	        implementation 'com.github.yxj1213:tpc_mvpSample_android:V1.1'
	}

集成步骤：
1、添加依赖
2、初始化网络请求的url
3、apistore相当于以前的URL


//杨小金新增========开始
1、mvp模式使用
2、retrofit+rxjava的网络请求框架的封装
3、沉浸式状态栏
4、统一标题toolbar封装


新加功能：
1、图片加载
2、网络请求加token、无网络时展示的界面、崩溃界面。
3、列表和适配器
4、自定义的列表（recyclerview、gridview）
5、dialog使用（仿ios）
6、版本更新
7、eventbus、banner、通用的item（个人中心界面）
8、欢迎页、引导页、登录


单独功能：
百度地图、popwindow、列表侧滑、图表、coordinatorLayout、通讯录、日历、手势解锁、表格、直播、全局修改字体、分享、支付

//杨小金新增========结束





> MVP + Retrofit + RxJava2 实践小结，此 Sample 最初是 Android MVP 示例，后来融合 Retrofit 和 RxJava2，供参考。


# 效果预览
![](http://7q5c2h.com1.z0.glb.clouddn.com/mvp_retrofit_rxjava.jpg)

# 详见博客
[Android MVP+Retrofit+RxJava实践小结](http://wuxiaolong.me/2016/06/12/mvpRetrofitRxjava/)

# 推荐阅读
1. [Android MVP 实例](http://wuxiaolong.me/2015/09/23/AndroidMVPSample/)

1. [Android Retrofit 2.0 使用-补充篇](http://wuxiaolong.me/2016/06/18/retrofits/)

1. [Android Retrofit 2.0使用](http://wuxiaolong.me/2016/01/15/retrofit/)

1. [RxJava](http://wuxiaolong.me/2016/01/18/rxjava/)

1. [RxBus](http://wuxiaolong.me/2016/04/07/rxbus/)


