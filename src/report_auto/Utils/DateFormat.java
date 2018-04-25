package report_auto.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class DateFormat {
	public String formatDateToString(long date) {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	    return formatter.format(date);
	}
	 
	public static String formatDate(Date date){       
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    return formatter.format(date);
	}
	
	public String formatDate(long date){       
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    return formatter.format(date);
	}
	/* 2018/4/8 - added method String formatToDateUseTimezone(String date,String timezoneid1, String timezoneid2)
	 * 次方法主要用来将特定时区的时间转换成指定时区的时间，比如将北京时间“2018-04-08 15:40:49.031”，转换对应的美国东部时间是“2018-04-08 03:40:49.031”
	 * 本方法共三个参数，第一个是待转换的时间，第二个是待转换时间是哪个时区，第三个是需要转换成的时区
	 * AvailableIDs：
	 * US/Eastern美国东部时间
	 * UTC世界标准时间
	 * PST太平洋标准时间
	 * Asia/Shanghai上海时间
	 */
	public String formatToDateUseTimezone(String date,String timezoneid1, String timezoneid2){
		TimeZone.setDefault(TimeZone.getTimeZone(timezoneid2));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		formatter.setTimeZone(TimeZone.getTimeZone(timezoneid1));
		Date d = null;
		String s = null;
			try {
				d = formatter.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			s = formatDate(d);
			return s;
	}
	
	public Date formatToDate(String date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    Date d = null;
	    try {
			d = formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}	 
	    return d;
	}
	 

	
	public long formatDate(String date){       
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    Date d = null;
	    try {
			d = formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}	 
	    return d.getTime();
	}
	
	public String getTimeString() {
		Calendar calendar = new GregorianCalendar();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		System.out.println("year is: "+year);
		String month = this.valueOfString(String.valueOf(calendar.get(Calendar.MONTH) + 1),2);	
		String day = this.valueOfString(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)),2);
		String hour = this.valueOfString(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)),2);
		String minute = this.valueOfString(String.valueOf(calendar.get(Calendar.MINUTE)),2);
		String second = this.valueOfString(String.valueOf(calendar.get(Calendar.SECOND)),2);
		String millisecond = this.valueOfString(String.valueOf(calendar.get(Calendar.MILLISECOND)),3);
		return year+month+day+hour+minute+second+millisecond;
	}	
	
	    // get current time.
		public static String getTimeStamp(){
			String time = ""; 
			
			String formatString = "yyyy-MM-dd hh:mm:ss";
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(formatString);

			time = sdf.format(cal.getTime());
			return time;
		}
		// get current time.
		public static String getCurrentYear(){
			String year = getTimeStamp().substring(0, 4);
			return year;
		}
		//Get 1 year after current Year
		public static String get1YearAfterCurrentYear(){
			String year = getTimeStamp().substring(0, 4);
			int afterYear = Integer.parseInt(year) + 1 ;
	    	year = String.valueOf(afterYear);
			return year;
		}
	    
	    public static String getCurrentMonthEng(){
			String month = getTimeStamp().substring(5, 7);
			return convertMonth(month);
		}
	    public static String geCurrenttMonth(){
	  		String month = getTimeStamp().substring(5, 7);
	  		return month;
	  	}
	    
	    public static String getCurrentDay(){
			String day = getTimeStamp().substring(8, 10);
			return day;
		}
	    
	    //Get before current day 
	    public static String getBeforeCurrentDay(){
	    	String day = getTimeStamp().substring(8, 10);
	    	int beforeDay =(day.equalsIgnoreCase("01"))?1:(Integer.parseInt(day) - 1);
	    	day = String.valueOf(beforeDay);
	    	return day;
	    }
		
		
		// get after one month time.
		public static String getTimeStampAfter1Month(){
			String time = ""; 
			
			String formatString = "yyyy-MM-dd hh:mm:ss";
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);
			SimpleDateFormat sdf = new SimpleDateFormat(formatString);

			time = sdf.format(cal.getTime());
			return time;
		}
	    
	    public static String getYearAfter1Month(){
			String year = getTimeStampAfter1Month().substring(0, 4);
			return year;
		}
	    
	    public static String getMonthEngAfter1Month(){
			String month = getTimeStampAfter1Month().substring(5, 7);
			return convertMonth(month);
		}
	    public static String getMonthAfter1Month(){
	  		String month = getTimeStampAfter1Month().substring(5, 7);
	  		return month;
	  	}
	    
	    public static String getDayAfter1Month(){
			String day = getTimeStampAfter1Month().substring(8, 10);
			return day;
		}
		
		public static String convertMonth(String key){
			Map<String, String> monthMap = new HashMap<String, String>();
			monthMap.put("01", "January");
			monthMap.put("02", "February");
			monthMap.put("03", "March");
			monthMap.put("04", "April");
			monthMap.put("05", "May");
			monthMap.put("06", "June");
			monthMap.put("07", "July");
			monthMap.put("08", "August");
			monthMap.put("09", "September");
			monthMap.put("10", "October");
			monthMap.put("11", "November");
			monthMap.put("12", "December");
			
			return (String) monthMap.get(key);
		}
		
	public static void main(String[] args) {
		String date = "2018-04-08 15:21:37.081";
		DateFormat df = new DateFormat();
//		System.out.println(df.formatDateToString(System.currentTimeMillis()));
		System.out.println(formatDate(new Date()));
//		System.out.println(df.formatDate(System.currentTimeMillis()));
//		System.out.println(df.formatDate("2015-07-16"));
//		System.out.println(df.formatToDate("2015-07-16"));
//		getTimeStamp();
		System.out.println(new Date());
//		for(String i:TimeZone.getAvailableIDs()){
//			System.out.println(i);
//		}
		/*
		 * 测试formatToDateUseTimezone(String date,String timezoneid1, String timezoneid2)
		 */
		System.out.println(df.formatToDateUseTimezone(formatDate(new Date()),"US/Eastern","Asia/Shanghai"));
		
		System.out.println(getTimeStamp());
		System.out.println(getCurrentYear());
	}
}
