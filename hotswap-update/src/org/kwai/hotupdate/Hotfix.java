package org.kwai.hotupdate;

public class Hotfix {
	
	private static final long interval = 3000;
	
	public void exec() {
		System.out.println("开头");
		System.out.println("Hotfix log: " + add());
	}
	
	private int add() {
		return 1+2;
	}

}
