package com.mws.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Utils - Common
 *
 * @author xingkong1221
 * @date 2015-05-19
 */
public class CommonUtils {

    /**
     * 比较版本号，前者大返回正数，后者大返回负数，相等返回0
     *
     * @param version1 版本号1
     * @param version2 版本号2
     * @return 比较结果
     */
    public static int compareVersion(String version1, String version2) {
        if (version1 == null || version2 == null) {
            throw new IllegalArgumentException("version1 and version2 must not be null");
        }

        String[] versionArray1 = version1.split("\\.");
        String[] versionArray2 = version2.split("\\.");

        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {
            ++idx;
        }
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }
    
    
    
    /** 
     * 复制单个文件(可更名复制) 
     * @param oldPathFile 准备复制的文件源 
     * @param newPathFile 拷贝到新绝对路径带文件名(注：目录路径需带文件名) 
     * @return 
     */  
    public static void CopySingleFile(String oldPathFile, String newPathFile) {  
        try {  
            int bytesum = 0;  
            int byteread = 0;  
            File oldfile = new File(oldPathFile);  
            if (oldfile.exists()) { //文件存在时  
                InputStream inStream = new FileInputStream(oldPathFile); //读入原文件  
                FileOutputStream fs = new FileOutputStream(newPathFile);  
                byte[] buffer = new byte[1444];  
                while ((byteread = inStream.read(buffer)) != -1) {  
                    bytesum += byteread; //字节数 文件大小  
                    //System.out.println(bytesum);  
                    fs.write(buffer, 0, byteread);  
                }  
                inStream.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    
    public static String transMapToString(Map map){  
    	  Entry entry;  
    	  StringBuffer sb = new StringBuffer();  
    	  for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)  
    	  {  
    	      entry = (Entry)iterator.next();  
    	      sb.append(entry.getKey().toString()).append( "'" ).append(null==entry.getValue()?"":  
    	      entry.getValue().toString()).append (iterator.hasNext() ? "^" : "");  
    	  }  
    	  return sb.toString();  
    	}
    
    /**
	 * @author zhouliangjun
	 * @param ts
	 * @return
	 */
	public static String timeChangeToAll(Date ts) {
		String time = "";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = sdf.format(ts);
		return time;
	}
	
	/**
	 * @author zhouliangjun
	 * @param ts
	 * @return
	 */
	public static String timeToStrByValue(Date ts,String formatVal) {
		String time = "";
		DateFormat sdf = new SimpleDateFormat(formatVal);
		time = sdf.format(ts);
		return time;
	}

	/**
	 * @author zhouliangjun
	 * @param ts
	 * @return
	 */
	public static String timeChangeToDte(Date ts) {
		String time = "";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		time = sdf.format(ts);
		String year = time.substring(0, 4);
		String mon = time.substring(5, 7);
		String date = time.substring(8, 10);
		time =  mon + "月" + date + "日";
		return time;
	}
	
	/**
	 * @author zhouliangjun
	 * @param ts
	 * @return
	 */
	public static String timeChangeToYMD(Date ts) {
		String time = "";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		time = sdf.format(ts);
		return time;
	}

	/**
	 * @author zhouliangjun
	 * @param ts
	 * @return
	 */
	public static String timeChangeToSecond(Date ts) {
		String time = "";
		DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		time = sdf.format(ts);
//		String hour = time.substring(0, 2);
//		int a = Integer.valueOf(hour);
//		if (a >= 0 && a < 12) {
//			time = "上午" + time;
//		} else if (a >= 12 && a < 24) {
//			time = "下午" + time;
//		}
		return time;
	}
	
	/**
	 * 比较时间
	 * @param begin
	 * @param current
	 * @param end
	 */
   public Boolean compareTime(Timestamp beginTime,Timestamp currentTime,Timestamp endTime){
	   
	    Boolean i = false;
		if(currentTime.after(beginTime) && endTime.after(currentTime)){
			i = true;
		}
		return i;
   }
}
