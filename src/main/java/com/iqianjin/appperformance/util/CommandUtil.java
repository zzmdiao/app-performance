package com.iqianjin.appperformance.util;


import com.iqianjin.appperformance.base.DriverManger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;

import static com.iqianjin.appperformance.util.ExcelUtil.getCurrentTime;

//android的内存快照，拿去结果后需要执行下面的命令
// hprof-conv -z ./result/1558419108478.hprof ./result/1558419108478-2.hprof
public class CommandUtil {
	private static Logger logger = LoggerFactory.getLogger(CommandUtil.class);

	public static String platformName = DriverManger.getInstance().getPlatform();

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
	//开启或关闭android性能采集
	public static void androidMonitoring(){
		if ("android".equalsIgnoreCase(platformName)){
			exec2("adb shell am start -n com.iqianjin.client/com.didichuxing.doraemonkit.ui.UniversalActivity -e fragment_CONTENT 18");
		}
	}
	//获取android启动时间写入文件
	public static void getAndroidStartTime(int num){
		try {
			FileWriter file = new FileWriter(new File("./result/startTime.txt"));
			BufferedWriter out = new BufferedWriter(file);
			int sum=0;
			for (int i=1;i<=num;i++){
				String result = getStartTime("com.iqianjin.client");
				sum += Integer.parseInt(result);
				out.write("第"+i+"次启动时间："+result+"\n");
			}
			int average = sum/num;
			out.write("平均值：");
			out.write(String.valueOf(average));
			out.flush();
			out.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	//将android最后的结果文件导出
	public static void getAndroidImportFile(){
		if (("android").equalsIgnoreCase(platformName)){
			sleep(3);
			exec2("adb pull /sdcard/Android/data/com.iqianjin.client/files/doraemon/result/ .");
			logger.info("开始转换hprof文件");
			String shpath = System.getProperty("user.dir")+"/result/changeFile.sh";
			String shpath2 = System.getProperty("user.dir")+"/result/";
			exec2("/bin/sh "+ shpath + " " + shpath2);

		}
	}
	public static void exec2(String command){
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec(command);
			//写出脚本执行中的过程信息
			BufferedReader infoInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader errorInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String line = "";
			while ((line = infoInput.readLine()) != null) {
				logger.info(line);
			}
			while ((line = errorInput.readLine()) != null) {
				logger.error(line);
			}
			infoInput.close();
			errorInput.close();

			//阻塞执行线程直至脚本执行完成后返回
			process.waitFor();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void sleep(long sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String getStartTime(String pkgName){
		CommandUtil.exec("adb shell am force-stop "+pkgName);
		String procc = CommandUtil.exec("adb shell am start -W -n "+pkgName+"/com.iqianjin.client.asurface.activity.StartActivity");
		return procc.substring(procc.indexOf("TotalTime")+11,procc.indexOf("WaitTime"));
	}
	public static void main(String[] args) {
		CommandUtil commandUtil = new CommandUtil();
//		System.out.println(commandUtil.getClientVersion("com.iqianjin.client"));
//		System.out.println(getPhoneModle());
//        getImportFile();
//		commandUtil.getAndroidStartTime(2);
//		commandUtil.getAndroidImportFile();

	}


}
