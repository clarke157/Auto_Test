package report_auto.pages;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import testautomation.util.Log;
import testautomation.util.RobotUtil;

public class CMOFilterPage {
	@FindBy(xpath = "//input[@value='GBS']")
	protected WebElement GBS;
	@FindBy(xpath = "//label[contains(text(),'United')]/preceding-sibling::div/following::input")
//	@FindBy(partialLinkText = "United States")
	protected WebElement UnitedStatus;
	@FindBy(xpath = "//input[@value='Y']")
	protected WebElement YES;
	@FindBy(xpath = "//button[text()='Finish']")
	protected WebElement finishButton;
	@FindBy(xpath = "//input[@value='TSS']/parent::div/parent::div/parent::div")
	//	aria-label="Multi select prompt for: IMT_GMT. Use the up or down arrow key to navigate through the options"
	protected WebElement HRLOBGroup;
	public WebDriver driver;
	public static String downloadFilePath="C:\\Users\\IBM_ADMIN\\Downloads";
	public static String downloadFile="\\Contractor Management Outlook report.xlsx";
	//constructor method#
	public CMOFilterPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public void configFilters(String s){
//		String xpath = "//input[@value='"+s+"']";
		String xpath = "//input[@id='"+s+"']";
		driver.findElement(By.xpath(xpath)).click();;
	}
	
	public Object getPageSource() {
		return driver.getPageSource();
	}
	
//	((JavascriptExecutor ) driver).executeScript("$(\"div.mb_btnbox\").find(\"span:eq(1)\").click()");
	public void scrollToBottom(){
		String js = "document.getElementsByClassName('clsCheckBoxList pv')[1].scrollTop=10000";
//		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",webelement);
		((JavascriptExecutor) driver).executeScript(js);
		try{
			Thread.sleep(3000);
		} catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	public void scrollToElement(WebElement webelement){
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",webelement);
		try{
			Thread.sleep(3000);
		} catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	@SuppressWarnings("rawtypes")
	public void switchToDownloadWindow(){
		String currentHandle = driver.getWindowHandle();
		Set handles = driver.getWindowHandles();
		handles.remove(currentHandle);
		if (handles.size() > 0) {
		    try{
		        driver.switchTo().window(handles.iterator().next().toString());
		    }catch(Exception e){
		        System.out.println(e.getMessage());
		    }
		}
		System.out.println("Did not find window");
		}
	
	public static int detect_download_excel_window() throws IOException, InterruptedException {
		
		String script = "DetectDownloadExcelWindow.vbs";
		String executable = "C:\\Windows\\SysWOW64\\wscript.exe";
		String cmdArr [] = {executable, script};
		Process result = Runtime.getRuntime().exec(cmdArr);
		result.waitFor(); // wait for the program closes
		return result.exitValue();
	}
	
	public void save_excel_to_default_location() throws IOException, InterruptedException {
		
		String script = "SaveExcelToDefaultLocation.vbs";
		String executable = "C:\\Windows\\SysWOW64\\wscript.exe";
		String cmdArr [] = {executable, script};
		Process result = Runtime.getRuntime().exec(cmdArr);
		result.waitFor(); // wait for the program closes
	}
	public void runCMOReport(){
		Log log = new Log();
		LogonPage LogonPage = new LogonPage(driver);
//		LogonPage.get();
		IBMCognosConnectionPage IBMCognosConnectionPage = LogonPage.login_prod("xxxx@xx.xx.com","123456xxxx");
		Assert.assertTrue(IBMCognosConnectionPage.getPageSource().toString().contains("IBM Cognos Connection"));
		CMOFilterPage CMOFilterPage = IBMCognosConnectionPage.openCMOReport();
		Assert.assertTrue(CMOFilterPage.getPageSource().toString().contains("Contractor Management Outlook report - IBM Cognos Viewer"));
		try {
		WebDriverWait wait = new WebDriverWait(driver,60);
	    wait.until(ExpectedConditions.visibilityOf(GBS));
	    CMOFilterPage.GBS.click();
		Thread.sleep(3000);
	    CMOFilterPage.scrollToBottom();
	    CMOFilterPage.UnitedStatus.click();
		Thread.sleep(3000);
	    CMOFilterPage.YES.click();
		Thread.sleep(3000);
		//if file exists, delete it
		log.info("file path is: "+downloadFilePath+downloadFile);
		File file = new File(downloadFilePath+downloadFile);
		if (!file.isDirectory()&&file.exists()) {
			file.delete();
			Assert.assertTrue(!file.exists(), "Old outputFile exists, please delete it");
		}
		CMOFilterPage.finishButton.click();
//		Thread.sleep(300000);
		
		/*
		 * directly saving excel to default loaction using vbscript.
		 */
		save_excel_to_default_location();
		
		/*
		 * 1. detect dowload window using vbscript
		 * 2. move down then click enter using rebot object
		 */
//		int isWindowAppear = detect_download_CSV_Window();
//		log.info("isWindowAppear=: "+isWindowAppear);
//		if(isWindowAppear==1){
//			RobotUtil robot = new RobotUtil();
//			robot.pressDownKey();
//			Thread.sleep(2000);
//			robot.pressEnterKy();
//		} else {
//			System.out.println("There is no download window pops up.");
//		}
		
//		switchToDownloadWindow();
		
//		Set <String> allWindowsId = driver.getWindowHandles();
//        for(String windowID:allWindowsId){
//            if (driver.switchTo().window(windowID).getTitle().contains("Contractor Management Outlook report")){
//                driver.switchTo().window(windowID);
//                WebElement save = driver.findElement(By.partialLinkText("Save File"));
//                WebElement ok = driver.findElement(By.partialLinkText("OK"));
//                save.click();
//                ok.click();
//                break;
//            }
//              System.out.println(windowID);
//        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
