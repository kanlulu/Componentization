## Android 组件化架构方案
#### 组件化 + T-MVP + DataBinding + RxJava

组件间通信：EventBus  

组件间跳转：ARouter  

网络通信：  Retrofit  

#####模块化与组件化切换  
````
if (isModule.toBoolean()) {
     apply plugin: 'com.android.application'//模块化 可单独编译打包
 } else {
     apply plugin: 'com.android.library'//组件化 打包完整的App
 }
 ````  
 
 **组件划分**    
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
  
