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
}
