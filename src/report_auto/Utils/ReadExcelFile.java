package report_auto.Utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import testautomation.util.Param;

public class ReadExcelFile {
	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
     public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
 
     public static final String EMPTY = "";
     public static final String POINT = ".";
     public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
     public static final String PROCESSING = "Processing...";
     public static Map<String, String> map = new HashMap<String, String>();
     public static int fromRow=4;
     public static int row=9;

	public Map<String, String> getData_serial_csa_id(String directory, String fileName,String sheetName) throws IOException {
  		String path=directory+"\\"+fileName;
        FileInputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
  		Map<String, String> resultmap = new HashMap<String, String>();
        System.out.println(PROCESSING + path);
          if (path == null || EMPTY.equals(path)) {
              if (xssfWorkbook!=null){xssfWorkbook.close();}
              return null;
          } else {
              String postfix = getPostfix(path);
              if (!EMPTY.equals(postfix)) {
                  if (OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                	  XSSFSheet xssfsheet=xssfWorkbook.getSheet(sheetName);
                	  resultmap=getSheetDataForSepcificColumn(xssfsheet);
                  } //else if (OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
//                      HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
//                	  HSSFSheet sheet=hssfWorkbook.getSheet(sheetName);
//                	  records1= getSheetData(sheet);          }
              } else {
                  System.out.println(path + NOT_EXCEL_FILE);
              }
          }
          if (xssfWorkbook!=null){xssfWorkbook.close();}
          return resultmap;
      }
	
