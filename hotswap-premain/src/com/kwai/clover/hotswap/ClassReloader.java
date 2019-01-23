package com.kwai.clover.hotswap;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 实现premain方法。
 * 使用MANIFEST.MF文件打包jar包。eclipse中导出时选项：use existing manifest from workspace
 * @author U-Demon
 * @date 2019年1月23日 下午8:26:07
 */
public final class ClassReloader {
	
	private static Instrumentation inst = null;

    public ClassReloader() {
    }

    public static void premain(String agentArgs, Instrumentation i) {
    	inst = i;
//    	System.out.println("agentArgs : " + agentArgs);
//    	inst.addTransformer(new ClassFileTransformer() {
//			
//			@Override
//			public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
//					ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
//				System.out.println("premain load Class     :" + className);
//    	        return classfileBuffer;
//			}
//		});
    }

    public static void reload(Class<?> cls, File file) throws IOException, ClassNotFoundException, UnmodifiableClassException {
        byte[] code = loadBytes(cls, file);
        if (code == null) {
            throw new IOException("Unknown File");
        } else {
            ClassDefinition def = new ClassDefinition(cls, code);
            inst.redefineClasses(new ClassDefinition[]{def});
            System.err.println(cls.getName() + " reloaded");
        }
    }

    private static byte[] loadBytes(Class<?> cls, File file) throws IOException, ClassNotFoundException {
        String name = file.getName();
        if (name.endsWith(".jar")) {
            return loadBytesFromJarFile(cls, file);
        } else {
            return name.endsWith(".class") ? loadBytesFromClassFile(file) : null;
        }
    }

    private static byte[] loadBytesFromClassFile(File classFile) throws IOException {
        byte[] buffer = new byte[(int)classFile.length()];
        FileInputStream fis = new FileInputStream(classFile);
        BufferedInputStream bis = new BufferedInputStream(fis);

        try {
            bis.read(buffer);
        } catch (IOException var8) {
            throw var8;
        } finally {
            bis.close();
        }

        return buffer;
    }

    private static byte[] loadBytesFromJarFile(Class<?> cls, File file) throws IOException, ClassNotFoundException {
    	try (JarFile jarFile = new JarFile(file);) {
    		String name = cls.getName();
            name = name.replaceAll("\\.", "/") + ".class";
            JarEntry en = jarFile.getJarEntry(name);
            if (en == null) {
                throw new ClassNotFoundException(name);
            } else {
                byte[] buffer = new byte[(int)en.getSize()];
                BufferedInputStream bis = new BufferedInputStream(jarFile.getInputStream(en));

                try {
                    bis.read(buffer);
                } catch (IOException var11) {
                    throw var11;
                } finally {
                    bis.close();
                }

                return buffer;
            }
    	}
    }

}
