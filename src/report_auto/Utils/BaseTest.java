package report_auto.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.safari.SafariDriver;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;


public class BaseTest {

	public static WebDriver driver;
	protected JSONObject env;
//	public static String downloadFilePath="C:\\at_IBM\\SLMT CLD\\Reports";
	public static String downloadFilePath="C:\\Users\\IBM_ADMIN\\Downloads";
	public static String downloadFile="\\Contractor Management Outlook report.xlsx";
	public static String sheetName = "Active";
	public static Map<String,String> CMO_sourcedata;
	public static Map<String,String> CMO_targetdata;
	public static String CMO_SQL;
	public static String serialnum;
	
	public BaseTest(JSONObject env) {
	this.env = env;
    }
	
	public static WebDriver getDriver(JSONObject env){
		WebDriver driver = null;
		try {
			if(getProperty("browser",env).equals(BrowserType.FIREFOX)){
				driver = new FirefoxDriver();
			}else if(getProperty("browser",env).equals(BrowserType.IEXPLORE)){
				driver = new InternetExplorerDriver();
			}else if(getProperty("browser",env).equals(BrowserType.CHROME)){
				driver = new ChromeDriver();
			}else if(getProperty("browser",env).equals(BrowserType.SAFARI)){
				driver = new SafariDriver();
			}else{
				System.out.println("browser is not supported.  Using default Firefox instead: " + getProperty("browser",env));
				driver = new FirefoxDriver();
			}
			return driver;
		} catch (Exception e) {
			System.out.println("Could not creae a web driver: " + e.getMessage());
			return null;
		}
	}
	
	public static Iterator<Object[]> envData() {
		//Get execution environments 
		Object[] environments;
		try {
			environments = BaseTest.prepareExecutionEnvironments("report_auto/configFile.json");
			Object[][] data = new Object[environments.length][4];
			for(int x = 0; x < environments.length;x++){
				JSONObject env = (JSONObject)environments[x];
				data[x][0] = env;
				data[x][1] = getProperty("environment",env);
				data[x][2] = getProperty("browser",env);
				data[x][3] = getProperty("reportType",env);
			}
			return Arrays.asList(data).iterator();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	protected static Object[] prepareExecutionEnvironments(String configFileClasspath) throws IOException{
		JSONObject config = JSONObject.parse(ClassLoader.getSystemResourceAsStream(configFileClasspath));
		JSONArray reportType = null;
		JSONArray locales = null;
//		JSONArray mobile_platform = null;
		JSONArray browser = null;
		
		JSONObject env = (JSONObject)config.get((String)config.get("env"));
		if (browser == null)
			browser = (JSONArray)config.get("browser");
		if (locales == null)
			locales = (JSONArray)config.get("locale");
		if(reportType==null)
			reportType = (JSONArray)config.get("reportType");
		JSONObject Filters = (JSONObject)config.get("filters");
		JSONObject dbInfo = (JSONObject)config.get("dbInfo");
		JSONArray executionEnvironments = new JSONArray();
		for(int k=0;k<reportType.size();k++){
			for(int x = 0; x < browser.size();x++){
				JSONObject o = JSONObject.parse(env.toString());
				o.putAll(Filters);
				o.putAll(dbInfo);
				o.put("reportType", reportType.get(k));
				o.put("browser", browser.get(x));
				executionEnvironments.add(o);
			}
		}
		return executionEnvironments.toArray();
	}	
	public static String getProperty(String propertyName,JSONObject env){
		//Check if property is system property
		String value=null;
		if(value ==  null || propertyName.equals("locale") || propertyName.equals("browser")||propertyName.equals("reportType")||propertyName.equals("env")){
			value = (String)env.get(propertyName);
		}
		return value;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getFiltersSpecificReport(){
		JSONArray filters = (JSONArray) env.get("filters." + env.get("reportType").toString().trim().toLowerCase());
		if (filters == null){
			filters = (JSONArray) env.get("filters.default." + env.get("reportType").toString().trim().toLowerCase());
		}
		ArrayList filtersList = new ArrayList(filters);		
		return filtersList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList getDbInfo(){
		JSONArray db = (JSONArray) env.get("DB."+env.get("env"));
		if (db == null){
			db = (JSONArray) env.get("DB.DEFAULT");
		}
		ArrayList dbInfoList = new ArrayList(db);		
		return dbInfoList;
	}
	
	public boolean isElementPresent(By by) {
		  try {
		   driver.findElement(by);
		   return true;
		  } catch (NoSuchElementException e) {
		   return false;
		  }
		 }

//	public static FirefoxProfile FirefoxDriverProfile() throws Exception {
//	FirefoxProfile profile = new FirefoxProfile();
//	profile.setPreference("browser.download.dir", downloadFilePath);
//	profile.setPreference("browser.download.folderList", 2); //browser.download.folderList 设置Firefox的默认 下载 文件夹。0是桌面；1是“我的下载”；2是自定义
//	profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/xls, application/octet-stream, application/vnd.ms-excel, text/csv, application/zip");
//	return profile;
//	}
	public static FirefoxProfile FirefoxDriverProfile() throws Exception {
		FirefoxProfile profile=new FirefoxProfile();
		profile.setPreference("browser.download.dir", downloadFilePath);
		profile.setPreference("browser.download.folderList", 2);
//		profile.setPreference("browser.download.manager.showWhenStarting", false);
//		profile.setPreference("browser.helperApps.neverAsk.openFile", "application/x-msdownload");
//		profile.setPreference("browser.helperApps.neverAsk.openFile", "application/octet-stream,"
//				+ "application/exe,text/csv,application/pdf,application/x-msexcel,application/excel,"
//				+ "applicaiton/x-excel,application/vnd.ms-excel,image/png,"
//				+ "image/jpeg,text/html,text/plain,application/msword,applicaiton/xml,application/x-msdownload");
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/xls, application/octet-stream, application/vnd.ms-excel, text/csv, application/zip ,application/xlsx,application/x-msdownload,application/x-msexcel,application/excel");
//		profile.setPreference("browser.helperApps.alwaysAsk.force",true);
//		profile.setPreference("browser.download.manager.alertOnExeOpen", true);
//		profile.setPreference("browser.download.manager.useWindow", false);
//		profile.setPreference("browser.download.manager.showAlertOnComplete", false);
//		profile.setPreference("browser.download.manager.closeWhenDone", false);
		return profile;
	}
	
	public static void main(String[] args) {
		Iterator<Object[]> it = BaseTest.envData();
		while (it.hasNext()){
			Object[] value = it.next();
			for (int i=0;i<value.length;i++){
				System.out.println(value[i]);
			}

		}
	}

}
