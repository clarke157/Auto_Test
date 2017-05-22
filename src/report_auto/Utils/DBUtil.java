package report_auto.Utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.testng.annotations.Test;

public class DBUtil {
    private static Connection conn;
	private static Statement stmt;
	private static ResultSet rs;
	private String xmlPath;
	private String address;
	private String instance;
	private static String defaultInstance = "1234";
	private static String defaultAddress = "xxx.xxx.ibm.com";
	private String dbName;
	private String userName;
	private String password;

	public DBUtil(String address,String instance,String dbName, String userName,String pwd) {
		conn = null;
		stmt = null;
		rs = null;
		this.dbName = dbName;
		this.userName = userName;
		this.password = pwd;
		this.address = address;
		this.instance = instance;
	}
	
	public DBUtil() {
	}

	public boolean ConnectDb2()
	{
		/**
		 * 6789 is the default port for JDBC
		 */
		String url = "jdbc:db2://" + this.address
		+ ":"+this.instance+"/" + this.dbName;
		String user = this.userName;
		String password = this.password;
		try{
			//Class.forName("COM.ibm.db2.jdbc.net.DB2Driver").newInstance();
			//type 4 driver is used here
			Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
			// System.out.println("["+user+"] ["+password+"]");
			conn = DriverManager.getConnection(url,user,password);
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		if(conn != null){
			return true;
		}else{
			return false;
		}
	}
	
	public Connection connectMysql(String url, String username, String pwd)  {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
//			"url example: jdbc:mysql://192.168.1.12/db","user","password";
			Connection connector = DriverManager.getConnection(url, username, pwd);
			conn = connector;
			return connector;
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;				
	}
	
	public boolean disconnectDB()
	{
		try
		{
			conn.close();
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
		
		if(conn != null){
			return false;
		}else{
			return true;
		}
	}
	
	public Map<String, String> getSourceDataFormatted(String sqlStatement,String columnLabel1,String columnLabel2){
		Map<String, String> result = new HashMap<String, String>();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlStatement);
			// Get result set meta data
	        ResultSetMetaData rsmd = rs.getMetaData();
	        int numColumns = rsmd.getColumnCount();
	        while(rs.next())
			{
//	        	System.out.println(rs.getRow());
		        for (int i=1; i<numColumns+1; i++) {
		            result.put(rs.getString(columnLabel1), rs.getString(columnLabel2));
		        }
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
    /*
     * The following code are used for debug
     */

	@Test
	public static void main(String[] args) throws IOException {
//		ReadExcelFile readexcelfile=new ReadExcelFile();
//		String serialnum = readexcelfile.getPrimaryKey("C:\\at_IBM\\SLMT CLD\\SLIC Reports", "Contractor Management Outlook report.xlsx", "Active");
//		String CMO_SQL = "SELECT SERIAL_NUM,CAND_SERVICE_REQUEST_ID"
//				+ " FROM CIDM.SLIC_CONTRACTOR CONT WHERE CONT.SERIAL_NUM IN ("
//				+serialnum+") WITH UR;";
//		System.out.print(CMO_SQL);
		
		String CMO_SQL = "SELECT SERIAL_NUM,CAND_SERVICE_REQUEST_ID"
		+ " FROM CIDD.SLIC_CONT CONT WHERE CONT.SER_NUM IN ('abcd','32sd') WITH UR;";
		DBUtil dbutil = new DBUtil("blew.bowwr.ibm.com", "50112", "M12312", "XIKAI", "111111");
		dbutil.ConnectDb2();
		Map<String,String>CMO_sourcedata = dbutil.getSourceDataFormatted(CMO_SQL, "SER_NUM", "CAND_SR_ID");

		Map<String,String> results=CMO_sourcedata;
			Iterator<Map.Entry<String, String>> it = results.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<String, String> entry = it.next();
				System.out.println(entry.getKey()+" "+entry.getValue());
			}

//		for(int i=0;i<results.size().length;i++){
//			for(int j=0;j<results[i].length;j++){
//				System.out.print(results[i][j]+" ");
//				}
//			System.out.print("\n");
//			}
	}
}
