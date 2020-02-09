package com.myproject.qa.testing.framework.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.myproject.qa.testing.framework.exceptions.FrameworkException;
import com.myproject.qa.testing.framework.logs.ScriptLogger;

public class DateUtils {

	public static String getCurrentDate(String... pattern) throws Exception {
		try {
			String format = (pattern.length>0)? pattern[0] : "yyyy-MM-dd";
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
			LocalDate localDate = LocalDate.now();
			String date = (dtf.format(localDate));
			ScriptLogger.info("Date is: "+date);
			return date;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

	public static String getYesterdayDate(String... pattern) throws Exception {
		try {
			String format = (pattern.length>0)? pattern[0] : "yyyy-MM-dd";
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
			Date yesterday = calendar.getTime();
			String yesterdayDate = dateFormat.format(yesterday);
			ScriptLogger.info("Date is: "+yesterdayDate);
			return yesterdayDate;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}
	public static String getTomorrowDate(String... pattern) throws Exception {
		try {
			String format = (pattern.length>0)? pattern[0] : "yyyy-MM-dd";
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 1);
			Date yesterday = calendar.getTime();
			String yesterdayDate = dateFormat.format(yesterday);
			ScriptLogger.info("Date is: "+yesterdayDate);
			return yesterdayDate;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}
	public static String getPreviousDateByDays(int days, String... pattern) throws Exception {
		ScriptLogger.info();
		try {
			String format = (pattern.length>0)? pattern[0] : "yyyy-MM-dd";
			DateFormat dateFormat = new SimpleDateFormat(format);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -days);

			String nDaysBeforeDate = dateFormat.format(calendar.getTime());
			ScriptLogger.info("Date is: "+nDaysBeforeDate);
			return nDaysBeforeDate;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

	public static String getFutureDateByDays(int days, String... pattern) throws Exception {
		ScriptLogger.info();
		try {
			String format = (pattern.length>0)? pattern[0] : "yyyy-MM-dd";
			DateFormat dateFormat = new SimpleDateFormat(format);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, days);

			String nDaysAfterDate = dateFormat.format(calendar.getTime());
			ScriptLogger.info("Date is: "+nDaysAfterDate);
			return nDaysAfterDate;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

	public static int compareDates(String firstDate, String secondDate, String... pattern) throws Exception {
		ScriptLogger.info();
		Date date1;
		Date date2;
		try {
			String dateFormat = (pattern.length>0)? pattern[0] : "yyyy-MM-dd";
			DateFormat format = new SimpleDateFormat(dateFormat);
			date1  = format.parse(firstDate);
			date2 = format.parse(secondDate);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		return date1.compareTo(date2);
	}

	public static int getDaysGapBetweenDates(String format, String date1, String date2) throws Exception {
		ScriptLogger.info();
		SimpleDateFormat myFormat = new SimpleDateFormat(format);
		try {
			Date dateBefore = myFormat.parse(date1);
			Date dateAfter = myFormat.parse(date2);
			long difference = dateAfter.getTime() - dateBefore.getTime();
			int daysBetween = (int) (difference / (1000*60*60*24));

			ScriptLogger.info("Number of Days between dates: "+daysBetween);
			return daysBetween;
		} catch (Exception e) {
			throw new FrameworkException(e, "Unable to get the difference of days between "+date1+" and "+date2);
		}
	}

	public static String getPreviousDateOfDayOfWeek(DayOfWeek dayOfWeek) throws Exception {
		ScriptLogger.info();
		try {
			String getPreviousDateOfDayOfWeekTest = LocalDate.now().with(TemporalAdjusters.previousOrSame(dayOfWeek)).toString();
			ScriptLogger.info("getPreviousDateOfDayOfWeekTest "+getPreviousDateOfDayOfWeekTest);
			return getPreviousDateOfDayOfWeekTest;
		}catch(Exception e) {
			throw new FrameworkException(e, "Unable to get date of previous sunday");
		}
	}


	public static String getFirstDateofNthLastMonth(int nthDay, int nthLastMonth) throws Exception {
		ScriptLogger.info();
		try {
			Calendar month = Calendar.getInstance();
			month.add(Calendar.MONTH, -nthLastMonth);
			month.set(Calendar.DATE, nthDay);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String getFirstDateofNthLastMonth = sdf.format(month.getTime());
			ScriptLogger.info("This is the nth("+nthDay+"th) date of nth("+nthDay+"th) Last months prior to today "+getFirstDateofNthLastMonth);
			return getFirstDateofNthLastMonth;
		}catch(Exception e) {
			throw new FrameworkException(e, "Unable to get first date of 3 months prior to today");
		}
	}


	public static String getDateInOtherFormatTest(String date, String fromPattern, String toPattern) throws Exception {
		try {
			DateFormat originalFormat = new SimpleDateFormat(fromPattern, Locale.ENGLISH);
			DateFormat targetFormat = new SimpleDateFormat(toPattern);
			Date oldFormattedDate = originalFormat.parse(date);
			return targetFormat.format(oldFormattedDate);
		} catch (Exception e) {
			throw new FrameworkException(e, "Unable to convert date in desired format.");
		}  
	}

	public static String getMillisToTimeStamp(Long timeInMillis, String... pattern){
		 Timestamp ts=new Timestamp(timeInMillis);  
         Date date=new Date(ts.getTime()); 
         return (pattern.length==0) ? (new SimpleDateFormat("HH:mm:ss a")).format(date) : (new SimpleDateFormat(pattern[0])).format(date);
	}
	
	public static long getDifferenceInDates(String startDate, String endDate, String... pattern) throws Exception{
		String disiredPattern = (pattern.length==0) ? "HH:mm:ss a" : pattern[0];
		Date d1;
		Date d2;
		try {
			d1 = new SimpleDateFormat(disiredPattern).parse(startDate);
			d2 = new SimpleDateFormat(disiredPattern).parse(endDate);
		} catch (Exception e) {
			throw new FrameworkException(e, "Date is not parsable");
		}
		return (d2.getTime()-d1.getTime())/1000;
   	}
	
}
