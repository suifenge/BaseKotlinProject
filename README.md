# BaseKotlinProject
base project design by kotlin
使用MVVM设计，加入协程，去除RxJava的使用；使用jetpack组件LifeCycle管理应用的生命周期
## 使用
### 引用工程
> 直接依赖basekotlin库工程
如果出现引入失败在project下的build.gradle中加入
```
allprojects {
    repositories {
        ...
        //因为使用了Toasty这个第三方库，如果不使用可以去除引用，这里就不需要再添加这行
        maven { url 'https://jitpack.io' }
    }
}
```
在app目录中的build.gradle加入
```
dependencies {
    ...
    implementation project(':kotlinbase')
}
```
> 使用ARouter
```
kapt "com.alibaba:arouter-compiler:1.1.4"

javaCompileOptions {
            annotationProcessorOptions {
                arguments = [moduleName: project.getName()]
            }
        }
```

### 配置AndroidManifest
> 配置权限
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
## proguard-rules.pro 混淆文件的
> 如果引用了FreeProguard，可以减少很多第三方的混淆配置
> 混淆模板
```
#------------------------------------------主项目混淆规则----------------------------------------------
#实体类不参与混淆


#-------------------------------------------Lib混淆规则----------------------------------------------
#---------------------------------1.实体类---------------------------------

#-------------------------------------------------------------------------

#--------------------------------2.第三方包-------------------------------
#support
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-dontwarn android.support.**

#databinding
-keep class android.databinding.** { *; }
-dontwarn android.databinding.**

#annotation
-keep class android.support.annotation.** { *; }
-keep interface android.support.annotation.** { *; }

#retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.sunloto.shandong.bean.** { *; }

#glide
-keep public class * implements com.bumptech.glide.module.AppGlideModule
-keep public class * implements com.bumptech.glide.module.LibraryGlideModule
-keep class com.bumptech.glide.** { *; }
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

#glide-transformations
-keep class jp.wasabeef.glide.transformations.** {*;}
-dontwarn jp.wasabeef.glide.transformations.**

#okhttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn okio.**

#=====================bindingcollectionadapter=====================


#---------------------------------------------------------------------------

#---------------------------------3.与js互相调用的类------------------------

#无

#----------------------------------------------------------------------------

#---------------------------------4.反射相关的类和方法-----------------------
-keep public class * extends com.suifeng.kotlin.base.ui.activity.BaseActivity{ *; }
-keep public class * extends com.suifeng.kotlin.base.ui.fragment.BaseFragment{ *; }

#----------------------------------------------------------------------------

#---------------------------------5.自定义控件------------------------------

-keep class com.suifeng.kotlin.base.widget.** { *; }

#----------------------------------------------------------------------------
#---------------------------------6.其他定制区-------------------------------
#native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
#Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#Serializable 不被混淆
-keepnames class * implements java.io.Serializable
#Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}
-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}
#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}
#保持类中的所有方法名
-keepclassmembers class * {
    public <methods>;
    private <methods>;
}

#----------------------------------------------------------------------------

#---------------------------------基本指令区---------------------------------
# 抑制警告
-ignorewarnings
#指定代码的压缩级别
-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
 #优化  不优化输入的类文件
-dontoptimize
 #预校验
-dontpreverify
 #混淆时是否记录日志
-verbose
 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#混淆包路径
-repackageclasses ''
-flattenpackagehierarchy ''

#保护注解
-keepattributes *Annotation*

#避免混淆泛型 如果混淆报错建议关掉
-keepattributes Signature

#保留SourceFile和LineNumber属性
-keepattributes SourceFile,LineNumberTable

#忽略警告
#-ignorewarning
#----------记录生成的日志数据,gradle build时在本项目根目录输出---------
#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#----------------------------------------------------------------------------
#----------------------------------------------------------------------------
```