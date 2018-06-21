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

使用步骤：
====
1、在程序初始化的时候修改网络请求的url

        BaseActivity.url="http://www.baidu.com.cn/";
	
2、新增apistore相当于以前的请求接口的URL

      范例看app的demo
