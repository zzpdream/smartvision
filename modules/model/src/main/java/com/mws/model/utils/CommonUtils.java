package com.mws.model.utils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Utils - Common
 *
 */
public class CommonUtils {

	/**
	 * 比较时间
	 * @param begin
	 * @param current
	 * @param end
	 */
   public static Boolean compareTime(Date beginTime,Timestamp currentTime,Date endTime){
	   
	    Boolean i = false;
		if(currentTime.after(beginTime) && endTime.after(currentTime)){
			i = true;
		}
		return i;
   }
}
