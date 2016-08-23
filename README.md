# LayoutFormat_androidstudioplugin
为android studio 写的一个布局格式化的插件，即插即用。
#前言
在使用android studio 的过程中，我们做开发总是习惯的根据UI设计将尺寸值和字符串直接写进.xml文件中，这样做是非常不符合android开发规范的，不管leader跟你说了几次，“不要在layout中硬编码”，但是大家总是这样子做，因为我们做程序开发的就是懒啊，所以就有了这个插件的用武之地了，哈哈。
#使用
1 .大家可以去我的github的网址：[LayoutFormat_androidstudioplugin](https://github.com/shang1101/LayoutFormat_androidstudioplugin) 下载`AndroidPlugin_Layoutformat.jar`,然后在adnroid studio 中，操作:
`双击shift > 搜索Plugins (搜索 include no-project items要勾选)）>点击Plugins > install plugin form disk... >选择下载的jar包 >重启android studio >在 layout 中右键 refactor > LayoutFormat`，ok啦
想要学习源码的可以将整个仓库fork过去。
 2 .大家可以在 Plugins 下 选择 Browse repositories...，然后搜索 LayoutFormat 即可，过几天应该就通过审核，就会在intellj plugin 仓库中搜到了。
# 展示
1 . 批量去格式化layout.xml
![批量修改layout](https://github.com/shang1101/LayoutFormat_androidstudioplugin/blob/master/layoutformat批量格式化展示.gif)
2 .修改某一个layout.xml
![单独修改一个布局](https://github.com/shang1101/LayoutFormat_androidstudioplugin/blob/master/layoutformat演示.gif)
#使用中可能会遇到的问题
在导入插件时，windows下可能会出现插件加载失败的情况。会报下面的错误：
```java
cannot create class "com.shang.layoutformat.LayoutFormat" [Plugin: com.shang.android.layoutformat]
com.intellij.diagnostic.PluginException: cannot create class "com.shang.layoutformat.LayoutFormat" [Plugin: com.shang.android.layoutformat]
	at com.intellij.openapi.actionSystem.impl.ActionManagerImpl.convertStub(ActionManagerImpl.java:177)
	at com.intellij.openapi.actionSystem.impl.ActionManagerImpl.convert(ActionManagerImpl.java:515)
	at com.intellij.openapi.actionSystem.impl.ActionManagerImpl.getActionImpl(ActionManagerImpl.java:495)
	at com.intellij.openapi.actionSystem.impl.ActionManagerImpl.getAction(ActionManagerImpl.java:488)
	at com.intellij.openapi.actionSystem.DefaultActionGroup.unStub(DefaultActionGroup.java:354)
	at com.intellij.openapi.actionSystem.DefaultActionGroup.getChildren(DefaultActionGroup.java:312)
	at com.intellij.openapi.actionSystem.impl.ActionManagerImpl$5.compute(ActionManagerImpl.java:1315)
	at com.intellij.openapi.actionSystem.impl.ActionManagerImpl$5.compute(ActionManagerImpl.java:1308)
	at com.intellij.openapi.application.impl.ApplicationImpl.runReadAction(ApplicationImpl.java:967)
	at com.intellij.openapi.actionSystem.impl.ActionManagerImpl.preloadActionGroup(ActionManagerImpl.java:1308)
	at com.intellij.openapi.actionSystem.impl.ActionManagerImpl.preloadActionGroup(ActionManagerImpl.java:1323)
	at com.intellij.openapi.actionSystem.impl.ActionManagerImpl.preloadActionGroup(ActionManagerImpl.java:1323)
	at com.intellij.openapi.actionSystem.impl.ActionManagerImpl.preloadActionGroup(ActionManagerImpl.java:1323)
	at com.intellij.openapi.actionSystem.impl.ActionManagerImpl.preloadActionGroup(ActionManagerImpl.java:1302)
	at com.intellij.openapi.actionSystem.impl.ActionManagerImpl.doPreloadActions(ActionManagerImpl.java:1290)
	at com.intellij.openapi.actionSystem.impl.ActionManagerImpl.access$200(ActionManagerImpl.java:77)
	at com.intellij.openapi.actionSystem.impl.ActionManagerImpl$4.run(ActionManagerImpl.java:1278)
	at com.intellij.openapi.application.impl.ApplicationImpl$8.run(ApplicationImpl.java:366)
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:471)
	at java.util.concurrent.FutureTask.run(FutureTask.java:262)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1145)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615)
	at java.lang.Thread.run(Thread.java:744)
	at org.jetbrains.ide.PooledThreadExecutor$1$1.run(PooledThreadExecutor.java:55)
Caused by: com.intellij.diagnostic.PluginException: com/shang/layoutformat/LayoutFormat : Unsupported major.minor version 52.0 [Plugin: com.shang.android.layoutformat]
	at com.intellij.ide.plugins.cl.PluginClassLoader.loadClassInsideSelf(PluginClassLoader.java:130)
	at com.intellij.ide.plugins.cl.PluginClassLoader.tryLoadingClass(PluginClassLoader.java:77)
	at com.intellij.ide.plugins.cl.PluginClassLoader.loadClass(PluginClassLoader.java:66)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:358)
	at java.lang.Class.forName0(Native Method)
	at java.lang.Class.forName(Class.java:270)
	at com.intellij.openapi.actionSystem.impl.ActionManagerImpl.convertStub(ActionManagerImpl.java:153)
	... 23 more
Caused by: java.lang.UnsupportedClassVersionError: com/shang/layoutformat/LayoutFormat : Unsupported major.minor version 52.0
	at java.lang.ClassLoader.defineClass1(Native Method)
	at java.lang.ClassLoader.defineClass(ClassLoader.java:800)
	at java.lang.ClassLoader.defineClass(ClassLoader.java:643)
	at com.intellij.util.lang.UrlClassLoader._defineClass(UrlClassLoader.java:260)
	at com.intellij.util.lang.UrlClassLoader.defineClass(UrlClassLoader.java:256)
	at com.intellij.util.lang.UrlClassLoader._findClass(UrlClassLoader.java:225)
	at com.intellij.ide.plugins.cl.PluginClassLoader.loadClassInsideSelf(PluginClassLoader.java:124)
```
这是因为你的android studio 的运行时的jre版本低于我的插件，查看android studio 的 java 版本是在：
``菜单栏 > Help  > About``；
你可以看到JRE:1.8.xx 这个东西，这个JRE是读取你的环境变量里面配置的java_home ,所以改下吧，提倡大家用最新的1.8的。因为我下载Intellj IDEA 开发时，它就默认我必须要关联1.8的jdk，坑爹啊
# 最后
感谢[LayoutFormatter](https://github.com/drakeet/LayoutFormatter)的作者，然后我想对作者说，里面的xml格式化的过程最好是用dom 或 sax 的方式去解析出来，而不是一行一行的去读然后判断，确实不是很科学啊。
