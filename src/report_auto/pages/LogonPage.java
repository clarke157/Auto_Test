package report_auto.pages;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class LogonPage extends LoadableComponent<LogonPage> {
	
	@FindBy(xpath = "//*[@id='cmdOK']")
	protected WebElement cmdOK;
	@FindBy(xpath = "//*[@id='CAMUsername']")
	protected WebElement userIdInput;
	@FindBy(xpath = "//*[@id='CAMPassword']")
	protected WebElement passwordInput;
	@FindBy(className="loginFooterButton")
	protected WebElement okButton;
	@FindBy(id="CAMNamespace")
	protected WebElement CAMNamespace;
	@FindBy(id="CAMUsername")
	protected WebElement CAMUsername;
	
	
	protected Integer DRIVER_WAIT_TIME = 5;
	private String produrl="https://w3-03.ibm.com/xxxx/xxxx/ServletGateway/servlet/Gateway";
	private String devurl="https://zcogwasd1.xxxx/transform/bacc/cognos/xxxx/ServletGateway/servlet/Gateway";
	private String title="Log On to IBM Cognos Software";
	public WebDriver driver;
	//constructor method#1
	public LogonPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	//constructor method#2
//	public LogonPage(WebDriver driver)throws Exception {
//		PageFactory.initElements(new AjaxElementLocatorFactory(driver, DRIVER_WAIT_TIME), this);
//	}
	
	@Override
	protected void load(){
		this.driver.get(devurl);
	}
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(driver.getTitle().contains(title));
	
	}
	//close browser
	public void quit(){
		this.driver.quit();
	}
	//log on
	public IBMCognosConnectionPage login_dev(String username, String pwd){
		driver.get(devurl);
		WebDriverWait wait = new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.visibilityOf(CAMNamespace));
	    Select droplist=new Select(CAMNamespace);
	    droplist.selectByValue("IBMIntranet");
		cmdOK.click();
	    wait.until(ExpectedConditions.visibilityOf(CAMUsername));
		userIdInput.sendKeys(username);
		passwordInput.sendKeys(pwd);
		okButton.click();
		return new IBMCognosConnectionPage(driver);
	}
	
	public IBMCognosConnectionPage login_prod(String username, String pwd){
		driver.get(produrl);
		WebDriverWait wait = new WebDriverWait(driver,10);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("CAMUsername")));
		userIdInput.sendKeys(username);
		passwordInput.sendKeys(pwd);
		okButton.click();
		return new IBMCognosConnectionPage(driver);
	}
	//return driver
	public WebDriver getDriver(){
		return driver;
	}

	public Object getPageSource() {
		return driver.getPageSource();
	}
	
}
