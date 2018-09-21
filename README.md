## Android 组件化架构方案
#### 组件化 + T-MVP + DataBinding + RxJava

组件间通信：[EventBus](https://github.com/greenrobot/EventBus)  

组件间跳转：[ARouter](https://github.com/alibaba/ARouter)  

网 络 通 信：  [Retrofit](https://github.com/square/retrofit)  
 
 #### **组件划分**    
 - app：  
 app壳工程，没有任何业务功能，主要负责项目打包发布，统筹管理；
 
 - common：
 通用基础模块，为其他业务组件提供基础依赖，需要最大程度的解耦，以便于可以自由的迁移到其他项  
 目中;
 
 - modules：  
 此目录下皆为按照业务划分的各个组件，组件之间的通信和跳转采用EventBus和ARouter，以达到组件  
 之间解耦的目的，能够做到组件的移除、增加、修改不影响其他组件的使用和整体项目的运行；
 
 - bean：  
 主要存放EventBus的事件类、ARouter的path、网络请求的实体类等，这些东西和业务组件是高度耦合的，  
 为了最大程度的降低common模块和业务组件的耦合度，我们将事件类、path路径和实体类等内容单独创建一个  
 module，让其被common依赖以最大程度的实现common与具体业务的解耦；  
 
 - thirdparty：  
 第三方功能模块,如：jpush、umeng、map等，主要目的是为了这些模块能够快速的接入项目或从  
 项目中快速移除而对其他模块和组件的影响尽量减少。
    
 _在modules下有一个module_main的组件，这个组件中我们通常会包含启动页、引导页和应用的主页面(也就是传  
 统所说的MainActivity)_
 #### 模块化与组件化切换  
````
if (isModule.toBoolean()) {
     apply plugin: 'com.android.application'//模块化 可单独编译打包
 } else {
     apply plugin: 'com.android.library'//组件化 打包完整的App
 }
 ````  
在对应组件的build.gradle中配置上述代码进行组件化与模块化的切换  
*注:isModule 在gradle.properities文件中进行配置(false true)*   

在模块化与组件化切换的同时需要对AndroidManifest文件中的内容进行切换，已达到组件化时整个项目中只有  
一个launchActivity,模块化时每个模块都有一个launchActivity可以单独打包安装运行测试；
- 组件化时的manifest:
````
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.yql.module_credit">

    <application android:theme="@style/AppTheme">
        <activity android:name=".CreditActivity" />
    </application>

</manifest>
````
- 模块化时的manifest:
````
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.yql.module_credit">
    
    <application
        android:name="debug.CreditApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".CreditActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
````
可以看到除了Activity的区别外，application也做了更改。application的icon、label等必要元素需要在模块化  
打包时体现出来。  
至于在组件化和模块化时使用不同的manifest文件，也可以在当前模块的build.gradle中进行配置
````
android{
    .....
     sourceSets {
            main {
                if (isModule.toBoolean()) {
                    manifest.srcFile 'src/main/module/AndroidManifest.xml'
                } else {
                    manifest.srcFile 'src/main/AndroidManifest.xml'
                    //集成开发模式下排除debug文件夹中的所有Java文件
                    java {
                        exclude 'debug/**'
                    }
                }
            }
        }
}
````
具体实现如上所示，这样AndroidManifest.xml文件就可以根据isModule的至进行切换。
  
#### 对Activity+Fragment的页面的处理  
上文提到module_main组件中包含主页面即:MainActivity，目前大多App的主页面都是MainActivity+Fragments的  
形式。  
我们可以直接使用传统的方式在module_main组件中完成所对应的所有的Fragments的代码，当然如果你的Fragment  
较多的话，你也可以使用组件化的方式将各个fragment组件化。  
这时候就会产生一个问题：  
考虑到组件化的基本规则，即：组件之间需要相互解耦，那么势必fragments组件是不能被Main组件所依赖，这样  
的话我们就没法直接向MainActivity中直接添加Fragment。
当然这个问题其实也很好解决，这里提供两种解决方案：
- 使用反射，反射可以让我们在Runtime(运行)时获取任何类的字节码。因此我们只需要知道fragment的具体路径，  
就可以获取到相应的Fragment；  
````
     Class<Fragment> c = (Class<Fragment>) Class.forName("com.kanlulu.Homefragment");
     //通过newInstance()获取对象实例。
     Userinfo u = c.newInstance();
````
- ARouter是专门为组件化而开发出来的框架，他同样支持Fragment的获取。  
````
    // 获取Fragment
    Fragment fragment = (Fragment) ARouter.getInstance().build("/test/fragment").navigation();
```` 
  
  以上两种方式酌情自取。当然如果fragment不多的话使用传统方式也没有什么问题。  
  
  本方案参考以下博客和书籍：  
 1、 [https://blog.csdn.net/guiying712/article/details/55213884](https://blog.csdn.net/guiying712/article/details/55213884)  
  
  2、[《Android组件化架构》，仓王](https://item.jd.com/26728862000.html)

