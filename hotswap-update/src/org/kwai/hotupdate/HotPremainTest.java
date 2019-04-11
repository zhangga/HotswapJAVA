package org.kwai.hotupdate;

public class HotPremainTest {
	
	/**
	 * JVM启动参数添加：
	 * -javaagent:classReloader.jar的路径。例如：
	 * -javaagent:D:\GitHub\HotswapJAVA\hotswap-premain\classReloader.jar
	 * 开启线程扫描文件夹，如果有类放进来，动态加载替换，调用ClassReloadManager.reloadClass()方法。
	 * 具体细致逻辑，自己实现，代码设计到了项目的东西。
	 * @param args
	 */
	public static void main(String[] args) {
		while (true) {
			System.out.println(1);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
