package com.iqianjin.appperformance.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.iqianjin.appperformance.util.ExcelUtil.getCurrentTime;

public class CommandUtil {
	public  static  String getClientVersion(String packageName){
		Runtime runtime = Runtime.getRuntime();
		Process proc = null;
		try {
			proc = runtime.exec("adb shell dumpsys package "+packageName+" |grep versionName");
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String line = null;
		try {
			line = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String str= line.substring(line.indexOf("=")+1, line.length());

		return str;
	}


	public static double streamDouble(double pixRate) {
		double newPixRate = (double)Math.round(pixRate * 100) / 100;
		return newPixRate;
	}
	//将日志输出到log文件
	public static void getAndroidLog(){
		String[] cmds = {"/bin/sh","-c","adb logcat -v time *:e | grep com.iqianjin.client > " + System.getProperty("user.dir") + "/data/"+getCurrentTime()+".log"};
		exec(cmds);
	}

	public static String  getPhoneModle(){
		String result = exec("adb shell getprop ro.product.model");
		return result;
	}

	public static ArrayList<String> exec(String[] command){
		ArrayList<String> result = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			Process process =null;
			process = Runtime.getRuntime().exec(command);
			reader= new BufferedReader(new InputStreamReader(process.getInputStream(),"UTF-8"));
			String line = null;
			System.out.println(reader.readLine());
			while((line = reader.readLine())!=null){
				result.add(line);
			}
			process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			try {
				if(reader!=null){
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}


	public static String exec(String adb){
		Runtime runtime = Runtime.getRuntime();
		StringBuffer buffer = new StringBuffer();
		Process proc = null;
		try {
			proc = runtime.exec(adb);
			BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream(),"UTF-8"));
			String line = null;
			while((line = reader.readLine())!=null){
				buffer.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	public static void main(String[] args) {
		CommandUtil commandUtil = new CommandUtil();
		System.out.println(commandUtil.getClientVersion("com.iqianjin.client"));
		System.out.println(getPhoneModle());

	}


}
