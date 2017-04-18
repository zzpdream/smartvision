package com.mws.core.utils.image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 调用windows、uninx和liunx系统脚本的工具类
 * 
 * @author ranfi
 * 
 */
public class InvokeScriptUtils {

	/**
	 * Runtime调用系统的执行脚本命令
	 * 
	 * @param command
	 */
	public static boolean runCommand(String command) {
		Runtime rt = Runtime.getRuntime();
		String osName = getOSName();
		String[] cmd = null;
		if (osName.toLowerCase().indexOf("windows") != -1) {
			cmd = new String[] { "cmd", "/c", command };
		}
		else {
			cmd = new String[] { "bash", "-c", command };
		}
		Process p = null;
		try {

			p = rt.exec(cmd);

			if (p.waitFor() != 0) {

				BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				StringBuilder errorDesc = new StringBuilder();
				for (String str = br.readLine(); str != null; str = br.readLine()) {
					errorDesc.append(str);
				}
				System.err.println("execute shell failed: " + errorDesc);
				// zkf35483 新增关闭流操作
				br.close();
				return false;
			}
			else {
				return true;
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		finally {
			// p.destroy();
		}
		return false;
	}

	public static String getOSName() {
		String os = System.getProperty("os.name");
		return os;
	}
}
