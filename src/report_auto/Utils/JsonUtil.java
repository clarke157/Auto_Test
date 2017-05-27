package report_auto.Utils;

	import java.text.ParseException;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;

	import org.json.JSONArray;
	import org.json.JSONException;
	import org.json.JSONObject;

	/**
	 * 使用json-lib构造和解析Json数据
	 */
	public class JsonUtil {

	    /**将Bean转换成Map
	     * 将Map转换Json数据
	     */public static String BuildJson(Param param) throws JSONException {

	        
	    	 Map<String, String> map1 = new HashMap<String, String>();
	         map1.put("no", param.getNo());
	         map1.put("name", param.getName());
	         map1.put("age", param.getAge());
	         map1.put("sex", param.getSex());
//	         map1.put("expResu", param.getExpResu());
	    	 // JSON格式数据解析对象
	        JSONObject jo = new JSONObject();
	        // 将Map转换为JSONArray数据
	        JSONArray ja = new JSONArray();
	        ja.put(map1);
//	        System.out.println("JSONArray对象数据格式："+ja.toString());
	        jo.put("map", ja);
//	        System.out.println("最终构造的JSON数据格式："+jo.toString());
	        return jo.toString();

	    }

	    /**
	     * 解析Json数据
	     *
	     */public static JSONArray ParseJson(String jsonString) throws JSONException,
	            ParseException {

	        JSONObject jo = new JSONObject(jsonString);
	        JSONArray ja = jo.getJSONArray("map");
//	        System.out.println(ja.getJSONObject(0).getString("name"));
	        return ja;
	    }
}
