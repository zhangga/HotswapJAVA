package org.kwai.hotupdate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.sun.tools.attach.VirtualMachine;

/**
 * 热更启动类
 * @author U-Demon
 * @date 2018年11月9日 下午2:40:12
 */
public class HotUpdate {
	
	public static void main(String[] args) {
		File file = new File("./config/hot.config");
		// jvm的进程id
		String pid = "";
		// 热更的文件目录
		String dir = "./";
		List<String> clazzs = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("pid=")) {
					pid = line.split("=")[1];
				}
				else if (line.startsWith("dir=")) {
					dir = line.split("=")[1];
					if (!dir.endsWith("/")) {
						dir += "/";
					}
				}
				else if (line.startsWith("class=")) {
					clazzs.add(line.split("=")[1]);
				}
			}
			
			VirtualMachine vm = VirtualMachine.attach(pid);
			for (String clazz : clazzs) {				
				String[] clazzStr = clazz.split("\\.");
				String path = dir + clazzStr[clazzStr.length - 1] + ".class";
				System.err.println("path == " + path);
				// path参数即agentmain()方法的第一个参数
				vm.loadAgent(dir + "agent/hotswap-agent.jar", dir + path);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

}
