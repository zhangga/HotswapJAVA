package org.kwai.hotupdate;

/**
 * premain方式测试类
 * @author U-Demon
 * @date 2019年9月23日 上午11:17:10
 */
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
		// 启动测试类
		startTest();
		// 启动线程扫描
		startReload();
	}
	
	public static void startTest() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Hotfix hot = new Hotfix();
				
				while (true) {					
					hot.exec();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public static void startReload() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {					
					try {
						Thread.sleep(5000);
						ClassReloadManager.reloadClass();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}
