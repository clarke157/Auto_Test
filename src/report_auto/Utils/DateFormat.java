package report_auto.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateFormat {
	public String formatDateToString(long date) {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	    return formatter.format(date);
	}
	 
	public String formatDate(Date date){       
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    return formatter.format(date);
	}
	
	public String formatDate(long date){       
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    return formatter.format(date);
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
		DateFormat df = new DateFormat();
		System.out.println(df.formatDateToString(System.currentTimeMillis()));
		System.out.println(df.formatDate(new Date()));
		System.out.println(df.formatDate(System.currentTimeMillis()));
		System.out.println(df.formatDate("2015-07-16"));
		System.out.println(df.formatToDate("2015-07-16"));
	}
}
