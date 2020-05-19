package com.knowology.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description:
 * @author: Conan
 * @create: 2019-02-27 15:03
 **/
public class DateUtil {
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";


	public static Date dateAddDays(Date date, int days) {
		if (date == null) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

	public static String dateFormat(Date date, String pattern) {
		if (StringUtils.isBlank(pattern)) {
			pattern = DateUtil.DATE_PATTERN;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static Date parseDate(String date,String pattern) throws ParseException {
		if(StringUtils.isBlank(pattern)){
			pattern = DateUtil.DATE_PATTERN;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(date);
	}
	public static Date parseDate(Date date,String pattern) throws ParseException {
		if(StringUtils.isBlank(pattern)){
			pattern = DateUtil.DATE_PATTERN;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(dateFormat(date,pattern));
	}

	/**
	 * 两个时间的小时差
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long differentDaysByHour(Date date1, Date date2){
		long hours = (date1.getTime() - date2.getTime())/(1000*60);
		return hours;
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(DateUtil.dateFormat(new Date(),"yyyyMMdd"));

		System.out.println(differentDaysByHour(new Date(),parseDate("20200313 15:00:00","yyyyMMdd hh:mm:ss")));
	}
}
