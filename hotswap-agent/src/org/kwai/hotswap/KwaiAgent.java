package org.kwai.hotswap;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

/**
 * JavaAgent热更代理类
 *
 * @author U-Demon
 * @date 2018年5月11日 下午6:02:19
 */
public class KwaiAgent {
	
	/**
	 * 该方法支持在JVM 启动后再启动代理，对应清单的Agent-Class: 属性
	 * @param path
	 * @param inst
	 */
	public static void agentmain(String path, Instrumentation inst) {
		try {
			File file = new File(path);
			byte[] targetClassFile = new byte[(int) file.length()];
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			dis.readFully(targetClassFile);
			dis.close();
			
			DynamicClassLoader myLoader = new DynamicClassLoader();
			Class<?> targetClazz = myLoader.findClass(targetClassFile);
			System.out.println("target Class path: " + targetClazz.getName());
			ClassDefinition clazzDef = new ClassDefinition(Class.forName(targetClazz.getName()), targetClassFile);
			inst.redefineClasses(clazzDef);
			
			System.out.println("redefine Class complete!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
