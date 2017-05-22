/*
 * This test case will:
 * 1. Read CMO report from the exported CMO report(xlsx format).
 * 2. Get the target data("SERIAL_NUM", "CAND_SERVICE_REQUEST_ID").
 * 3. Retrieve source data("SERIAL_NUM", "CAND_SERVICE_REQUEST_ID") from database based on SERIAL_NUM.
 * 4. Compare the target data with the source data, write the compare results to local file(matching or not matching for every record).
 */

package report_auto.testcase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.ibm.json.java.JSONObject;

import testautomation.BaseTest;
import testautomation.page.CMOFilterPage;
import testautomation.util.DBUtil;
import testautomation.util.ReadExcelFile;

public class TestCMOReport extends BaseTest {
	public TestCMOReport(JSONObject env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	@Factory(dataProvider="SLICDataProvider")
	public TestCMOReport(JSONObject env,String environmentName,String browserName,String productType) {
		super(env);
	}
	
	@DataProvider(name="SLICDataProvider")
	public static Iterator<Object[]> data(){
		return envData();
	}
	//@BeforeMethod
//	public void beforeMethod() throws Exception{
//		driver=new FirefoxDriver(); 
//		driver=new FirefoxDriver(FirefoxDriverProfile()); 
//	}
	
	//@AfterMethod
	public void afterMethod(){
		driver.quit(); 
	}
	@Test
	public void testCMOReport() throws Exception{
		driver = super.getDriver(env);
		//run CMO report in SLIC dev, then save the report to local file
		CMOFilterPage cmofilterpage = new CMOFilterPage(driver);
		cmofilterpage.runCMOReport();
		//get target date from the CMO report on local disk
		Thread.sleep(3000);
		Map<String, String> targetdata = this.getSerialIdCsaIdFromReport();
		//get source date from the CLD PROD DB
		Map<String, String> sourcedata = this.getSouceData();
		//compare the target data with the source data,write the result in txt file
		new ReadExcelFile().compareData(targetdata, sourcedata);
	}
	
	public Map<String,String> getSerialIdCsaIdFromReport() throws IOException{
		ReadExcelFile readexcelfile=new ReadExcelFile();
		CMO_targetdata = readexcelfile.getData_serial_csa_id(downloadFilePath, downloadFile, sheetName);
		serialnum = readexcelfile.getPrimaryKey(downloadFilePath, downloadFile, sheetName);
		CMO_SQL = "SELECT SER_NUM,CAND_SR_ID"
				+ " FROM CIDS.SLIC_CONT CONT WHERE CONT.SER_NUM IN ("
				+serialnum+") WITH UR;";
		System.out.print(CMO_SQL);
		return CMO_targetdata;
	}

	@SuppressWarnings("rawtypes")
	public Map<String,String> getSouceData(){
		ArrayList dbinfo = getDbInfo();
		DBUtil dbutil = new DBUtil((String)dbinfo.get(0),(String)dbinfo.get(1),(String)dbinfo.get(2),(String)dbinfo.get(3),(String)dbinfo.get(4));
		dbutil.ConnectDb2();
		CMO_sourcedata = dbutil.getSourceDataFormatted(CMO_SQL, "SER_NUM", "CAND_SR_ID");
		return CMO_sourcedata;
	}
}
