 # x5webview-cordova-plugin

该项目 Fork https://github.com/runner525/x5webview-cordova-plugin.git

做了如下调整:

* 适配cordova 7.1.0 及其以上Cordova版本
* 调整WebView布局提高滑动感应度
* 调整Html文件上传响应代码.
* 增加Application启动类,自动根据Tbs服务调整X5版本

## 安装
```
cordova plugin add https://github.com/zhangjianying/x5webview-cordova-plugin.git
```

## X5 介绍
腾讯浏览服务（TBS，Tencent Browsing Service）整合腾讯底层浏览技术和腾讯平台资源及能力，提供整体浏览服务解决方案。腾讯浏览服务面向应用开发商和广大开发者，提供浏览增强，内容框架，广告体系，H5游戏分发，大数据等服务，能够帮助应用开发商大幅改善应用体验，有效提升开发，运营，商业化的效率。

腾讯浏览服务目前已接入超过1000款App，涵盖20个多个行业和领域；目前平台日活跃用户超5亿；日均处理访问需求超 110亿次，累计处理移动页面需求已接近57000亿次。腾讯浏览服务的发布同时也意味着腾讯首次对外开放万亿级数据能力为行业伙伴提供服务。

 ![X5介绍](https://x5.tencent.com/tbs/img/article/tbs4.png)
 ![X5介绍](https://x5.tencent.com/tbs/img/article/tbs10.png)

## X5优点
### 1. TBS(腾讯浏览服务)的优势

1) 速度快：相比系统webview的网页打开速度有30+%的提升；

2) 省流量：使用云端优化技术使流量节省20+%；

3) 更安全：安全问题可以在24小时内修复；

4) 更稳定：经过亿级用户的使用考验，CRASH率低于0.15%；

5) 兼容好：无系统内核的碎片化问题，更少的兼容性问题；

6) 体验优：支持夜间模式、适屏排版、字体设置等浏览增强功能；

7) 功能全：在Html5、ES6上有更完整支持；

8) 更强大：集成强大的视频播放器，支持视频格式远多于系统webview；

9) 视频和文件格式的支持x5内核多于系统内核

10) 防劫持是x5内核的一大亮点

### 2. 运行环境

1)手机ROM版本高于或等于2.2版本

2)手机RAM大于500M，该RAM值通过手机 /proc/meminfo 文件的MemTotal动态获取

注：如果不满足上述条件，SDK会自动切换到系统WebView，SDK使用者不用关心该切换过程。

### 3. SDK尺寸指标

1)SDK提供的JAR包约250K


 ## X5 诊断
 •	如何判断已加载了x5内核？

打开网页http://soft.imtt.qq.com/browser/tes/feedback.html，显示000000表示加载的是系统内核，显示大于零的数字表示加载了x5内核（该数字是x5内核版本号）
或者下载android端工具查看应用是否启动了X5内核


 •	与crosswalk的区别？

crosswalk只更新到chrome53版本停更了.而且是需要全量打包,而X5借助微信/QQ庞大的装机量无需自带内核到应用.



 •	64位手机无法加载x5(libmttwebview.so is 32-bit instead of 64-bit)
x5内核暂时不提供64位的so文件,在64位手机上需要让AP以32位模式运行。具体操作如下： 
1.如果使用是Eclipse则需要将所有的.so文件都放置在so加载目录：lib/armeabi文件夹下(没有该目录则新建一个，AP中没有使用到.so文件则需要拷贝任意一个32位的so文件到该目录下,如果没有合适的so可以到官网http://x5.tencent.com/tbs/sdk.html下载官网“SDK接入示例“,拷贝对应目录下的liblbs.so文件)，lib文件夹下不要有其他以”armeabi“开头的文件夹。 
2.如果使用的是 Android studio则需要进行两项配置， 
(1)打开对应module中的build.gradle文件,在文件的android{}中的defaultConfig{}里(如果没有defaultConfig{}则手动添加)添加如下配置: ndk{abiFilters "armeabi"}，如果配置后编译报错，那么需要在gradle.properties文件中加上Android.useDeprecatedNdk=true； 
(2)找出build.gradle中配置的so加载目录:jniLibs.srcDir:customerDir,如果没有该项配置则so加载目录默认为：src/main/jniLibs，需要将.so文件都放置在so加载目录的armeabi文件夹下(没有该目录则新建一个，AP中没有使用到.so文件则需要拷贝任意一个32位的so文件到该目录下，如果没有合适的so可以到官网http://x5.tencent.com/tbs/sdk.html下载官网“SDK接入示例“,拷贝对应目录下的liblbs.so文件)，so加载目录下不要有其他以”armeabi“开头的文件夹。 
