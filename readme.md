#JAVA代码热更
工程本身使用JDK1.8编写，也可以热更1.8以上的项目。注意：热更的class文件最好和原项目运行的class文件版本号保持一致。

##hotswap-agent工程：
###打包成hotswap-agent.jar包，在工程下有已打包好的jar包。

##hotswap-update工程：
###热更服务器代码工程 。
###lib包下的tools.jar即${JAVA_HOME}/lib/tools.jar.该jar包分win和linux版本。当前项目下方的是linux版。
项目下打包好的hotswap-update.jar包是可直接使用的，已经把linux版的tools.jar一起打进去了。

##release工程：
###将release直接部署到linux，执行sh即可。
注意：修改sh中的目录地址。
修改config文件中的配置。



#premain方式，可以新增类和方法。
##JVM启动时添加参数：-javaagent:D:\GitHub\HotswapJAVA\hotswap-premain\classReloader.jar
##工程中开启线程扫描指定文件夹看时候有需要热更的class文件，然后加载。
##详情见hotswap-update/HotPremainTest.java