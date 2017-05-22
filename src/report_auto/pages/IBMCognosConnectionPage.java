package report_auto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IBMCognosConnectionPage {

	@FindBy(xpath = "//td[@class='tableText']/a[text()='SLMT']")
	protected WebElement SLMTLink;
	@FindBy(xpath = "//td[@class='tableText']/a[text()='Reports']")
	protected WebElement ReportsLink;
	@FindBy(xpath = "//td[@class='tableText']/a[text()='CM']")
	protected WebElement CMLink;
	@FindBy(xpath = "//td[@class='tableText']/a[text()='UAT']")
	protected WebElement UATLink;
	@FindBy(xpath = "//td[@class='tableText']/a[text()='Contractor Management Outlook report']")
	protected WebElement CMOLink;
	@FindBy(xpath = "//td[@class='tableText']/a[text()='OOBT Work in Progress-CR374069']")
	protected WebElement WIPLink;
	
	public WebDriver driver;
	//constructor method#
	public IBMCognosConnectionPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	public CMOFilterPage openCMOReport(){
		try {
		Thread.sleep(3000);
		this.SLMTLink.click();
		Thread.sleep(3000);
		this.ReportsLink.click();
		Thread.sleep(3000);
		this.CMLink.click();
		Thread.sleep(3000);
		this.CMOLink.click();
		Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new CMOFilterPage(driver);
}
		public CMOFilterPage openWIPReport(){
			try {
				this.SLMTLink.click();
				Thread.sleep(3000);
				this.ReportsLink.click();
				this.UATLink.click();
				this.WIPLink.click();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return new CMOFilterPage(driver);
	}
		public Object getPageSource() {
			return driver.getPageSource();
		}
	
}
