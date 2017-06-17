package report_auto.Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class FileUtil {
	public static String compareResultFilePath="C:\\at_IBM\\SLMT CLD\\SLIC Reports\\compare result.txt";
	public void WriteResultToFile(String str) {
	        try {
	            FileWriter fw = new FileWriter(compareResultFilePath, true);
	            BufferedWriter bw = new BufferedWriter(fw);
	            bw.append(str);
//	            bw.write("abc\r\n ");
	            bw.close();
	            fw.close();
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	
	/*
	 * delete all files and directory from specific directory.
	 * include the specific directory
	 */
	public static boolean deleteFolder(String path) {  
		//automatically add file separator when path is not end with file separator
//		if (!path.endsWith(File.separator)) {  
//			path = path + File.separator;  
//		}  
		File dirFile = new File(path);  
		if (!dirFile.exists() && !dirFile.isDirectory()) {  
			return false;
		}  
		//delete files in specific directory and the sub directory
		//the flag indicates if delete the sub directories
		boolean flag=true;
		File[] files = dirFile.listFiles();  
		for (File filename : files) {  
			//delete file
			if (filename.isFile()){
			flag = deleteFile(filename.getAbsolutePath());
			if(!flag) break;
			}//delete sub directory  
			else 
				flag = deleteFileIncludeDirectory(filename.getAbsolutePath());
			if(!flag) break; 
			}
		if(!flag) return false;
		if (dirFile.delete()) return true;
		else return false;
		}
	//delete all files and directory from specific directory.
	//include the directory and sub directory
	public static boolean deleteFileIncludeDirectory(String path) {  
		File dirFile = new File(path);  
		if (!dirFile.exists() && !dirFile.isDirectory()) {  
			return false;
		}
		boolean flag=true;
		File[] files = dirFile.listFiles();  
		for (File filename : files) {  
			//delete files
			if (filename.isFile()){
			flag = deleteFile(filename.getAbsolutePath());
			if(!flag) break;
			}//delete directories
			else {
				flag = deleteFileIncludeDirectory(filename.getAbsolutePath());
			if(!flag) break;
			}
		}
		if(!flag) return false;
		if (dirFile.delete()) return true;
		else return false;
		}
	//delete all files from specific directory.
	//exclude the directory and sub directory
	public static boolean deleteFileExcludeDirectory(String path) { 
		File dirFile = new File(path);  
		if (!dirFile.exists() && !dirFile.isDirectory()) {  
			return false;
		}  
		boolean flag=true;
		File[] files = dirFile.listFiles();  
		for (File filename : files) {  
			//delete files  
			if (filename.isFile()){
			flag = deleteFile(filename.getAbsolutePath());
			if(!flag) break;
			}//delete directories
			else {
				flag = deleteFileExcludeDirectory(filename.getAbsolutePath());
			if(!flag) break;
			}
		}
		return flag;
		}
	
		//delete files
	public static boolean deleteFile(String path){
		boolean flag=true;
		File file=new File(path);
		if(file.isFile()&&file.exists()){
			file.delete();
			flag=true;
			}
		return flag;
	}
}
