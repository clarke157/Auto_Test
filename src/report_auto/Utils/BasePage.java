package report_auto.Utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	protected WebDriver driver;
	protected WebDriverWait wait;
	protected final static int DRIVER_WAIT_TIME = 200;

	public BasePage(WebDriver driver){
		this.driver = driver;
		wait = new WebDriverWait(driver, DRIVER_WAIT_TIME);
	}
	protected void takeScreenshot(WebDriver driver,String fileName) {
		File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String imageFileDir = System.getProperty("selenium.screenshot.dir"); 
		if(imageFileDir == null){
			imageFileDir = System.getProperty("java.io.tmpdir");
		}
		try {
			fileName =  fileName+ ".png";
			FileUtils.copyFile(screenShot,new File(System.getProperty("user.dir") + "\\..\\screenshots",fileName));
		} catch (IOException ioe) {
			throw new RuntimeException(ioe.getMessage(), ioe);
		}
	}
	public WebDriverWait getWait() {
		return wait;
	}
	public void setWait(WebDriverWait wait) {
		this.wait = wait;
	}
	
}
