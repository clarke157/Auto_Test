/*
 * Purpose of this class:
 * Encapsulate assertEquals function to avoid program interrupt when assert failed.
 * Use a flag to indicate if the result is equal or not equal.
 */
package report_auto.Utils;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
public class Assertion {
	private static Log log = new Log(Assertion.class);
	public static boolean flag = true;
	public static List<Error> errors = new ArrayList<Error>();
	
	public static void begin(){
		flag = true;
	}
	
	public static void end(){
		Assert.assertTrue(flag);
	}
	
	public synchronized static boolean setFlag(boolean fla){
		flag=fla;
		return flag;
		}

	//program will not interrupt when not equals.
	public static void verifyEquals(Object actual, Object expected){
		try{
			if(actual instanceof Long){
				Long ex = Long.valueOf(String.valueOf(expected));
				Assert.assertEquals(actual, ex);
			}else{
				Assert.assertEquals(actual, expected);
			}
			log.info("verify success : "+actual);
		}catch(Error e){
			errors.add(e);
			setFlag(false);
		}
	}
	//program will not interrupt when not equals.
	public static void verifyEquals(Object actual, Object expected, String message){
		try{
			if(actual instanceof Long){
				Long ex = Long.valueOf(String.valueOf(expected));
				Assert.assertEquals(actual, ex, message);
			}else{
				Assert.assertEquals(actual, expected, message);
			}
			log.info("verify success : "+message+" "+actual);
		}catch(Error e){
			errors.add(e);
			setFlag(false);
		}
	}
	//program interrupt when not equals.
	public static void assertEquals(Object actual, Object expected){
		if(actual instanceof Long){
			Long ex = Long.valueOf(String.valueOf(expected));
			Assert.assertEquals(actual, ex);
		}else{
			Assert.assertEquals(actual, expected);
		}
		log.info("verify success : "+actual);
	}
	//program interrupt when not equals.
	public static void assertEquals(Object actual, Object expected, String message){
		if(actual instanceof Long){
			Long ex = Long.valueOf(String.valueOf(expected));
			Assert.assertEquals(actual, ex, message);
		}else{
			Assert.assertEquals(actual, expected, message);
		}
		log.info("verify success : "+message+" "+actual);
	}
	
	public static void contains(String value, String sub){
		try{
			if(value.contains(sub)){
				log.info("verify success : ["+value+"] contains ["+sub+"].");
			}else{
				Assert.fail("verify failed : ["+value+"] not contains ["+sub+"].");
			}
		}catch(Error e){
			errors.add(e);
			setFlag(false);
		}
	}
	
public static void contains(String value, String sub, String message){
	try{
		if(value.contains(sub)){
			log.info("verify success : "+message+" ["+value+"] contains ["+sub+"].");
		}else{
			Assert.fail("verify failed : "+message+" ["+value+"] not contains ["+sub+"].");
		}
	}catch(Error e){
		errors.add(e);
		setFlag(false);
//		flag = false;
	}
}

/*
 * the following code is used to debug the thread synchronization
 * 
 */
public synchronized boolean setFlag(){
	if (flag==true) {
		flag=false;
	}else if (flag==false){
		flag=true;
	}
	return flag;
	}

public int d=100;
public synchronized int getDemo(){
	return d = d -1;
	}
}
