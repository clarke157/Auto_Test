package report_auto.Utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class RobotUtil {
	
	public void pressTabKey(){
		Robot robot=null;
		try{
			robot=new Robot();
		} catch (AWTException e){
			e.printStackTrace();
		}
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
	}
	public void pressDownKey(){
		Robot robot=null;
		try{
			robot=new Robot();
		} catch (AWTException e){
			e.printStackTrace();
		}
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
	}
	public void pressEnterKy(){
		Robot robot=null;
		try{
			robot=new Robot();
		} catch (AWTException e){
			e.printStackTrace();
		}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	public void setAndctrlVClipboardData(String str){
		StringSelection selection = new StringSelection(str);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
		
		Robot robot=null;
		try{
			robot=new Robot();
		} catch (AWTException e){
			e.printStackTrace();
		}
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}
	

}