    private Map<String, String> getSheetDataForSepcificColumn(XSSFSheet xssfsheet){
    	int serial_num=0;
    	int csa_id=0;
        int rowCount=xssfsheet.getLastRowNum()-xssfsheet.getFirstRowNum();
        int totalRowNum=rowCount+1;
    	System.out.println("The total number of row is: "+totalRowNum);
//Get the column num for serial_num and csa id
        XSSFRow title=xssfsheet.getRow(3);
        for (int colNum=0;colNum<title.getLastCellNum();colNum++){
            XSSFCell field=title.getCell(colNum);
            if (this.getCellValue(field).equalsIgnoreCase("Serial Number")) {
            	System.out.println("Serial number is in at column #"+colNum);
            	serial_num=colNum;
            }else if(this.getCellValue(field).equalsIgnoreCase("CSA ID")){
            	System.out.println("CSA ID is in at column #"+colNum);
            	csa_id=colNum;
            };
        }
//Determing how many rows will be selected from the report
//        for(int rowNum =1;rowNum<rowCount+1; rowNum++){
        for(int rowNum =fromRow;rowNum<fromRow+row; rowNum++){
       	 XSSFRow row=xssfsheet.getRow(rowNum);
//       	 System.out.println(getCellValue(row.getCell(serial_num)));
//       	 System.out.println(getCellValue(row.getCell(csa_id)));
       		map.put(getCellValue(row.getCell(serial_num)), getCellValue(row.getCell(csa_id)));
        }
        return map;
    }
    
//For Excel 2007-2010, postfix is "xlsx"
	@SuppressWarnings("deprecation")
	private String getCellValue(XSSFCell xssfCell) {
		String cellvalue="";
        DataFormatter formatter = new DataFormatter();
        if (null != xssfCell) {   
            switch (xssfCell.getCellType()) {   
            case Cell.CELL_TYPE_NUMERIC:
            	if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(xssfCell)) {
                  cellvalue = formatter.formatCellValue(xssfCell);
              } else {
                  double value = xssfCell.getNumericCellValue();
                  int intValue = (int) value;
                  cellvalue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
              }
            	break;
			case Cell.CELL_TYPE_STRING: 
				cellvalue=xssfCell.getStringCellValue(); 
                break;   
            case Cell.CELL_TYPE_BOOLEAN:   
            	cellvalue=String.valueOf(xssfCell.getBooleanCellValue()); 
                break;   
            case Cell.CELL_TYPE_FORMULA:  
            	cellvalue=String.valueOf(xssfCell.getCellFormula());   
                break;   
            case Cell.CELL_TYPE_BLANK: 
            	cellvalue="";   
                break;   
            case Cell.CELL_TYPE_ERROR: 
            	cellvalue="";   
                break;   
            default:   
            	cellvalue="UNKNOWN TYPE";   
                break;   
            }   
        } else {   
            System.out.print("-");   
        }
        return cellvalue.trim();
    }
     private static String getPostfix(String path) {
         if (path == null || EMPTY.equals(path.trim())) {
             return EMPTY;
         }
         if (path.contains(POINT)) {
             return path.substring(path.lastIndexOf(POINT) + 1, path.length());
         }
         return EMPTY;
     }
     
     public String getPrimaryKey(String directory, String fileName,String sheetName) throws IOException{
    	 String primarykey = null;
    	 primarykey = mapkeyToString(getData_serial_csa_id(directory, fileName,sheetName));
		return primarykey;
     }
     
     public String mapkeyToString(Map<String,String> map){
    	 String str = "";
    	 String str1 = "";
    	 String str2 = "";
    	 String keystring="";
    	 Iterator<String> it = map.keySet().iterator();
    	 while (it.hasNext()) {
    	   str = it.next().toString();
    	   str1 = "'"+str;
    	   str2 +=str1+"',";
//    	   System.out.println(str2);
    	 }keystring = str2.substring(0, str2.length()-1);
		return keystring;
     }
     
 	public void compareData(Map<String,String> targetdata, Map<String,String> sourcedata){
 		if(targetdata == sourcedata)
        	{new FileUtil().WriteResultToFile("All records in "+targetdata+" and "+sourcedata+" are matching"+"\r\n");
            return;};
        if(targetdata == null || sourcedata == null)
        	{new FileUtil().WriteResultToFile("Records are NOT matching because either null or both null"+"\r\n");
            return;};
//        if(targetdata.size()!= sourcedata.size())
//        	{new FileUtil().WriteResultToFile("Maps do not have the same size, targetsize: "+targetdata.size()+"sourcesize: "+sourcedata.size()+"\r\n");
//        	return;};
        Iterator<Entry<String, String>> iter1 = targetdata.entrySet().iterator();
        while(iter1.hasNext()){
            Map.Entry<String, String> entry1 = (Entry<String, String>) iter1.next();
            String m1value = entry1.getValue() == null?"":entry1.getValue();
            String m2value = sourcedata.get(entry1.getKey())==null?"":sourcedata.get(entry1.getKey());
            if (m1value.equals(m2value)) {
//if the key has different values, then write the key/value to C:\\at_IBM\\SLMT CLD\\SLIC Reports\\compare result.txt file.
            	new FileUtil().WriteResultToFile(DateFormat.getTimeStamp()+" "+entry1.getKey().toString()+" "+m1value+" "+m2value+" "+"matching"+"\r\n");
            } else if(!m1value.equals(m2value)){
            	new FileUtil().WriteResultToFile(DateFormat.getTimeStamp()+" "+entry1.getKey().toString()+" "+m1value+" "+m2value+" "+"not matching"+"\r\n");
            }
        }
    }
 	
 	public static List<Param> read() throws Exception{
		 HSSFWorkbook wb = new HSSFWorkbook();
		 HSSFSheet s = wb.createSheet();
		 HSSFRow row = s.createRow(0);
		 HSSFCell cell = row.createCell((int)0,0);

		 //－－－－－－－－－－－－从xls读出数据
		 wb = new HSSFWorkbook(new FileInputStream("C:\\learn\\test.xls"));
		 s = wb.getSheetAt(0);
		 
		 //获得EXCEL行数
		 int rowNums=s.getLastRowNum();
		 //获得Excell列数
		 //int columnNum=r.getPhysicalNumberOfCells();
		 
		 List<Param> params=new ArrayList<Param>();
		 for(int i=1;i<=rowNums;i++){
			 HSSFRow r = s.getRow(i);
			 cell=r.getCell(0);
			 Param param= new Param();
			 param.setNo(r.getCell(0).getStringCellValue());
			 param.setName(r.getCell(1).getStringCellValue());
			 param.setAge(r.getCell(2).getStringCellValue());
			 param.setSex(r.getCell(3).getStringCellValue());
			 param.setExpResu(r.getCell(4).getStringCellValue());
//			 System.out.println(cell.getRichStringCellValue());
			 params.add(param);
		 }
		 return params;

	}

	/**
	  * 写入Excel，在任意坐标处写入数据。
	  * String value：你要输入的内容
	  * int x ：行坐标，Excel从 0 算起
	  * int y   ：列坐标，Excel从 0 算起
	  */
		public static void writeCell(String filePath,int x,int y,String value) {
			try {
				// 创建Excel的工作书册 Workbook,对应到一个excel文档
				HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath));
				HSSFSheet sheet=wb.getSheetAt(0);
				HSSFRow row=sheet.getRow(x);
				HSSFCell cell=row.getCell((short) y);
				cell.setCellValue(value);
				FileOutputStream os;
				os = new FileOutputStream(filePath);
				wb.write(os);
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
    /*
     * The following code are used for debug
     */
	public static void main(String[] args) throws IOException {
		ReadExcelFile mytest=new ReadExcelFile();
//		Object[][] results=mytest.getExcelDataForAllSheets("C:\\downloadFiles\\ExcelSampleData.xls");
//		Map<String,String> results=mytest.getData_serial_csa_id("C:\\downloadFiles", "ExcelSampleData.xlsx", "Sheet2");
		Map<String,String> results=mytest.getData_serial_csa_id("C:\\at_IBM\\SLMT CLD\\SLIC Reports", "Contractor Management Outlook report.xlsx", "Active");
			Iterator<Map.Entry<String, String>> it = results.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<String, String> entry = it.next();
				System.out.println(entry.getKey()+" "+entry.getValue());
			}
			
			String primarykey = mytest.getPrimaryKey("C:\\at_IBM\\SLMT CLD\\SLIC Reports", "Contractor Management Outlook report.xlsx", "Active");
			System.out.println(primarykey);
//		for(int i=0;i<results.size().length;i++){
//			for(int j=0;j<results[i].length;j++){
//				System.out.print(results[i][j]+" ");
//				}
//			System.out.print("\n");
//			}
	}

}
