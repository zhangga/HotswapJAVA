package org.kwai.hotswap;

/**
 * 动态加载class
 *
 * @author U-Demon
 * @date 2018年5月11日 下午6:08:27
 */
public class DynamicClassLoader extends ClassLoader {
	
	public Class<?> findClass(byte[] b) throws ClassNotFoundException {
		return defineClass(null, b, 0, b.length);
	}

}
