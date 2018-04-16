package report_auto.Utils;

	import java.text.ParseException;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;

	import net.sf.json.JSONArray;
	import net.sf.json.JSONException;
	import net.sf.json.JSONObject;
	
//	import org.json.JSONArray;
//	import org.json.JSONException;
//	import org.json.JSONObject;

	/**
	 * 使用json-lib构造和解析Json数据
	 */
	
	
	public class JsonUtil {

	    /**将Bean转换成Map
	     * 将Map转换Json数据
	     * 或者直接将Bean转换成Json
	     */public static String BuildJson(Param param) throws JSONException {

	        
	    	 Map<String, String> map1 = new HashMap<String, String>();
	         map1.put("no", param.getNo());
	         map1.put("name", param.getName());
	         map1.put("age", param.getAge());
	         map1.put("sex", param.getSex());
	    	 // 将Param格式转换直接转换成JSONArray格式，例如： [{"no":"123456","actResu":"","pass":"","sex":"男","name":"张三","age":"20","expResu":"","desc":""}]
	         // 或者将Param格式转换直接转换成JSONObject格式，例如： {"no":"123456","actResu":"","pass":"","sex":"男","name":"张三","age":"20","expResu":"","desc":""}
//	         JSONArray jo1 = JSONArray.fromObject(param);
	         JSONObject jo1 = JSONObject.fromObject(param);
	        System.out.println("JSONArray对象数据格式："+jo1.toString());
	    	 // 将MAP格式转换成JSONArray格式，例如： [{"no":"123456","sex":"男","name":"张三","age":"20"}]
		    JSONArray ja = JSONArray.fromObject(map1);
	    	 // 将MAP格式转换成JSONObject格式，例如：{"no":"123456","sex":"男","name":"张三","age":"20"}
	        JSONObject jo = JSONObject.fromObject(map1);
	        System.out.println("JSONArray对象数据格式："+ja.toString());
	        System.out.println("最终构造的JSON数据格式："+jo.toString());
	        return jo.toString();

	    }

	    /**
	     * 解析String格式Json数据，返回JSONArray
	     *
	     */public static JSONArray ParseJson(String jsonString) throws JSONException,
	            ParseException {
	    	 JSONArray ja = JSONArray.fromObject(jsonString);
	        return ja;
	    }
	     
	     
	     /*
	      * The following code is used for debugging.
	      */
	     public static void main(String[] args) throws JSONException{
	    	 Param pa = new Param();
	    	 pa.setName("张三");
	    	 pa.setSex("男");
	    	 pa.setNo("123456");
	    	 pa.setAge("20");

	    	 String str = BuildJson(pa);
	     }
}
