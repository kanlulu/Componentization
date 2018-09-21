## Android 组件化架构方案
#### 组件化 + T-MVP + DataBinding + Rxjava

组件间通信：EventBus

组件间跳转：ARouter

网络通信：Retrofit

模块化与组件化切换

`
if (isModule.toBoolean()) {
     apply plugin: 'com.android.application'
 } else {
     apply plugin: 'com.android.library'
 }
 `

