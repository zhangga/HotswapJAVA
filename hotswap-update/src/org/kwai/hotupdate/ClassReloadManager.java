package org.kwai.hotupdate;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import com.kwai.clover.hotswap.ClassReloader;

/**
 * 热更代码（将编译好的class文件建立好完整包名后放到data/classhotfix）
 *
 */
public class ClassReloadManager {
	
	/** class路径 */
	private static final String CLASSDIR = "classhotfix";
	/**
	 * 热更代码（将编译好的class文件建立好完整包名后放到data/classhotfix）
	 */
	public static void reloadClass() {
		String classPath = "/root/hotfix/" + CLASSDIR;
		classPath = "./" + CLASSDIR;
		List<File> classFiles = new ArrayList<>();
		getClassFiles(classPath, classFiles);
		for(File file : classFiles) {
			Class<?> clazz = null;
			try {
				String url = file.getAbsolutePath().replace(CLASSDIR, "@");
				url = url.substring(url.indexOf('@') + 1).replaceAll("\\\\", ".").replaceAll("/", ".");
				String forName = url.substring(1, url.length() - 6);
				System.out.print("找到class文件：" + forName);
				clazz = Class.forName(forName);
				// ClassReloader类为hotswap-premain工程下的。
				ClassReloader.reload(clazz, file);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		reloadClass();
	}
	
	/**
	 * 获得配置表的class文件（不包括$符号的文件）
	 * @return
	 */
	public static void getClassFiles(String path, List<File> result){
		File dir = new File(path);
		if (!dir.exists() || !dir.isDirectory())
			return;

		File[] dirFiles = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory() || file.getName().endsWith(".class");
			}
		});

		for (File file : dirFiles) {
			if (file.isDirectory()) {
				getClassFiles(file.getAbsolutePath(), result);
			} else {
				result.add(file);
			}
 		}
	}
}
