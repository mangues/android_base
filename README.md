#LifeCircleApp
>安卓基础开发模板,利用了MVP模型

##网络
>利用okhttp，RxAndroid，retrofit 构建

##控件绑定
>利用butterknife进行绑定

##消息传递
>利用了eventbus

##日志
>com.orhanobut:logger

##IOC
>dagger

##cache
>1.全局缓存，**GlobalVariables**类。内存，文件双重缓存，当内存不够时，不影响程序的正常试用。
>2.SecureSharedPreferences，获取安全的SharePreferences，采用RSA 加salt及向量的 对key与value一起加密的SharePreferences


##token、sign 认证
>用户token,和url 的sign认证


##定位 高德定位方法
```
GaoDeMapLocation.getInstance().startLocation(new GaoDeLocationListener() {
             @Override
             public void location(LocationInfo locationInfo) {
                 circlePresenter.circleList(locationInfo);
 
             }
 
             @Override
             public void error(AMapLocation location) {
                 showToast(location.getErrorInfo());
             }
         });
```
